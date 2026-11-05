import java.util.List;

public interface PaymentStrategy {
    boolean validate(double amount);

    void pay(double amount);

    List<AuthorizationMethod> getRequiredAuthorizations();

    boolean validateAuthorization(AuthorizationMethod method, String input);

    boolean canPay(double amount); 

    String getDescription();
}