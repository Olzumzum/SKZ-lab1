package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.loaderFile.FileLoader;

import java.io.File;

public class Controller {
    private Stage primaryStage;

    //кнопка загрузки файла
    @FXML
    private Button loadFileButton;
    //кнопка фильтр 1
    @FXML
    private Button filterOneButton;
    //кнопка фильтр 2
    @FXML
    private Button filterTwoButton;
    @FXML
    private ImageView originalImage;

    private FileLoader fileLoader = FileLoader.getInstance();


    void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void onClickLoadFile() {
        fileLoader.load(primaryStage);
        showImage(fileLoader.getFile());
    }

    @FXML
    public void showImage(File file) {
        try {
            Image image = new Image(file.toURI().toString(),120,120,false,false);
            System.out.println("Проблема чтения файла " + image);
            originalImage.setImage(image);
            originalImage.setPreserveRatio(true);



        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @FXML
    public void onClickApplyFilterOne() {
        filterOneButton.setText("Apply1");
    }

    @FXML
    public void onClickApplyFilterTwo() {
        filterTwoButton.setText("Apply2");
    }


}
