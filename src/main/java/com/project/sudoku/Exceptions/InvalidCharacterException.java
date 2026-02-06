package com.project.sudoku.Exceptions;

public class InvalidCharacterException extends RuntimeException {
    public InvalidCharacterException(String message) {
        super(message);
    }
}