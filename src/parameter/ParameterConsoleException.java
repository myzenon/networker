package parameter;

public class ParameterConsoleException extends Exception {
    private String consoleMessage;

    public String getConsoleMessage() {
        return consoleMessage;
    }

    public ParameterConsoleException(String message, String consoleMessage) {
        super(message);
        this.consoleMessage = consoleMessage;
    }
}
