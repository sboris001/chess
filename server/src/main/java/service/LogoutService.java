package service;

import dataAccess.*;
import exceptions.ResponseException;
import exceptions.Unauthorized;

import static java.util.Objects.isNull;

public class LogoutService {


    AuthAccess authDB;

    public LogoutService() throws ResponseException, DataAccessException {
        try {
            authDB = new SQLAuthAccess();
        } catch (DataAccessException | ResponseException e) {
            System.out.println(e.getMessage());
        }
    }

    public void logout(String auth) throws DataAccessException, Unauthorized, ResponseException {
        if (isNull(authDB.getAuth(auth))) {
            throw new Unauthorized("Error: unauthorized");
        } else {
            authDB.deleteAuth(auth);
        }
    }
}
