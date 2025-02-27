package controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class SettingsController {
    @FXML
    private Button backButton;

    @FXML
    private void handleBackButtonAction() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        backButton.setOnAction(event -> handleBackButtonAction());
    }
}