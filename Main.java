import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Wallet wallet = new Wallet("Dompet Utama", 10000);
        DebitCard debit = new DebitCard("1234-5678-9012-3456", 100000, "1234");

        while (true) {
            System.out.println("\n Dynamic Payment System");
            System.out.print("Masukkan jumlah yang dibayar (0 untuk keluar): ");
            double amount;
            try {
                amount = Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                continue;
            }
            if (amount == 0)
                break;

            System.out.print("Pilih metode pembayaran (Cash/Debit): ");
            String method = scanner.nextLine().trim().toLowerCase();

            try {
                if (method.equals("cash")) {
                    wallet.pay(amount);
                    System.out.println("Pembayaran cash berhasil, sisa: " + wallet.getCashValue());
                } else if (method.equals("debit")) {
                    if (!debit.validate(amount)) {
                        System.out.println("Saldo debit tidak mencukupi.");
                        continue;
                    }
                    System.out.print("Masukkan PIN: ");
                    String pin = scanner.nextLine().trim();
                    if (!pin.equals(debit.getPin())) {
                        System.out.println("PIN salah.");
                        continue;
                    }
                    debit.pay(amount);
                    System.out.println("Pembayaran debit berhasil, sisa: " + debit.getBalance());
                } else {
                    System.out.println("Metode tidak dikenal.");
                }
            } catch (RuntimeException e) {
                System.out.println("!! " + e.getMessage());
            }
        }
        scanner.close();
    }
}