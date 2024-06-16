package org.mateo.jfx;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;

import java.io.IOException;

public class OptionsController {

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    protected void handleAddTransaction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = loader.load();

            MainController mainController = loader.getController();
            mainController.setUsername(username);

            Stage stage = new Stage();
            stage.setTitle("Add Transaction");
            stage.setScene(new Scene(root, 800, 600));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleViewFinances() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
            Parent root = loader.load();

            ViewController viewController = loader.getController();
            viewController.setUsername(username);
            viewController.loadChartData();

            Stage stage = new Stage();
            stage.setTitle("View Finances");
            stage.setScene(new Scene(root, 800, 600));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleManageTransactions() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("manage.fxml"));
            Parent root = loader.load();

            ManageController manageController = loader.getController();
            manageController.setUsername(username);

            Stage stage = new Stage();
            stage.setTitle("Manage Transactions");
            stage.setScene(new Scene(root, 800, 600));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}