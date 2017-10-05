package ProgramViewModel.ImageMenu;

import Program.ImageProgram.WebcamProgram;

public class WebcamPVM extends ImageEditorBasePVM {

    public WebcamPVM(WebcamProgram program){
        super(program);
    }

    @Override
    public String getName() {
        return "Webcam";
    }

    private WebcamProgram Program(){
        return (WebcamProgram)program;
    }
}
