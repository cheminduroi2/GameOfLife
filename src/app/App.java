package app;

import java.util.Arrays;
import java.lang.Math;
import java.lang.Thread;
import java.lang.InterruptedException;;

//Note: needs to be specified as entry point/main file when being executed
public class App {
    // a cell can either be alive or dead
    static final int ALIVE_STATE = 1, DEAD_STATE = 0;

    // returns 2d array of all dead cells
    public static int[][] generateBoardOfDeadCells(int width, int height) {
        return new int[width][height];
    }

    // returns 2d array of cells with randomized states (alive or dead)
    public static int[][] generateGameBoard(int width, int height) {
        int[][] gameBoard = new int[width][height];
        for (int[] row: gameBoard)
            Arrays.fill(row, Math.random() > 0.85 ? ALIVE_STATE : DEAD_STATE);
        return gameBoard;
    }

    // calculates appropriate value a cell should have in the next state of the board,
    // based on the rules of the Game of Life
    // see rules at https://robertheaton.com/2018/07/20/project-2-game-of-life/
    public static int updatedCellValue(int x, int y, int[][] board) {
        int aliveNeighbors = 0;
        // check cell's neighbors
        for (int i = x - 1; i < (x + 1) + 1; i++) {
            // don't go off the edge of the board
            if (i < 0 || i >= board.length) {
                continue;
            }
            
            for (int j = y - 1; j < (y + 1) + 1; j++) {
                // don't go off the edge of the board
                if (j < 0 || j >= board[0].length) {
                    continue;
                }
                // don't count current cell as a neighbor of itself
                if (i == x && j == y) {
                    continue;
                }

                if (board[i][j] == ALIVE_STATE) {
                    aliveNeighbors++;
                }
            }

        }

        if (board[x][y] == ALIVE_STATE) {
            if (aliveNeighbors <= 1) {
                return DEAD_STATE;
            } else if (aliveNeighbors <= 3) {
                return ALIVE_STATE;
            } else {
                return DEAD_STATE;
            }
        } else {
            if (aliveNeighbors == 3) {
                return ALIVE_STATE;
            } else {
                return DEAD_STATE;
            }
        }
    }

    // on each call, checks all cells in board to update their values
    public static int[][] generateNextBoardState(int[][] board) {
        int[][] calibratedBoard = generateBoardOfDeadCells(board.length, board[0].length);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                calibratedBoard[i][j] = updatedCellValue(i, j, board);
            }
        }
        return calibratedBoard;
    }

    // returns what should be displayed on the board for a particular cell,
    // based on if it is alive or not
    public static String getCellCharSequenceForBoard(int val) {
        return new String(new char[2]).replace("\0", val == ALIVE_STATE ? "⚪" : " ");
    }

    // displays board in console
    public static void renderBoard(int[][] gameBoard) {
        for (int i = 0; i < gameBoard[0].length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                System.out.print(getCellCharSequenceForBoard(gameBoard[j][i]));
            }
            System.out.println();
        }
    }

    public static void startSimulation(int[][] gameBoard) {
        int[][] boardTemp = gameBoard;
        // programs runs indefinitely, simulation should be stopped manually
        while (true) {
            App.renderBoard(boardTemp);
            boardTemp = generateNextBoardState(boardTemp);
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                System.out.print("Error: " + e.getMessage());
                System.exit(-1);
            }
        }
    }

    public static void main(String[] args) {
        int[][] gameBoard = generateGameBoard(100, 50);
        startSimulation(gameBoard);
    }
}