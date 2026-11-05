import java.util.List;
import java.util.Random;

public class EWallet implements PaymentStrategy {

    private static final Random RANDOM = new Random();
    private final String phoneNumber;
    private double balance;
    private final String pin;
    private String currentOtp;

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
        currentOtp = String.format("%06d", RANDOM.nextInt(1_000_000));
        System.out.println("[SIMULASI SMS] OTP Anda: " + currentOtp);
        return List.of(AuthorizationMethod.PIN, AuthorizationMethod.OTP);
    }

    @Override
    public boolean validateAuthorization(AuthorizationMethod method, String input) {
        switch (method) {
            case PIN:
                return input.equals(pin);
            case OTP:
                return input.equals(currentOtp);
            default:
                return false;
        }
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