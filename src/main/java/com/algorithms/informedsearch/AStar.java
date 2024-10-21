package com.algorithms.informedsearch;

import com.algorithms.puzzle.Board;
import com.algorithms.puzzle.Node;

import java.util.*;

public class AStar {
    private static final PriorityQueue<Node> uncheckedStates = new PriorityQueue<>(Comparator.comparing(Node::getHeuristicValue));
    private static final List<Node> checkedStates = new ArrayList<>();

    public static Node search(Board initialBoard) {
        Node initialState = new Node(initialBoard, null, new ArrayList<>());
        initialState.setHeuristicValue(initialState.getDepth() + manhattanDistance(initialState.getCurrentBoard().getBoard()));
        uncheckedStates.add(initialState);
        Node currentState = null;
        while (!uncheckedStates.isEmpty()) {
            currentState = uncheckedStates.poll();
            if (manhattanDistance(currentState.getCurrentBoard().getBoard()) == 0) {
                break;
            }
            if (checkedStates.contains(currentState)) {
                continue;
            }
            currentState.generateChildren();
            currentState.getChildren().forEach(s -> {
                s.setHeuristicValue(s.getDepth() + manhattanDistance(s.getCurrentBoard().getBoard()));
            });
            uncheckedStates.addAll(currentState.getChildren());
            checkedStates.add(currentState);
            uncheckedStates.remove(currentState);
        }
        assert currentState != null;
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

    public static void printSolution(Node finalState) {
        List<Board> solutionPath = new ArrayList<>();
        Node current = finalState;

        while (current != null) {
            solutionPath.add(current.getCurrentBoard());
            current = current.getParent();
        }

        Collections.reverse(solutionPath);

        for (Board board : solutionPath) {
            board.printBoard();
            System.out.println("  | ");
            System.out.println("  | ");
            System.out.println(" \\'/ \n");
        }
    }

}
