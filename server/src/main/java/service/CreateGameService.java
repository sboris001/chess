package service;

import chess.ChessGame;
import dataAccess.*;
import exceptions.BadRequest;
import exceptions.ResponseException;
import exceptions.Unauthorized;
import model.CreateGameObj;
import model.GameData;
import model.GameID;

import static java.util.Objects.isNull;

public class CreateGameService {
    int gameID = 1;
    GameAccess games = new MemoryGameAccess();
    AuthAccess authDB = new MemoryAuthAccess();
    public GameID makeGame(String authToken, CreateGameObj game) throws Unauthorized, DataAccessException, BadRequest, ResponseException {
        if (isNull(authDB.getAuth(authToken))) {
            throw new Unauthorized("Error: unauthorized");
        }
        if (isNull(game.gameName())) {
            throw new BadRequest("Error: bad request");
        } else {
            games.createGame(new GameData(gameID, null, null, game.gameName(), new ChessGame()));
            gameID += 1;
            return new GameID(gameID-1);
        }
    }
}
