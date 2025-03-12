package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import simu.Dao.MenuDao;
import simu.model.MenuItem;

public class DatabaseController {

    private MenuDao menuDao = new MenuDao();

    @FXML
    private TextArea dessertTextArea;

    @FXML
    private TextArea mainMealTextArea;

    @FXML
    private TextArea starterTextArea;

    @FXML
    private Button closeButton;


    @FXML
    public void initialize() {
        loadMenuItems();
    }

    public void loadMenuItems() {
        StringBuilder startersText = new StringBuilder();
        for (MenuItem item : menuDao.getAllStarters()) {
            startersText.append(item.getName()).append(" - ").append(item.getPrepTimeMinutes()).append(" mins\n");
        }
        starterTextArea.setText(startersText.toString());

        StringBuilder mainMealsText = new StringBuilder();
        for (MenuItem item : menuDao.getAllMainMeals()) {
            mainMealsText.append(item.getName()).append(" - ").append(item.getPrepTimeMinutes()).append(" mins\n");
        }
        mainMealTextArea.setText(mainMealsText.toString());

        StringBuilder dessertsText = new StringBuilder();
        for (MenuItem item : menuDao.getAllDesserts()) {
            dessertsText.append(item.getName()).append(" - ").append(item.getPrepTimeMinutes()).append(" mins\n");
        }
        dessertTextArea.setText(dessertsText.toString());
    }

    @FXML
    public void backToMainView()  {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}