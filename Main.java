import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Object> methods = new ArrayList<>();
    private static final List<String> types = new ArrayList<>();

    public static void main(String[] args) {
        addMethod(new Wallet("Dompet Utama", 10000), "CASH");
        addMethod(new DebitCard("1234-5678-9012-3456", 100000, "1234"), "DEBIT");
        addMethod(new CreditCard("4321-8765-2109-6543", 5_000_000, "123", "4321"), "CREDIT");

        boolean running = true;
        while (running) {
            System.out.println("\nDynamic Payment System");
            System.out.println("1. Bikin Pembayaran");
            System.out.println("2. Tambah Metode Pembayaran");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");
            switch (scanner.nextLine().trim()) {
                case "1":
                    doPayment();
                    break;
                case "2":
                    addMethodMenu();
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

    private static void addMethod(Object method, String type) {
        methods.add(method);
        types.add(type);
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
            switch (types.get(i)) {
                case "CASH": {
                    Wallet w = (Wallet) methods.get(i);
                    System.out.println((i + 1) + ". Cash [" + w.getName() + "] saldo: " + w.getCashValue());
                    break;
                }
                case "DEBIT": {
                    DebitCard d = (DebitCard) methods.get(i);
                    System.out.println((i + 1) + ". Debit [" + d.getCardNumber() + "] saldo: " + d.getBalance());
                    break;
                }
                case "CREDIT": {
                    CreditCard c = (CreditCard) methods.get(i);
                    System.out.println(
                            (i + 1) + ". Credit [" + c.getCardNumber() + "] sisa limit: " + c.getAvailableLimit());
                    break;
                }
            }
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

        String type = types.get(idx);
        Object m = methods.get(idx);

        try {
            switch (type) {
                case "CASH": {
                    Wallet w = (Wallet) m;
                    if (!w.validate(amount)) {
                        System.out.println("Saldo kurang.");
                        return;
                    }
                    w.pay(amount);
                    System.out.println("Cash berhasil. Sisa: " + w.getCashValue());
                    break;
                }
                case "DEBIT": {
                    DebitCard d = (DebitCard) m;
                    if (!d.validate(amount)) {
                        System.out.println("Saldo kurang.");
                        return;
                    }
                    System.out.print("PIN: ");
                    if (!scanner.nextLine().trim().equals(d.getPin())) {
                        System.out.println("PIN salah.");
                        return;
                    }
                    d.pay(amount);
                    System.out.println("Debit berhasil. Sisa: " + d.getBalance());
                    break;
                }
                case "CREDIT": {
                    CreditCard c = (CreditCard) m;
                    if (!c.validate(amount)) {
                        System.out.println("Limit kurang.");
                        return;
                    }
                    System.out.print("CCV: ");
                    if (!scanner.nextLine().trim().equals(c.getCcv())) {
                        System.out.println("CCV salah.");
                        return;
                    }
                    System.out.print("PIN: ");
                    if (!scanner.nextLine().trim().equals(c.getPin())) {
                        System.out.println("PIN salah.");
                        return;
                    }
                    c.pay(amount);
                    System.out.println("Credit berhasil. Sisa limit: " + c.getAvailableLimit());
                    break;
                }
            }
        } catch (RuntimeException e) {
            System.out.println("!! " + e.getMessage());
        }
    }

    private static void addMethodMenu() {
        System.out.println("1. Cash (Wallet baru)");
        System.out.println("2. Debit Card");
        System.out.println("3. Credit Card");
        System.out.print("Pilih jenis (1-3): ");
        switch (scanner.nextLine().trim()) {
            case "1": {
                System.out.print("Nama wallet: ");
                String name = scanner.nextLine().trim();
                System.out.print("Saldo awal: ");
                double cash = Double.parseDouble(scanner.nextLine().trim());
                addMethod(new Wallet(name, cash), "CASH");
                break;
            }
            case "2": {
                System.out.print("Nomor kartu: ");
                String card = scanner.nextLine().trim();
                System.out.print("Saldo: ");
                double bal = Double.parseDouble(scanner.nextLine().trim());
                System.out.print("PIN: ");
                addMethod(new DebitCard(card, bal, scanner.nextLine().trim()), "DEBIT");
                break;
            }
            case "3": {
                System.out.print("Nomor kartu: ");
                String card = scanner.nextLine().trim();
                System.out.print("Limit: ");
                double lim = Double.parseDouble(scanner.nextLine().trim());
                System.out.print("CCV: ");
                String ccv = scanner.nextLine().trim();
                System.out.print("PIN: ");
                addMethod(new CreditCard(card, lim, ccv, scanner.nextLine().trim()), "CREDIT");
                break;
            }
            default:
                System.out.println("Jenis tidak dikenal.");
        }
        System.out.println("Metode ditambahkan.");
    }
}