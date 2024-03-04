package service;

import dataAccess.AuthAccess;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthAccess;
import exceptions.ResponseException;
import exceptions.Unauthorized;

import static java.util.Objects.isNull;

public class LogoutService {
    AuthAccess authDB = new MemoryAuthAccess();
    public void logout(String auth) throws DataAccessException, Unauthorized, ResponseException {
        if (isNull(authDB.getAuth(auth))) {
            throw new Unauthorized("Error: unauthorized");
        } else {
            authDB.deleteAuth(auth);
        }
    }
}
