package dataAccessTests;

import dataAccess.*;
import exceptions.ResponseException;
import model.AuthData;
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
    // Get Auth Tests
    @Test
    public void getAuthPositive() throws ResponseException, DataAccessException {
        AuthAccess auths = new SQLAuthAccess();
        AuthData auth = auths.getAuth("Authorized");
        Assertions.assertFalse(isNull(auth));
    }
    // Try to get an auth that doesn't exist
    @Test
    public void getAuthNegative() throws ResponseException, DataAccessException {
        AuthAccess auths = new SQLAuthAccess();
        AuthData auth = auths.getAuth("Unauthorized");
        Assertions.assertTrue(isNull(auth));
    }
    // Delete Auth test
    @Test
    public void deleteAuthPositive() throws ResponseException, DataAccessException {
        AuthAccess auths = new SQLAuthAccess();
        auths.deleteAuth("Authorized");
        Assertions.assertTrue(isNull(auths.getAuth("Authorized")));
    }
    // Delete an auth that doesn't exist -- make sure it doesn't delete all auths
    @Test
    public void deleteAuthNegative() throws ResponseException, DataAccessException {
        AuthAccess auths = new SQLAuthAccess();
        auths.deleteAuth("Unauthorized");
        Assertions.assertFalse(isNull(auths.getAuth("Authorized")));
    }
    // Clear Auths Test
    @Test
    public void clearAuthsPositive() throws ResponseException, DataAccessException {
        AuthAccess auths = new SQLAuthAccess();
        auths.clearAuths();
        Assertions.assertTrue(isNull(auths.getAuth("Authorized")));
    }
    // No real way to test negative case
}
