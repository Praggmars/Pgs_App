package Pgs_App;

import Program.*;
import Program.ImageProgram.ImageEditorProgram;
import Program.ImageProgram.WebcamProgram;
import Program.SudokuProgram.SudokuProgram;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.opencv.core.Core;

import java.util.ArrayList;
import java.util.List;

public class Pgs_App extends Application {

    ////////////////////////////////////////////////////////////////
    //fields
    ////////////////////////////////////////////////////////////////
    public static Pgs_App App;

    private Stage window;
    private Scene scene;

    private MenuBar menuBar;
    private List<Program> programList;

    private BorderPane container;
    private TabPane runningProgramTabs;
    private SingleSelectionModel<Tab> tabSelection;
    private Pane optionPane;
    private Pane secondaryProgramPane;


    ////////////////////////////////////////////////////////////////
    //getters, setters
    ////////////////////////////////////////////////////////////////
    public Pane getOptionPane() {
        return optionPane;
    }

    public Stage getWindow() {
        return window;
    }


    ////////////////////////////////////////////////////////////////
    //methods
    ////////////////////////////////////////////////////////////////

    //program menu registration

    private void RegisterMainMenu(){
        Menu mainMenu = new Menu("Menu");
        menuBar.getMenus().add(mainMenu);

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e->CloseWindow());
        mainMenu.getItems().add(exit);
    }

    private void RegisterImageMenu(){
        Menu imageMenu = new Menu("Image");
        menuBar.getMenus().add(imageMenu);

        MenuItem image = new MenuItem("Image editor");
        image.setOnAction(e->OpenProgram(new ImageEditorProgram()));
        imageMenu.getItems().add(image);

        MenuItem webcam = new MenuItem("Webcam");
        webcam.setOnAction(e->OpenProgram(new WebcamProgram()));
        imageMenu.getItems().add(webcam);
    }

    private void RegisterSudokuMenu(){
        Menu sudokuMenu = new Menu("Sudoku");
        menuBar.getMenus().add(sudokuMenu);

        MenuItem sudoku = new MenuItem("Sudoku");
        sudoku.setOnAction(e->OpenProgram(new SudokuProgram()));
        sudokuMenu.getItems().add(sudoku);
    }

    private void RegisterMenus(){
        RegisterMainMenu();
        RegisterImageMenu();
        RegisterSudokuMenu();
    }

    private void CreateMenu(){
        menuBar = new MenuBar();
        RegisterMenus();
    }

    //program opening and closing

    public void OpenProgram(Program program){
        programList.add(program);
        Tab tab = program.getViewModel().getTab();
        runningProgramTabs.getTabs().add(tab);
        tabSelection.select(tab);
        program.Run();
    }

    public void CloseProgram(Program program){
        program.Close();
        programList.remove(program);
    }

    //setting up application

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        App = this;
        LoadWindow();
    }

    private void LoadWindow(){
        CreateMenu();
        CreateProgramDisplay();
        CreateProgramManager();
        CreateWindow();
    }

    private void CreateProgramManager() {
        programList = new ArrayList<>();
        tabSelection = runningProgramTabs.getSelectionModel();
    }

    private void CreateProgramDisplay(){
        runningProgramTabs = new TabPane();
        optionPane = new Pane();
        secondaryProgramPane = new Pane();
        SplitPane vSplitPane = new SplitPane(optionPane, secondaryProgramPane);
        vSplitPane.setOrientation(Orientation.VERTICAL);
        SplitPane hSplitPane = new SplitPane(runningProgramTabs, vSplitPane);
        hSplitPane.setOrientation(Orientation.HORIZONTAL);
        hSplitPane.setDividerPositions(0.75);
        container = new BorderPane();
        container.setTop(menuBar);
        container.setCenter(hSplitPane);
        container.setPrefSize(1280, 720);
    }

    private void CreateWindow(){
        scene = new Scene(container);
        window.setScene(scene);
        window.setTitle("Pgs_App");
        window.setOnCloseRequest(e->{
            for (Program p : programList)
                p.Close();
        });
        window.show();
    }

    public void CloseWindow(){
        for (Program p : programList)
            p.Close();
        window.close();
    }

    static {System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}

    public static void main(String[] args) {
        launch();
    }
}
