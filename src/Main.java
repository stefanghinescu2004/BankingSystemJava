import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CustomerAccess customer = new CustomerAccess();
        AccountAccess account = new AccountAccess();
        TransactionAccess transaction = new TransactionAccess();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Banking System ---");
            System.out.println("1. Add Customer");
            System.out.println("2. View Customers");
            System.out.println("3. Create account");
            System.out.println("4. View accounts by customer");
            System.out.println("5. Deposit");
            System.out.println("6.  Withdraw");
            System.out.println("7.  Transfer");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    customer.addCustomer(name, email);
                    break;
                case 2:
                    List<Customer> customers = customer.getAllCustomers();
                    customers.forEach(System.out::println);
                    break;
                case 3:
                    System.out.print("Enter customer ID: ");
                    int custId = scanner.nextInt();
                    System.out.print("Enter initial balance: ");
                    double balance = scanner.nextDouble();
                    account.createAccount(custId, balance);
                    break;
                case 4:
                    System.out.print("Enter customer ID: ");
                    int customerId = scanner.nextInt();
                    account.getAccountsByCustomer(customerId).forEach(System.out::println);
                    break;
                case 5:
                    System.out.print("Enter account ID: ");
                    int accIdDeposit = scanner.nextInt();
                    System.out.print("Enter amount: ");
                    double depAmount = scanner.nextDouble();
                    transaction.deposit(accIdDeposit, depAmount);
                    break;
                case 6:
                    System.out.print("Enter account ID: ");
                    int accIdWithdraw = scanner.nextInt();
                    System.out.print("Enter amount: ");
                    double wdAmount = scanner.nextDouble();
                    transaction.withdraw(accIdWithdraw, wdAmount);
                    break;
                case 7:
                    System.out.print("Enter source account ID: ");
                    int fromId = scanner.nextInt();
                    System.out.print("Enter destination account ID: ");
                    int toId = scanner.nextInt();
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    transaction.transfer(fromId, toId, transferAmount);
                    break;

                case 0:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

}
