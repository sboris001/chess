package dataAccessTests;

import chess.ChessGame;
import dataAccess.*;
import exceptions.ResponseException;
import exceptions.Unauthorized;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;
import spark.Response;

import java.util.ArrayList;

import static java.util.Objects.isNull;

public class GameAccessTests {
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
    // Create Game Tests
    @Test
    public void createGamePositive() throws ResponseException, DataAccessException {
        GameAccess games = new SQLGameAccess();
        games.createGame(new GameData(100, null,null, "Test Game", new ChessGame()));

        Assertions.assertFalse(isNull(games.getGame(100)));
    }
    // Try to create a game with a duplicate ID.  Should throw a Response Exception
    @Test
    public void createGameNegative() throws ResponseException, DataAccessException {
        GameAccess games = new SQLGameAccess();

        ResponseException thrown = Assertions.assertThrows(ResponseException.class, () -> games.createGame(new GameData(1, null,null, "Test Game", new ChessGame())));

        Assertions.assertEquals("unable to update database: INSERT INTO games (gameID, whiteUsername, blackUsername, gameName, json) VALUES (?, ?, ?, ?, ?), Duplicate entry '1' for key 'games.PRIMARY'", thrown.getMessage());
    }
    // Get Game Tests
    @Test
    public void getGamePositive() throws ResponseException, DataAccessException {
        GameAccess games = new SQLGameAccess();
        GameData game = games.getGame(1);

        Assertions.assertFalse(isNull(game));
    }
    // Try to get a game that doesn't exist.  Should return null
    @Test
    public void getGameNegative() throws ResponseException, DataAccessException {
        GameAccess games = new SQLGameAccess();
        GameData game = games.getGame(100);
        Assertions.assertTrue(isNull(game));
    }
    // List Games Tests
    @Test
    public void listGamesPositive() throws ResponseException, DataAccessException {
        GameAccess games = new SQLGameAccess();
        ArrayList<GameData> list = games.listGames();

        Assertions.assertEquals(list.size(), 3);
    }
    // Try to get the list when no games are in the db.  Should return an empty array
    @Test
    public void listGamesNegative() throws ResponseException, DataAccessException {
        GameAccess games = new SQLGameAccess();
        games.clearGames();
        ArrayList<GameData> list = games.listGames();
        Assertions.assertEquals(list, new ArrayList<GameData>());
    }
    // Update Game Tests
    @Test
    public void updateGamePositive() throws ResponseException, DataAccessException {
        GameAccess games = new SQLGameAccess();
        GameData updatedGame = new GameData(1, "Johnny", null, "Game 1", new ChessGame());
        games.updateGame(1, updatedGame);

        GameData pulledGame = games.getGame(1);
        Assertions.assertEquals(pulledGame.whiteUsername(), "Johnny");
    }
    // Try to update a game that doesn't exist.  Shouldn't throw an error, but it shouldn't do anything.  Nothing updated, no new row created
    // Check to see if it creates a new row or not
    @Test
    public void updateGameNegative() throws ResponseException, DataAccessException {
        GameAccess games = new SQLGameAccess();
        GameData updatedGame = new GameData(100, "Johnny", null, "Game 100", new ChessGame());
        games.updateGame(100, updatedGame);
        GameData pulledGame = games.getGame(100);
        Assertions.assertTrue(isNull(pulledGame));
    }
}
