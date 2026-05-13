package com.sudoku.utils;

public class FormatUtils {

    public static String formatTime(int seconds) {
        return String.format("%02d:%02d", seconds / 60, seconds % 60);
    }
}
