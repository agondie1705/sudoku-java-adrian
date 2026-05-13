package com.sudoku.service;

import com.sudoku.model.Difficulty;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameManagerTest {

    @Test
    void testErrorLimit() {
        GameManager gm = new GameManager(Difficulty.MEDIUM);

        gm.addError();
        gm.addError();
        gm.addError();

        assertTrue(gm.isGameOver());
    }

    @Test
    void testVictory() {
        GameManager gm = new GameManager(Difficulty.EASY);

        gm.setVictory(true);

        assertTrue(gm.isVictory());
    }
}
