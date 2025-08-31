public class Account {
    private int id;
    private int customerId;
    private double balance;

    public Account(int id, int customerId, double balance) {
        this.id = id;
        this.customerId = customerId;
        this.balance = balance;
    }

    // Getters
    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public double getBalance() { return balance; }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", balance=" + balance +
                '}';
    }
}
