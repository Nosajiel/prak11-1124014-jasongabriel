public interface AuthorizationMethod {
    String getName();
    String getPrompt();
    boolean authorize(String input);
}