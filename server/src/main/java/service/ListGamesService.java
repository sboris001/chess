package service;

import dataAccess.*;
import exceptions.ResponseException;
import exceptions.Unauthorized;
import model.GameData;
import model.ListGames;

import java.util.ArrayList;

import static java.util.Objects.isNull;

public class ListGamesService {
    GameAccess gameDB = new MemoryGameAccess();
    AuthAccess authDB = new MemoryAuthAccess();
    public ListGames listGames(String authToken) throws Unauthorized, DataAccessException, ResponseException {
        if (isNull(authDB.getAuth(authToken))) {
            throw new Unauthorized("Error: unauthorized");
        } else {
            ArrayList<GameData> gameList = gameDB.listGames();
            return new ListGames(gameList);
        }
    }
}
