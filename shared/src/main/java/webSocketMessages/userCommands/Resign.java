package webSocketMessages.userCommands;

public class Resign extends UserGameCommand {
    public Resign(String authToken, Integer gameID) {
        super(authToken);
        commandType = CommandType.RESIGN;
        this.gameID = gameID;
    }

    public Integer getGameID() {
        return gameID;
    }

    private Integer gameID;
}
