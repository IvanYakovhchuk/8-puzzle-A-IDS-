package com.algorithms.uninformedsearch;

import com.algorithms.puzzle.Board;
import com.algorithms.puzzle.State;

import java.util.*;

public class IterativeDepthSearch {

    private static final List<Board> goalBoards = List.of(
            new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}),
            new Board(new int[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}})
    );

    // Use a LinkedHashSet to ensure insertion order and avoid unnecessary duplicates
    private static final Set<Board> processedStates = new LinkedHashSet<>();

    // Keep track of the maximum depth reached for efficiency
    private static int maxDepthReached = 0;

    public static State IDDFS(Board initialState, int maxDepth) {
        for (int depth = 1; depth <= maxDepth; depth++) {
            maxDepthReached = Math.max(maxDepthReached, depth); // Update max depth
            State result = DLS(new State(initialState, null, null), depth);
            if (result != null) {
                return result; // Goal found
            }
            // Clear processed states after each depth iteration to avoid memory buildup
            processedStates.clear();
        }
        return null; // Goal not found
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

        // Generate children only if the state hasn'  t been processed at this depth
        if (!processedStates.contains(node.getCurrentBoard())) {
            node.children = new ArrayList<>();
            node.generateChildren();
            processedStates.add(node.getCurrentBoard()); // Mark as processed for current depth
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


//public class IterativeDepthSearch {
//    private static final List<Board> goalBoards = List.of(
//            new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}),
//            new Board(new int[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}})
//    );
//    private static final Set<Board> processedStates = new HashSet<>();  // Збереження оброблених станів
//    private static final Map<Board, List<State>> stateCache = new HashMap<>();  // Кеш для станів і їх нащадків
//    private static final int currentDepth = 0;
//
////    public static State iterativeDeepeningSearch(Board initialState) {
////        int depth = 0;
////        State goalState;
////        do {
////            goalState = depthLimitedSearch(initialState, depth);
////            depth++;
////            if (depth > 20) {
////                System.out.println("Depth limited search exceeded");
////                System.exit(0);
////            }
////        } while (goalState == null);
////        return goalState;
////    }
////
////    private static State depthLimitedSearch(Board initialState, int depthLimit) {
////        return depthLimitedSearchRecursive(new State(initialState, null, stateCache.getOrDefault(initialState, new ArrayList<>())), depthLimit);
////    }
////
////    private static State depthLimitedSearchRecursive(State currentState, int depthLimit) {
////        if (currentState.getDepth() >= depthLimit) {
////            return null;  // Якщо досягнули ліміту глибини
////        }
////        if (goalBoards.contains(currentState.getCurrentBoard())) {
////            return currentState;  // Якщо знайшли ціль, повертаємо стан
////        }
////
////        // Зберігання нащадків поточного стану в кеші
////        if (!stateCache.containsKey(currentState.getCurrentBoard())) {
////            currentState.generateChildren();  // Генеруємо нащадків тільки для нових станів
////            stateCache.put(currentState.getCurrentBoard(), currentState.getChildren());  // Зберігаємо в кеш
////        }
////
////        processedStates.add(currentState.getCurrentBoard());
////
////            // Перевірка чи вже оброблявся цей стан
////        if (processedStates.contains(currentState.getCurrentBoard()) && currentState.getChildren().isEmpty()) {
////            return null;
////        } else if (processedStates.contains(currentState.getCurrentBoard())) {
////            for (State child : currentState.getChildren()) {
////                State result = depthLimitedSearchRecursive(child, depthLimit);
////                if (result != null) {
////                    return result;
////                }
////            }
////        }
////
////        return null;
////    }
//public static State IDDFS(State root, int maxDepth) {
//    State result;
//    for (int depth = 1; depth <= maxDepth; depth++) {
//        result = DLS(root, depth);
//        if (result != null) {
//            return result; // Ціль знайдена
//        }
//    }
//    return null; // Ціль не знайдена
//}
//
//    private static State DLS(State node, int depth) {
//        if (node == null) {
//            return null;
//        }
//        if (goalBoards.contains(node.getCurrentBoard())) {
//            return node;
//        }
//        if (depth <= 0) {
//            return null;
//        }
//
//        if (!stateCache.containsKey(node.getCurrentBoard())) {
//            node.generateChildren();
//            stateCache.put(node.getCurrentBoard(), node.getChildren());
//        }
//        for (State child : node.getChildren()) {
//            State found = DLS(child, depth - 1);
//            if (found != null) {
//                return found;
//            }
//        }
//        return null;
//    }
//}
