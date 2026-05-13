package com.sudoku.service;
/**
 * Solves a Sudoku board using recursive backtracking.
 */
public class SudokuSolver {
	/**
     * Attempts to solve the given Sudoku board.
     *
     * @param board 9x9 matrix with zeros as empty cells
     * @return true if the board is solved
     */
    public boolean solve(int[][] board) {

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {

                if (board[row][col] == 0) {

                    for (int number = 1; number <= 9; number++) {

                        if (isValid(board, row, col, number)) {

                            board[row][col] = number;

                            if (solve(board)) {
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

    private boolean isValid(int[][] board, int row, int col, int number) {

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
}
