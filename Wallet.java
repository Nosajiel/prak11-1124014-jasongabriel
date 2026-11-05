import java.util.Collections;
import java.util.List;

public class Wallet implements PaymentStrategy {

    private final String name;
    private double cashValue;

    public Wallet(String name, double cashValue) {
        this.name = name;
        this.cashValue = cashValue;
    }

    @Override
    public boolean validate(double amount) {
        return amount > 0 && cashValue >= amount;
    }

    @Override
    public void pay(double amount) {
        if (!validate(amount))
            throw new IllegalStateException("Saldo cash tidak cukup.");
        cashValue -= amount;
    }

    @Override
    public List<AuthorizationMethod> getRequiredAuthorizations() {
        return Collections.emptyList();
    }

    @Override
    public boolean validateAuthorization(AuthorizationMethod method, String input) {
        return false;
    }

    @Override
    public String getDescription() {
        return "Cash [" + name + "] saldo: " + cashValue;
    }

    @Override
    public boolean canPay(double amount) {
        return amount >= 100 && amount <= 1_000_000;
    }
}