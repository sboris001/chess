package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthAccess;
import exceptions.Unauthorized;
import model.LogoutUser;

import static java.util.Objects.isNull;

public class LogoutService {
    MemoryAuthAccess authDB = new MemoryAuthAccess();
    public void logout(LogoutUser auth) throws DataAccessException, Unauthorized {
        String authToken = auth.authToken();
        if (isNull(authDB.getAuth(authToken))) {
            throw new Unauthorized("Error: unauthorized");
        } else {
            authDB.deleteAuth(authToken);
        }
    }
}
