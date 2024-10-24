package com.algorithms;

import com.algorithms.informedsearch.AStar;
import com.algorithms.puzzle.Board;
import com.algorithms.puzzle.State;
import com.algorithms.uninformedsearch.IterativeDepthSearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board myBoard = new Board();
        State result;
        boolean cond;
        try (Scanner scanner = new Scanner(System.in)) {
            do {
                String input;
                do {
                    System.out.println("""
                            1) Enter "generate" to get a randomly created puzzle
                            2) Enter "input" to create your own puzzle""");
                    input = scanner.nextLine();
                    if (input.equalsIgnoreCase("generate")) {
                        do {
                            myBoard.generateBoard();
                        }
                        while (!myBoard.isSolvable());
                    } else if (input.equalsIgnoreCase("input")) {
                        myBoard.inputBoard(scanner);
                    } else {
                        System.out.println("Invalid input! Please try again.");
                    }
                }
                while (myBoard.equals(new Board()));

                cond = false;
                do {
                    System.out.println("""
                            1) Enter A* to solve the puzzle using A* algorithm
                            2) Enter IDS to solve the puzzle using IDS algorithm""");
                    input = scanner.nextLine();

                    if (input.equalsIgnoreCase("ids")) {
                        result = IterativeDepthSearch.IDDFS(myBoard, 14);
                        cond = true;
                    } else if (input.equalsIgnoreCase("a*")) {
                        result = AStar.search(myBoard);
                        cond = true;
                    } else {
                        System.out.println("Invalid input! Please try again.");
                        continue;
                    }
                    if (result != null) {
                        System.out.println("\u001B[31m" + "Red number will be moved next");
                        System.out.println("\u001B[32m" + "Green number was moved last\n" + "\u001B[0m");
                        printSolution(result);
                    } else {
                        System.out.println("Can't find goal state");
                    }
                } while (!cond);

                System.out.println("Do you want to continue? (y/n)");
                input = scanner.nextLine();
                cond = input.equalsIgnoreCase("y");
            } while (cond);
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
                System.out.println("\u001B[33m"+"\nCongratulations! You solved the puzzle!"+"\u001B[0m");
            }
        });
    }
}