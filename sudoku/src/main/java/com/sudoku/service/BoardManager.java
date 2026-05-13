package com.sudoku.service;
/**
 * Utility class for board operations such as copying
 * and checking if the board is complete.
 */
public class BoardManager {
	/**
     * Checks if a Sudoku board has no empty cells.
     *
     * @param board 9x9 matrix
     * @return true if complete
     */
    public boolean isComplete(int[][] board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == 0) return false;
            }
        }
        return true;
    }
    /**
     * Creates a deep copy of a 9x9 Sudoku board.
     *
     * @param original board to copy
     * @return new 9x9 matrix with the same values
     */
    public int[][] copy(int[][] original) {
        int[][] copy = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, 9);
        }
        return copy;
    }
}
