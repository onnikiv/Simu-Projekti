package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import simu.framework.IEngine;
import simu.model.OwnEngine;

public class SettingsController {

    private IEngine engine;
    private ControllerForFxml controller;

    @FXML
    private Button backButton;

    @FXML
    private Slider seatingSlider, serviceSlider, orderingSlider, exitingSlider,  arrivalSlider,  eatingSlider;

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

    public void setEngine(IEngine engine) {
        this.engine = engine;
    }

    public void setMainController(ControllerForFxml mainController) {
        this.controller = mainController;
    }


    @FXML
    private void handleBackButtonAction() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
        if (controller != null) {
            controller.resumeSimulation();
        }
    }

    // Ottaa Sliderista arvot ja printtaa tuloksen OwnEnginessä consoliin (Vielä ei muuta mitään Distributionin meaneja)
    public void updateSliderValue(Slider slider, String type) {
        if (engine == null) {
            return;
        }
        double value = slider.getValue();
        OwnEngine ownEngine = (OwnEngine) engine;

        switch (type) {
            case "arrival": ownEngine.changeArrivalMean(value); break;
            case "eating": ownEngine.changeEatingMean(value);  break;
            case "exiting": ownEngine.changeExitingMean(value);break;
            case "ordering": ownEngine.changeOrderingMean(value);break;
            case "seating": ownEngine.changeSeatingMean(value);break;
            case "service": ownEngine.changeServiceMean(value);break;
        }
    }
}