package controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ResultsController {
    private Stage stage;

    public void openResultsWindow() {
        Platform.runLater(() -> {
            try {
                stage = new Stage();
                stage.setTitle("Results");

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/results.fxml"));
                Parent root = fxmlLoader.load();
                root.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}