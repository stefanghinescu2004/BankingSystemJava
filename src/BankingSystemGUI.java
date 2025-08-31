import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankingSystemGUI extends JFrame {

    private CustomerAccess customer = new CustomerAccess();
    private AccountAccess account = new AccountAccess();
    private TransactionAccess transactionAccess= new TransactionAccess();

    public BankingSystemGUI() {
        setTitle("Banking System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 1, 5, 5));

        // Buttons
        JButton btnAddCustomer = new JButton("Add Customer");
        JButton btnViewCustomers = new JButton("View Customers");
        JButton btnCreateAccount = new JButton("Create Account");
        JButton btnViewAccounts = new JButton("View Accounts");
        JButton btnDeposit = new JButton("Deposit");
        JButton btnWithdraw = new JButton("Withdraw");
        JButton btnTransfer = new JButton("Transfer");

        add(btnAddCustomer);
        add(btnViewCustomers);
        add(btnCreateAccount);
        add(btnViewAccounts);
        add(btnDeposit);
        add(btnWithdraw);
        add(btnTransfer);

        // Button actions
        btnAddCustomer.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter customer name:");
            String email = JOptionPane.showInputDialog("Enter customer email:");
            customer.addCustomer(name, email);
        });

        btnViewCustomers.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (Customer c : customer.getAllCustomers()) {
                sb.append(c).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString(), "Customers", JOptionPane.INFORMATION_MESSAGE);
        });

        btnCreateAccount.addActionListener(e -> {
            int customerId = Integer.parseInt(JOptionPane.showInputDialog("Enter customer ID:"));
            double balance = Double.parseDouble(JOptionPane.showInputDialog("Enter initial balance:"));
            account.createAccount(customerId, balance);
        });

        btnViewAccounts.addActionListener(e -> {
            int customerId = Integer.parseInt(JOptionPane.showInputDialog("Enter customer ID:"));
            StringBuilder sb = new StringBuilder();
            for (Account a : account.getAccountsByCustomer(customerId)) {
                sb.append(a).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString(), "Accounts", JOptionPane.INFORMATION_MESSAGE);
        });

        btnDeposit.addActionListener(e -> {
            int accId = Integer.parseInt(JOptionPane.showInputDialog("Enter account ID:"));
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter amount to deposit:"));
            transactionAccess.deposit(accId, amount);
        });

        btnWithdraw.addActionListener(e -> {
            int accId = Integer.parseInt(JOptionPane.showInputDialog("Enter account ID:"));
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter amount to withdraw:"));
            transactionAccess.withdraw(accId, amount);
        });

        btnTransfer.addActionListener(e -> {
            int fromId = Integer.parseInt(JOptionPane.showInputDialog("Enter source account ID:"));
            int toId = Integer.parseInt(JOptionPane.showInputDialog("Enter destination account ID:"));
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter amount to transfer:"));
            transactionAccess.transfer(fromId, toId, amount);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BankingSystemGUI().setVisible(true);
        });
    }

}
