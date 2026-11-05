import java.util.List;

public class CreditCard implements PaymentStrategy {

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

    public double getAvailableLimit() {
        return limit - currentDebt;
    }

    @Override
    public boolean validate(double amount) {
        return amount > 0 && amount <= getAvailableLimit();
    }

    @Override
    public void pay(double amount) {
        if (!validate(amount))
            throw new IllegalStateException("Limit kredit tidak cukup.");
        currentDebt += amount;
    }

    @Override
    public List<AuthorizationMethod> getRequiredAuthorizations() {
        return List.of(AuthorizationMethod.CCV, AuthorizationMethod.PIN);
    }

    @Override
    public boolean validateAuthorization(AuthorizationMethod method, String input) {
        switch (method) {
            case CCV:
                return input.equals(ccv);
            case PIN:
                return input.equals(pin);
            default:
                return false;
        }
    }

    @Override
    public String getDescription() {
        return "Credit [" + cardNumber + "] sisa limit: " + getAvailableLimit();
    }

    @Override
    public boolean canPay(double amount) {
        return amount >= 100_000 && amount <= 100_000_000;
    }
}