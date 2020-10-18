package sample.loaderFile;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;
import java.util.List;

final public class FileLoader {

    List<FileChooser.ExtensionFilter> filters = Arrays.asList(
            new FileChooser.ExtensionFilter("JPG", "*.jpg"),
            new FileChooser.ExtensionFilter("PNG", "*.png"));

    @FXML
    private static void build(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Document");

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Images",
                "*.jpg",
                "*.png",
                "*.svg",
                "*.bmp"
        ));


        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            System.out.println("Процесс открытия файла");
        }
    }

    public static void load(Stage primaryStage) {
        build(primaryStage);

    }


}
