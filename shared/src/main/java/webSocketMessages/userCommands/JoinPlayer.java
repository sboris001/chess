package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessPiece;
import model.AuthData;

public class JoinPlayer extends UserGameCommand {
    private int gameID;
    private ChessGame.TeamColor playerColor;
    public JoinPlayer(String authToken, Integer gameID, ChessGame.TeamColor playerColor) {
        super(authToken);
        commandType = CommandType.JOIN_PLAYER;
        this.gameID = gameID;
        this.playerColor = playerColor;
    }

    public int getGameID() {
        return gameID;
    }

    public ChessGame.TeamColor getColor() {
        return playerColor;
    }
}
