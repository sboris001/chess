package unitTests;

import dataAccess.DataAccessException;
import dataAccess.SQLUserAccess;
import exceptions.ResponseException;
import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void addingUsers() throws ResponseException, DataAccessException {
        SQLUserAccess users = new SQLUserAccess();
        UserData user = new UserData("username", "password", "email@email.com");
        users.addUser(user);
        Assertions.assertEquals(users.getUser(user.username()), user);
    }

    @Test
    public void gettingUsers() throws ResponseException, DataAccessException {
        SQLUserAccess users = new SQLUserAccess();
        UserData spencer = new UserData("Spencer", "Password", "Spencer@email.com");
        UserData spencerFromSQL = users.getUser("Spencer");
        Assertions.assertEquals(spencer, spencerFromSQL);
    }
}
