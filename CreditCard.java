public class CreditCard {

    private final String cardNumber;
    private final double limit;
    private double currentDebt;
    private final String ccv;
    private final String pin;

    public CreditCard(String cardNumber, double limit, String ccv, String pin) {
        this.cardNumber = cardNumber;
        this.limit = limit;
        this.ccv = ccv;
        this.pin = pin;
        this.currentDebt = 0;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public double getLimit() {
        return limit;
    }

    public double getCurrentDebt() {
        return currentDebt;
    }

    public double getAvailableLimit() {
        return limit - currentDebt;
    }

    public String getCcv() {
        return ccv;
    }

    public String getPin() {
        return pin;
    }

    public boolean validate(double amount) {
        return amount > 0 && amount <= getAvailableLimit();
    }

    public void pay(double amount) {
        if (!validate(amount)) {
            throw new IllegalStateException("Limit kredit tidak mencukupi.");
        }
        currentDebt += amount;
    }
}