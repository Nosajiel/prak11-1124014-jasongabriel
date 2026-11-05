import java.util.List;
import java.util.Scanner;

public class PaymentProcessor {

    private final Scanner scanner;

    public PaymentProcessor(Scanner scanner) {
        this.scanner = scanner;
    }

    public void processPayment(PaymentStrategy paymentStrategy, double amount) {

        if (!paymentStrategy.validate(amount)) {
            throw new IllegalArgumentException("Validasi gagal: saldo/limit tidak mencukupi.");
        }

        List<AuthorizationMethod> requiredAuthorizations = paymentStrategy.getRequiredAuthorizations();

        for (AuthorizationMethod method : requiredAuthorizations) {
            System.out.print(promptFor(method));
            String input = scanner.nextLine().trim();
            if (!paymentStrategy.validateAuthorization(method, input)) {
                throw new IllegalArgumentException("Otorisasi " + method + " gagal.");
            }
        }

        paymentStrategy.pay(amount);
        System.out.println("Pembayaran berhasil: Rp " + amount);
    }

    private String promptFor(AuthorizationMethod method) {
        switch (method) {
            case PIN:
                return "Masukkan PIN: ";
            case CCV:
                return "Masukkan CCV: ";
            default:
                return "Masukkan " + method.name() + ": ";
        }
    }
}