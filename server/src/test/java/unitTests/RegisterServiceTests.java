package unitTests;

import dataAccess.DataAccessException;
import dataAccess.MemoryUserAccess;
import exceptions.AlreadyTaken;
import exceptions.BadRequest;
import exceptions.ResponseException;
import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;
import service.RegisterService;

public class RegisterServiceTests {
    @BeforeEach
    public void fillDB() throws DataAccessException {
        MemoryUserAccess users = new MemoryUserAccess();
        users.addUser(new UserData("Spencer", "Password", "Spencer@email.com"));
    }
    @AfterEach
    public void reset() throws DataAccessException, ResponseException {
        ClearService clear = new ClearService();
        clear.clearDB();
    }

    @Test
    public void worksAssertion() throws BadRequest, AlreadyTaken, DataAccessException, ResponseException {
        RegisterService register = new RegisterService();
        UserData newUser = new UserData("John", "pass", "John@email.com");
        register.registerUser(newUser);
        Assertions.assertTrue(MemoryUserAccess.users.contains(newUser));
    }
    @Test
    public void alreadyTakenAssertion() throws BadRequest, AlreadyTaken, DataAccessException {
        RegisterService register = new RegisterService();
        UserData newUser = new UserData("Spencer", "Password", "Spencer@email.com");
        AlreadyTaken thrown = Assertions.assertThrows(AlreadyTaken.class, () -> register.registerUser(newUser));

        Assertions.assertEquals("Error: already taken", thrown.getMessage());
    }

    @Test
    public void badRequestAssertion() throws BadRequest, AlreadyTaken, DataAccessException {
        RegisterService register = new RegisterService();
        UserData newUser = new UserData(null, "Password", "Spencer@email.com");
        BadRequest thrown = Assertions.assertThrows(BadRequest.class, () -> register.registerUser(newUser));

        Assertions.assertEquals("Error: bad request", thrown.getMessage());
    }
}
