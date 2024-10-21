package com.algorithms;

import com.algorithms.informedsearch.AStar;
import com.algorithms.puzzle.Board;
import com.algorithms.puzzle.Node;

public class Main {
    public static void main(String[] args) {
        Board myBoard = new Board();
        myBoard.generateBoard();
        if (!myBoard.isSolvable()) {
            System.out.println("Puzzle can't be solved: number of inversions is odd!");
        }
        else {
            Node goalState = AStar.search(myBoard);
            AStar.printSolution(goalState);
        }
    }
}