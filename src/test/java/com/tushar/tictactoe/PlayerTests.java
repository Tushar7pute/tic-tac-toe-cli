package com.tushar.tictactoe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTests {

    @Test
    void createPlayerTest(){
        Player player1 = new Player.Builder().setSymbol("X").build();
        assertEquals("X", player1.getSymbol());
        Player player2 = new Player.Builder().setSymbol("O").build();
        assertEquals("O", player2.getSymbol());
    }
}
