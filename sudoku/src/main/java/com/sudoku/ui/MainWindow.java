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

    // 🔥 Atributos añadidos arriba de la clase
    private int[][] board;
    private SudokuGenerator generator;
    private SudokuValidator validator;
    private GameManager gameManager;
    private Difficulty difficulty;

    private JTextField[][] cells;
    private int[][] generatedBoard;

    public MainWindow() {

        // 🔥 Inicialización obligatoria según tu instrucción
        this.difficulty = Difficulty.MEDIUM; // por defecto
        this.generator = new SudokuGenerator();
        this.validator = new SudokuValidator();
        this.gameManager = new GameManager(difficulty);

        // Generar tablero completo
        this.board = generator.generateBoard();

        // Mantengo tu variable original para no romper nada
        this.generatedBoard = board;

        initializeWindow();
        createBoard();
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

                boardPanel.add(cell);
            }
        }

        add(boardPanel, BorderLayout.CENTER);
    }
}
