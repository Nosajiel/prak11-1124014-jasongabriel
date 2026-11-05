public class DebitCard {

    private final String cardNumber;
    private double balance;
    private final String pin;

    public DebitCard(String cardNumber, double balance, String pin) {
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.pin = pin;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getPin() {
        return pin;
    }

    public boolean validate(double amount) {
        return amount > 0 && balance >= amount;
    }

    public void pay(double amount) {
        if (!validate(amount)) {
            throw new IllegalStateException("Saldo debit tidak mencukupi.");
        }
        balance -= amount;
    }
}