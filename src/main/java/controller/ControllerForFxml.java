package controller;

import java.io.IOException;
import java.text.DecimalFormat;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import simu.framework.IEngine;
import simu.model.OwnEngine;
import view.ISimulatorUI;
import view.IVisualization;
import view.Visualization;


public class ControllerForFxml implements IControllerForM, IControllerForV, ISimulatorUI {
    private IEngine engine;
    private IVisualization screen;
    private ISimulatorUI ui;
    private AudioClip customerSound;
    private SettingsController settingsController;

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
    private Label countC0;

    @FXML
    private Label countC1;

    @FXML
    private Label countC2;

    @FXML
    private Label countC3;

    @FXML
    private Label countC4;

    @FXML
    private Label countC5;


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
    private Button settingsButton;


    @FXML
    public void initialize() {
        Canvas[] canvases = {canvas, canvas1, canvas2, canvas3, canvas4, canvas5};
        Color[] colors = {Color.DARKGRAY, Color.DARKBLUE, Color.DARKCYAN, Color.DARKGOLDENROD, Color.DARKGREEN, Color.DARKMAGENTA};
        String[] types = {"SAAPUMINEN", "PÖYTIINOHJAUS", "TILAAMINEN", "TARJOILU", "SAFKAAMINEN", "POISTUMINEN"};
        Visualization visualization = new Visualization(canvases, colors, types);
        setIVisualization(visualization);
        settingsController = new SettingsController();
        setUi(this);
        customerSound = new AudioClip(getClass().getResource("/sounds/customer.mp3").toString());
        settingsButton.setOnAction(event -> openSettings());
        muteButton.setOnAction(event -> {if (mute) {mute = false;} else if (!mute) { mute = true;}});

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

    boolean mute = true;
    
    @Override
    public void startSimulation() {

        
        // ERROR HANDLÄYSTÄ
        try {
            double time = ui.getTime();
            long delay = ui.getDelay();
            
            if (time < 0 || delay < 0) {
                throw new IllegalArgumentException("ERROR // Time and delay can't be negative\n");
            }
            // testings :)))
            startButton.setVisible(false);

            engine = new OwnEngine(this, ControllerForFxml.this, settingsController);
            engine.setSimulationTime(time);
            engine.setDelay(delay);
            ((Thread) engine).start();


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
    public synchronized void visualizeCustomer(int customer) {
        Platform.runLater(() -> {
            getVisualization().newCustomer(customer);
            System.out.println("UUSI ASIAKAS VISUALISOITU   " + customer);
            if (!mute && customer == 0) {
                customerSound.play();
            }
            
            
            // LABEL - ASIAKAS MÄÄRÄ
            switch (customer) {
                case 0 -> {
                    int c0 = Integer.parseInt(countC0.getText()) + 1;
                    countC0.setText(String.valueOf(c0));
                }
                case 1 -> {
                    int c1 = Integer.parseInt(countC1.getText()) + 1;
                    countC1.setText(String.valueOf(c1));
                }
                case 2 -> {
                    int c2 = Integer.parseInt(countC2.getText()) + 1;
                    countC2.setText(String.valueOf(c2));
                }
                case 3 -> {
                    int c3 = Integer.parseInt(countC3.getText()) + 1;
                    countC3.setText(String.valueOf(c3));
                }
                case 4 -> {
                    int c4 = Integer.parseInt(countC4.getText()) + 1;
                    countC4.setText(String.valueOf(c4));
                }
                case 5 -> {
                    int c5 = Integer.parseInt(countC5.getText()) + 1;
                    countC5.setText(String.valueOf(c5));
                }
                default -> throw new AssertionError();
            }
        });
    }

    @Override
    public synchronized void visualizeRemoveCustomers(int customer) {
        Platform.runLater(() -> {
            getVisualization().removeCustomer(customer);
        });
    }

    @Override
    public void speedUp() {
        if (engine != null) {
        engine.setDelay((long) (engine.getDelay() * 0.9));
        }
    }

    @Override
    public void slowDown() {
        if (engine != null) {
        engine.setDelay((long) (engine.getDelay() * 1.1));
        }

    }

    public synchronized void updateTextArea(String message) {
        if (consoleLogTextArea != null) {
            Platform.runLater(() -> consoleLogTextArea.appendText(message));
        } else {
            System.err.println("TextArea is not initialized.");
        }
    }
    @FXML
    private void openSettings() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/settings.fxml"));
            Parent root = fxmlLoader.load();
            SettingsController settingsController  = fxmlLoader.getController();
            settingsController.setEngine(engine);
            settingsController.setMainController(this);
            settingsController.initialize();
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root));
            stage.show();
            pauseSimulation();
        } catch (IOException e) {
        }
    }


    @FXML
    private void pauseSimulation() {
        if (engine != null) {
            ((OwnEngine) engine).pauseSimulation();
        }
    }

    @FXML
    public void resumeSimulation() {
        if (engine != null) {
            ((OwnEngine) engine).resumeSimulation();
        }
    }

}