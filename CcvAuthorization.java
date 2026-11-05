public class CcvAuthorization implements AuthorizationMethod {
    private final String expectedCcv;

    public CcvAuthorization(String expectedCcv) {
        this.expectedCcv = expectedCcv;
    }

    @Override
    public String getName() {
        return "CCV";
    }

    @Override
    public String getPrompt() {
        return "Masukkan CCV: ";
    }

    @Override
    public boolean authorize(String input) {
        return input != null && input.equals(expectedCcv);
    }
}