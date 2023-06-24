package com.tushar.tictactoe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

@AllArgsConstructor
@Getter
@Setter
public class Game {

    private Player player1;
    private Player player2;

    private String[][] board;
    private int boardSize;
    private int totalTiles;
    private boolean boardFilled = false;
    private boolean winnerDeclared = false;
    private int filledTiles = 0;

    private Player currentTurn;
    private Player nextTurn;



    public Game(String player1Symbol, String player2Symbol, int boardSize) {
        this.boardSize = boardSize;
        this.totalTiles = boardSize*boardSize;
        board = new String[boardSize][boardSize];
        player1 = new Player.Builder().setSymbol(player1Symbol).build();
        player2 = new Player.Builder().setSymbol(player2Symbol).build();
        currentTurn = player1;
    }

    public void launch(){
        System.out.println("Let the Game begin!!!");
        while(!isBoardFilled() && !isWinnerDeclared()) {
            Scanner scanner = new Scanner(System.in);
            Player player = getCurrentTurn();
            System.out.println("Hey " + player.getName() + "(" + player.getSymbol() + ")" +
                    " please enter the tile number: ");
            int playerMove = scanner.nextInt();

            try{
                playTurn(playerMove);
            } catch (IllegalStateException e) {
                System.out.println( e.getMessage() + '\n' + playerMove + " is already occupied.");
                continue;
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            filledTiles++;
            printBoard();
            if(checkVictory(playerMove) == null) {
                switchTurn();
            }
            if(this.filledTiles == 9){
                this.boardFilled=true;
                System.out.println("Game Over!!!");
            }
        }
    }

    public String getSymbolForTileFromBoard(int tileNumber) {

        int row = (tileNumber - 1) / boardSize;
        int column = (tileNumber - 1) % boardSize;

        if(tileNumber < 1 || tileNumber > totalTiles) {
            return null;
        } else {
            return board[row][column];
        }
    }

    /*
    * This method enables player to play their turn
    * @param tileNumber - the box/tile in which player wants to mark their symbol
    * */
    public int playTurn(int tileNumber) {

        int row = (tileNumber - 1) / boardSize;
        int column = (tileNumber - 1) % boardSize;

        if(tileNumber < 1 || tileNumber > totalTiles) {
            throw new IllegalArgumentException("Enter correct tile number!");
        }

        if(board[row][column] != null) {
            throw new IllegalStateException("This tile is already marked.!");
        }

        // marking the player symbol on the board
        board[row][column] = currentTurn.getSymbol();
        System.out.println(getCurrentTurn().getName() + " marked tile number " + tileNumber);

        return tileNumber;
    }



    /*
    * Check the current board state and tell the winner
    *
    * @return player1 or player2 if anyone has won, else return null
    * */
    public Player checkVictory(int tileNumber) {

        Player currentPlayer = this.getCurrentTurn();

        if (tileNumber < 1 || tileNumber > totalTiles) {
            throw new IllegalArgumentException("Winner cannot be checked for invalid tile number!");
        }

        int row = (tileNumber - 1) / boardSize;
        int column = (tileNumber - 1) % boardSize;
        String symbol = this.getCurrentTurn().getSymbol();

        /*
        * Checking if the Row, Column or Diagonals are occupied by the current player's symbol
        * */

        int symbolCount = 0;

        /* check if the row is occupied by this player */
        for (int tempColumn = 0; tempColumn < boardSize; tempColumn++) {
            if (doesCellContainThisSymbol(row, tempColumn, symbol)) {
                symbolCount++;
            } else {
                symbolCount = 0;
                break;
            }
        }
        if (hasWon(symbolCount)) {
            return currentPlayer;
        }


        /* check if the Column is occupied by this player */
        for (int tempRow = 0; tempRow < boardSize; tempRow++) {
            if (doesCellContainThisSymbol(tempRow, column, symbol)) {
                symbolCount++;
            } else {
                symbolCount = 0;
                break;
            }
        }
        if (hasWon(symbolCount)) {
            return currentPlayer;
        }

        /* check if the Diagonals are occupied by this player */
        if (row == column || (row + column == 2)) {
            if (row == column) {
                for (int tempRow = 0; tempRow < 3; tempRow++) {

                    if (doesCellContainThisSymbol(tempRow, tempRow, symbol)) {
                        symbolCount++;
                    } else {
                        symbolCount = 0;
                        break;
                    }
                }
                if (hasWon(symbolCount)) {
                    return currentPlayer;
                }
            }
            if (row + column == 2) {
                for (int tempRow = 0; tempRow < this.boardSize; tempRow++) {
                    if (doesCellContainThisSymbol(tempRow, boardSize - 1 - tempRow, symbol)) {
                        symbolCount++;
                    } else {
                        symbolCount = 0;
                        break;
                    }
                }
                if (hasWon(symbolCount)) {
                    return currentPlayer;
                }
            }
        }
        return null;
    }


    public Player switchTurn (){
        // switch turn
        currentTurn = (currentTurn == player1 ? player2 : player1);
        return currentTurn;
    }

    public void printBoard() {
        for (int row = 0; row < this.boardSize; row++) {
            for (int column = 0; column < this.boardSize; column++) {
                String message = board[row][column] == null ? "- " : board[row][column] + " ";
                System.out.print(message);
            }
            System.out.println(" ");
        }
    }

    boolean isCellEmpty(int row, int column) {
        return board[row][column] == null;
    }

    boolean doesCellContainThisSymbol(int row, int column, String symbol) {
        if (board[row][column] == null) {
            return false;
        }
        return board[row][column].equals(symbol);
    }

    boolean hasWon(int symbolCount) {
        if (symbolCount == this.boardSize) {
            this.setWinnerDeclared(true);
            System.out.println(this.getCurrentTurn().getName() + " has won the game!!!");
            return true;
        }
        return false;
    }
}
