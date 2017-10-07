package ProgramViewModel.ImageMenu;

import Program.ImageProgram.WebcamProgram;

public class WebcamPVM extends ImageEditorBasePVM {

    ////////////////////////////////////////////////////////////////
    //getters, setters
    ////////////////////////////////////////////////////////////////

    private WebcamProgram Program(){
        return (WebcamProgram)program;
    }

    @Override
    public String getName() {
        return "Webcam";
    }


    ////////////////////////////////////////////////////////////////
    //methods
    ////////////////////////////////////////////////////////////////

    public WebcamPVM(WebcamProgram program){
        super(program);
    }
}
