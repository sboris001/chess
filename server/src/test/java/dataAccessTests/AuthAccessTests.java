package dataAccessTests;

import chess.ChessGame;
import dataAccess.*;
import exceptions.ResponseException;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

import static java.util.Objects.isNull;

public class AuthAccessTests {
    @BeforeEach
    public void fillDB() throws DataAccessException, ResponseException {
        AuthAccess auths = new SQLAuthAccess();
        auths.createAuth(new AuthData("Spencer", "Authorized"));
    }
    @AfterEach
    public void clear() throws DataAccessException, ResponseException {
        ClearService clear = new ClearService();
        clear.clearDB();
    }
    // Create Auth Tests
    @Test
    public void createAuthPositive() throws ResponseException, DataAccessException {
        AuthAccess auths = new SQLAuthAccess();
        auths.createAuth(new AuthData("UserTesting", "NewAuthorized"));
        Assertions.assertFalse(isNull(auths.getAuth("NewAuthorized")));
    }
    // Try to create a duplicate authToken -- Should throw a ResponseException
    @Test
    public void createAuthNegative() throws ResponseException, DataAccessException {
        AuthAccess auths = new SQLAuthAccess();
        ResponseException thrown = Assertions.assertThrows(ResponseException.class, () -> auths.createAuth(new AuthData("Spencer", "Authorized")));

        Assertions.assertEquals("unable to update database: INSERT INTO auths (authToken, username, json) VALUES (?, ?, ?), Duplicate entry 'Authorized' for key 'auths.PRIMARY'", thrown.getMessage());
    }
}
