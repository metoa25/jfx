package org.mateo.jfx;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class MainController {

    @FXML
    private TextField amountField;

    @FXML
    private TextField categoryField;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private DatePicker datePicker;

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    @FXML
    protected void handleAddTransaction() {
        String type = typeComboBox.getValue();
        String amountText = amountField.getText();
        String category = categoryField.getText();
        LocalDate date = datePicker.getValue();

        if (type == null || amountText.isEmpty() || category.isEmpty() || date == null) {
            showAlert("Input Error", "All fields must be filled.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Amount must be a number.");
            return;
        }

        if (addTransaction(username, type, amount, category, date)) {
            showAlert("Success", "Transaction added successfully.");
        } else {
            showAlert("Error", "Failed to add transaction.");
        }
    }

    private boolean addTransaction(String username, String type, double amount, String category, LocalDate date) {
        String sql = "INSERT INTO transactions(user_id, type, amount, category, date) VALUES((SELECT id FROM users WHERE username = ?), ?, ?, ?, ?)";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, type);
            pstmt.setDouble(3, amount);
            pstmt.setString(4, category);
            pstmt.setDate(5, java.sql.Date.valueOf(date));
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
