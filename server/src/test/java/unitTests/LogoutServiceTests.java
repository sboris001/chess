package unitTests;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthAccess;
import exceptions.Unauthorized;
import model.AuthData;
import model.LogoutUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.LogoutService;

import static java.util.Objects.isNull;

public class LogoutServiceTests {

    @BeforeEach
    public void fillDB() throws DataAccessException {
        MemoryAuthAccess auths = new MemoryAuthAccess();
        auths.createAuth(new AuthData("Spencer", "TestToken"));
    }

    @Test
    public void worksAssertion() throws Unauthorized, DataAccessException {
        String auth = "TestToken";
        LogoutService logout = new LogoutService();
        logout.logout(auth);

        Assertions.assertTrue(isNull(MemoryAuthAccess.auths.get(auth)));
    }

    @Test
    public void failsAssertion() throws Unauthorized, DataAccessException {
        String auth = "BadToken";
        LogoutService logout = new LogoutService();
        Unauthorized thrown = Assertions.assertThrows(Unauthorized.class, () -> logout.logout(auth));

        Assertions.assertEquals("Error: unauthorized", thrown.getMessage());
    }
}
