package org.mateo.jfx;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.Map;

public class ViewController {

    @FXML
    private BarChart<String, Number> financeChart;

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public void loadChartData() {
        financeChart.getData().clear();

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Month");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount");

        financeChart.setTitle("Income and Expenses");

        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("Income");
        Map<String, Double> incomeData = DataFetcher.getIncomeData(username);
        for (Map.Entry<String, Double> entry : incomeData.entrySet()) {
            incomeSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();
        expenseSeries.setName("Expenses");
        Map<String, Double> expenseData = DataFetcher.getExpenseData(username);
        for (Map.Entry<String, Double> entry : expenseData.entrySet()) {
            expenseSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        financeChart.getData().addAll(incomeSeries, expenseSeries);

        // Load the CSS file
        String css = getClass().getResource("/finance_chart.css").toExternalForm();
        financeChart.getStylesheets().add(css);
    }
}
