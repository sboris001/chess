package serviceTests;

import com.google.gson.Gson;
import dataAccess.*;
import exceptions.BadRequest;
import exceptions.ResponseException;
import exceptions.Unauthorized;
import model.AuthData;
import model.CreateGameObj;
import model.GameID;
import org.junit.jupiter.api.*;
import service.ClearService;
import service.CreateGameService;

import static java.util.Objects.isNull;

public class CreateGameServiceTests {

    @BeforeEach
    public void fillDB() throws DataAccessException, ResponseException {
        AuthAccess auth = new SQLAuthAccess();
        auth.createAuth(new AuthData("Spencer", "Authorized"));
    }

    @AfterEach
    public void clearDB() throws DataAccessException, ResponseException {
        ClearService clear = new ClearService();
        clear.clearDB();
    }

    @Test
    public void worksAssertion() throws BadRequest, DataAccessException, Unauthorized, ResponseException {
        CreateGameService createGame = new CreateGameService();
        GameAccess games = new SQLGameAccess();
        String authToken = "Authorized";
        CreateGameObj game = new CreateGameObj("Game 1");
        GameID gameID = createGame.makeGame(authToken, game);

        Assertions.assertTrue(!isNull(games.getGame(gameID.gameID())));
    }
    @Test
    public void unauthorizedAssertion() throws BadRequest, Unauthorized, DataAccessException, ResponseException {
        CreateGameService createGame = new CreateGameService();
        String authToken = "Unauthorized";
        CreateGameObj game = new CreateGameObj("Game 1");
        Unauthorized thrown = Assertions.assertThrows(Unauthorized.class, () -> createGame.makeGame(authToken, game));

        Assertions.assertEquals("Error: unauthorized", thrown.getMessage());
    }

    @Test
    public void badRequestAssertion() throws BadRequest, Unauthorized, DataAccessException, ResponseException {
        CreateGameService createGame = new CreateGameService();
        String authToken = "Authorized";
        CreateGameObj game = new CreateGameObj(null);
        BadRequest thrown = Assertions.assertThrows(BadRequest.class, () -> createGame.makeGame(authToken, game));

        Assertions.assertEquals("Error: bad request", thrown.getMessage());
    }

}
