package service;

import dataAccess.*;

public class ClearService {
    AuthAccess authDB = new MemoryAuthAccess();
    UserAccess userDB = new MemoryUserAccess();
    GameAccess gameDB = new MemoryGameAccess();
    public void clearDB() throws DataAccessException {
        authDB.clearAuths();
        userDB.clearUsers();
        gameDB.clearGames();
    }
}
