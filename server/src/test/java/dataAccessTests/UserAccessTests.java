package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.SQLUserAccess;
import exceptions.ResponseException;
import exceptions.Unauthorized;
import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Objects.isNull;

public class UserAccessTests {
    @BeforeEach
    public void fillDB() throws DataAccessException, ResponseException {
        SQLUserAccess users = new SQLUserAccess();
        users.addUser(new UserData("Spencer", "Password", "Spencer@email.com"));
    }
    @AfterEach
    public void clearDB() throws ResponseException, DataAccessException {
        SQLUserAccess users = new SQLUserAccess();
        users.clearUsers();
    }

    @Test
    public void addingUsersPositive() throws ResponseException, DataAccessException {
        SQLUserAccess users = new SQLUserAccess();
        UserData user = new UserData("username", "password", "email@email.com");
        users.addUser(user);
        Assertions.assertEquals(users.getUser(user.username()), user);
    }

    //Try adding with a null entry
    @Test
    public void addingUsersNegative() throws ResponseException, DataAccessException {
        SQLUserAccess users = new SQLUserAccess();
        UserData user = new UserData(null, "password", "myEmail");

        ResponseException thrown = Assertions.assertThrows(ResponseException.class, () -> users.addUser(user));
        Assertions.assertEquals("unable to update database: INSERT INTO users (username, password, email, json) VALUES (?, ?, ?, ?), Column 'username' cannot be null", thrown.getMessage());
    }

    @Test
    public void gettingUsersPositive() throws ResponseException, DataAccessException {
        SQLUserAccess users = new SQLUserAccess();
        UserData spencer = new UserData("Spencer", "Password", "Spencer@email.com");
        UserData spencerFromSQL = users.getUser("Spencer");
        Assertions.assertEquals(spencer, spencerFromSQL);
    }

    // Negative test -- Try to get a user that doesn't exist
    @Test
    public void gettingUsersNegative() throws ResponseException, DataAccessException {
        SQLUserAccess users = new SQLUserAccess();
        UserData notThere = users.getUser("Fake Username");
        Assertions.assertTrue(isNull(notThere));
    }

    @Test
    public void clearUsersPositive() throws ResponseException, DataAccessException {
        SQLUserAccess users = new SQLUserAccess();
        users.clearUsers();
        Assertions.assertTrue(isNull(users.getUser("Spencer")));
    }
    // No way to test clearUsers negatively
}
