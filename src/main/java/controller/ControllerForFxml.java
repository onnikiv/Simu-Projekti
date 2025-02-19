//package controller;
//
//import javafx.application.Platform;
//import javafx.fxml.FXML;
//import javafx.scene.control.TextField;
//import javafx.scene.control.Label;
//import simu.framework.IEngine;
//import simu.model.OwnEngine;
//import view.ISimulatorUI;
//import view.IVisualization;
//import javafx.scene.control.Button;
//
//import java.text.DecimalFormat;
//
//public class ControllerForFxml implements IControllerForM, IControllerForV {
//
//    private IEngine engine;
//    private ISimulatorUI ui;
//
//    @FXML
//    private TextField timeTextField;
//
//    @FXML
//    private TextField delayTextField;
//
//    @FXML
//    private Label resultLabel;
//
//    @FXML
//    private Button speedupButton;
//
//    @FXML
//    private Button slowDownButton;
//
//    @FXML
//    private IVisualization screen;
//
//    public ControllerForFxml() {
//
//    }
//
//    public ControllerForFxml(ISimulatorUI ui) {
//        this.ui = ui;
//    }
//
//
//    @Override
//    public void startSimulation() {
//        engine = new OwnEngine(this); // Create a new simulation engine
//        engine.setSimulationTime(getTime());
//        engine.setDelay(getDelay());
//        screen.clearScreen(); // Clear visualization
//        ((Thread) engine).start();
//    }
//
//    @Override
//    public void slowDown() {
//        engine.setDelay((long) (engine.getDelay() * 1.10));
//    }
//
//    @Override
//    public void speedUp() {
//        engine.setDelay((long) (engine.getDelay() * 0.9));
//    }
//
//    @Override
//    public void showEndTime(double time) {
//        Platform.runLater(() -> setEndTime(time));
//    }
//
//    @Override
//    public void visualizeCustomer() {
//        Platform.runLater(() -> screen.newCustomer());
//    }
//
//
//    public double getTime() {
//        return Double.parseDouble(timeTextField.getText());
//    }
//
//
//    public long getDelay() {
//        return Long.parseLong(delayTextField.getText());
//    }
//
//
//    public void setEndTime(double time) {
//        DecimalFormat formatter = new DecimalFormat("#0.00");
//        resultLabel.setText(formatter.format(time));
//    }
//
//
//    public IVisualization getVisualization() {
//        return screen;
//    }
//}