package service;

import dataAccess.*;
import exceptions.AlreadyTaken;
import exceptions.BadRequest;
import exceptions.ResponseException;
import model.AuthData;
import model.UserData;

import java.util.UUID;

import static java.util.Objects.isNull;

public class RegisterService {
    AuthAccess authDB = new MemoryAuthAccess();
    UserAccess userDB;

    public RegisterService() {
        try {
            userDB = new SQLUserAccess();
        } catch (DataAccessException | ResponseException e) {
            System.out.println(e.getMessage());
        }
    }
    public AuthData registerUser(UserData user) throws DataAccessException, AlreadyTaken, BadRequest, ResponseException {
        String username = user.username();
        if (isNull(userDB.getUser(username))) {
            userDB.addUser(user);
        } else {
            throw new AlreadyTaken("Error: already taken");
        }
        if (isNull(user.username()) || isNull(user.password()) || isNull(user.email())) {
            throw new BadRequest("Error: bad request");
        }

        String authToken = UUID.randomUUID().toString();
        AuthData authorization = new AuthData(username, authToken);
        authDB.createAuth(authorization);

        return authorization;
    }
}
