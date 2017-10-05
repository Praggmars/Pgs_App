package ProgramViewModel;

import Pgs_App.Pgs_App;
import Program.Program;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

public abstract class ProgramViewModel {

    protected Program program;
    protected Tab tab;
    protected Pane options;

    public Program getProgram() {
        return program;
    }
    public Tab getTab() {
        return tab;
    }

    public ProgramViewModel(Program program){
        this.program = program;
        UIinit();
        DisplaySettings();
        OptionSettings();
    }

    protected void UIinit(){
        tab = new Tab(getName());
        tab.setClosable(true);
        tab.setOnClosed(e->Pgs_App.App.CloseProgram(program));
        tab.setOnSelectionChanged(e->{
            if (tab.isSelected())
                program.Resume();
            else
                program.Pause();
        });
        options = Pgs_App.App.getOptionPane();
    }

    protected abstract void OptionSettings();
    protected abstract void DisplaySettings();

    public abstract void FillOptionMenu();

    public abstract String getName();
}
