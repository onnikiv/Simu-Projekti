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
    @FXML
    private Text startersCount;

    @FXML
    private Text mainsCount;

    @FXML
    private Text dessertsCount;

    @FXML
    private TextArea performanceArea;

    private Stage stage;

    /**
     * Opens the results window and updates the text areas with the provided meal data.
     *
     * @param allMeals a map containing meal types and their respective meal counts
     */
    public void openResultsWindow(Map<String, Map<String, Integer>> allMeals, double simulationTime, int starter, int main, int dessert,  Map<String, Double> metrics) {        Platform.runLater(() -> {
            try {
                stage = new Stage();
                stage.setTitle("Results");

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/results.fxml"));
                Parent root = fxmlLoader.load();
                root.getStylesheets().add(getClass().getResource("/aboutStyle.css").toExternalForm());

                ResultsController controller = fxmlLoader.getController();
                controller.updateOutputAreas(allMeals, simulationTime, starter, main, dessert, metrics);

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
    public void updateOutputAreas(Map<String, Map<String, Integer>> allMeals, double simulationTime, int starter, int main, int dessert, Map<String, Double> metrics) {
        
        Platform.runLater(() -> {
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
        startersCount.setText("Amount: " + starter);
        mainsCount.setText("Amount: " + main);
        dessertsCount.setText("Amount: " + dessert);
            performanceArea.setText(String.format(
                    "Average Queue Time: %.2f\nMax Queue Time: %.2f\nMin Queue Time: %.2f\n" +
                            "Average Service Time: %.2f\nMax Service Time: %.2f\nMin Service Time: %.2f\n" +
                            "Average Time in System: %.2f",
                    metrics.get("averageQueueTime"), metrics.get("maxQueueTime"), metrics.get("minQueueTime"),
                    metrics.get("averageServiceTime"), metrics.get("maxServiceTime"), metrics.get("minServiceTime"),
                    metrics.get("averageTimeInSystem")
            ));
    });


    }
}
