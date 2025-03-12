package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import simu.framework.IEngine;

/**
 * Controller class for handling the interactions in the About window.
 */
public class AboutController {

    private IEngine engine;
    private ControllerForFxml controller;

    @FXML
    private Button closeButton;

    /**
     * Closes the About window and resumes the simulation if the main controller is set.
     */
    @FXML
    private void closeAboutWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        if (controller != null) {
            controller.resumeSimulation();
        }
    }

    /**
     * Sets the simulation engine.
     *
     * @param engine the simulation engine
     */
    public void setEngine(IEngine engine) {
        this.engine = engine;
    }

    /**
     * Sets the main controller.
     *
     * @param mainController the main controller
     */
    public void setMainController(ControllerForFxml mainController) {
        this.controller = mainController;
    }
}