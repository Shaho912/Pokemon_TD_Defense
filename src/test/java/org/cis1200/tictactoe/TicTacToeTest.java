package org.cis1200.tictactoe;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Below are some example tests for the TicTacToe game.
 */

public class TicTacToeTest {
    TicTacToe t;

    @BeforeEach
    public void setUp() {
        t = new TicTacToe();
    }

    @Test
    public void checkResetConditions() {
        t.reset();

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                assertEquals(0, t.getCell(col, row));
            }
        }

        assertTrue(t.getCurrentPlayer());
        assertEquals(0, t.checkWinner());
    }

    @Test
    public void turnsAlternateOnlyValid() {
        assertTrue(t.getCurrentPlayer()); // Player 1's turn
        assertTrue(t.playTurn(0, 0));

        assertFalse(t.getCurrentPlayer()); // Player 2's turn
        assertTrue(t.playTurn(0, 1));

        assertTrue(t.getCurrentPlayer());
        assertFalse(t.playTurn(0, 0)); // Invalid: square is already taken

        assertTrue(t.getCurrentPlayer());
        assertTrue(t.playTurn(1, 0));

        assertFalse(t.getCurrentPlayer());
        assertTrue(t.playTurn(2, 2));

        assertTrue(t.getCurrentPlayer());
        assertTrue(t.playTurn(2, 0)); // Player 1 wins across the first row

        assertFalse(t.playTurn(1, 1)); // Invalid: game is over
    }

    @Test
    public void checkWinConditionsWin() {
        assertEquals(0, t.checkWinner());

        t.playTurn(0, 0);
        t.playTurn(1, 0);
        t.playTurn(2, 0);

        assertEquals(0, t.checkWinner());

        t.playTurn(1, 1);
        t.playTurn(2, 2);
        t.playTurn(1, 2);

        assertEquals(2, t.checkWinner());
    }

    @Test
    public void checkWinConditionsDraw() {
        assertEquals(0, t.checkWinner());
        t.playTurn(1, 1);
        t.playTurn(1, 2);
        t.playTurn(2, 2);
        t.playTurn(0, 0);
        t.playTurn(0, 1);
        t.playTurn(2, 1);
        t.playTurn(1, 0);
        t.playTurn(0, 2);

        assertEquals(0, t.checkWinner());
        t.playTurn(2, 0);
        assertEquals(3, t.checkWinner());
    }

}
