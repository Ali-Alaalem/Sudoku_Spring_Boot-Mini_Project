package com.project.sudoku.Services;

import com.project.sudoku.Models.SudokuCell;
import org.springframework.stereotype.Service;


@Service
public class SudokuSolverService {

    public boolean solve(SudokuCell sudoku) {
        return backtrack(sudoku, 0, 0);
    }

    private boolean backtrack(SudokuCell sudoku, int row, int col) {
        if (row == 9) return true;
        if (col == 9) return backtrack(sudoku, row + 1, 0);

        SudokuCell cell = sudoku.getBoard()[row][col];
        if (cell.isFixed()) return backtrack(sudoku, row, col + 1);

        for (int num = 1; num <= 9; num++) {
            if (isValid(sudoku, row, col, num)) {
                cell.setValue(num);
                if (backtrack(sudoku, row, col + 1)) return true;
                cell.setValue(0);
            }
        }
        return false;
    }

    private boolean isValid(SudokuCell sudoku, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (sudoku.getBoard()[row][i].getValue() == num) return false;
            if (sudoku.getBoard()[i][col].getValue() == num) return false;
        }
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++)
            for (int j = startCol; j < startCol + 3; j++)
                if (sudoku.getBoard()[i][j].getValue() == num) return false;

        return true;
    }
}