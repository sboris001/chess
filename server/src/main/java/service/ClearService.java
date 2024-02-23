package service;

import dataAccess.MemoryUserAccess;
import dataAccess.MemoryGameAccess;
import dataAccess.MemoryAuthAccess;

public class ClearService {
    MemoryAuthAccess authDB = new MemoryAuthAccess();
    MemoryUserAccess userDB = new MemoryUserAccess();
    MemoryGameAccess gameDB = new MemoryGameAccess();
    public void clearDB() {
    }
}
