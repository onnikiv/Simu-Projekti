package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import simu.framework.IEngine;
import simu.model.OwnEngine;

import javafx.event.ActionEvent;

/**
 * Controller class for handling the settings UI interactions.
 * Provides methods to update simulation parameters through sliders.
 */

public class SettingsController {

    private IEngine engine;
    private ControllerForFxml controller;
    private int maxGroupSize = 4;
    private int tableAmount = 15;
    private int tableSize = 4;

    @FXML
    private Button backButton, decreaseGroupSize, increaseGroupSize, decreaseTableSize,
            increaseTableSize, decreaseTables, increaseTables;

    @FXML
    private TextField tableAmountField, tableSizeField, groupSizeField;

    @FXML
    private Slider seatingSlider, serviceSlider, orderingSlider, exitingSlider, arrivalSlider, eatingSlider;

    /**
     * Initializes the controller, setting up the event handlers for the sliders and back button.
     */

    @FXML
    public void initialize() {
        backButton.setOnAction(event -> handleBackButtonAction());
        backButton.setOnAction(event -> handleBackButtonAction());
        decreaseGroupSize.setOnAction(event -> decrementValue(groupSizeField));
        increaseGroupSize.setOnAction(event -> incrementValue(groupSizeField));
        decreaseTableSize.setOnAction(event -> decrementValue(tableSizeField));
        increaseTableSize.setOnAction(event -> incrementValue(tableSizeField));
        decreaseTables.setOnAction(event -> decrementValue(tableAmountField));
        increaseTables.setOnAction(event -> incrementValue(tableAmountField));
        arrivalSlider.setOnMouseReleased(event -> updateSliderValue(arrivalSlider, "arrival"));
        eatingSlider.setOnMouseReleased(event -> updateSliderValue(eatingSlider, "eating"));
        exitingSlider.setOnMouseReleased(event -> updateSliderValue(exitingSlider, "exiting"));
        orderingSlider.setOnMouseReleased(event -> updateSliderValue(orderingSlider, "ordering"));
        seatingSlider.setOnMouseReleased(event -> updateSliderValue(seatingSlider, "seating"));
        serviceSlider.setOnMouseReleased(event -> updateSliderValue(serviceSlider, "service"));
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
     *
     * @param slider the slider whose value has changed
     * @param type   the type of parameter to update
     */

    // Ottaa Sliderista arvot ja printtaa tuloksen OwnEnginessä consoliin (Vielä ei muuta mitään Distributionin meaneja)
    public void updateSliderValue(Slider slider, String type) {
        if (engine == null) {
            return;
        }
        double value = slider.getValue();
        OwnEngine ownEngine = (OwnEngine) engine;

        switch (type) {
            case "arrival":
                ownEngine.changeArrivalMean(value);
                break;
            case "seating":
                ownEngine.changeSeatingMean(value);
                break;
            case "ordering":
                ownEngine.changeOrderingMean(value);
                break;
            case "service":
                ownEngine.changeServiceMean(value);
                break;
            case "eating":
                ownEngine.changeEatingMean(value);
                break;
            case "exiting":
                ownEngine.changeExitingMean(value);
                break;
        }
    }

    public int getMaxGroupSize() {
        return maxGroupSize;
    }

    public int getTableAmount() {
        return tableAmount;
    }

    public int getTableSize() {
        return tableSize;
    }

    public void decrementValue(TextField field) {
        int value = Integer.parseInt(field.getText());
        if (value > 1) {
            value--;
            field.setText(String.valueOf(value));
            updateSettigns(field);
        }
    }

    public void incrementValue(TextField field) {
        int value = Integer.parseInt(field.getText());
        value++;
        field.setText(String.valueOf(value));
        updateSettigns(field);
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        Button source = (Button) event.getSource();
        switch (source.getId()) {
            case "increaseTables":
                incrementValue(tableAmountField);
                break;
            case "decreaseTables":
                decrementValue(tableAmountField);
                break;
            case "increaseTableSize":
                incrementValue(tableSizeField);
                break;
            case "decreaseTableSize":
                decrementValue(tableSizeField);
                break;
            case "increaseGroupSize":
                incrementValue(groupSizeField);
                break;
            case "decreaseGroupSize":
                decrementValue(groupSizeField);
                break;
        }
    }

    public void updateSettigns(TextField field) {
        if (field == tableAmountField) {
            tableAmount = Integer.parseInt(field.getText());
            System.out.println("Table amount: " + tableAmount);
        } else if (field == tableSizeField) {
            tableSize = Integer.parseInt(field.getText());
            System.out.println("Table size: " + tableSize);
        } else if (field == groupSizeField) {
            setMaxGroupSize(Integer.parseInt(field.getText()));
            System.out.println("Group size: " + maxGroupSize);
        }
        updateTextFields();
    }

    public void setMaxGroupSize(int size) {
        this.maxGroupSize = size;
        if (this.maxGroupSize > this.tableSize) {
            this.tableSize = this.maxGroupSize;
        }
        updateTextFields();
    }

    public void updateTextFields() {
        tableAmountField.setText(String.valueOf(tableAmount));
        tableSizeField.setText(String.valueOf(tableSize));
        groupSizeField.setText(String.valueOf(maxGroupSize));
        System.out.println("Table amount: " + tableAmount + ", Table size: " + tableSize + ", Group size: " + maxGroupSize);
    }
}