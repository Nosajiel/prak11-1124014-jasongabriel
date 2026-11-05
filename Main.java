import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Wallet wallet = new Wallet("Dompet Utama", 10000);
        DebitCard debit = new DebitCard("1234-5678-9012-3456", 100000, "1234");
        CreditCard credit = new CreditCard("4321-8765-2109-6543", 5_000_000, "123", "4321");

        while (true) {
            System.out.println("\nDynamic Payment System");
            System.out.print("Masukkan jumlah yang dibayar (0 untuk keluar): ");
            double amount;
            try {
                amount = Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                continue;
            }
            if (amount == 0)
                break;

            System.out.print("Pilih metode pembayaran (Cash/Debit/Credit): ");
            String method = scanner.nextLine().trim().toLowerCase();

            try {
                switch (method) {
                    case "cash": {
                        wallet.pay(amount);
                        System.out.println("Cash berhasil, sisa: " + wallet.getCashValue());
                        break;
                    }
                    case "debit": {
                        if (!debit.validate(amount)) {
                            System.out.println("Saldo kurang.");
                            break;
                        }
                        System.out.print("PIN: ");
                        if (!scanner.nextLine().trim().equals(debit.getPin())) {
                            System.out.println("PIN salah.");
                            break;
                        }
                        debit.pay(amount);
                        System.out.println("Debit berhasil, sisa: " + debit.getBalance());
                        break;
                    }
                    case "credit": {
                        if (!credit.validate(amount)) {
                            System.out.println("Limit kurang.");
                            break;
                        }
                        System.out.print("CCV: ");
                        if (!scanner.nextLine().trim().equals(credit.getCcv())) {
                            System.out.println("CCV salah.");
                            break;
                        }
                        System.out.print("PIN: ");
                        if (!scanner.nextLine().trim().equals(credit.getPin())) {
                            System.out.println("PIN salah.");
                            break;
                        }
                        credit.pay(amount);
                        System.out.println("Credit berhasil, sisa limit: " + credit.getAvailableLimit());
                        break;
                    }
                    default:
                        System.out.println("Metode tidak dikenal.");
                }
            } catch (RuntimeException e) {
                System.out.println("!! " + e.getMessage());
            }
        }
        scanner.close();
    }
}