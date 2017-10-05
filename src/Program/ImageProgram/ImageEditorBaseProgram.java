package Program.ImageProgram;

import Program.Program;
import ProgramViewModel.ImageMenu.ImageEditorBasePVM;
import javafx.scene.image.Image;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.ByteArrayInputStream;

public abstract class ImageEditorBaseProgram extends Program {

    protected ImageEffect effect;
    protected Mat originalImage;
    protected Mat editedImage;
    protected MatOfByte buffer;

    public ImageEditorBaseProgram() {
        originalImage = new Mat();
        editedImage = new Mat();
        buffer = new MatOfByte();
        effect = ImageEffect.NORMAL;
    }

    public void setEffect(ImageEffect effect){
        this.effect = effect;
    }

    public void ShowImage(){
        if (!originalImage.empty()) {
            effect.ApplyEffect(originalImage, editedImage);
            ViewModel().SetImage(MatToImage());
        }
    }

    public void LoadImageFromFile(String path){
        originalImage = Imgcodecs.imread(path);
        ViewModel().SetImageViewSize(originalImage.cols(), originalImage.rows());
    }

    private Image MatToImage(){
        Imgcodecs.imencode(".png", editedImage, buffer);
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }

    private ImageEditorBasePVM ViewModel(){
        return (ImageEditorBasePVM)getViewModel();
    }
}
