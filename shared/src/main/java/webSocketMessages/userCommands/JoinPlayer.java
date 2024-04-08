package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessPiece;
import model.AuthData;

public class JoinPlayer extends UserGameCommand {
    public JoinPlayer(String authToken, Integer GameID, ChessGame.TeamColor color) {
        super(authToken);
        commandType = CommandType.JOIN_PLAYER;
    }

}
