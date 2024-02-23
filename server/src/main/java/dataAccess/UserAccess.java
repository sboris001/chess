package dataAccess;

import model.UserData;

public interface UserAccess {
    public void clearUsers() throws DataAccessException;
    public void addUser(UserData user) throws DataAccessException;
    public void getUser(String username) throws DataAccessException;
}
