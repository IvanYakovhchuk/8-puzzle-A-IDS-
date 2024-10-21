package com.algorithms.puzzle;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private final Board currentBoard;
    private final Node parent;
    private final int depth;
    private int heuristicValue;

    private final List<Node> children;

    public Node(Board currentState, Node parent, List<Node> children) {
        this.currentBoard = currentState;
        this.parent = parent;
        this.depth = (parent != null) ? parent.depth + 1 : 0;
        this.children = children;
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }
    public Node getParent() {
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
    public List<Node> getChildren() {
        return children;
    }

    public void generateChildren() {
        if (!currentBoard.equals(currentBoard.moveLeftTile())) {
            children.add(new Node(currentBoard.moveLeftTile(), this, new ArrayList<>()));
        }
        if (!currentBoard.equals(currentBoard.moveRightTile())) {
            children.add(new Node(currentBoard.moveRightTile(), this, new ArrayList<>()));
        }
        if (!currentBoard.equals(currentBoard.moveUpTile())) {
            children.add(new Node(currentBoard.moveUpTile(), this, new ArrayList<>()));
        }
        if (!currentBoard.equals(currentBoard.moveDownTile())) {
            children.add(new Node(currentBoard.moveDownTile(), this, new ArrayList<>()));
        }
    }
}
