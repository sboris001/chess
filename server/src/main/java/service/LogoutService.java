package service;

import dataAccess.AuthAccess;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthAccess;
import dataAccess.SQLAuthAccess;
import exceptions.ResponseException;
import exceptions.Unauthorized;

import static java.util.Objects.isNull;

public class LogoutService {
    AuthAccess authDB = new SQLAuthAccess();

    public LogoutService() throws ResponseException, DataAccessException {
    }

    public void logout(String auth) throws DataAccessException, Unauthorized, ResponseException {
        if (isNull(authDB.getAuth(auth))) {
            throw new Unauthorized("Error: unauthorized");
        } else {
            authDB.deleteAuth(auth);
        }
    }
}
