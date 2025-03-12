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
        return settingsMap.get("maxGroupSize");
    }

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

    public void decrementValue(TextField field) {
        int value = Integer.parseInt(field.getText());
        if (value > 1) {
            value--;
            field.setText(String.valueOf(value));
            updateSettingsView(field);
        }
    }

    public void incrementValue(TextField field) {
        int value = Integer.parseInt(field.getText());
        value++;
        field.setText(String.valueOf(value));
        updateSettingsView(field);
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

    public void setGroupSize(int size) {
        settingsMap.put("maxGroupSize", size);
        if (size > settingsMap.get("tableSize")) {
            settingsMap.put("tableSize", size);
        }
        updateTextFields();
    }

    public void updateTextFields() {
        tableAmountField.setText(String.valueOf(settingsMap.get("tableAmount")));
        tableSizeField.setText(String.valueOf(settingsMap.get("tableSize")));
        groupSizeField.setText(String.valueOf(settingsMap.get("maxGroupSize")));
    }
}