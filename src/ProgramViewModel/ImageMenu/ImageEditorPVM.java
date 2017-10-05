package ProgramViewModel.ImageMenu;

import Program.ImageProgram.ImageEditorProgram;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.io.File;

public class ImageEditorPVM extends ImageEditorBasePVM {

    public ImageEditorPVM(ImageEditorProgram program){
        super(program);
    }

    private ImageEditorProgram Program(){
        return (ImageEditorProgram)program;
    }

    @Override
    protected void OptionSettings() {
        super.OptionSettings();
    }

    @Override
    protected void DisplaySettings() {
        super.DisplaySettings();
        AcceptDragAndDropFile();
    }

    private void AcceptDragAndDropFile(){
        display.setOnDragOver(e->{
            Dragboard db = e.getDragboard();
            if (db.hasFiles())
                e.acceptTransferModes(TransferMode.COPY);
            else
                e.consume();
        });
        display.setOnDragDropped(e->{
            Dragboard db = e.getDragboard();
            if (db.hasFiles()){
                for (File f : db.getFiles()) {
                    String path = f.getAbsolutePath();
                    Program().LoadImageFromFile(path);
                    Program().ShowImage();
                }
            }
            e.setDropCompleted(true);
            e.consume();
        });
    }

    @Override
    public String getName() {
        return "Image editor";
    }
}
