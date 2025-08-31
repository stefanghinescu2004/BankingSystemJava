import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class CustomerAccess {
    public void addCustomer(String name, String email) {
        String sql = "INSERT INTO customers (name, email) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.executeUpdate();
            System.out.println(" Customer added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Customer c = new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
                customers.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

}
