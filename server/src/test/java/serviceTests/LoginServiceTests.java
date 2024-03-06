package serviceTests;

import dataAccess.*;
import exceptions.ResponseException;
import exceptions.Unauthorized;
import model.AuthData;
import model.LoginUser;
import model.UserData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.ClearService;
import service.LoginService;

import static java.util.Objects.isNull;

public class LoginServiceTests {

    @BeforeAll
    public static void fillDB() throws DataAccessException, ResponseException {
        UserAccess users = new SQLUserAccess();
        users.addUser(new UserData("Spencer", "Password", "Spencer@email.com"));
    }

    @AfterAll
    public static void clearDB() throws DataAccessException, ResponseException {
        ClearService clear = new ClearService();
        clear.clearDB();
    }

    @Test
    public void worksAssertion() throws Unauthorized, DataAccessException, ResponseException {
        LoginService login = new LoginService();
        LoginUser user = new LoginUser("Spencer", "Password");
        AuthAccess auths = new SQLAuthAccess();
        AuthData authorization = login.login(user);
        Assertions.assertFalse(isNull(auths.getAuth(authorization.authToken())));
    }
    @Test
    public void unauthorizedAssertion() throws Unauthorized, DataAccessException, ResponseException {
        LoginService login = new LoginService();
        LoginUser user = new LoginUser("Spencer", "NotPassword");
        Unauthorized thrown = Assertions.assertThrows(Unauthorized.class, () -> login.login(user));

        Assertions.assertEquals("Error: unauthorized", thrown.getMessage());
    }
}
