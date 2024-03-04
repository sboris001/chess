package dataAccess;

import exceptions.ResponseException;
import model.UserData;

public interface UserAccess {
    public void clearUsers() throws DataAccessException, ResponseException;
    public void addUser(UserData user) throws DataAccessException, ResponseException;
    public UserData getUser(String username) throws DataAccessException, ResponseException;
}
