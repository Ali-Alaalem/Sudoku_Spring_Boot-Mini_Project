package com.project.sudoku.Services;


import com.project.sudoku.Exceptions.InvalidCharacterException;
import com.project.sudoku.Exceptions.SudokuFileNotFoundException;
import com.project.sudoku.Models.SudokuCell;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



@Service
@RequiredArgsConstructor
public class SudokuService {

    private final SudokuSolverService solverService;


    public SudokuCell loadFromFile(String fileName) {

        SudokuCell sudoku = new SudokuCell(true);
        SudokuCell[][] board = sudoku.getBoard();

        File file = new File(fileName);
        if (!file.exists()) throw new SudokuFileNotFoundException("File not found: " + fileName);

        try (Scanner scanner = new Scanner(file)) {
            int row = 0;
            while (scanner.hasNextLine() && row < 9) {
                String line = scanner.nextLine().trim().replaceAll("\\s+", " ");
                if (line.isEmpty() || line.startsWith("-")) continue;

                String[] nums = line.split(" ");
                int col = 0;
                for (String num : nums) {
                    if (num.equals("|")) continue;
                    int val;
                    try {
                        val = Integer.parseInt(num);
                    } catch (NumberFormatException e) {
                        throw new InvalidCharacterException("Invalid character in file: " + num);
                    }

                    board[row][col] = new SudokuCell(val, val != 0);
                    col++;
                }
                row++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sudoku;
    }


    public void printBoard(SudokuCell sudoku) {
        SudokuCell[][] board = sudoku.getBoard();
        System.out.println("+-------+-------+-------+");
        for (int i = 0; i < 9; i++) {
            System.out.print("| ");
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j].getValue() + " ");
                if ((j + 1) % 3 == 0) System.out.print("| ");
            }
            System.out.println();
            if ((i + 1) % 3 == 0) System.out.println("+-------+-------+-------+");
        }
    }


    public void saveSolution(SudokuCell sudoku, String fileName) {
        SudokuCell[][] board = sudoku.getBoard();
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    writer.print(board[i][j].getValue() + " ");
                    if ((j + 1) % 3 == 0 && j != 8) writer.print("| ");
                }
                writer.println();
                if ((i + 1) % 3 == 0 && i != 8) writer.println("---------------------");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> StartPoint() {

        File currentDir = new File(".");
        File[] files = currentDir.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            return List.of("No puzzle files found!");
        }

        List<String> results = new ArrayList<>();

        for (File file : files) {

            SudokuCell sudoku = loadFromFile(file.getName());

            System.out.println("Before: " + file.getName());
            printBoard(sudoku);

            solve(sudoku);

            System.out.println("Solved: " + file.getName());
            printBoard(sudoku);
            System.out.println("");
            System.out.println("############################################################");
            System.out.println("");

            String solutionFile = file.getName().replace(".txt", ".solution.txt");

            saveSolution(sudoku, solutionFile);

            results.add("Solved: " + file.getName() + " → " + solutionFile);
        }

        return results;
    }


    public void solve(SudokuCell sudoku) {
        solverService.solve(sudoku);
    }
}