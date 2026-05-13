package com.sudoku.service;

public class SudokuValidator {

    public boolean isValidMove(int[][] board, int row, int col, int number) {

        return isRowValid(board, row, number)
                && isColumnValid(board, col, number)
                && isBoxValid(board, row, col, number);

    }

    private boolean isRowValid(int[][] board, int row, int number) {

        for (int col = 0; col < 9; col++) {

            if (board[row][col] == number) {

                return false;

            }
        }

        return true;
    }

    private boolean isColumnValid(int[][] board, int col, int number) {

        for (int row = 0; row < 9; row++) {

            if (board[row][col] == number) {

                return false;

            }
        }

        return true;
    }

    private boolean isBoxValid(int[][] board, int row, int col, int number) {

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
}