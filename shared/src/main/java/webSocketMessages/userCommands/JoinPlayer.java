package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessPiece;
import model.AuthData;

public class JoinPlayer extends UserGameCommand {
    private int gameID;
    private ChessGame.TeamColor color;
    public JoinPlayer(String authToken, Integer gameID, ChessGame.TeamColor color) {
        super(authToken);
        commandType = CommandType.JOIN_PLAYER;
        this.gameID = gameID;
        this.color = color;
    }

    public int getGameID() {
        return gameID;
    }

    public ChessGame.TeamColor getColor() {
        return color;
    }
}
