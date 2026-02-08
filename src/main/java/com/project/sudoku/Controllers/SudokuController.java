package com.project.sudoku.Controllers;

import com.project.sudoku.Services.SudokuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SudokuController {

    private final SudokuService sudokuService;

    public SudokuController(SudokuService sudokuService) {
        this.sudokuService = sudokuService;
    }

    @PostMapping("/sudoku/solve-files")
    public List<String> solveAllFiles() {
        return sudokuService.StartPoint();
    }
}
