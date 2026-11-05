public class PinAuthorization implements AuthorizationMethod {
    private final String expectedPin;

    public PinAuthorization(String expectedPin) {
        this.expectedPin = expectedPin;
    }

    @Override
    public String getName() {
        return "PIN";
    }

    @Override
    public String getPrompt() {
        return "Masukkan PIN: ";
    }

    @Override
    public boolean authorize(String input) {
        return input != null && input.equals(expectedPin);
    }
}