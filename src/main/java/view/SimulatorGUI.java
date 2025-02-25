package view;


import java.text.DecimalFormat;

import controller.Controller;
import controller.IControllerForV;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simu.framework.Trace;
import simu.framework.Trace.Level;


public class SimulatorGUI extends Application implements ISimulatorUI {

    //Kontrollerin esittely (tarvitaan käyttöliittymässä)
    private IControllerForV controller;

    // Käyttöliittymäkomponentit:
    private TextField time;
    private TextField delay;
    private Label result;
    private Label timeLabel;
    private Label delayLabel;
    private Label resultLabel;

    private Button startButton;
    private Button slowDownButton;
    private Button SpeedUpButton;

    private IVisualization screen;


    @Override
    public void init() {

        Trace.setTraceLevel(Level.INFO);

        controller = new Controller(this);
    }

    @Override
    public void start(Stage primaryStage) {
        // Käyttöliittymän rakentaminen
        try {

            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    Platform.exit();
                    System.exit(0);
                }
            });


            primaryStage.setTitle("Simulaattori");

            startButton = new Button();
            startButton.setText("Käynnistä simulointi");
            startButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    controller.startSimulation();
                    startButton.setDisable(true);
                }
            });

            slowDownButton = new Button();
            slowDownButton.setText("Hidasta");
            slowDownButton.setOnAction(e -> controller.slowDown());

            SpeedUpButton = new Button();
            SpeedUpButton.setText("Nopeuta");
            SpeedUpButton.setOnAction(e -> controller.speedUp());

            timeLabel = new Label("Simulointiaika:");
            timeLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            time = new TextField("20");
            time.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            time.setPrefWidth(150);

            delayLabel = new Label("Viive:");
            delayLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            delay = new TextField("1");
            delay.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            delay.setPrefWidth(150);

            resultLabel = new Label("Kokonaisaika:");
            resultLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            result = new Label();
            result.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            result.setPrefWidth(150);

            HBox hBox = new HBox();
            hBox.setPadding(new Insets(15, 12, 15, 12)); // marginaalit ylÃ¤, oikea, ala, vasen
            hBox.setSpacing(10);   // noodien välimatka 10 pikseliä

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setVgap(10);
            grid.setHgap(5);

            grid.add(timeLabel, 0, 0);   // sarake, rivi
            grid.add(time, 1, 0);          // sarake, rivi
            grid.add(delayLabel, 0, 1);      // sarake, rivi
            grid.add(delay, 1, 1);           // sarake, rivi
            grid.add(resultLabel, 0, 2);      // sarake, rivi
            grid.add(result, 1, 2);           // sarake, rivi
            grid.add(startButton, 0, 3);  // sarake, rivi
            grid.add(SpeedUpButton, 0, 4);   // sarake, rivi
            grid.add(slowDownButton, 1, 4);   // sarake, rivi

            screen = new Visualization(400, 200);

            // TÃ¤ytetÃ¤Ã¤n boxi:
            hBox.getChildren().addAll(grid, (Canvas) screen);

            Scene scene = new Scene(hBox);
            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Käyttöliittymän rajapintametodit (kutsutaan kontrollerista)

    @Override
    public double getTime() {
        return Double.parseDouble(time.getText());
    }

    @Override
    public long getDelay() {
        return Long.parseLong(delay.getText());
    }

    @Override
    public void setEndTime(double time) {
        DecimalFormat formatter = new DecimalFormat("#0.00");
        this.result.setText(formatter.format(time));
    }


    @Override
    public IVisualization getVisualization() {
        return screen;
    }
}