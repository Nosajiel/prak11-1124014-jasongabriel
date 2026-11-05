import java.util.List;

public class EWallet implements PaymentStrategy {
    private final String phoneNumber;
    private double balance;
    private final String pin;

    public EWallet(String phoneNumber, double balance, String pin) {
        this.phoneNumber = phoneNumber;
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
            throw new IllegalStateException("Saldo e-wallet tidak cukup.");
        balance -= amount;
    }

    @Override
    public List<AuthorizationMethod> getRequiredAuthorizations() {
        return List.of(new PinAuthorization(pin), new OtpAuthorization());
    }

    @Override
    public boolean canPay(double amount) {
        return amount >= 100 && amount <= 10_000_000;
    }

    @Override
    public String getDescription() {
        return "E-Wallet [" + phoneNumber + "] saldo: " + balance;
    }
}