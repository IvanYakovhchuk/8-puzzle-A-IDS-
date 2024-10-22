package com.algorithms.puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class State {

    private final Board currentBoard;
    private final State parent;
    private final int depth;
    private int heuristicValue;
    private final List<State> children;

    public State(Board currentState, State parent, List<State> children) {
        this.currentBoard = currentState;
        this.parent = parent;
        this.depth = (parent != null) ? parent.depth + 1 : 0;
        this.children = children;
    }

    private int[] findLastZeroPosition() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (parent != null && parent.getCurrentBoard().getBoard()[row][col] == 0) {
                    return new int[] { row, col };
                }
            }
        }
        return new int[] { -1, -1 };
    }

    private int[] findZeroPosition() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (parent != null && currentBoard.getBoard()[row][col] == 0) {
                    return new int[] { row, col };
                }
            }
        }
        return new int[] { -1, -1 };
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }
    public State getParent() {
        return parent;
    }
    public int getDepth() {
        return depth;
    }
    public int getHeuristicValue() {
        return heuristicValue;
    }
    public void setHeuristicValue(int heuristicValue) {
        this.heuristicValue = heuristicValue;
    }
    public List<State> getChildren() {
        return children;
    }

    public void generateChildren() {
        if (!currentBoard.equals(currentBoard.moveLeftTile())) {
            children.add(new State(currentBoard.moveLeftTile(), this, new ArrayList<>()));
        }
        if (!currentBoard.equals(currentBoard.moveRightTile())) {
            children.add(new State(currentBoard.moveRightTile(), this, new ArrayList<>()));
        }
        if (!currentBoard.equals(currentBoard.moveUpTile())) {
            children.add(new State(currentBoard.moveUpTile(), this, new ArrayList<>()));
        }
        if (!currentBoard.equals(currentBoard.moveDownTile())) {
            children.add(new State(currentBoard.moveDownTile(), this, new ArrayList<>()));
        }
    }

    public static void printSolution(State finalState) {
        List<State> solutionPath = new ArrayList<>();
        State current = finalState;

        while (current != null) {
            solutionPath.add(current);
            current = current.getParent();
        }

        Collections.reverse(solutionPath);

        solutionPath.forEach(s -> {
            int[] nextMovePosition = {-1, -1};
            int nextMoveValue = 0;
            if (solutionPath.indexOf(s) < solutionPath.size() - 1) {
                nextMovePosition = solutionPath.get(solutionPath.indexOf(s) + 1).findZeroPosition();
            }
            s.getCurrentBoard().printBoard(s.findLastZeroPosition(), nextMovePosition);
            if (!(solutionPath.getLast() == s)) {
                System.out.println();
                System.out.println("   | ");
                System.out.println("   | ");
                System.out.println("  \\'/ \n");
            }
            else {
                System.out.println("\u001B[33m"+"\nCongratulations! You solved the puzzle!");
            }
        });
    }
}
