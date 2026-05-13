package com.sudoku.service;

import com.sudoku.model.Difficulty;

public class GameManager {

    private int errors;
    private final int maxErrors = 3;
    private boolean gameOver;
    private boolean victory;
    private Difficulty difficulty;

    public GameManager(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.errors = 0;
        this.gameOver = false;
        this.victory = false;
    }

    public void addError() {
        errors++;
        if (errors >= maxErrors) {
            gameOver = true;
        }
    }

    public int getErrors() {
        return errors;
    }

    public int getMaxErrors() {
        return maxErrors;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isVictory() {
        return victory;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void reset() {
        this.errors = 0;
        this.gameOver = false;
        this.victory = false;
    }
}
