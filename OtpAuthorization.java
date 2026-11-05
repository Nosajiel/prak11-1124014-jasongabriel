import java.security.SecureRandom;

public class OtpAuthorization implements AuthorizationMethod {
    private static final SecureRandom RANDOM = new SecureRandom();
    private final String expectedOtp;

    public OtpAuthorization() {
        this.expectedOtp = String.format("%06d", RANDOM.nextInt(1_000_000));
    }

    @Override
    public String getName() {
        return "OTP";
    }

    @Override
    public String getPrompt() {
        return "Masukkan OTP (simulasi SMS, kode Anda: " + expectedOtp + "): ";
    }

    @Override
    public boolean authorize(String input) {
        return input != null && input.equals(expectedOtp);
    }
}