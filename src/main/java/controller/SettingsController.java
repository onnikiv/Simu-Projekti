package controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import simu.framework.IEngine;

public class SettingsController {

    private IEngine engine;
    private ControllerForFxml controller;

    @FXML
    private Button backButton;


    @FXML
    private void handleBackButtonAction() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        if(controller != null)  {
            controller.resumeSimulation();
        }
    }

    @FXML
    public void initialize() {
        backButton.setOnAction(event -> handleBackButtonAction());
    }

    public void setEngine(IEngine engine) {
        this.engine = engine;
    }

    public void setMainController(ControllerForFxml mainController) {
        this.controller = mainController;
    }
}