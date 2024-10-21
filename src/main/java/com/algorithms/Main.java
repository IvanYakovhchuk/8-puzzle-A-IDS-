package com.algorithms;

import com.algorithms.informedsearch.AStar;
import com.algorithms.puzzle.Board;
import com.algorithms.puzzle.Node;

public class Main {
    public static void main(String[] args) {
        Board myBoard = new Board();
        do {
            myBoard.generateBoard();
        } while (!myBoard.isSolvable());
        Node goalState = AStar.search(myBoard);
        AStar.printSolution(goalState);
    }
}