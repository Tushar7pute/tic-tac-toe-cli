package com.tushar.tictactoe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {

    @Test
    void gameCreationTest(){
        Game g = new Game("X", "O", 3);
        assertEquals("X", g.getPlayer1().getSymbol());
        assertEquals("O", g.getPlayer2().getSymbol());

        for (int i=1; i<=9; i++){
            assertNull(g.getSymbolForTileFromBoard(i));
        }
        assertEquals(g.getPlayer1(), g.getCurrentTurn());
    }

    @Test
    void playingTurnTest(){
        Game g = new Game("X", "O", 3);

        assertEquals(g.getPlayer1(), g.getCurrentTurn());

        g.playTurn(1);
        assertEquals(g.getSymbolForTileFromBoard(1), "X");

        g.switchTurn();
        assertEquals(g.getPlayer2(), g.getCurrentTurn());


        //Check that already marked boxes are not allowed to be marked
        assertThrowsExactly(IllegalStateException.class, () -> g.playTurn(1));

    }

    //Check whether box number is between 1 and 9.
    @Test
    void invalidTileNumberTest(){
        Game g = new Game("X", "O", 3);
        assertThrowsExactly(IllegalArgumentException.class, () -> g.playTurn(10));
    }

    @Test
    void checkVictoryTest(){
        Game g = new Game("X", "O", 3);
        g.printBoard();
        //assertNull(g.checkVictory(1));

        int tileNumber1 = g.playTurn(1);
        g.printBoard();
        assertNull(g.checkVictory(tileNumber1));
        g.switchTurn();

        int tileNumber4 = g.playTurn(4);
        g.printBoard();
        assertNull(g.checkVictory(tileNumber4));
        g.switchTurn();

        int tileNumber2 = g.playTurn(2);
        g.printBoard();
        assertNull(g.checkVictory(tileNumber2));
        g.switchTurn();

        int tileNumber7 = g.playTurn(7);
        g.printBoard();
        assertNull(g.checkVictory(tileNumber7));
        g.switchTurn();

        int tileNumber3 = g.playTurn(3);
        g.printBoard();
        assertEquals(g.getPlayer1(), g.checkVictory(tileNumber3));
        g.switchTurn();
    }


}
