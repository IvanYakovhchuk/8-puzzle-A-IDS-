package com.algorithms;

import com.algorithms.puzzle.Board;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Board board;

    @ParameterizedTest
    @CsvFileSource(resources = "/arrays2d.csv")
    void shouldCountInversionsOnGeneratedBoard(String arrayData, int res) {
        int[][] array = getArray(arrayData);
        board = new Board(array);
        assertEquals(res, board.countInversions());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/arrays2d.csv")
    void shouldCheckIfPuzzleIsSolvable(String arrayData, int trash, boolean res) {
        int[][] array = getArray(arrayData);
        board = new Board(array);
        assertEquals(res, board.isSolvable());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/arrays2d.csv")
    void shouldMoveTileOnTheBoardCorrectly(String initial,
                                           int trash1, boolean trash2,
                                           String leftTileMoved,
                                           String rightTileMoved,
                                           String upTileMoved,
                                           String downTileMoved) {
        int[][] array = getArray(initial);
        board = new Board(array);
        Board changedBoard = board.moveLeftTile();
        assertEquals(new Board(getArray(leftTileMoved)), changedBoard);
        changedBoard = board.moveRightTile();
        assertEquals(new Board(getArray(rightTileMoved)), changedBoard);
        changedBoard = board.moveUpTile();
        assertEquals(new Board(getArray(upTileMoved)), changedBoard);
        changedBoard = board.moveDownTile();
        assertEquals(new Board(getArray(downTileMoved)), changedBoard);
    }

    private static int[][] getArray(String arrayData) {
        return Arrays.stream(arrayData.split(";"))
                .map(s -> Arrays.stream(s.split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray())
                .toArray(int[][]::new);
    }
}