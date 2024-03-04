package service;

import dataAccess.*;
import exceptions.ResponseException;
import exceptions.Unauthorized;
import model.GameData;
import model.ListGames;

import java.util.ArrayList;

import static java.util.Objects.isNull;

public class ListGamesService {
    GameAccess gameDB = new SQLGameAccess();
    AuthAccess authDB = new SQLAuthAccess();

    public ListGamesService() throws ResponseException, DataAccessException {
    }

    public ListGames listGames(String authToken) throws Unauthorized, DataAccessException, ResponseException {
        if (isNull(authDB.getAuth(authToken))) {
            throw new Unauthorized("Error: unauthorized");
        } else {
            ArrayList<GameData> gameList = gameDB.listGames();
            return new ListGames(gameList);
        }
    }
}
