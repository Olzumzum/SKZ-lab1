package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("screen.fxml"));
        primaryStage.setTitle("Обработка изображения");
        primaryStage.setScene(new Scene(root, 720, 350));
        primaryStage.show();
        Controller controller = new Controller();
        controller.setStage(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
