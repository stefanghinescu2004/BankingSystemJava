import java.sql.*;
public class TransactionAccess {
    public void deposit(int accountId, double amount) {
        String updateBalance = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
        String insertTransaction = "INSERT INTO transactions (account_id, type, amount) VALUES (?, 'DEPOSIT', ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(updateBalance);
                 PreparedStatement stmt2 = conn.prepareStatement(insertTransaction)) {


                stmt1.setDouble(1, amount);
                stmt1.setInt(2, accountId);
                stmt1.executeUpdate();


                stmt2.setInt(1, accountId);
                stmt2.setDouble(2, amount);
                stmt2.executeUpdate();

                conn.commit();
                System.out.println(" Deposit successful!");

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Withdraw money
    public void withdraw(int accountId, double amount) {
        String checkBalance = "SELECT balance FROM accounts WHERE account_id = ?";
        String updateBalance = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
        String insertTransaction = "INSERT INTO transactions (account_id, type, amount) VALUES (?, 'WITHDRAW', ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtCheck = conn.prepareStatement(checkBalance)) {
                stmtCheck.setInt(1, accountId);
                ResultSet rs = stmtCheck.executeQuery();

                if (rs.next()) {
                    double balance = rs.getDouble("balance");
                    if (balance < amount) {
                        System.out.println(" Insufficient funds!");
                        return;
                    }
                }
            }

            try (PreparedStatement stmt1 = conn.prepareStatement(updateBalance);
                 PreparedStatement stmt2 = conn.prepareStatement(insertTransaction)) {

                // Update balance
                stmt1.setDouble(1, amount);
                stmt1.setInt(2, accountId);
                stmt1.executeUpdate();

                // Log transaction
                stmt2.setInt(1, accountId);
                stmt2.setDouble(2, amount);
                stmt2.executeUpdate();

                conn.commit();
                System.out.println(" Withdrawal successful!");

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void transfer(int fromAccountId, int toAccountId, double amount) {
        String checkBalance = "SELECT balance FROM accounts WHERE account_id = ?";
        String withdrawSql = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
        String depositSql = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
        String insertTransaction = "INSERT INTO transactions (account_id, type, amount) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // start transaction

            // Check if fromAccount has enough balance
            try (PreparedStatement stmtCheck = conn.prepareStatement(checkBalance)) {
                stmtCheck.setInt(1, fromAccountId);
                ResultSet rs = stmtCheck.executeQuery();
                if (rs.next()) {
                    double balance = rs.getDouble("balance");
                    if (balance < amount) {
                        System.out.println(" Insufficient funds!");
                        return;
                    }
                } else {
                    System.out.println(" Source account not found!");
                    return;
                }
            }

            try (
                    PreparedStatement stmtWithdraw = conn.prepareStatement(withdrawSql);
                    PreparedStatement stmtDeposit = conn.prepareStatement(depositSql);
                    PreparedStatement stmtTrans = conn.prepareStatement(insertTransaction)
            ) {
                // Withdraw from source account
                stmtWithdraw.setDouble(1, amount);
                stmtWithdraw.setInt(2, fromAccountId);
                stmtWithdraw.executeUpdate();

                // Deposit to destination account
                stmtDeposit.setDouble(1, amount);
                stmtDeposit.setInt(2, toAccountId);
                stmtDeposit.executeUpdate();

                // Log withdrawal transaction
                stmtTrans.setInt(1, fromAccountId);
                stmtTrans.setString(2, "TRANSFER");
                stmtTrans.setDouble(3, -amount); // negative amount for source
                stmtTrans.executeUpdate();

                // Log deposit transaction
                stmtTrans.setInt(1, toAccountId);
                stmtTrans.setString(2, "TRANSFER");
                stmtTrans.setDouble(3, amount); // positive amount for destination
                stmtTrans.executeUpdate();

                conn.commit();
                System.out.println(" Transfer successful!");

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
