package service;

import chess.ChessGame;
import dataAccess.*;
import exceptions.BadRequest;
import exceptions.ResponseException;
import exceptions.Unauthorized;
import model.CreateGameObj;
import model.GameData;
import model.GameID;
import java.util.Random;

import static java.util.Objects.isNull;

public class CreateGameService {
    Random rand = new Random();

    GameAccess games = new SQLGameAccess();
    AuthAccess authDB = new SQLAuthAccess();

    public CreateGameService() throws ResponseException, DataAccessException {
    }

    public GameID makeGame(String authToken, CreateGameObj game) throws Unauthorized, DataAccessException, BadRequest, ResponseException {
        int gameID = rand.nextInt(1000000);
        if (isNull(authDB.getAuth(authToken))) {
            throw new Unauthorized("Error: unauthorized");
        }
        if (isNull(game.gameName())) {
            throw new BadRequest("Error: bad request");
        } else {
            games.createGame(new GameData(gameID, null, null, game.gameName(), new ChessGame()));
            return new GameID(gameID);
        }
    }
}
