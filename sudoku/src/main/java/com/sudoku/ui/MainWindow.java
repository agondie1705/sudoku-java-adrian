package com.sudoku.ui;

import java.awt.event.KeyAdapter;
import com.sudoku.service.SudokuGenerator;
import com.sudoku.service.SudokuSolver;
import com.sudoku.service.SudokuValidator;
import com.sudoku.utils.FormatUtils;
import com.sudoku.service.GameManager;
import com.sudoku.model.Difficulty;
import com.sudoku.service.BoardManager;

import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Random;

/**
 * Main window of the Sudoku game.
 * Handles UI, user input, timers and game flow.
 */
public class MainWindow extends JFrame {

    private int[][] board;
    private int[][] solutionBoard;
    private int[][] visibleBoard;

    private boolean[][] fixedCells;

    private SudokuGenerator generator;
    private SudokuValidator validator;
    private GameManager gameManager;
    private Difficulty difficulty;

    private BoardManager boardManager = new BoardManager();

    private JTextField[][] cells;

    private JLabel errorLabel;
    private JLabel timeLabel;

    private Timer timer;
    private int elapsedSeconds;

    /**
     * Initializes the Sudoku window, generates a new board
     * and prepares all UI components.
     */
    public MainWindow() {

        this.difficulty = Difficulty.MEDIUM;
        this.generator = new SudokuGenerator();
        this.validator = new SudokuValidator();
        this.gameManager = new GameManager(difficulty);

        // ✅ CAMBIO 1: generar solución real usando el solver
        int[][] tempBoard = generator.generateBoard();
        SudokuSolver solver = new SudokuSolver();
        solver.solve(tempBoard);                 // resuelve el tablero
        solutionBoard = boardManager.copy(tempBoard); // solución completa

        visibleBoard = boardManager.copy(solutionBoard);
        applyDifficultyMask(visibleBoard, difficulty);

        fixedCells = new boolean[9][9];
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                fixedCells[r][c] = visibleBoard[r][c] != 0;

        board = visibleBoard;

        initializeWindow();
        createBoard();
        createBottomPanel();
        setVisible(true);
    }

    private void initializeWindow() {
        setTitle("Sudoku");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void createBoard() {

        JPanel boardPanel = new JPanel(new GridLayout(9, 9));
        cells = new JTextField[9][9];

        Font cellFont = new Font("Arial", Font.BOLD, 20);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                JTextField cell = new JTextField();
                cell.setHorizontalAlignment(JTextField.CENTER);
                cell.setFont(cellFont);

                int top = (row % 3 == 0) ? 3 : 1;
                int left = (col % 3 == 0) ? 3 : 1;
                int bottom = (row == 8) ? 3 : 1;
                int right = (col == 8) ? 3 : 1;

                Border border = BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK);
                cell.setBorder(border);

                cells[row][col] = cell;

                if (fixedCells[row][col]) {
                    cell.setText(String.valueOf(visibleBoard[row][col]));
                    cell.setEditable(false);
                } else {
                    cell.setText("");
                }

                cell.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        char input = e.getKeyChar();
                        if (!Character.isDigit(input) || input == '0')
                            e.consume();
                    }
                });

                final int r = row;
                final int c = col;
                cell.addActionListener(e -> handleCellInput(r, c));

                boardPanel.add(cell);
            }
        }

        add(boardPanel, BorderLayout.CENTER);
    }

    private void createBottomPanel() {

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        errorLabel = new JLabel("Errors: 0 / " + gameManager.getMaxErrors());
        bottomPanel.add(errorLabel);

        elapsedSeconds = 0;
        timeLabel = new JLabel("Time: 00:00");

        timer = new Timer(1000, e -> {
            elapsedSeconds++;
            updateTimeLabel();
        });
        timer.start();

        bottomPanel.add(timeLabel);

        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(e -> onSolveClicked());
        bottomPanel.add(solveButton);

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> resetGame());
        bottomPanel.add(newGameButton);

        String[] levels = {"EASY", "MEDIUM", "HARD"};
        JComboBox<String> difficultyBox = new JComboBox<>(levels);
        difficultyBox.setSelectedItem("MEDIUM");

        difficultyBox.addActionListener(e -> {
            try {
                difficulty = Difficulty.valueOf(difficultyBox.getSelectedItem().toString());
            } catch (Exception ex) {
                difficulty = Difficulty.MEDIUM;
            }
            resetGame();
        });

        bottomPanel.add(difficultyBox);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Handles the input of a cell and validates the move.
     */
    private void handleCellInput(int row, int col) {

        if (gameManager.isGameOver()) {
            JOptionPane.showMessageDialog(this, "GAME OVER");
            return;
        }

        String text = cells[row][col].getText().trim();
        if (text.isEmpty()) return;

        // Asegurar que solo hay un dígito válido
        if (!text.matches("[1-9]")) {
            cells[row][col].setText("");
            return;
        }

        int number = Character.getNumericValue(text.charAt(0));

        System.out.println("Fila " + row + ", Col " + col +
                " | Introducido: " + number +
                " | Solución: " + solutionBoard[row][col]);

        if (number == solutionBoard[row][col]) {
            board[row][col] = number;
            cells[row][col].setEditable(false);
            cells[row][col].setForeground(Color.DARK_GRAY);
            updateCompletedNumbers();
            checkVictory();
        } else {
            cells[row][col].setText("");
            gameManager.addError();
            refreshErrorDisplay();
            if (gameManager.isGameOver()) {
                JOptionPane.showMessageDialog(this, "GAME OVER");
                disableBoard();
            }
        }
    }


    private void refreshErrorDisplay() {
        errorLabel.setText("Errors: " + gameManager.getErrors() + " / " + gameManager.getMaxErrors());
    }

    private void disableBoard() {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                cells[r][c].setEditable(false);

        if (timer != null)
            timer.stop();
    }

    private void checkVictory() {
        if (boardManager.isComplete(board)) {
            gameManager.setVictory(true);
            JOptionPane.showMessageDialog(this, "HAS GANADO");
            disableBoard();
        }
    }

    private void updateTimeLabel() {
        int minutes = elapsedSeconds / 60;
        int seconds = elapsedSeconds % 60;
        timeLabel.setText("Time: " + FormatUtils.formatTime(elapsedSeconds));

    }

    private void onSolveClicked() {

        SudokuSolver solver = new SudokuSolver();
        int[][] copy = boardManager.copy(board);

        if (!solver.solve(copy)) {
            JOptionPane.showMessageDialog(this, "No se puede resolver este tablero.");
            return;
        }

        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                cells[r][c].setText(String.valueOf(copy[r][c]));
                cells[r][c].setEditable(false);
            }

        if (timer != null)
            timer.stop();

        JOptionPane.showMessageDialog(this, "Sudoku resuelto.");
    }

    private void applyDifficultyMask(int[][] board, Difficulty difficulty) {

        int cellsToHide = 0;

        switch (difficulty) {
            case EASY:
                cellsToHide = 30;
                break;
            case MEDIUM:
                cellsToHide = 40;
                break;
            case HARD:
                cellsToHide = 50;
                break;
            default:
                cellsToHide = 50;
                break;
        }

        Random r = new Random();
        int removed = 0;
        int attempts = 0;

        while (removed < cellsToHide && attempts < 200) {
            int row = r.nextInt(9);
            int col = r.nextInt(9);
            attempts++;

            if (board[row][col] != 0) {
                board[row][col] = 0;
                removed++;
            }
        }
    }

    private void resetGame() {

        // ✅ CAMBIO 2: misma lógica que en el constructor
        int[][] tempBoard = generator.generateBoard();
        SudokuSolver solver = new SudokuSolver();
        solver.solve(tempBoard);
        solutionBoard = boardManager.copy(tempBoard);

        visibleBoard = boardManager.copy(solutionBoard);
        applyDifficultyMask(visibleBoard, difficulty);

        fixedCells = new boolean[9][9];
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                fixedCells[r][c] = visibleBoard[r][c] != 0;

        board = visibleBoard;

        loadBoardToUI();

        gameManager.reset();
        elapsedSeconds = 0;
        timer.restart();
    }

    private void loadBoardToUI() {

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {

                if (fixedCells[r][c]) {
                    cells[r][c].setText(String.valueOf(visibleBoard[r][c]));
                    cells[r][c].setEditable(false);
                } else {
                    cells[r][c].setText("");
                    cells[r][c].setEditable(true);
                }
            }
        }
    }

    private void updateCompletedNumbers() {
        // Si no usas el panel de números, deja este método vacío
        // para que no dé error al compilar.
    }
}
