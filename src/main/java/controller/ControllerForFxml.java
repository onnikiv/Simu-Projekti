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
import javafx.stage.Modality;
import javafx.stage.Stage;
import simu.framework.IEngine;
import simu.model.OwnEngine;
import view.ISimulatorUI;
import view.IVisualization;
import view.Visualization;

/**
 * Controller class for handling the FXML UI interactions.
 * Implements interfaces for communication with the simulation engine and visualization.
 */

public class ControllerForFxml implements IControllerForM, IControllerForV, ISimulatorUI {
    private IEngine engine;
    private IVisualization screen;
    private ISimulatorUI ui;
    private AudioClip customerSound;
    private SettingsController settingsController;
    private boolean mute = true;

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
    private Label countC0, countC1, countC2,
            countC3, countC4, countC5;

    @FXML
    private Label queue0, queue1, queue2, queue3, queue4;


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
    private Button aboutButton;



    /**
     * Initializes the controller, setting up the visualization and event handlers.
     */

    @FXML
    public void initialize() {
        Canvas[] canvases = {canvas, canvas1, canvas2, canvas3, canvas4, canvas5};
        Color[] colors = {Color.DARKBLUE, Color.DARKCYAN, Color.DEEPSKYBLUE, Color.MEDIUMPURPLE, Color.PURPLE, Color.DARKMAGENTA};
        String[] types = {"SAAPUMINEN", "PÖYTIINOHJAUS", "TILAAMINEN", "TARJOILU", "SAFKAAMINEN", "POISTUMINEN"};
        Visualization visualization = new Visualization(canvases, colors, types);
        setIVisualization(visualization);
        settingsController = new SettingsController();
        setUi(this);
        customerSound = new AudioClip(getClass().getResource("/sounds/customer.mp3").toString());
        settingsButton.setOnAction(event -> openSettings());
        muteButton.setOnAction(event -> {
            if (mute) {
                mute = false;
            } else if (!mute) {
                mute = true;
            }
        });
    }


    /**
     * Sets the visualization interface.
     *
     * @param screen the visualization interface
     */

    public void setIVisualization(IVisualization screen) {
        this.screen = screen;
    }


    /**
     * Sets the simulator UI interface.
     *
     * @param ui the simulator UI interface
     */

    public void setUi(ISimulatorUI ui) {
        this.ui = ui;
    }

    /**
     * Returns the time of the simulation.
     *
     * @return the time of the simulation
     */

    @Override
    public double getTime() {
        return Double.parseDouble(timeTextField.getText());
    }

    /**
     * Returns the delay of the simulation.
     *
     * @return the delay of the simulation
     */

    @Override
    public long getDelay() {
        return (long) Double.parseDouble(delayTextField.getText());
    }

    /**
     * Sets the end time of the simulation.
     *
     * @param time the end time of the simulation
     */

    @Override
    public void setEndTime(double time) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        resultLabel.setText(formatter.format(time));
    }

    public void updateSimulationTime(double time) {
        Platform.runLater(() -> {
            DecimalFormat formatter = new DecimalFormat("#0.00");
            resultLabel.setText(formatter.format(time));
        });
    }

    /**
     * Returns the visualization interface.
     *
     * @return the visualization interface
     */

    @Override
    public IVisualization getVisualization() {
        return screen;
    }


    /**
     * Starts the simulation with the specified time and delay.
     */

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

    /**
     * Shows the end time of the simulation.
     *
     * @param time the end time of the simulation
     */

    @Override
    public void showEndTime(double time) {
        Platform.runLater(() -> {
            setEndTime(time);
            consoleLogTextArea.appendText("\nSimulation has Ended...\n");
        });

    }

    /**
     * Visualizes the specified customer.
     *
     * @param customer the customer to visualize
     */

    @Override
    public synchronized void visualizeCustomer(int customer, int listSize) {
        Platform.runLater(() -> {
            getVisualization().newCustomer(customer, listSize);
            if (!mute && customer == 0) {
                customerSound.play();
            }
        });
    }

    /**
     * Removes the specified customer from the visualization.
     *
     * @param customer the customer to remove
     */

    @Override
    public synchronized void visualizeRemoveCustomers(int customer, int listSize) {
        Platform.runLater(() -> {
            getVisualization().removeCustomer(customer, listSize);
        });
    }

    /**
     * Updates the service point sums with the specified values.
     *
     * @param c0 the sum of customers at service point 0
     * @param c1 the sum of customers at service point 1
     * @param c2 the sum of customers at service point 2
     * @param c3 the sum of customers at service point 3
     * @param c4 the sum of customers at service point 4
     * @param c5 the sum of customers at service point 5
     */

    @Override
    public synchronized void updateServicePointSums(int c0, int c1, int c2, int c3, int c4, int c5,
                                                    int q0, int q1, int q2, int q3, int q4) {
        Platform.runLater(() -> {
            countC0.setText(String.valueOf(c0));
            countC1.setText(String.valueOf(c1));
            countC2.setText(String.valueOf(c2));
            countC3.setText(String.valueOf(c3));
            countC4.setText(String.valueOf(c4));
            countC5.setText(String.valueOf(c5));
            queue0.setText(String.valueOf(q0));
            queue1.setText(String.valueOf(q1));
            queue2.setText(String.valueOf(q2));
            queue3.setText(String.valueOf(q3));
            queue4.setText(String.valueOf(q4));
        });
    }

    /**
     * Speeds up the simulation.
     */

    @Override
    public void speedUp() {
        if (engine != null) {
            engine.setDelay((long) (engine.getDelay() * 0.9));
        }
    }

    /**
     * Slows down the simulation.
     */

    @Override
    public void slowDown() {
        if (engine != null) {
            engine.setDelay((long) (engine.getDelay() * 1.1));
        }
    }

    /**
     * Updates the console log text area with the specified message.
     *
     * @param message the message to append to the console log
     */

    @FXML
    public synchronized void updateTextArea(String message) {

        Platform.runLater(() -> consoleLogTextArea.appendText(message));

    }

    /**
     * Opens the settings window.
     */

    @FXML
    public void openSettings() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/settings.fxml"));
            Parent root = fxmlLoader.load();
            root.getStylesheets().add(getClass().getResource("/settingsStyle.css").toExternalForm());
            SettingsController settingsController = fxmlLoader.getController();
            settingsController.setEngine(engine);
            settingsController.setMainController(this);
            settingsController.initialize();
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root));
            pauseSimulation();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void openDatabaseWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/databaseView.fxml"));
            Parent root = fxmlLoader.load();
            root.getStylesheets().add(getClass().getResource("/aboutStyle.css").toExternalForm());
            Stage stage = new Stage();
            stage.setTitle("Menu  Items");
            stage.setScene(new Scene(root));
            pauseSimulation();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the about window.
     */

    @FXML
    public void openAboutWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/aboutView.fxml"));
        Parent root = loader.load();
        root.getStylesheets().add(getClass().getResource("/aboutstyle.css").toExternalForm());
        AboutController aboutController = loader.getController();
        aboutController.setEngine(engine);
        aboutController.setMainController(this);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        pauseSimulation();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /**
     * Pauses the simulation.
     */

    @FXML
    private void pauseSimulation() {
        if (engine != null) {
            ((OwnEngine) engine).pauseSimulation();
        }
    }

    /**
     * Resumes the simulation.
     */

    @FXML
    public void resumeSimulation() {
        if (engine != null) {
            ((OwnEngine) engine).resumeSimulation();
        }
    }

}