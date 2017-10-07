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
        AcceptDragAndDropFile(display);
    }

    @Override
    protected void HandleDroppedFile(File file){
        Program().LoadImageFromFile(file.getAbsolutePath());
        Program().ShowImage();
    }


    @Override
    public String getName() {
        return "Image editor";
    }
}
