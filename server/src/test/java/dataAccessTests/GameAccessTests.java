package dataAccessTests;

import chess.ChessGame;
import dataAccess.*;
import exceptions.ResponseException;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

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

}
