package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        showSplashScreen(primaryStage);
    }

    private void showSplashScreen(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SplashScreen.fxml"));
        Parent splashRoot = loader.load();

        Stage splashStage = new Stage();
        splashStage.setScene(new Scene(splashRoot));
        splashStage.show();

        new Thread(() -> {
            try {
                Thread.sleep(3000); // Simulate loading time

                Platform.runLater(() -> {
                    try {
                        showLoginScreen(primaryStage, splashStage);
                        //splashStage.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void showLoginScreen(Stage primaryStage, Stage splashStage) throws Exception {
    	primaryStage = (Stage) splashStage.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent loginRoot = loader.load();
        primaryStage.setScene(new Scene(loginRoot));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
