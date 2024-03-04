package service;

import dataAccess.*;
import exceptions.AlreadyTaken;
import exceptions.BadRequest;
import exceptions.ResponseException;
import exceptions.Unauthorized;
import model.GameData;
import model.JoinGame;

import static java.util.Objects.isNull;

public class JoinGameService {
    AuthAccess authDB = new SQLAuthAccess();
    GameAccess gameDB = new SQLGameAccess();

    public JoinGameService() throws ResponseException, DataAccessException {
    }

    public void joinGame(String authToken, JoinGame player) throws Unauthorized, DataAccessException, BadRequest, AlreadyTaken, ResponseException {
        if (isNull(authDB.getAuth(authToken))) {
            throw new Unauthorized("Error: unauthorized");
        }
        String username = authDB.getAuth(authToken).username();

        String playerColor = player.playerColor();
        Integer gameID = player.gameID();
        GameData game = gameDB.getGame(gameID);
        if (isNull(game)) {
            throw new BadRequest("Error: bad request");
        }
        if (!isNull(playerColor)){
            if (playerColor.equals("WHITE") && !isNull(game.whiteUsername())) {
                throw new AlreadyTaken("Error: already taken");
            } else {
                if (playerColor.equals("WHITE")){
                    GameData updatedGame = new GameData(gameID, username, game.blackUsername(),game.gameName(),game.game());
                    gameDB.updateGame(gameID, updatedGame);
                }
            }
            if (playerColor.equals("BLACK") && !isNull(game.blackUsername())) {
                throw new AlreadyTaken("Error: already taken");
            } else {
                if (playerColor.equals("BLACK")){
                    GameData updatedGame = new GameData(gameID, game.whiteUsername(), username,game.gameName(),game.game());
                    gameDB.updateGame(gameID, updatedGame);
                }
            }
        } else {
            //Add observers later
        }

    }
}
