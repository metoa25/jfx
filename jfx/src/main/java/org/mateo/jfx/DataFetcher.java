package org.mateo.jfx;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataFetcher {
    public static Map<String, Double> getIncomeData(String username) {
        Map<String, Double> data = new LinkedHashMap<>();
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        for (String month : months) {
            data.put(month, 0.0);
        }

        String sql = "SELECT monthname(date) as month, sum(amount) as total " +
                "FROM transactions WHERE type='income' AND user_id=(SELECT id FROM users WHERE username=?) " +
                "GROUP BY month ORDER BY monthname(date)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                data.put(rs.getString("month"), rs.getDouble("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static Map<String, Double> getExpenseData(String username) {
        Map<String, Double> data = new LinkedHashMap<>();
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        for (String month : months) {
            data.put(month, 0.0);
        }

        String sql = "SELECT monthname(date) as month, sum(amount) as total " +
                "FROM transactions WHERE type='expense' AND user_id=(SELECT id FROM users WHERE username=?) " +
                "GROUP BY month ORDER BY monthname(date)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                data.put(rs.getString("month"), rs.getDouble("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}