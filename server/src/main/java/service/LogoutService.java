package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthAccess;
import exceptions.Unauthorized;

import static java.util.Objects.isNull;

public class LogoutService {
    MemoryAuthAccess authDB = new MemoryAuthAccess();
    public void logout(String auth) throws DataAccessException, Unauthorized {
        if (isNull(authDB.getAuth(auth))) {
            throw new Unauthorized("Error: unauthorized");
        } else {
            authDB.deleteAuth(auth);
        }
    }
}
