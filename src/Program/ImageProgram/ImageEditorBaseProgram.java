package Program.ImageProgram;

import Program.Program;
import ProgramViewModel.ImageMenu.ImageEditorBasePVM;
import javafx.scene.image.Image;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.ByteArrayInputStream;

public abstract class ImageEditorBaseProgram extends Program {

    ////////////////////////////////////////////////////////////////
    //fields
    ////////////////////////////////////////////////////////////////

    protected ImageEffect effect;
    protected Mat originalImage;
    protected Mat editedImage;
    protected MatOfByte buffer;


    ////////////////////////////////////////////////////////////////
    //getters, setters
    ////////////////////////////////////////////////////////////////

    public void setEffect(ImageEffect effect){
        this.effect = effect;
    }


    ////////////////////////////////////////////////////////////////
    //methods
    ////////////////////////////////////////////////////////////////

    ImageEditorBaseProgram(){
        originalImage = new Mat();
        editedImage = new Mat();
        buffer = new MatOfByte();
        effect = ImageEffect.NORMAL;
    }

    public void ShowImage(){
        if (!originalImage.empty()) {
            effect.ApplyEffect(originalImage, editedImage);
            ((ImageEditorBasePVM)viewModel).setImage(MatToImage());
        }
    }

    public void LoadImageFromFile(String path){
        originalImage = Imgcodecs.imread(path);
        ((ImageEditorBasePVM)viewModel).setImageViewSize(originalImage.cols(), originalImage.rows());
    }

    private Image MatToImage(){
        Imgcodecs.imencode(".png", editedImage, buffer);
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }
}
