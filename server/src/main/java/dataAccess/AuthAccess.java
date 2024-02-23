package dataAccess;

import model.AuthData;

public interface AuthAccess {
    public void createAuth(AuthData auth) throws DataAccessException;
    public AuthData getAuth(String authToken) throws DataAccessException;
    public void clearAuths() throws DataAccessException;
}
