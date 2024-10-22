package com.algorithms;

import com.algorithms.informedsearch.AStar;
import com.algorithms.puzzle.Board;
import com.algorithms.puzzle.State;

import java.util.Scanner;

import static com.algorithms.puzzle.State.printSolution;

public class Main {
    public static void main(String[] args) {
        Board myBoard = new Board();
        try(Scanner scanner = new Scanner(System.in)) {
            do {
                System.out.println("""
                1) Enter "generate" to get a randomly created puzzle
                2) Enter "input" to create your own puzzle""");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("generate")) {
                    do {
                        myBoard.generateBoard();
                    } while (!myBoard.isSolvable());
                } else if (input.equalsIgnoreCase("input")) {
                    myBoard.inputBoard();
                } else {
                    System.out.println("Invalid input! Please try again.");
                }
            } while (myBoard.equals(new Board()));
        }
        State goalState = AStar.search(myBoard);
        System.out.println("\u001B[31m"+"Red number will be moved next");
        System.out.println("\u001B[32m"+"Green number was moved last\n"+"\u001B[0m");
        printSolution(goalState);
    }
}