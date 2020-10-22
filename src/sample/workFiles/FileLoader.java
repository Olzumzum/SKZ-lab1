package sample.workFiles;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class FileLoader {
    private static FileLoader instance;
    //оригинал загруженного файла
    private File uploadedFile;
    //для открытия диалогового окна
    private static FileChooser fileChooser;
    //состояние для открытия элементов в родителе
    private Stage primaryStage;

    private FileLoader() {
    }

    public static FileLoader getInstance() {
        if (instance == null) {
            instance = new FileLoader();
        }
        initFileChooser();
        return instance;
    }


    public File getFile() {
        return uploadedFile;
    }

    private static void initFileChooser(){
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
    }

    private void build() {
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
        this.primaryStage = primaryStage;
        build();

    }

    /**
     * сохранить файл
     * @param bufferedImage
     */
    public void saveFile(BufferedImage bufferedImage) {
        if (bufferedImage != null) {

            File file = fileChooser.showSaveDialog(primaryStage);
            System.out.println("Файл  " + file);
            if(file != null) {
                try {
                    ImageIO.write(bufferedImage, "bmp", file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }


}
