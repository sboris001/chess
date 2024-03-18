package clientTests;

import com.google.gson.Gson;
import model.*;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;

import java.io.IOException;
import java.net.*;


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
    }

    @AfterAll
    static void stopServer() {
        server.stop();
        System.out.println("Server Stopped");
    }

    @BeforeEach
    public void clearDB() {
        try{
            URL url = (new URI("http://localhost:" + port + "/db")).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("DELETE");
            http.setDoOutput(true);
            http.connect();
            if (http.getResponseCode() == 200) {
                System.out.println("DB cleared");
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void registerTest() {
        try {
            facade.registerUser(new UserData("testUser", "testPassword", "testEmail"));
            System.out.println("Register Successful");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void loginTest() {
        try {
            facade.registerUser(new UserData("fred", "pw", "maile"));
            AuthData auth = facade.loginUser(new LoginUser("fred", "pw"));
            System.out.println(auth.authToken());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createGameTest() {
        try {
            AuthData auth = facade.registerUser(new UserData("fred", "pw", "maile"));
            GameID id = facade.createGame(auth, new CreateGameObj("Game 1"));
            System.out.println(new Gson().toJson(id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
