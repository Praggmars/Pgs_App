package ProgramViewModel.SudokuMenu;

import Pgs_App.Pgs_App;
import Program.Program;
import Program.SudokuProgram.SudokuProgram;
import ProgramViewModel.ProgramViewModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

public class SudokuPVM extends ProgramViewModel {

    ////////////////////////////////////////////////////////////////
    //fields
    ////////////////////////////////////////////////////////////////

    private GraphicsContext graphicsContext;

    private SudokuDrawer sudokuDrawer;


    ////////////////////////////////////////////////////////////////
    //getters, setters
    ////////////////////////////////////////////////////////////////

    private SudokuProgram Program(){
        return (SudokuProgram)program;
    }

    @Override
    public String getName() {
        return "Sudoku";
    }


    ////////////////////////////////////////////////////////////////
    //methods
    ////////////////////////////////////////////////////////////////

    public SudokuPVM(Program program) {
        super(program);
        sudokuDrawer = new SudokuDrawer(Program().getSudoku());
    }

    public void DrawSudoku(){
        sudokuDrawer.Draw(graphicsContext);
    }

    @Override
    protected void OptionSettings() {
        Button btn_load = new Button("Load");
        btn_load.setOnAction(e->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
            fileChooser.setTitle("Choose sudoku");
            Program().LoadSudokuFromFile(fileChooser.showOpenDialog(Pgs_App.App.getWindow()));
            sudokuDrawer.Draw(graphicsContext);
        });
        Button btn_solve = new Button("Solve");
        btn_solve.setOnAction(e->{
            Program().Solve();
            sudokuDrawer.Draw(graphicsContext);
        });
        Button btn_clear = new Button("Clear");
        btn_clear.setOnAction(e->{
            Program().ClearSudoku();
            sudokuDrawer.Draw(graphicsContext);
        });
        optionMenu = new VBox(btn_load, btn_clear, btn_solve);
    }

    @Override
    protected void DisplaySettings() {
        Canvas canvas = new Canvas(540, 540);
        display = new Pane(canvas);
        tab.setContent(display);
        graphicsContext = canvas.getGraphicsContext2D();
        AcceptDragAndDropFile(display);
        canvas.setOnMouseClicked(e->{
            sudokuDrawer.ClickEvent(e.getX(), e.getY());
            sudokuDrawer.Draw(graphicsContext);
        });
    }

    @Override
    protected void HandleDroppedFile(File file){
        Program().getSudoku().Load(file);
        sudokuDrawer.Draw(graphicsContext);
    }
}
