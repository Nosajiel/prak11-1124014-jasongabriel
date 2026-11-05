import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<PaymentStrategy> methods = new ArrayList<>();
    private static final PaymentProcessor processor = new PaymentProcessor(scanner);

    public static void main(String[] args) {
        methods.add(new Wallet("Dompet Utama", 10000));
        methods.add(new DebitCard("1234-5678-9012-3456", 100000, "1234"));
        methods.add(new CreditCard("4321-8765-2109-6543", 5_000_000, "123", "4321"));

        boolean running = true;
        while (running) {
            System.out.println("\nDynamic Payment System");
            System.out.println("1. Bikin Pembayaran");
            System.out.println("2. Tambah Metode Pembayaran");
            System.out.println("3. Lihat Semua Metode");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");
            switch (scanner.nextLine().trim()) {
                case "1":
                    doPayment();
                    break;
                case "2":
                    addMethod();
                    break;
                case "3":
                    showAll();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
        scanner.close();
    }

    private static void doPayment() {
        System.out.print("Masukkan jumlah yang dibayar: ");
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid.");
            return;
        }

        System.out.println("Metode tersedia:");
        for (int i = 0; i < methods.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + methods.get(i).getDescription());
        }

        System.out.print("Pilih (1-" + methods.size() + "): ");
        int idx;
        try {
            idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid.");
            return;
        }
        if (idx < 0 || idx >= methods.size()) {
            System.out.println("Pilihan tidak valid.");
            return;
        }

        try {
            processor.processPayment(methods.get(idx), amount);
        } catch (RuntimeException e) {
            System.out.println("!! " + e.getMessage());
        }
    }

    private static void addMethod() {
        System.out.println("1. Cash (Wallet baru)");
        System.out.println("2. Debit Card");
        System.out.println("3. Credit Card");
        System.out.print("Pilih jenis (1-3): ");
        switch (scanner.nextLine().trim()) {
            case "1": {
                System.out.print("Nama wallet: ");
                String name = scanner.nextLine().trim();
                System.out.print("Saldo awal: ");
                methods.add(new Wallet(name, readDouble()));
                break;
            }
            case "2": {
                System.out.print("Nomor kartu: ");
                String card = scanner.nextLine().trim();
                System.out.print("Saldo: ");
                double bal = readDouble();
                System.out.print("PIN: ");
                methods.add(new DebitCard(card, bal, scanner.nextLine().trim()));
                break;
            }
            case "3": {
                System.out.print("Nomor kartu: ");
                String card = scanner.nextLine().trim();
                System.out.print("Limit: ");
                double lim = readDouble();
                System.out.print("CCV: ");
                String ccv = scanner.nextLine().trim();
                System.out.print("PIN: ");
                methods.add(new CreditCard(card, lim, ccv, scanner.nextLine().trim()));
                break;
            }
            default:
                System.out.println("Jenis tidak dikenal.");
        }
    }

    private static void showAll() {
        for (int i = 0; i < methods.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + methods.get(i).getDescription());
        }
    }

    private static double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Angka tidak valid, ulangi: ");
            }
        }
    }
}