package controller;

import java.io.IOException;
import java.util.Map;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The ResultsController class is responsible for displaying the results window
 * and updating the text areas with meal data.
 */
public class ResultsController {
    @FXML
    private TextArea startersArea;
    @FXML
    private TextArea mainsArea;
    @FXML
    private TextArea dessertsArea;
    @FXML
    private Text timebox;

    private Stage stage;

    /**
     * Opens the results window and updates the text areas with the provided meal data.
     *
     * @param allMeals a map containing meal types and their respective meal counts
     */
    public void openResultsWindow(Map<String, Map<String, Integer>> allMeals, double simulationTime) {
        Platform.runLater(() -> {
            try {
                stage = new Stage();
                stage.setTitle("Results");

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/results.fxml"));
                Parent root = fxmlLoader.load();
                root.getStylesheets().add(getClass().getResource("/aboutStyle.css").toExternalForm());

                ResultsController controller = fxmlLoader.getController();
                controller.addMealsToTextAreas(allMeals, simulationTime);

                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Updates the text areas with the provided meal data.
     *
     * @param allMeals a map containing meal types and their respective meal counts
     */
    public void addMealsToTextAreas(Map<String, Map<String, Integer>> allMeals, double simulationTime) {
        allMeals.forEach((mealType, meals) -> {
            StringBuilder text = new StringBuilder();
            meals.forEach((meal, count) -> text.append(meal).append(": ").append(count).append("\n"));

            switch (mealType.toLowerCase()) {
                case "starters" -> startersArea.appendText(text.toString());
                case "mains" -> mainsArea.appendText(text.toString());
                case "desserts" -> dessertsArea.appendText(text.toString());
            }
        });
        timebox.setText(String.format("%.2f", simulationTime));
    }
}