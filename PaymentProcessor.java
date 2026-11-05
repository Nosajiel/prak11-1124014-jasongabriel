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
            System.out.print(method.getPrompt()); 
            String input = scanner.nextLine().trim();
            if (!method.authorize(input)) { 
                throw new IllegalArgumentException("Otorisasi " + method.getName() + " gagal.");
            }
        }

        paymentStrategy.pay(amount);
        System.out.println(" Pembayaran berhasil: Rp " + amount);
    }
}