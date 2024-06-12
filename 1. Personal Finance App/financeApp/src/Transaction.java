import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Transaction {
    private int userId;
    private String type;
    private double amount;
    private String category;
    private String date;

    public Transaction(int userId, String type, double amount, String category, String date) {
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public boolean addTransaction() {
        String sql = "INSERT INTO transactions(user_id, type, amount, category, date) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.userId);
            pstmt.setString(2, this.type);
            pstmt.setDouble(3, this.amount);
            pstmt.setString(4, this.category);
            pstmt.setString(5, this.date);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
