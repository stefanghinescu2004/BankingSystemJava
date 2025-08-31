import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class AccountAccess {
    public void createAccount(int customerId, double initialBalance) {
        String sql = "INSERT INTO accounts (customer_id, balance) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            stmt.setDouble(2, initialBalance);
            stmt.executeUpdate();
            System.out.println(" Account created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all accounts for a customer
    public List<Account> getAccountsByCustomer(int customerId) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE customer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Account acc = new Account(
                        rs.getInt("account_id"),
                        rs.getInt("customer_id"),
                        rs.getDouble("balance")
                );
                accounts.add(acc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

}
