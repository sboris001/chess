package webSocketMessages.userCommands;

public class Redraw extends UserGameCommand {

    public Redraw(String authToken, int gameID) {
        super(authToken);
        this.gameID = gameID;
        commandType = CommandType.REDRAW;
    }

    public int getGameID() {
        return gameID;
    }

    private final int gameID;
}
