package clientTests;

import chess.ChessGame;
import model.*;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;

import java.io.IOException;
import java.net.*;

import static java.util.Objects.isNull;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;
    static int port;

    @BeforeAll
    public static void init() {
        server = new Server();
        port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        String url = "http://localhost:" + port;
        facade = new ServerFacade(url);
        try{
            URL otherurl = (new URI("http://localhost:" + port + "/db")).toURL();
            HttpURLConnection http = (HttpURLConnection) otherurl.openConnection();
            http.setRequestMethod("DELETE");
            http.setDoOutput(true);
            http.connect();
            if (http.getResponseCode() == 200) {
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void stopServer() {
        server.stop();
        System.out.println("Server Stopped");
    }

    @AfterEach
    public void clearDB() {
        try{
            URL url = (new URI("http://localhost:" + port + "/db")).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("DELETE");
            http.setDoOutput(true);
            http.connect();
            if (http.getResponseCode() == 200) {
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void registerTestPositive() {
        AuthData auth;
        try {
            auth = facade.registerUser(new UserData("testUser", "testPassword", "testEmail"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isNull(auth));
    }

    @Test
    public void registerTestNegative() {
        AuthData auth;
        try {
            facade.registerUser(new UserData("user", "pass", "email"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IOException thrown = Assertions.assertThrows(IOException.class, () -> facade.registerUser(new UserData("user", "pass", "email")));
        Assertions.assertEquals("Could not register", thrown.getMessage());
    }

    @Test
    public void loginTestPositive() {
        AuthData auth;
        try {
            facade.registerUser(new UserData("testUser", "testPassword", "testEmail"));
            auth = facade.loginUser(new LoginUser("testUser", "testPassword"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isNull(auth));
    }

    @Test
    public void loginTestNegative() {
        IOException thrown = Assertions.assertThrows(IOException.class, () -> facade.loginUser(new LoginUser("user", "pass")));
        Assertions.assertEquals("Could not login", thrown.getMessage());
    }

    @Test
    public void createGameTestPositive() {
        GameID id;
        try {
            AuthData auth = facade.registerUser(new UserData("testUser", "testPassword", "testEmail"));
            id = facade.createGame(auth, new CreateGameObj("testGame"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isNull(id));
    }
    @Test
    public void createGameNegativeTest() {
        AuthData fakeAuth = new AuthData("user", "auth");
        IOException thrown = Assertions.assertThrows(IOException.class, () -> facade.createGame(fakeAuth, new CreateGameObj("gameName")));
        Assertions.assertEquals("Could not create game", thrown.getMessage());
    }

    @Test
    public void joinGamePositiveTest() {
        GameID id;
        ListGames list;
        try {
            AuthData auth = facade.registerUser(new UserData("testUser", "testPassword", "testEmail"));
            id = facade.createGame(auth, new CreateGameObj("testGame"));
            facade.joinGame(auth, new JoinGame("WHITE", id.gameID()));
            list = facade.listGames(auth);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(list.games().contains(new GameData(id.gameID(), "testUser", null, "testGame", new ChessGame())));
    }

    @Test
    public void joinGameNegativeTest() {
        AuthData fakeAuth = new AuthData("user", "auth");
        IOException thrown = Assertions.assertThrows(IOException.class, () -> facade.joinGame(fakeAuth, new JoinGame("WHITE", 123890)));
        Assertions.assertEquals("Could not join game", thrown.getMessage());
    }

    @Test
    public void listGamesPositiveTest() {
        GameID id;
        ListGames list;
        try {
            AuthData auth = facade.registerUser(new UserData("testUser", "testPassword", "testEmail"));
            id = facade.createGame(auth, new CreateGameObj("testGame"));
            facade.joinGame(auth, new JoinGame("WHITE", id.gameID()));
            list = facade.listGames(auth);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isNull(list));
    }

    @Test
    public void listGamesNegativeTest() {
        AuthData fakeAuth = new AuthData("user", "auth");
        IOException thrown = Assertions.assertThrows(IOException.class, () -> facade.listGames(fakeAuth));
        Assertions.assertEquals("Could not list games", thrown.getMessage());
    }

    @Test
    public void logoutUserPositiveTest() {
        AuthData auth;
        try {
            auth = facade.registerUser(new UserData("testUser", "testPassword", "testEmail"));
            facade.logoutUser(auth);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IOException thrown = Assertions.assertThrows(IOException.class, () -> facade.listGames(auth));
        Assertions.assertEquals("Could not list games", thrown.getMessage());
    }

    @Test
    public void logoutUserNegativeTest() {
        AuthData auth;
        GameID id;
        ListGames list;
        try {
            auth = facade.registerUser(new UserData("testUser", "testPassword", "testEmail"));
            id = facade.createGame(auth, new CreateGameObj("testGame"));
            facade.logoutUser(auth);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IOException thrown = Assertions.assertThrows(IOException.class, () -> facade.joinGame(auth, new JoinGame("WHITE", id.gameID())));
        Assertions.assertEquals("Could not join game", thrown.getMessage());

    }
}
