package org.mateo.jfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageController {

    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    private TableColumn<Transaction, Integer> idColumn;

    @FXML
    private TableColumn<Transaction, String> typeColumn;

    @FXML
    private TableColumn<Transaction, Double> amountColumn;

    @FXML
    private TableColumn<Transaction, String> categoryColumn;

    @FXML
    private TableColumn<Transaction, String> dateColumn;

    private String username;

    private ObservableList<Transaction> transactionList = FXCollections.observableArrayList();

    public void setUsername(String username) {
        this.username = username;
        loadTransactions();
    }

    private void loadTransactions() {
        transactionList.clear();
        String sql = "SELECT id, type, amount, category, date FROM transactions WHERE user_id = (SELECT id FROM users WHERE username = ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        rs.getDate("date").toLocalDate()
                );
                transactionList.add(transaction);
            }

            transactionTable.setItems(transactionList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Custom cell factory to change the text color of the amount column
        amountColumn.setCellFactory(new Callback<TableColumn<Transaction, Double>, TableCell<Transaction, Double>>() {
            @Override
            public TableCell<Transaction, Double> call(TableColumn<Transaction, Double> param) {
                return new TableCell<Transaction, Double>() {
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item.toString());
                            Transaction transaction = getTableView().getItems().get(getIndex());
                            if ("income".equals(transaction.getType())) {
                                setStyle("-fx-text-fill: green;");
                            } else {
                                setStyle("-fx-text-fill: red;");
                            }
                        }
                    }
                };
            }
        });
    }

    @FXML
    private void handleEditTransaction() {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            // Code to open edit window and update transaction
            showAlert("Edit Transaction", "Editing transactions is not implemented yet.");
        } else {
            showAlert("No Selection", "No transaction selected.");
        }
    }

    @FXML
    private void handleDeleteTransaction() {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            String sql = "DELETE FROM transactions WHERE id = ?";
            try (Connection conn = Database.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, selectedTransaction.getId());
                pstmt.executeUpdate();
                transactionList.remove(selectedTransaction);
                showAlert("Success", "Transaction deleted successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to delete transaction.");
            }
        } else {
            showAlert("No Selection", "No transaction selected.");
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
