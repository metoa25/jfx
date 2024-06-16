package org.mateo.jfx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class EditTransactionController {

    @FXML
    private TextField amountField;

    @FXML
    private TextField categoryField;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private DatePicker datePicker;

    private Stage dialogStage;
    private Transaction transaction;
    private boolean saveClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;

        amountField.setText(Double.toString(transaction.getAmount()));
        categoryField.setText(transaction.getCategory());
        typeComboBox.setValue(transaction.getType());
        datePicker.setValue(transaction.getDate());
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            transaction.setAmount(Double.parseDouble(amountField.getText()));
            transaction.setCategory(categoryField.getText());
            transaction.setType(typeComboBox.getValue());
            transaction.setDate(datePicker.getValue());

            updateTransaction(transaction);

            saveClicked = true;
            dialogStage.close();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (amountField.getText() == null || amountField.getText().isEmpty()) {
            errorMessage += "No valid amount!\n";
        } else {
            try {
                Double.parseDouble(amountField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid amount (must be a number)!\n";
            }
        }

        if (categoryField.getText() == null || categoryField.getText().isEmpty()) {
            errorMessage += "No valid category!\n";
        }

        if (typeComboBox.getValue() == null) {
            errorMessage += "No valid type!\n";
        }

        if (datePicker.getValue() == null) {
            errorMessage += "No valid date!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }

    private void updateTransaction(Transaction transaction) {
        String sql = "UPDATE transactions SET type = ?, amount = ?, category = ?, date = ? WHERE id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, transaction.getType());
            pstmt.setDouble(2, transaction.getAmount());
            pstmt.setString(3, transaction.getCategory());
            pstmt.setDate(4, java.sql.Date.valueOf(transaction.getDate()));
            pstmt.setInt(5, transaction.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
