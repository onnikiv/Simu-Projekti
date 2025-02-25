package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simu.framework.Trace;
import controller.ControllerForFxml;
import view.Visualization;

import java.io.IOException;


public class SimulatorView extends Application {


    @Override
    public void init() {
        Trace.setTraceLevel(Trace.Level.INFO);
    }

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
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}