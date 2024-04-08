package webSocketMessages.serverMessages;

public class Error extends ServerMessage {
    public Error(String error) {
        super(ServerMessageType.ERROR);
        this.error = error;
    }
    public String getError() {
        return error;
    }
    private final String error;
}
