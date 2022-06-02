package app;

import java.util.Arrays;

// simple unit test class without using any testing framework (JUnit, etc)
// Note: to test, this class must be specified as the entry point/main file
public class UnitTest {
    public static int[][] initTest(String testDesc, int[][] startBoard) {
        System.out.println(testDesc);
        System.out.println("Starting board...");
        App.renderBoard(startBoard);
        return startBoard;
    }

    public static int[][] nextBoardState(int[][] startBoard) {
        int[][] nextBoard = App.generateNextBoardState(startBoard);
        System.out.println("Actual Result Board...");
        App.renderBoard(nextBoard);
        return nextBoard;
    }

    public static int[][] expectedBoardState(int[][] expectedResultBoard) {
        System.out.println("Expected Result Board...");
        App.renderBoard(expectedResultBoard);
        return expectedResultBoard;
    }

    public static void printTestResults(int[][] resultBoard, int[][] expectedResultBoard) {
        if (Arrays.deepEquals(resultBoard, expectedResultBoard)) {
            System.out.println("Test passed.");
        } else {
            System.out.println("Test failed.");
        }
    }

    public static void deadCellNoAliveNeighbors() {
        int[][] startBoard = initTest(
            "Test: A cell should be dead if all of its neighbors are dead", 
            App.generateBoardOfDeadCells(3, 3)
        );
        printTestResults(
            nextBoardState(startBoard), 
            expectedBoardState(startBoard) // board shouldn't change from start state
        );
    }

    public static void deadCellThreeAliveNeighbors() {
        printTestResults(
            nextBoardState(
                initTest(
                    "Test: A dead cell should come to life if it has exactly 3 alive neighbors",
                    new int[][] {
                        {0, 0, 1},
                        {0, 1, 1},
                        {0, 0, 0}
                    }
                )
            ),
            expectedBoardState(
                new int[][] {
                    {0, 1, 1}, 
                    {0, 1, 1},
                    {0, 0, 0}
                }
            )
        );
    }

    public static void main(String[] args)
    {
        deadCellNoAliveNeighbors();
        deadCellThreeAliveNeighbors();
    }
}