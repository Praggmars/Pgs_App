package ProgramViewModel.ImageMenu;

import Program.ImageProgram.ImageEditorProgram;

import java.io.File;

public class ImageEditorPVM extends ImageEditorBasePVM {

    ////////////////////////////////////////////////////////////////
    //getters, setters
    ////////////////////////////////////////////////////////////////

    private ImageEditorProgram Program(){
        return (ImageEditorProgram)program;
    }

    @Override
    public String getName() {
        return "Image editor";
    }


    ////////////////////////////////////////////////////////////////
    //methods
    ////////////////////////////////////////////////////////////////

    public ImageEditorPVM(ImageEditorProgram program){
        super(program);
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
}
