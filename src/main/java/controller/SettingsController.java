package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import simu.framework.IEngine;
import simu.model.OwnEngine;

/**
 * Controller class for handling the settings UI interactions.
 * Provides methods to update simulation parameters through sliders.
 */

public class SettingsController {

    private IEngine engine;
    private ControllerForFxml controller;

    @FXML
    private Button backButton;

    @FXML
    private Slider seatingSlider, serviceSlider, orderingSlider, exitingSlider,  arrivalSlider,  eatingSlider;

    /**
     * Initializes the controller, setting up the event handlers for the sliders and back button.
     */

    @FXML
    public void initialize() {

        backButton.setOnAction(event -> handleBackButtonAction());
        backButton.setOnAction(event -> handleBackButtonAction());
        arrivalSlider.setOnMouseReleased(event -> updateSliderValue(arrivalSlider, "arrival"));
        eatingSlider.setOnMouseReleased(event -> updateSliderValue(eatingSlider, "eating"));
        exitingSlider.setOnMouseReleased(event -> updateSliderValue(exitingSlider, "exiting"));
        orderingSlider.setOnMouseReleased(event -> updateSliderValue(orderingSlider, "ordering"));
        seatingSlider.setOnMouseReleased(event -> updateSliderValue(seatingSlider, "seating"));
        serviceSlider.setOnMouseReleased(event -> updateSliderValue(serviceSlider, "service"));

    }

    /**
     * Sets the simulation engine.
     * @param engine the simulation engine
     */

    public void setEngine(IEngine engine) {
        this.engine = engine;
    }

    /**
     * Sets the main controller.
     * @param mainController the main controller
     */

    public void setMainController(ControllerForFxml mainController) {
        this.controller = mainController;
    }

    /**
     * Handles the action for the back button, closing the settings window and resuming the simulation.
     */

    @FXML
    private void handleBackButtonAction() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        if (controller != null) {
            controller.resumeSimulation();
        }
    }

    /**
     * Updates the simulation parameter based on the slider value.
     * @param slider the slider whose value has changed
     * @param type the type of parameter to update
     */

    // Ottaa Sliderista arvot ja printtaa tuloksen OwnEnginessä consoliin (Vielä ei muuta mitään Distributionin meaneja)
    public void updateSliderValue(Slider slider, String type) {
        if (engine == null) {
            showAlert("Error", "Engine not set\nSimulation needs to be started first");
            return;
        }
        double value = slider.getValue();
        OwnEngine ownEngine = (OwnEngine) engine;

        switch (type) {
            case "arrival": ownEngine.changeArrivalMean(value); break;
            case "seating": ownEngine.changeSeatingMean(value);break;
            case "ordering": ownEngine.changeOrderingMean(value);break;
            case "service": ownEngine.changeServiceMean(value);break;
            case "eating": ownEngine.changeEatingMean(value);  break;
            case "exiting": ownEngine.changeExitingMean(value);break;
        }
    }

    /**
     * Displays an alert dialog with the specified title and message.
     *
     * @param title the title of the alert dialog
     * @param message the message to be displayed in the alert dialog
     */

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }
}