package service;

import dataAccess.*;
import exceptions.ResponseException;

public class ClearService {
    AuthAccess authDB = new SQLAuthAccess();
    UserAccess userDB = new SQLUserAccess();
    GameAccess gameDB = new SQLGameAccess();

    public ClearService() throws ResponseException, DataAccessException {
    }

    public void clearDB() throws DataAccessException, ResponseException {
        authDB.clearAuths();
        userDB.clearUsers();
        gameDB.clearGames();
    }
}
