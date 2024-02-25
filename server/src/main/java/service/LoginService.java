package service;

import dataAccess.*;
import exceptions.Unauthorized;
import model.AuthData;
import model.LoginUser;
import model.UserData;

import java.util.UUID;

import static java.util.Objects.isNull;

public class LoginService {
    AuthAccess authDB = new MemoryAuthAccess();
    UserAccess userDB = new MemoryUserAccess();

    public AuthData login(LoginUser user) throws DataAccessException, Unauthorized {
        String username = user.username();
        if (!isNull(userDB.getUser(username))) {
            UserData storedUser = userDB.getUser(username);
            if (storedUser.password().equals(user.password())) {
                String authToken = UUID.randomUUID().toString();
                AuthData authorization = new AuthData(username, authToken);
                authDB.createAuth(authorization);
                return authorization;
            } else {
                throw new Unauthorized("Error: unauthorized");
            }
        } else {
            throw new Unauthorized("Error: unauthorized");
        }
    }
}
