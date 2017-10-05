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
    protected ImageView imageView;
    protected ComboBox<ImageEffect> effectList;
    protected Pane display;
    protected VBox optionMenu;
    protected double imgWidth;
    protected double imgHeight;


    public ImageEditorBasePVM(ImageEditorBaseProgram program){
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
            SetImageViewSize(imgWidth*scale, imgHeight*scale);
        });
    }

    @Override
    public void FillOptionMenu(){
        options.getChildren().clear();
        options.getChildren().add(optionMenu);
    }

    public void SetImage(Image image){
        imageView.setImage(image);
        imageView.setFitWidth(image.getWidth());
        imageView.setFitHeight(image.getHeight());
    }

    public void SetImageViewSize(double width, double height){
        imgWidth = width;
        imgHeight = height;
        imageView.setFitWidth(imgWidth);
        imageView.setFitHeight(imgHeight);
    }
}
