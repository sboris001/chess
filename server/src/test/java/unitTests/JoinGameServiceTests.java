package unitTests;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthAccess;
import dataAccess.MemoryGameAccess;
import exceptions.AlreadyTaken;
import exceptions.BadRequest;
import exceptions.Unauthorized;
import model.*;
import org.junit.jupiter.api.*;
import service.ClearService;
import service.CreateGameService;
import service.JoinGameService;

public class JoinGameServiceTests {
    @BeforeEach
    public void fillDB() throws DataAccessException {
        MemoryAuthAccess auths = new MemoryAuthAccess();
        MemoryGameAccess games = new MemoryGameAccess();
        auths.createAuth(new AuthData("Spencer", "Authorized"));
        games.createGame(new GameData(1, null, null, "Game 1", new ChessGame()));
    }
    @AfterEach
    public void clear() throws DataAccessException {
        ClearService clear = new ClearService();
        clear.clearDB();
    }

    @Test
    public void worksAssertion() throws BadRequest, DataAccessException, Unauthorized, AlreadyTaken {
        MemoryGameAccess games = new MemoryGameAccess();
        JoinGameService joinGame = new JoinGameService();
        String authToken = "Authorized";
        String username = "Spencer";
        JoinGame user = new JoinGame("WHITE", 1);
        joinGame.joinGame(authToken, user);
        Assertions.assertEquals(games.getGame(1).whiteUsername(), username);
    }
    @Test
    public void unauthorizedAssertion() throws BadRequest, Unauthorized, DataAccessException, AlreadyTaken {
        JoinGameService joinGame = new JoinGameService();
        String authToken = "Unauthorized";
        JoinGame user = new JoinGame("WHITE", 1);
        Unauthorized thrown = Assertions.assertThrows(Unauthorized.class, () -> joinGame.joinGame(authToken, user));

        Assertions.assertEquals("Error: unauthorized", thrown.getMessage());
    }
    @Test
    public void alreadyTakenAssertion() throws BadRequest, Unauthorized, DataAccessException, AlreadyTaken {
        GameData updatedGame = new GameData(1, "Johnathan", null, "Game 1", new ChessGame());
        MemoryGameAccess games = new MemoryGameAccess();
        games.updateGame(1, updatedGame);
        JoinGameService joinGame = new JoinGameService();
        String authToken = "Authorized";
        JoinGame user = new JoinGame("WHITE", 1);
        AlreadyTaken thrown = Assertions.assertThrows(AlreadyTaken.class, () -> joinGame.joinGame(authToken, user));

        Assertions.assertEquals("Error: already taken", thrown.getMessage());
    }
}
