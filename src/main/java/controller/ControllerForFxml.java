package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import simu.framework.IEngine;
import javafx.scene.control.Button;
import simu.model.OwnEngine;

import view.Visualization;

import java.awt.*;
import java.text.DecimalFormat;


public class ControllerForFxml implements IControllerForM, IControllerForV {
    private IEngine engine;
    private Visualization ui;
    private Visualization screen;

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
    private Canvas canvas;


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


    public void startSimulation() {
        engine = new OwnEngine(this);
        engine.setSimulationTime(getTime());
        engine.setDelay(getDelay());
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
            public void run() {
                ui.newCustomer();
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
}