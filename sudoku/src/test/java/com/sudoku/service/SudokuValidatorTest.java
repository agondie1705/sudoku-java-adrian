package com.sudoku.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuValidatorTest {

    @Test
    void testInvalidRow() {
        SudokuValidator validator = new SudokuValidator();

        int[][] board = new int[9][9];
        board[0][0] = 5;
        board[0][3] = 5;

        assertFalse(validator.isValidMove(board, 0, 3, 5));
    }

    @Test
    void testInvalidColumn() {
        SudokuValidator validator = new SudokuValidator();

        int[][] board = new int[9][9];
        board[0][0] = 7;
        board[5][0] = 7;

        assertFalse(validator.isValidMove(board, 5, 0, 7));
    }

    @Test
    void testInvalidBox() {
        SudokuValidator validator = new SudokuValidator();

        int[][] board = new int[9][9];
        board[1][1] = 9;
        board[2][2] = 9;

        assertFalse(validator.isValidMove(board, 2, 2, 9));
    }

    @Test
    void testValidMove() {
        SudokuValidator validator = new SudokuValidator();

        int[][] board = new int[9][9];
        board[0][0] = 1;

        assertTrue(validator.isValidMove(board, 0, 1, 2));
    }
}
