package ProgramViewModel.ImageMenu;

import Program.ImageProgram.ImageEditorBaseProgram;
import Program.ImageProgram.ImageEffect;
import ProgramViewModel.ProgramViewModel;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract class ImageEditorBasePVM extends ProgramViewModel {

    ////////////////////////////////////////////////////////////////
    //fields
    ////////////////////////////////////////////////////////////////

    protected ImageView imageView;
    protected ComboBox<ImageEffect> effectList;
    protected double imgWidth;
    protected double imgHeight;

    ////////////////////////////////////////////////////////////////
    //getters, setters
    ////////////////////////////////////////////////////////////////

    public void setImage(Image image){
        imageView.setImage(image);
        imageView.setFitWidth(imgWidth);
        imageView.setFitHeight(imgHeight);
    }

    public void setImageViewSize(double width, double height){
        imgWidth = width;
        imgHeight = height;
        imageView.setFitWidth(imgWidth);
        imageView.setFitHeight(imgHeight);
    }


    ////////////////////////////////////////////////////////////////
    //methods
    ////////////////////////////////////////////////////////////////

    ImageEditorBasePVM(ImageEditorBaseProgram program){
        super(program);
    }

    @Override
    protected void OptionSettings() {
        effectList = new ComboBox<>();
        effectList.getItems().addAll(ImageEffect.values());
        effectList.setValue(ImageEffect.NORMAL);
        effectList.valueProperty().addListener((observable, oldValue, newValue) -> ((ImageEditorBaseProgram)program).setEffect(newValue));
        optionMenu = new VBox(effectList);
    }

    @Override
    protected void DisplaySettings() {
        imageView = new ImageView();
        display = new Pane(imageView);
        tab.setContent(display);
        display.setOnScroll(e -> {
            double scale = e.getDeltaY() < 0 ? 1.0/1.1 : 1.1;
            setImageViewSize(imgWidth*scale, imgHeight*scale);
        });
    }
}
