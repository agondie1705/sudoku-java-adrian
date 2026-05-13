package com.sudoku.model;

public class SudokuBoard {

    private int[][] board;

    public SudokuBoard() {

        board = new int[9][9];

    }

    public int[][] getBoard() {

        return board;

    }

    public void setValue(int row, int col, int value) {

        board[row][col] = value;

    }

    public int getValue(int row, int col) {

        return board[row][col];

    }
}