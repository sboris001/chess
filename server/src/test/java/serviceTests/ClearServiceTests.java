package serviceTests;
import chess.ChessGame;
import dataAccess.*;
import exceptions.ResponseException;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.ClearService;

public class ClearServiceTests {
    @BeforeAll
    public static void fillDB() throws DataAccessException, ResponseException {
        UserAccess users = new SQLUserAccess();
        GameAccess games = new SQLGameAccess();
        AuthAccess auths = new SQLAuthAccess();

        users.addUser(new UserData("Spencer", "password", "spencer@email.com"));
        games.createGame(new GameData(1, "Spencer", "Jaden", "Friendly game", new ChessGame()));
        auths.createAuth(new AuthData("testToken", "Spencer"));
    }

    @Test
    public void simpleAssertionTest() throws DataAccessException, ResponseException {
        ClearService clear = new ClearService();
        clear.clearDB();

        Assertions.assertTrue(MemoryUserAccess.users.isEmpty());
        Assertions.assertTrue(MemoryAuthAccess.auths.isEmpty());
        Assertions.assertTrue(MemoryGameAccess.games.isEmpty());
    }
}
