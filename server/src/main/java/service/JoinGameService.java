package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthAccess;
import dataAccess.MemoryGameAccess;
import exceptions.AlreadyTaken;
import exceptions.BadRequest;
import exceptions.Unauthorized;
import model.GameData;
import model.JoinGame;

import static java.util.Objects.isNull;

public class JoinGameService {
    MemoryAuthAccess authDB = new MemoryAuthAccess();
    MemoryGameAccess gameDB = new MemoryGameAccess();
    public void joinGame(String authToken, JoinGame player) throws Unauthorized, DataAccessException, BadRequest, AlreadyTaken {
        if (isNull(authDB.getAuth(authToken))) {
            throw new Unauthorized("Error: unauthorized");
        }
        String username = authDB.getAuth(authToken).username();

        String playerColor = player.playerColor();
        Integer gameID = player.gameID();
        if (isNull(gameDB.getGame(gameID))) {
            throw new BadRequest("Error: bad request");
        }
        if (!isNull(playerColor)){
            if (playerColor.equals("WHITE") && !isNull(gameDB.getGame(gameID).whiteUsername())) {
                throw new AlreadyTaken("Error: already taken");
            } else {
                if (playerColor.equals("WHITE")){
                    GameData updatedGame = new GameData(gameID, username, gameDB.getGame(gameID).blackUsername(),gameDB.getGame(gameID).gameName(),gameDB.getGame(gameID).game());
                    gameDB.updateGame(gameID, updatedGame);
                }
            }
            if (playerColor.equals("BLACK") && !isNull(gameDB.getGame(gameID).blackUsername())) {
                throw new AlreadyTaken("Error: already taken");
            } else {
                if (playerColor.equals("BLACK")){
                    GameData updatedGame = new GameData(gameID, gameDB.getGame(gameID).whiteUsername(), username,gameDB.getGame(gameID).gameName(),gameDB.getGame(gameID).game());
                    gameDB.updateGame(gameID, updatedGame);
                }
            }
        }

    }
}
