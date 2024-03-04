package unitTests;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthAccess;
import dataAccess.MemoryGameAccess;
import exceptions.AlreadyTaken;
import exceptions.BadRequest;
import exceptions.ResponseException;
import exceptions.Unauthorized;
import model.AuthData;
import model.GameData;
import model.JoinGame;
import model.ListGames;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;
import service.JoinGameService;
import service.ListGamesService;

public class ListGamesServiceTests {
    @BeforeEach
    public void fillDB() throws DataAccessException {
        MemoryAuthAccess auths = new MemoryAuthAccess();
        MemoryGameAccess games = new MemoryGameAccess();
        auths.createAuth(new AuthData("Spencer", "Authorized"));
        games.createGame(new GameData(1, null, null, "Game 1", new ChessGame()));
        games.createGame(new GameData(2, null, null, "Game 2", new ChessGame()));
        games.createGame(new GameData(3, null, null, "Game 3", new ChessGame()));
    }
    @AfterEach
    public void clear() throws DataAccessException, ResponseException {
        ClearService clear = new ClearService();
        clear.clearDB();
    }

    @Test
    public void worksAssertion() throws DataAccessException, Unauthorized {
        MemoryGameAccess games = new MemoryGameAccess();
        ListGamesService listGames = new ListGamesService();
        String authToken = "Authorized";
        ListGames list = listGames.listGames(authToken);

        Assertions.assertEquals(list.games(), games.listGames());
    }
    @Test
    public void unauthorizedAssertion() throws Unauthorized, DataAccessException {
        ListGamesService listGames = new ListGamesService();
        String authToken = "Unauthorized";
        Unauthorized thrown = Assertions.assertThrows(Unauthorized.class, () -> listGames.listGames(authToken));

        Assertions.assertEquals("Error: unauthorized", thrown.getMessage());
    }

}

