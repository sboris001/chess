package unitTests;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthAccess;
import dataAccess.MemoryUserAccess;
import exceptions.ResponseException;
import exceptions.Unauthorized;
import model.AuthData;
import model.LoginUser;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.LoginService;
import service.LogoutService;

import static java.util.Objects.isNull;

public class LoginServiceTests {

    @BeforeAll
    public static void fillDB() throws DataAccessException {
        MemoryUserAccess users = new MemoryUserAccess();
        users.addUser(new UserData("Spencer", "Password", "Spencer@email.com"));
    }

    @Test
    public void worksAssertion() throws Unauthorized, DataAccessException, ResponseException {
        LoginService login = new LoginService();
        LoginUser user = new LoginUser("Spencer", "Password");
        AuthData authorization = login.login(user);
        Assertions.assertTrue(MemoryAuthAccess.auths.containsValue(authorization));
    }
    @Test
    public void unauthorizedAssertion() throws Unauthorized, DataAccessException, ResponseException {
        LoginService login = new LoginService();
        LoginUser user = new LoginUser("Spencer", "NotPassword");
        Unauthorized thrown = Assertions.assertThrows(Unauthorized.class, () -> login.login(user));

        Assertions.assertEquals("Error: unauthorized", thrown.getMessage());
    }
}
