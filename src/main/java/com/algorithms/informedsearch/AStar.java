package com.algorithms.informedsearch;

import com.algorithms.puzzle.Board;
import com.algorithms.puzzle.State;

import java.util.*;

public class AStar {
    private static final PriorityQueue<State> uncheckedStates = new PriorityQueue<>(Comparator.comparing(State::getHeuristicValue));
    private static final Set<Board> checkedStates = new HashSet<>();

    public static State search(Board initialBoard) {
        State initialState = new State(initialBoard, null, new ArrayList<>());
        initialState.setHeuristicValue(initialState.getDepth() + manhattanDistance(initialState.getCurrentBoard().getBoard()));
        uncheckedStates.add(initialState);
        State currentState = null;
        while (!uncheckedStates.isEmpty()) {
            currentState = uncheckedStates.poll();
            if (manhattanDistance(currentState.getCurrentBoard().getBoard()) == 0) {
                break;
            }
            if (checkedStates.contains(currentState.getCurrentBoard())) {
                continue;
            }
            currentState.generateChildren();
            currentState.getChildren().forEach(s -> {
                s.setHeuristicValue(s.getDepth() + manhattanDistance(s.getCurrentBoard().getBoard()));
                if (!checkedStates.contains(s.getCurrentBoard())) {
                    uncheckedStates.add(s);
                }
            });
            checkedStates.add(currentState.getCurrentBoard());
        }
        checkedStates.clear();
        return currentState;
    }

    private static int manhattanDistance(int[][] board) {
        int distance = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int value = board[row][col];
                if (value == 0) {
                    continue;
                }

                int targetRow = (value - 1) / 3;
                int targetCol = (value - 1) % 3;

                distance += Math.abs(row - targetRow) + Math.abs(col - targetCol);
            }
        }

        return distance;
    }
}
