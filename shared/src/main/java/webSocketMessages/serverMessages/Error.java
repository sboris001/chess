package webSocketMessages.serverMessages;

public class Error extends ServerMessage {
    public Error(String errorMessage) {
        super(ServerMessageType.ERROR);
        this.errorMessage = errorMessage;
    }
    public String getError() {
        return errorMessage;
    }
    private final String errorMessage;
}
