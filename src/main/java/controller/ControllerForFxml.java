package controller;

import java.text.DecimalFormat;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import simu.framework.IEngine;
import simu.model.OwnEngine;
import view.ISimulatorUI;
import view.IVisualization;
import view.Visualization;


public class ControllerForFxml implements IControllerForM, IControllerForV, ISimulatorUI {
    private IEngine engine;
    private IVisualization screen;
    private Visualization visualization;
    private ISimulatorUI ui;

    @FXML
    private TextArea consoleLogTextArea;

    @FXML
    private TextField timeTextField;

    @FXML
    private TextField delayTextField;

    @FXML
    private Label resultLabel;

    @FXML
    private Button speedUpButton;

    @FXML
    private Button slowDownButton;

    @FXML
    private Button startButton;

    @FXML
    private Label customerAmount;


    @FXML
    private Canvas canvas;

    @FXML
    public void initialize() {
        visualization = new Visualization(canvas);
        setIVisualization(visualization);
        setUi(this);
    }

    public void setIVisualization(IVisualization screen) {
        this.screen = screen;
    }

    public void setUi(ISimulatorUI ui) {
        this.ui = ui;
    }

    public double getTime() {
        return Double.parseDouble(timeTextField.getText());
    }


    public long getDelay() {
        return (long) Double.parseDouble(delayTextField.getText());
    }


    public void setEndTime(double time) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        resultLabel.setText(formatter.format(time));
    }

    @Override
    public IVisualization getVisualization() {
        return screen;
    }

    public void startSimulation() {
        engine = new OwnEngine(this, ControllerForFxml.this);
        engine.setSimulationTime(ui.getTime());
        engine.setDelay(ui.getDelay());
        ((Thread) engine).start();
        //((Thread)moottori).run(); // Ei missään tapauksessa näin. Miksi?
    }

    @Override
    public void showEndTime(double time) {
        Platform.runLater(() -> setEndTime(time));

    }

    @Override
    public void visualizeCustomer() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            getVisualization().newCustomer();
            
            // LABEL - ASIAKAS MÄÄRÄ
            customerAmount.setText(String.valueOf(visualization.getCustomerAmount()));

            }
        });
    }

    @Override
    public void speedUp() {
        engine.setDelay((long) (engine.getDelay() * 0.9));
    }

    @Override
    public void slowDown() {
        engine.setDelay((long) (engine.getDelay() * 1.1));

    }

    public void updateTextArea(String message) {
        if (consoleLogTextArea != null) {
            Platform.runLater(() -> consoleLogTextArea.appendText(message));
        } else {
            System.err.println("TextArea is not initialized.");
        }
    }
}