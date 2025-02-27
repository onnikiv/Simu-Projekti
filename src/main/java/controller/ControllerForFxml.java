package controller;

import java.text.DecimalFormat;

import javafx.application.Platform;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import simu.framework.IEngine;
import simu.model.OwnEngine;
import view.ISimulatorUI;
import view.IVisualization;
import view.Visualization;
import javafx.scene.media.AudioClip;


public class ControllerForFxml implements IControllerForM, IControllerForV, ISimulatorUI {
    private IEngine engine;
    private IVisualization screen;
    private ISimulatorUI ui;
    private AudioClip customerSound;;

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
    private Canvas canvas1;

    @FXML
    private Canvas canvas2;

    @FXML
    private Canvas canvas3;
    @FXML
    private Canvas canvas4;
    @FXML
    private Canvas canvas5;

    @FXML
    private Button muteButton;

    @FXML
    public void initialize() {
        Canvas[] canvases = {canvas, canvas1, canvas2, canvas3, canvas4, canvas5};
        Color[] colors = {Color.DARKGRAY, Color.DARKBLUE, Color.DARKCYAN, Color.DARKGOLDENROD, Color.DARKGREEN, Color.DARKMAGENTA};
        String[] types = {"SAAPUMINEN", "PÖYTIINOHJAUS", "TILAAMINEN", "TARJOILU", "SAFKAAMINEN", "POISTUMINEN"};
        Visualization visualization = new Visualization(canvases, colors, types);
        setIVisualization(visualization);
        setUi(this);
        customerSound = new AudioClip(getClass().getResource("/sounds/customer.mp3").toString());
    }

    public void setIVisualization(IVisualization screen) {
        this.screen = screen;
    }

    public void setUi(ISimulatorUI ui) {
        this.ui = ui;
    }

    @Override
    public double getTime() {
        return Double.parseDouble(timeTextField.getText());
    }


    @Override
    public long getDelay() {
        return (long) Double.parseDouble(delayTextField.getText());
    }


    @Override
    public void setEndTime(double time) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        resultLabel.setText(formatter.format(time));
    }

    @Override
    public IVisualization getVisualization() {
        return screen;
    }

    boolean mute = false;
    @Override
    public void startSimulation() {

        // ERROR HANDLÄYSTÄ
        try {
            double time = ui.getTime();
            long delay = ui.getDelay();

            if (time < 0 || delay < 0) {
            throw new IllegalArgumentException("ERROR // Time and delay can't be negative\n");
            }

            engine = new OwnEngine(this, ControllerForFxml.this);
            engine.setSimulationTime(time);
            engine.setDelay(delay);
            ((Thread) engine).start();

            muteButton.setOnAction(event -> {if (mute) {mute = false;} else if (!mute) { mute = true;}});

        } catch (NumberFormatException e) {
            updateTextArea("ERROR // Time and delay must be numbers!\n");

        } catch (IllegalArgumentException e) {
            updateTextArea(e.getMessage());
        }
        //((Thread)moottori).run(); // Ei missään tapauksessa näin. Miksi?
    }

    @Override
    public void showEndTime(double time) {
        Platform.runLater(() -> setEndTime(time));

    }

    @Override
    public void visualizeCustomer(int customer) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getVisualization().newCustomer(customer);
                if (!mute && customer == 0) {
                    customerSound.play();
                }



                // LABEL - ASIAKAS MÄÄRÄ
                customerAmount.setText(String.valueOf(getVisualization().getCustomerAmount()));

            }
        });
    }

    @Override
    public void visualizeRemoveCustomers(int customer) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getVisualization().removeCustomer(customer);
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