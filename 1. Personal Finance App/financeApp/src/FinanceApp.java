import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.Map;

public class FinanceApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Personal Finance Management App");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Month");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Income and Expenses");

        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Income");
        Map<String, Double> incomeData = DataFetcher.getIncomeData();
        for (Map.Entry<String, Double> entry : incomeData.entrySet()) {
            incomeSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();
        expenseSeries.setName("Expenses");
        Map<String, Double> expenseData = DataFetcher.getExpenseData();
        for (Map.Entry<String, Double> entry : expenseData.entrySet()) {
            expenseSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().addAll(incomeSeries, expenseSeries);

        Scene scene = new Scene(barChart, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
