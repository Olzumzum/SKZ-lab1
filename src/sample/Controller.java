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
    //кнопка сохранения результата обработки
    @FXML
    private Button saveButton;
    //контейнер для отображения оригинального изображения
    @FXML
    private ImageView originalImage;
    //контейнер для отображения результата 1
    @FXML
    private ImageView firstModifiedImage;
    //контейнер для отображения результата 2
    @FXML
    private ImageView secondModifiedImage;

    private FileLoader fileLoader = FileLoader.getInstance();
    private File originFile = null;


    void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * открыть диалоговое окно для выбора изображения для обработки
     */
    @FXML
    public void onClickLoadFile() {
        fileLoader.load(primaryStage);
        originFile = fileLoader.getFile();
        showImage(originalImage, originFile);
    }


    /**
     * применить фильтр 1
     */
    @FXML
    public void onClickApplyFilterOne() {
        filterOneButton.setText("Apply1");
        // вызов операций фильтрации 1
        //передаем originFile получаем другой файл
        // пока заглушка
        File convertedFile = originFile;
        showImage(firstModifiedImage, convertedFile);
    }

    /**
     * применить фильтр 2
     */
    @FXML
    public void onClickApplyFilterTwo() {
        filterTwoButton.setText("Apply2");
        // вызов операций фильтрации 2
        //передаем originFile получаем другой файл
        // пока заглушка
        File convertedFile = originFile;
        showImage(secondModifiedImage, convertedFile);
    }

    @FXML
    public void onClickSaveButton(){
        saveButton.setText("Saved");
    }

    /**
     * отобразить изображение в контейнере
     * @param view - контейнер
     * @param file - изображение
     */
    @FXML
    public void showImage(ImageView view, File file) {
        try {
            Image image = new Image(file.toURI().toString(),120,120,false,false);
            System.out.println("Проблема чтения файла " + image);
            view.setImage(image);
            view.setPreserveRatio(true);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
