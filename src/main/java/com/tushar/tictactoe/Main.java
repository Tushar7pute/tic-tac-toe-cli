package com.tushar.tictactoe;

public class Main {
    public static void main(String[] args) {
        System.out.println("Let's play TIC TAC TOE!");
        Game g = new Game("X", "O", 3);
        g.launch();
    }
}