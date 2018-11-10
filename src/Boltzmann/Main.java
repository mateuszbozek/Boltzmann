package Boltzmann;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("EngTheBoltzann.fxml"));
        Pane root = fxmlloader.load();
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Modelowanie krzepnięcia materiału z wykorzystaniem metody kratowego równania Boltzmanna");
        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(2);
        });

    }


    public static void main(String[] args) {
        launch(args);

    }
}
