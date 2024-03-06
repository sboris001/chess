package serviceTests;

import chess.ChessGame;
import dataAccess.*;
import exceptions.ResponseException;
import exceptions.Unauthorized;
import model.AuthData;
import model.GameData;
import model.ListGames;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;
import service.ListGamesService;

import java.util.ArrayList;

public class ListGamesServiceTests {
    @BeforeEach
    public void fillDB() throws DataAccessException, ResponseException {
        AuthAccess auths = new SQLAuthAccess();
        GameAccess games = new SQLGameAccess();
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
    public void worksAssertion() throws DataAccessException, Unauthorized, ResponseException {
        GameAccess games = new SQLGameAccess();
        ListGamesService listGames = new ListGamesService();
        AuthData auth = new AuthData("Spencer", "Authorized");
        ArrayList<GameData> list = listGames.listGames(auth.authToken()).games();
        Assertions.assertEquals(games.listGames().size(), list.size());

    }
    @Test
    public void unauthorizedAssertion() throws Unauthorized, DataAccessException, ResponseException {
        ListGamesService listGames = new ListGamesService();
        String authToken = "Unauthorized";
        Unauthorized thrown = Assertions.assertThrows(Unauthorized.class, () -> listGames.listGames(authToken));

        Assertions.assertEquals("Error: unauthorized", thrown.getMessage());
    }

}

