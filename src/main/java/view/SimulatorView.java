package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simu.framework.Trace;
import controller.ControllerForFxml;
import view.Visualization;

import java.io.IOException;

/**
 * The SimulatorView class represents the view of the simulator.
 * It contains the main method for starting the simulator.
 */

public class SimulatorView extends Application {
    @FXML
    private Canvas canvas;

    /**
     * Sets the trace level to INFO.
     */

    @Override
    public void init() {
        Trace.setTraceLevel(Trace.Level.INFO);
    }

    /**
     * Starts the simulator.
     *
     * @param stage the stage
     * @throws Exception if an error occurs
     */

    @Override
    public void start(Stage stage) throws Exception {
        try {
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    Platform.exit();
                    System.exit(0);
                }
            });
            stage.setTitle("Simulator");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view.fxml"));
            Parent root = fxmlLoader.load();
            root.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}