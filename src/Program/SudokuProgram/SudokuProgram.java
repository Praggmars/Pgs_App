package Program.SudokuProgram;

import Program.Program;
import ProgramViewModel.SudokuMenu.SudokuPVM;

import java.io.File;

public class SudokuProgram extends Program {

    ////////////////////////////////////////////////////////////////
    //fields
    ////////////////////////////////////////////////////////////////

    private Sudoku sudoku;


    ////////////////////////////////////////////////////////////////
    //getters, setters
    ////////////////////////////////////////////////////////////////

    public Sudoku getSudoku() {
        return sudoku;
    }

    private SudokuPVM ViewModel(){
        return (SudokuPVM)viewModel;
    }


    ////////////////////////////////////////////////////////////////
    //methods
    ////////////////////////////////////////////////////////////////

    public SudokuProgram(){
        sudoku = new Sudoku();
        viewModel = new SudokuPVM(this);
    }

    @Override
    protected void RunProgram() {
        ViewModel().DrawSudoku();
    }

    @Override
    protected void PauseProgram() {

    }

    @Override
    protected void ResumeProgram() {

    }

    @Override
    protected void CloseProgram() {

    }

    public void LoadSudokuFromFile(File file) {
        sudoku.Load(file);
    }

    public boolean Solve() {
        return sudoku.Solve();
    }

    public void ClearSudoku() {
        sudoku.Clear();
    }
}
