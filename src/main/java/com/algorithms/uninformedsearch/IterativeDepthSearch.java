package com.algorithms.uninformedsearch;

import com.algorithms.puzzle.Board;
import com.algorithms.puzzle.State;

import java.util.*;

public class IterativeDepthSearch {

    private static final List<Board> goalBoards = List.of(
            new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}),
            new Board(new int[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}})
    );

    private static final Set<Board> processedStates = new LinkedHashSet<>();

    public static State IDDFS(Board initialState, int maxDepth) {
        for (int depth = 1; depth <= maxDepth; depth++) {
            State result = DLS(new State(initialState, null, null), depth);
            if (result != null) {
                return result;
            }
            processedStates.clear();
        }
        return null;
    }

    private static State DLS(State node, int depth) {
        if (node == null) {
            return null;
        }
        if (goalBoards.contains(node.getCurrentBoard())) {
            return node;
        }
        if (depth <= 0) {
            return null;
        }

        if (!processedStates.contains(node.getCurrentBoard())) {
            node.children = new ArrayList<>();
            node.generateChildren();
            processedStates.add(node.getCurrentBoard());
        }

        for (State child : node.getChildren()) {
            State found = DLS(child, depth - 1);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
}