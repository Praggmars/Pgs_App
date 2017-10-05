package Program.ImageProgram;

import ProgramViewModel.ImageMenu.WebcamPVM;
import javafx.animation.AnimationTimer;
import org.opencv.videoio.VideoCapture;

public class WebcamProgram extends ImageEditorBaseProgram {

    private VideoCapture cammera;
    private AnimationTimer timer;

    public WebcamProgram() {
        super();
        viewModel = new WebcamPVM(this);
        WebcamInit();
    }

    private void WebcamInit() {
        cammera = new VideoCapture();
        cammera.open(0);
        cammera.read(originalImage);
        ViewModel().SetImageViewSize(originalImage.cols(), originalImage.rows());
        cammera.release();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                cammera.read(originalImage);
                ShowImage();
            }
        };
    }

    @Override
    protected void RunProgram() {
        cammera.open(0);
        timer.start();
    }

    @Override
    protected void PauseProgram() {
        timer.stop();
        cammera.release();
    }

    @Override
    protected void ResumeProgram() {
        cammera.open(0);
        timer.start();
    }

    @Override
    protected void CloseProgram() {
        timer.stop();
        cammera.release();
    }

    private WebcamPVM ViewModel(){
        return (WebcamPVM)getViewModel();
    }
}
