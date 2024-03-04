package dataAccess;

import exceptions.ResponseException;
import model.AuthData;

public interface AuthAccess {
    public void createAuth(AuthData auth) throws DataAccessException, ResponseException;
    public AuthData getAuth(String authToken) throws DataAccessException, ResponseException;
    public void clearAuths() throws DataAccessException, ResponseException;
    public void deleteAuth(String authToken) throws DataAccessException, ResponseException;
}
