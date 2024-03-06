package serviceTests;

import chess.ChessGame;
import dataAccess.*;
import exceptions.AlreadyTaken;
import exceptions.BadRequest;
import exceptions.ResponseException;
import exceptions.Unauthorized;
import model.*;
import org.junit.jupiter.api.*;
import service.ClearService;
import service.JoinGameService;

public class JoinGameServiceTests {
    @BeforeEach
    public void fillDB() throws DataAccessException, ResponseException {
        AuthAccess auths = new SQLAuthAccess();
        GameAccess games = new SQLGameAccess();
        auths.createAuth(new AuthData("Spencer", "Authorized"));
        games.createGame(new GameData(1, null, null, "Game 1", new ChessGame()));
    }
    @AfterEach
    public void clear() throws DataAccessException, ResponseException {
        ClearService clear = new ClearService();
        clear.clearDB();
    }

    @Test
    public void worksAssertion() throws BadRequest, DataAccessException, Unauthorized, AlreadyTaken, ResponseException {
        GameAccess games = new SQLGameAccess();
        JoinGameService joinGame = new JoinGameService();
        String authToken = "Authorized";
        String username = "Spencer";
        JoinGame user = new JoinGame("WHITE", 1);
        joinGame.joinGame(authToken, user);
        Assertions.assertEquals(games.getGame(1).whiteUsername(), username);
    }
    @Test
    public void unauthorizedAssertion() throws BadRequest, Unauthorized, DataAccessException, AlreadyTaken, ResponseException {
        JoinGameService joinGame = new JoinGameService();
        String authToken = "Unauthorized";
        JoinGame user = new JoinGame("WHITE", 1);
        Unauthorized thrown = Assertions.assertThrows(Unauthorized.class, () -> joinGame.joinGame(authToken, user));

        Assertions.assertEquals("Error: unauthorized", thrown.getMessage());
    }
    @Test
    public void alreadyTakenAssertion() throws BadRequest, Unauthorized, DataAccessException, AlreadyTaken, ResponseException {
        GameData updatedGame = new GameData(1, "Johnathan", null, "Game 1", new ChessGame());
        GameAccess games = new SQLGameAccess();
        games.updateGame(1, updatedGame);
        JoinGameService joinGame = new JoinGameService();
        String authToken = "Authorized";
        JoinGame user = new JoinGame("WHITE", 1);
        AlreadyTaken thrown = Assertions.assertThrows(AlreadyTaken.class, () -> joinGame.joinGame(authToken, user));

        Assertions.assertEquals("Error: already taken", thrown.getMessage());
    }
}
