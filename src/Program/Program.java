package Program;

import ProgramViewModel.ProgramViewModel;

public abstract class Program {

    protected ProgramViewModel viewModel;
    private boolean started = false;


    public void Run(){
        if (!started) {
            started = true;
            viewModel.FillOptionMenu();
            ResumeProgram();
        }
    }
    protected abstract void RunProgram();
    public void Pause(){
        if (started)
            PauseProgram();
    }
    protected abstract void PauseProgram();
    public void Resume(){
        if (started) {
            viewModel.FillOptionMenu();
            ResumeProgram();
        }
    }
    protected abstract void ResumeProgram();
    public void Close(){
        if (started)
            CloseProgram();
    }
    protected abstract void CloseProgram();

    public ProgramViewModel getViewModel(){
        return viewModel;
    }
}
