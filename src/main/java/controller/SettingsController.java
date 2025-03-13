package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import simu.framework.IEngine;
import simu.model.OwnEngine;

import javafx.event.ActionEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller class for handling the settings UI interactions.
 * Provides methods to update simulation parameters through sliders.
 */
public class SettingsController {

    private IEngine engine;
    private ControllerForFxml controller;
    private final Map<String, Integer> settingsMap;

    @FXML
    private Button backButton, decreaseGroupSize, increaseGroupSize, decreaseTableSize,
            increaseTableSize, decreaseTables, increaseTables;

    @FXML
    public TextField tableAmountField;
    @FXML
    public TextField tableSizeField;
    @FXML
    private TextField groupSizeField;

    @FXML
    private Slider seatingSlider, serviceSlider, orderingSlider, exitingSlider, arrivalSlider, eatingSlider;

    public SettingsController() {
        settingsMap = new HashMap<>();
        loadSettings();
    }

    /**
     * Loads the default settings for the simulation.
     */
    private void loadSettings() {
        settingsMap.put("maxGroupSize", 4);
        settingsMap.put("tableAmount", 15);
        settingsMap.put("tableSize", 4);
    }

    @FXML
    public void initialize() {
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

        updateTextFields();
    }

    /**
     * Sets the engine for the controller.
     * @param engine the engine to set
     */
    public void setEngine(IEngine engine) {
        this.engine = engine;
    }

    /**
     * Sets the main controller for the controller.
     * @param mainController the main controller to set
     */
    public void setMainController(ControllerForFxml mainController) {
        this.controller = mainController;
    }

    /**
     * Handles the back button action.
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
     * Updates the simulation parameters based on the slider value.
     * @param slider the slider to update
     * @param type the type of parameter to update
     */
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

    /**
     * Returns the maximum group size.
     * @return the maximum group size
     */
    public int getMaxGroupSize() {
        return settingsMap.get("maxGroupSize");
    }

    /**
     * Returns the table amount.
     * @return the table amount
     */
    public int getTableAmount() {
        return settingsMap.get("tableAmount");
    }

    public int getTableSize() {
        return settingsMap.get("tableSize");
    }

    public void setTableAmount(int tableAmount) {
        settingsMap.put("tableAmount", tableAmount);
    }

    public void setTableSize(int tableSize) {
        settingsMap.put("tableSize", tableSize);
    }

    /**
     * Decrements the value of the given text field by one.
     * @param field the text field to decrement
     */
    public void decrementValue(TextField field) {
        int value = Integer.parseInt(field.getText());
        if (value > 1) {
            value--;
            field.setText(String.valueOf(value));
            updateSettingsView(field);
        }
    }

    /**
     * Increments the value of the given text field by one.
     * @param field the text field to increment
     */
    public void incrementValue(TextField field) {
        int value = Integer.parseInt(field.getText());
        value++;
        field.setText(String.valueOf(value));
        updateSettingsView(field);
    }

    /**
     * Handles the button actions for incrementing and decrementing values.
     * @param event the action event
     */
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

    /**
     * Updates the settings view based on the given text field.
     * @param field the text field to update
     */
    public void updateSettingsView(TextField field) {
        if (field == tableAmountField) {
            settingsMap.put("tableAmount", Integer.parseInt(field.getText()));
        } else if (field == tableSizeField) {
            int tableSize = Integer.parseInt(field.getText());
            settingsMap.put("tableSize", tableSize);
            if (tableSize < settingsMap.get("maxGroupSize")) {
                setGroupSize(tableSize);
            }
        } else if (field == groupSizeField) {
            setGroupSize(Integer.parseInt(field.getText()));
        }
        updateTextFields();
    }

    /**
     * Sets the maximum group size to the given value.
     * @param size the maximum group size
     */
    public void setGroupSize(int size) {
        settingsMap.put("maxGroupSize", size);
        if (size > settingsMap.get("tableSize")) {
            settingsMap.put("tableSize", size);
        }
        updateTextFields();
    }

    /**
     * Updates the text fields with the current settings values.
     */
    public void updateTextFields() {
        tableAmountField.setText(String.valueOf(settingsMap.get("tableAmount")));
        tableSizeField.setText(String.valueOf(settingsMap.get("tableSize")));
        groupSizeField.setText(String.valueOf(settingsMap.get("maxGroupSize")));
    }
}