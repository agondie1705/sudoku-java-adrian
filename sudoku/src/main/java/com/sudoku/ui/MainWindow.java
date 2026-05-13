package com.sudoku.ui;

import java.awt.event.KeyAdapter;
import com.sudoku.service.SudokuGenerator;
import com.sudoku.service.SudokuValidator;
import com.sudoku.service.GameManager;
import com.sudoku.model.Difficulty;

import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainWindow extends JFrame {

    private int[][] board;
    private SudokuGenerator generator;
    private SudokuValidator validator;
    private GameManager gameManager;
    private Difficulty difficulty;

    private JTextField[][] cells;
    private int[][] generatedBoard;

    // 🔥 PASO 9 — Label de errores
    private JLabel errorLabel;

    public MainWindow() {

        this.difficulty = Difficulty.MEDIUM;
        this.generator = new SudokuGenerator();
        this.validator = new SudokuValidator();
        this.gameManager = new GameManager(difficulty);

        this.board = generator.generateBoard();
        this.generatedBoard = board;

        initializeWindow();
        createBoard();
        createBottomPanel(); // 🔥 Añadido aquí
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

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(9, 9));

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

                Border border = BorderFactory.createMatteBorder(
                        top, left, bottom, right, Color.BLACK
                );

                cell.setBorder(border);
                cells[row][col] = cell;

                cell.setText(String.valueOf(generatedBoard[row][col]));

                if (generatedBoard[row][col] != 0) {
                    cell.setEditable(false);
                } else {
                    cell.setText("");
                }

                // Solo permite números del 1 al 9
                cell.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        char input = e.getKeyChar();
                        if (!Character.isDigit(input) || input == '0') {
                            e.consume();
                        }
                    }
                });

                // Listener de acción
                final int r = row;
                final int c = col;
                cell.addActionListener(e -> onCellInput(r, c));

                boardPanel.add(cell);
            }
        }

        add(boardPanel, BorderLayout.CENTER);
    }

    // 🔥 PASO 9 — Crear panel inferior con contador de errores
    private void createBottomPanel() {

        errorLabel = new JLabel("Errors: 0 / " + gameManager.getMaxErrors());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(errorLabel);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // 🔥 Método que ya integraste antes, ahora funcional con errorLabel
    private void onCellInput(int row, int col) {

        if (gameManager.isGameOver()) {
            JOptionPane.showMessageDialog(this, "GAME OVER");
            return;
        }

        String text = cells[row][col].getText().trim();

        if (text.isEmpty()) {
            return;
        }

        if (!text.matches("[1-9]")) {
            cells[row][col].setText("");
            return;
        }

        int number = Integer.parseInt(text);

        if (validator.isValidMove(board, row, col, number)) {
            board[row][col] = number;
            checkVictory();
        } else {
            cells[row][col].setText("");
            gameManager.addError();
            updateErrorLabel();

            if (gameManager.isGameOver()) {
                JOptionPane.showMessageDialog(this, "GAME OVER");
                disableBoard();
            }
        }
    }

    // 🔥 PASO 9 — Actualizar contador de errores
    private void updateErrorLabel() {
        errorLabel.setText("Errors: " + gameManager.getErrors() + " / " + gameManager.getMaxErrors());
    }

    // 🔥 PASO 10 — Desactivar tablero
    private void disableBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col].setEditable(false);
            }
        }
    }

 // PASO 12 — Comprobar si el tablero está completo
    private boolean isBoardComplete() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // PASO 13 — Comprobar victoria
    private void checkVictory() {
        if (isBoardComplete()) {
            gameManager.setVictory(true);
            JOptionPane.showMessageDialog(this, "HAS GANADO");
            disableBoard();
        }
    }

}
