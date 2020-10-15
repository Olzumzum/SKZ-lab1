package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.awt.*;

public class Controller {

    @FXML
    private Button loadFileButton;
    @FXML
    private Button filterOneButton;
    @FXML
    private Button filterTwoButton;


    @FXML
    public void onClickLoadFile(){
        loadFileButton.setText("Thanks!");
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
