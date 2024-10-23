package com.algorithms.puzzle;

import java.util.*;

public class Board {

    private int[][] board = new int[3][3];
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";

    public Board() {}
    public Board(int[][] board) {
        for (int i = 0; i < 3; i++) {
            System.arraycopy(board[i], 0, this.board[i], 0, 3);
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public void generateBoard() {
        List<Integer> list = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8));
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Collections.shuffle(list);
                board[row][col] = list.removeFirst();
            }
        }
    }
    public void inputBoard(Scanner scanner) {
        boolean isValidInput = false;
        while (!isValidInput) {
            System.out.println("""
                    Print your board like the following:

                    Matrix:
                        1  2  3
                        4  5  6
                        7  8  9

                    Input: 1 2 3 4 5 6 7 8 9""");
            List<Integer> list;
            try {
                list = new ArrayList<>(
                        Arrays.stream(scanner.nextLine()
                                        .split(" "))
                                .toList()
                                .stream().map(Integer::parseInt)
                                .toList()
                );
            } catch (Exception e) {
                System.out.println("Invalid input! Please try again.");
                continue;
            }
            List<Integer> correctList = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8));
            if (list.retainAll(correctList)) {
                System.out.println("8-puzzle game can only contain puzzles from 0 to 8 where 0 is empty tile! Please create another puzzle!");
                continue;
            }
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    board[row][col] = list.removeFirst();
                }
            }
            if (!this.isSolvable()) {
                System.out.println("The puzzle isn't solvable! Please create another puzzle.");
                continue;
            }
            isValidInput = true;
        }
    }

    public Board moveLeftTile() {
        int[][] clonedBoard = new int[this.board.length][];
        for (int i = 0; i < this.board.length; i++) {
            clonedBoard[i] = this.board[i].clone(); // Клонуємо кожен рядок
        }
        Board newBoard = new Board(clonedBoard);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (newBoard.getBoard()[row][col] == 0) {
                    if (col == 0) {
                        return newBoard;
                    }
                    changeTiles(newBoard, row, col, row, col - 1);
                    return newBoard;
                }
            }
        }
        return newBoard;
    }
    public Board moveRightTile() {
        int[][] clonedBoard = new int[this.board.length][];
        for (int i = 0; i < this.board.length; i++) {
            clonedBoard[i] = this.board[i].clone(); // Клонуємо кожен рядок
        }
        Board newBoard = new Board(clonedBoard);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (newBoard.getBoard()[row][col] == 0) {
                    if (col == newBoard.getBoard().length - 1) {
                        return newBoard;
                    }
                    changeTiles(newBoard, row, col, row, col + 1);
                    return newBoard;
                }
            }
        }
        return newBoard;
    }
    public Board moveUpTile() {
        int[][] clonedBoard = new int[this.board.length][];
        for (int i = 0; i < this.board.length; i++) {
            clonedBoard[i] = this.board[i].clone(); // Клонуємо кожен рядок
        }
        Board newBoard = new Board(clonedBoard);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (newBoard.getBoard()[row][col] == 0) {
                    if (row == 0) {
                        return newBoard;
                    }
                    changeTiles(newBoard, row, col, row - 1, col);
                    return newBoard;
                }
            }
        }
        return newBoard;
    }
    public Board moveDownTile() {
        int[][] clonedBoard = new int[this.board.length][];
        for (int i = 0; i < this.board.length; i++) {
            clonedBoard[i] = this.board[i].clone(); // Клонуємо кожен рядок
        }
        Board newBoard = new Board(clonedBoard);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (newBoard.getBoard()[row][col] == 0) {
                    if (row == newBoard.getBoard().length - 1) {
                        return newBoard;
                    }
                    changeTiles(newBoard, row, col, row + 1, col);
                    return newBoard;
                }
            }
        }
        return newBoard;
    }

    public void changeTiles(Board newBoard, int row1, int col1, int row2, int col2) {
        int temp = newBoard.getBoard()[row1][col1];
        newBoard.getBoard()[row1][col1] = newBoard.getBoard()[row2][col2];
        newBoard.getBoard()[row2][col2] = temp;
    }

    public int countInversions() {
        int[] array = transformTo1DArray();
        int inversions = 0;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] != 0 && array[j] != 0 && array[j] < array[i]) {
                    inversions++;
                }
            }
        }
        return inversions;
    }

    public boolean isSolvable() {
        return countInversions() % 2 == 0;
    }

    public int[] transformTo1DArray() {
        int[] oneDimensionalArray = new int[9];
        int currentPosition = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                oneDimensionalArray[currentPosition] = board[row][col];
                currentPosition++;
            }
        }
        return oneDimensionalArray;
    }

    public void printBoard(int[] lastZeroPosition, int[] nextMovePosition) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == nextMovePosition[0] && j == nextMovePosition[1]) {
                    System.out.print(RED + board[i][j] + "  " + RESET);
                }
                else if (board[i][j] == 0) {
                    System.out.print("   ");
                }
                else if (i == lastZeroPosition[0] && j == lastZeroPosition[1]) {
                    System.out.print(GREEN + board[i][j] + "  " + RESET);
                }
                else {
                    System.out.printf("%d  ", board[i][j]);
                }
            }
            System.out.println();
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Board) {
            return Arrays.deepEquals(this.board, ((Board) obj).getBoard());
        }
        return false;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Board clonedBoard = (Board) super.clone();
        clonedBoard.board = board.clone();
        return clonedBoard;
    }
}
