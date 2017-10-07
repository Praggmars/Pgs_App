package Program.ImageProgram;

import ProgramViewModel.ImageMenu.ImageEditorPVM;

public class ImageEditorProgram extends ImageEditorBaseProgram {

    ////////////////////////////////////////////////////////////////
    //getters, setters
    ////////////////////////////////////////////////////////////////

    private ImageEditorPVM ViewModel(){
        return (ImageEditorPVM)getViewModel();
    }


    ////////////////////////////////////////////////////////////////
    //methods
    ////////////////////////////////////////////////////////////////

    public ImageEditorProgram() {
        super();
        viewModel = new ImageEditorPVM(this);
    }

    @Override
    public void setEffect(ImageEffect effect){
        super.setEffect(effect);
        ShowImage();
    }

    @Override
    protected void RunProgram() {

    }

    @Override
    protected void PauseProgram() {

    }

    @Override
    protected void ResumeProgram() {

    }

    @Override
    protected void CloseProgram() {

    }
}
