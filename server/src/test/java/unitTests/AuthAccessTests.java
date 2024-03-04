package unitTests;

import dataAccess.DataAccessException;
import dataAccess.SQLAuthAccess;
import exceptions.ResponseException;
import model.AuthData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthAccessTests {
    @BeforeEach
    public void fillDB() throws DataAccessException, ResponseException {
        SQLAuthAccess auths = new SQLAuthAccess();
        auths.createAuth(new AuthData("username", "authToken"));
    }
    @AfterEach
    public void clearDB() throws ResponseException, DataAccessException {
        SQLAuthAccess users = new SQLAuthAccess();
        users.clearAuths();
    }

    @Test
    public void addingAuths() throws ResponseException, DataAccessException {
        SQLAuthAccess auths = new SQLAuthAccess();
        AuthData auth = new AuthData("newUsername", "newAuthToken");
        auths.createAuth(auth);
        Assertions.assertEquals(auths.getAuth("newAuthToken"), auth);
    }

    @Test
    public void deletingAnAuth() throws ResponseException, DataAccessException {
        SQLAuthAccess auths = new SQLAuthAccess();
        auths.deleteAuth("authToken");
        Assertions.assertNull(auths.getAuth("authToken"));
    }

    @Test
    public void gettingAuths() throws ResponseException, DataAccessException {
        SQLAuthAccess auths = new SQLAuthAccess();
        AuthData authDataFromSQL = auths.getAuth("authToken");
        AuthData authData = new AuthData("username", "authToken");
        Assertions.assertEquals(authDataFromSQL, authData);
    }
}
