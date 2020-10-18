package sample.loaderFile;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public final class FileLoader {
    private static FileLoader instance;
    private File uploadedFile;

    private FileLoader(){}

    public static FileLoader getInstance(){
        if(instance == null){
            instance = new FileLoader();
        }
        return instance;
    }



    public File getFile(){
//        InputStream inputStream = null;
//        try {
//             inputStream = new FileInputStream(uploadedFile);
//        } catch (FileNotFoundException e) {
//            System.out.println("Проблема чтения файла " + uploadedFile.getName());
//            e.printStackTrace();
//        }
        return uploadedFile;
    }

    @FXML
    private void build(Stage primaryStage) {
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
            System.out.println("Процесс открытия файла " + file.getName());
            uploadedFile = file;
        }
    }

    public void load(Stage primaryStage) {
        build(primaryStage);

    }


}
