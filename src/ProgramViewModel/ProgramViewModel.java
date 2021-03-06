package ProgramViewModel;

import Pgs_App.Pgs_App;
import Program.Program;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

import java.io.File;

public abstract class ProgramViewModel {

    ////////////////////////////////////////////////////////////////
    //fields
    ////////////////////////////////////////////////////////////////

    protected Program program;
    protected Tab tab;
    private Pane optionsPlaceHolder;
    protected Pane optionMenu;
    protected Pane display;


    ////////////////////////////////////////////////////////////////
    //getters, setters
    ////////////////////////////////////////////////////////////////

    public Program getProgram() {
        return program;
    }

    public Tab getTab() {
        return tab;
    }

    public abstract String getName();


    ////////////////////////////////////////////////////////////////
    //methods
    ////////////////////////////////////////////////////////////////

    public ProgramViewModel(Program program){
        this.program = program;
        UIinit();
        DisplaySettings();
        OptionSettings();
    }

    private void UIinit(){
        tab = new Tab(getName());
        tab.setClosable(true);
        tab.setOnClosed(e->Pgs_App.App.CloseProgram(program));
        tab.setOnSelectionChanged(e->{
            if (tab.isSelected())
                program.Resume();
            else
                program.Pause();
        });
        optionsPlaceHolder = Pgs_App.App.getOptionPane();
    }

    protected void AcceptDragAndDropFile(Node dst){
        dst.setOnDragOver(e->{
            Dragboard db = e.getDragboard();
            if (db.hasFiles())
                e.acceptTransferModes(TransferMode.COPY);
            else
                e.consume();
        });
        dst.setOnDragDropped(e->{
            Dragboard db = e.getDragboard();
            if (db.hasFiles()){
                for (File f : db.getFiles()) {
                    HandleDroppedFile(f);
                }
            }
            e.setDropCompleted(true);
            e.consume();
        });
    }

    protected void HandleDroppedFile(File file){
    }

    protected abstract void OptionSettings();
    protected abstract void DisplaySettings();

    public void FillOptionMenu(){
        optionsPlaceHolder.getChildren().clear();
        optionsPlaceHolder.getChildren().add(optionMenu);
    }
}
