import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DataFetcher {
    public static Map<String, Double> getIncomeData() {
        Map<String, Double> data = new HashMap<>();
        String sql = "SELECT monthname(date) as month, sum(amount) as total FROM transactions WHERE type='income' GROUP BY month ORDER BY date";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                data.put(rs.getString("month"), rs.getDouble("total"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return data;
    }

    public static Map<String, Double> getExpenseData() {
        Map<String, Double> data = new HashMap<>();
        String sql = "SELECT monthname(date) as month, sum(amount) as total FROM transactions WHERE type='expense' GROUP BY month ORDER BY date";

        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                data.put(rs.getString("month"), rs.getDouble("total"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return data;
    }
}
