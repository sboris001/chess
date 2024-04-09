package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMove extends UserGameCommand {

    public final Integer gameID;
    public final ChessMove move;
    public MakeMove(String authToken, Integer gameID, ChessMove move) {
        super(authToken);
        this.gameID = gameID;
        this.move = move;
    }

    public Integer getGameID() {
        return gameID;
    }

    public ChessMove getMove() {
        return move;
    }
}
