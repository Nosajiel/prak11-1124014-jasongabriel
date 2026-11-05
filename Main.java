import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Wallet wallet = new Wallet("Dompet Utama", 10000);

        System.out.println("Saldo awal wallet: " + wallet.getCashValue());

        while (true) {
            System.out.print("Masukkan jumlah yang dibayar (0 untuk keluar): ");
            double amount;
            try {
                amount = Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid.");
                continue;
            }
            if (amount == 0)
                break;

            try {
                wallet.pay(amount);
                System.out.println("Pembayaran cash berhasil, sisa wallet: " + wallet.getCashValue());
            } catch (RuntimeException e) {
                System.out.println("!! " + e.getMessage());
            }
        }
        scanner.close();
    }
}