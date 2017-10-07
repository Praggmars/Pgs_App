package Program;

import ProgramViewModel.ProgramViewModel;

public abstract class Program {

    ////////////////////////////////////////////////////////////////
    //fields
    ////////////////////////////////////////////////////////////////
    protected ProgramViewModel viewModel;
    private boolean started = false;


    ////////////////////////////////////////////////////////////////
    //getters, setters
    ////////////////////////////////////////////////////////////////

    public ProgramViewModel getViewModel(){
        return viewModel;
    }


    ////////////////////////////////////////////////////////////////
    //methods
    ////////////////////////////////////////////////////////////////

    public void Run(){
        if (!started) {
            started = true;
            viewModel.FillOptionMenu();
            RunProgram();
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
}
