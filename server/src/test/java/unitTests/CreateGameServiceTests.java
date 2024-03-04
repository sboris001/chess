package unitTests;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthAccess;
import dataAccess.MemoryGameAccess;
import exceptions.BadRequest;
import exceptions.ResponseException;
import exceptions.Unauthorized;
import model.AuthData;
import model.CreateGameObj;
import model.GameID;
import org.junit.jupiter.api.*;
import service.CreateGameService;

public class CreateGameServiceTests {

    @BeforeAll
    public static void fillDB() throws DataAccessException {
        MemoryAuthAccess auth = new MemoryAuthAccess();
        auth.createAuth(new AuthData("Spencer", "Authorized"));
    }

    @Test
    public void worksAssertion() throws BadRequest, DataAccessException, Unauthorized, ResponseException {
        CreateGameService createGame = new CreateGameService();
        String authToken = "Authorized";
        CreateGameObj game = new CreateGameObj("Game 1");
        GameID gameID = createGame.makeGame(authToken, game);

        Assertions.assertTrue(MemoryGameAccess.games.containsKey(gameID.gameID()));
    }
    @Test
    public void unauthorizedAssertion() throws BadRequest, Unauthorized, DataAccessException {
        CreateGameService createGame = new CreateGameService();
        String authToken = "Unauthorized";
        CreateGameObj game = new CreateGameObj("Game 1");
        Unauthorized thrown = Assertions.assertThrows(Unauthorized.class, () -> createGame.makeGame(authToken, game));

        Assertions.assertEquals("Error: unauthorized", thrown.getMessage());
    }

    @Test
    public void badRequestAssertion() throws BadRequest, Unauthorized, DataAccessException {
        CreateGameService createGame = new CreateGameService();
        String authToken = "Authorized";
        CreateGameObj game = new CreateGameObj(null);
        BadRequest thrown = Assertions.assertThrows(BadRequest.class, () -> createGame.makeGame(authToken, game));

        Assertions.assertEquals("Error: bad request", thrown.getMessage());
    }

}
