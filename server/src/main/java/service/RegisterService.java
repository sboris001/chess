package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthAccess;
import dataAccess.MemoryUserAccess;
import model.AuthData;
import model.UserData;

import java.util.UUID;

import static java.util.Objects.isNull;

public class RegisterService {
    MemoryAuthAccess authDB = new MemoryAuthAccess();
    MemoryUserAccess userDB = new MemoryUserAccess();
    public AuthData registerUser(UserData user) throws DataAccessException {
        String username = user.username();
        if (isNull(userDB.getUser(username))) {
            userDB.addUser(user);
        } else {
            throw new DataAccessException("User already exists");
        }
        String authToken = UUID.randomUUID().toString();
        AuthData authorization = new AuthData(username, authToken);
        authDB.createAuth(authorization);

        return authorization;
    }
}
