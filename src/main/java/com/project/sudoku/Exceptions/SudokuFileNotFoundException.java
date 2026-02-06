package com.project.sudoku.Exceptions;

public class SudokuFileNotFoundException extends RuntimeException {
    public SudokuFileNotFoundException(String message) {
        super(message);
    }
}