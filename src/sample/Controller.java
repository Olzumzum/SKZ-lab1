package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.loaderFile.FileLoader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
    //матрица фильтров
    @FXML
    private TextField value00, value01, value02, value10, value11, value12, value20, value21, value22;
//    private

    private double[][] matrixFilter = new double[3][3];

    private FileLoader fileLoader = FileLoader.getInstance();
    private File originFile = null;

    void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * получить данные с экрана из таблицы фильтров
     */
    private void getFilterMatrix() {
        Set<TextField> listField = new HashSet(
                Arrays.asList(value00, value01, value02, value10, value11, value12, value20, value21, value22)
        );

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String name = "value" + i +j;
                for (TextField el : listField) {
                    if(name.compareTo(el.getId()) == 0){
                        if(!checkMatrix(el))
                           throw  new NumberFormatException("Ошибка ввода данных в таблицу");

                        matrixFilter[i][j] = Double.parseDouble(el.getText());
                    }
                }

            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
               System.out.println(matrixFilter[i][j]);
            }
        }
    }

    /**
     * проверка введеных значений в матрицу
     * @param field - поле, куда введено значение
     * @return - флаг, является ли значение поля числовым
     */
    private boolean checkMatrix(TextField field){
            char[] value = field.getText().toCharArray();
            for(char v: value){
                if(!Character.isDigit(v)){
                    if(v != '.')
                        return false;
                }
        }
        return true;
    }

    /**
     * открыть диалоговое окно для выбора изображения для обработки
     */
    @FXML
    public void onClickLoadFile() {
        fileLoader.load(primaryStage);
        originFile = fileLoader.getFile();
        if (originFile != null)
            showImage(originalImage, originFile);
    }

    /**
     * применить фильтр 1
     */
    @FXML
    public void onClickApplyFilterOne() {
        getFilterMatrix();
        // вызов операций фильтрации 1
        //передаем originFile получаем другой файл
        // пока заглушка
        File convertedFile = originFile;
        if (convertedFile != null)
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
        if (convertedFile != null)
            showImage(secondModifiedImage, convertedFile);
    }

    /**
     * сохранить измененные изображения
     */
    @FXML
    public void onClickSaveButton() {
        saveButton.setText("Saved");
        Image g = firstModifiedImage.getImage();
        System.out.println("Изображение создано " + g);
        if (g != null) {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(g, null);
            System.out.println("Потоковый файл создан " + bufferedImage);
            fileLoader.saveFile(bufferedImage);
        }
    }

    /**
     * отобразить изображение в контейнере
     *
     * @param view - контейнер
     * @param file - изображение
     */
    @FXML
    public void showImage(ImageView view, File file) {
        try {
            Image image = new Image(file.toURI().toString(), 120, 120, false, false);
            System.out.println("Проблема чтения файла " + image);
            view.setImage(image);
            view.setPreserveRatio(true);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
