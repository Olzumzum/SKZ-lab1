package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.loaderFile.FileLoader;

import java.awt.*;

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



    void setStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    @FXML
    public void onClickLoadFile(){
        FileLoader.load(primaryStage);

    }

    @FXML
    public void onClickApplyFilterOne(){
        filterOneButton.setText("Apply1");
    }

    @FXML
    public void onClickApplyFilterTwo(){
        filterTwoButton.setText("Apply2");
    }


}
