package dataAccess;

import model.UserData;

import java.util.ArrayList;

public class MemoryUserAccess implements UserAccess {

    public static ArrayList<UserData> users = new ArrayList<>();

    @Override
    public void clearUsers() throws DataAccessException {
        users.clear();
    }

    @Override
    public void addUser(UserData user) throws DataAccessException {
        users.add(user);
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        for (UserData user: users){
            if (user.username().equals(username)){
                return user;
            }
        }
        return null;
    }
}
