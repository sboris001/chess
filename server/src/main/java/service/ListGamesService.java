package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthAccess;
import dataAccess.MemoryGameAccess;
import exceptions.Unauthorized;
import model.GameData;
import model.ListGames;

import java.util.ArrayList;

import static java.util.Objects.isNull;

public class ListGamesService {
    MemoryGameAccess gameDB = new MemoryGameAccess();
    MemoryAuthAccess authDB = new MemoryAuthAccess();
    public ListGames listGames(String authToken) throws Unauthorized, DataAccessException {
        if (isNull(authDB.getAuth(authToken))) {
            throw new Unauthorized("Error: unauthorized");
        } else {
            ArrayList<GameData> gameList = gameDB.listGames();
            return new ListGames(gameList);
        }
    }
}
