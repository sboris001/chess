package webSocketMessages.userCommands;

public class Leave extends UserGameCommand {
    public Leave(String authToken, Integer gameID) {
        super(authToken);
        this.gameID = gameID;
        commandType = CommandType.LEAVE;
    }

    public Integer getGameID() {
        return gameID;
    }
    private final Integer gameID;
}
