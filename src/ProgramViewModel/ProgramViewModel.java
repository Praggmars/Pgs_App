package ProgramViewModel;

import Program.Program;

public abstract class ViewModel {
    private Program program;

    public Program getProgram() {
        return program;
    }

    public abstract void RunProgram();
    public abstract void CloseProgram();
    public abstract void PauseProgram();
    public abstract void ResumeProgram();
}
