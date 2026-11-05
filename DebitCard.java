import java.util.List;

public class DebitCard implements PaymentStrategy {

    private final String cardNumber;
    private double balance;
    private final String pin;

    public DebitCard(String cardNumber, double balance, String pin) {
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.pin = pin;
    }

    @Override
    public boolean validate(double amount) {
        return amount > 0 && balance >= amount;
    }

    @Override
    public void pay(double amount) {
        if (!validate(amount))
            throw new IllegalStateException("Saldo debit tidak cukup.");
        balance -= amount;
    }

    @Override
    public List<AuthorizationMethod> getRequiredAuthorizations() {
        return List.of(AuthorizationMethod.PIN);
    }

    @Override
    public boolean validateAuthorization(AuthorizationMethod method, String input) {
        return method == AuthorizationMethod.PIN && input.equals(pin);
    }

    @Override
    public String getDescription() {
        return "Debit [" + cardNumber + "] saldo: " + balance;
    }
}