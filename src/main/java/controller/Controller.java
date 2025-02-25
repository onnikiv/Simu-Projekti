//package controller;
//
//import javafx.application.Platform;
//import simu.framework.IEngine;
//import simu.model.OwnEngine;
//import view.ISimulatorUI;
//
//public class Controller implements IControllerForM, IControllerForV {   // UUSI
//
//    private IEngine engine;
//    private ISimulatorUI ui;
//
//    public Controller(ISimulatorUI ui) {
//        this.ui = ui;
//
//    }
//
//
//    // Moottorin ohjausta:
//
//    @Override
//    public void startSimulation() {
//        engine = new OwnEngine(this); // luodaan uusi moottorisäie jokaista simulointia varten
//        engine.setSimulationTime(ui.getTime());
//        engine.setDelay(ui.getDelay());
//        ui.getVisualization().clearScreen();
//        ((Thread) engine).start();
//        //((Thread)moottori).run(); // Ei missään tapauksessa näin. Miksi?
//    }
//
//    @Override
//    public void slowDown() { // hidastetaan moottorisäiettä
//        engine.setDelay((long) (engine.getDelay() * 1.10));
//    }
//
//    @Override
//    public void speedUp() { // nopeutetaan moottorisäiettä
//        engine.setDelay((long) (engine.getDelay() * 0.9));
//    }
//
//
//    // Simulointitulosten välittämistä käyttöliittymään.
//    // Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata JavaFX-säikeeseen:
//
//    @Override
//    public void showEndTime(double time) {
//        Platform.runLater(() -> ui.setEndTime(time));
//    }
//
//
//    @Override
//    public void visualizeCustomer() {
//        Platform.runLater(new Runnable() {
//            public void run() {
//                ui.getVisualization().newCustomer();
//            }
//        });
//    }
//
//
//}