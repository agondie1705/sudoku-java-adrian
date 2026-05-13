package com.sudoku.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Generates complete Sudoku boards using backtracking.
 */
public class SudokuGenerator {

    private int[][] board;

    public SudokuGenerator() {
        board = new int[9][9];
    }
    /**
     * Generates a full valid Sudoku board.
     *
     * @return 9x9 solved board
     */
    public int[][] generateBoard() {
        fillBoard();
        removeCells(40);
        return board;
    }

    private boolean fillBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    List<Integer> numbers = generateRandomNumbers();

                    for (int number : numbers) {
                        if (isValid(row, col, number)) {
                            board[row][col] = number;

                            if (fillBoard()) {
                                return true;
                            }

                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int row, int col, int number) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == number || board[i][col] == number) {
                return false;
            }
        }

        int boxRow = row - row % 3;
        int boxCol = col - col % 3;

        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (board[i][j] == number) {
                    return false;
                }
            }
        }

        return true;
    }

    private List<Integer> generateRandomNumbers() {
        List<Integer> numbers = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);
        return numbers;
    }

    public void removeCells(int cellsToRemove) {
        while (cellsToRemove > 0) {
            int row = (int) (Math.random() * 9);
            int col = (int) (Math.random() * 9);

            if (board[row][col] != 0) {
                board[row][col] = 0;
                cellsToRemove--;
            }
        }
    }
}
