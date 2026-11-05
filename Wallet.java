public class Wallet {

    private final String name;
    private double cashValue;

    public Wallet(String name, double cashValue) {
        this.name = name;
        this.cashValue = cashValue;
    }

    public String getName() {
        return name;
    }

    public double getCashValue() {
        return cashValue;
    }

    public boolean validate(double amount) {
        return amount > 0 && cashValue >= amount;
    }

    public void pay(double amount) {
        if (!validate(amount)) {
            throw new IllegalStateException("Saldo cash tidak mencukupi.");
        }
        cashValue -= amount;
    }
}