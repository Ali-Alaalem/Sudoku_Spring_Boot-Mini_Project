package com.project.sudoku.Models;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class SudokuCell {

    private int value;
    private boolean fixed;


    private SudokuCell[][] board;


    public SudokuCell(int value, boolean fixed) {
        this.value = value;
        this.fixed = fixed;
    }

    
    public SudokuCell(boolean createBoard) {
        if (createBoard) {
            board = new SudokuCell[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    board[i][j] = new SudokuCell(0, false);
                }
            }
        }
    }
}