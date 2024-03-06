package serviceTests;

import dataAccess.AuthAccess;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthAccess;
import dataAccess.SQLAuthAccess;
import exceptions.ResponseException;
import exceptions.Unauthorized;
import model.AuthData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;
import service.LogoutService;

import static java.util.Objects.isNull;

public class LogoutServiceTests {

    @BeforeEach
    public void fillDB() throws DataAccessException, ResponseException {
        AuthAccess auths = new SQLAuthAccess();
        auths.createAuth(new AuthData("Spencer", "TestToken"));
    }

    @AfterEach
    public void clearDB() throws DataAccessException, ResponseException {
        ClearService clear = new ClearService();
        clear.clearDB();
    }

    @Test
    public void worksAssertion() throws Unauthorized, DataAccessException, ResponseException {
        String auth = "TestToken";
        AuthAccess auths = new SQLAuthAccess();
        LogoutService logout = new LogoutService();
        logout.logout(auth);

        Assertions.assertTrue(isNull(auths.getAuth(auth)));
    }

    @Test
    public void failsAssertion() throws Unauthorized, DataAccessException, ResponseException {
        String auth = "BadToken";
        LogoutService logout = new LogoutService();
        Unauthorized thrown = Assertions.assertThrows(Unauthorized.class, () -> logout.logout(auth));

        Assertions.assertEquals("Error: unauthorized", thrown.getMessage());
    }
}
