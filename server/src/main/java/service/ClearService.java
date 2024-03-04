package service;

import dataAccess.*;
import exceptions.ResponseException;

public class ClearService {
    AuthAccess authDB = new MemoryAuthAccess();
    UserAccess userDB = new MemoryUserAccess();
    GameAccess gameDB = new MemoryGameAccess();
    public void clearDB() throws DataAccessException, ResponseException {
        authDB.clearAuths();
        userDB.clearUsers();
        gameDB.clearGames();
    }
}
