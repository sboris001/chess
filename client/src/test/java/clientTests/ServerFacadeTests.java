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
        try{
            URL otherurl = (new URI("http://localhost:" + port + "/db")).toURL();
            HttpURLConnection http = (HttpURLConnection) otherurl.openConnection();
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
            facade.registerUser(new UserData("testUser", "testPassword", "testEmail"));
            AuthData auth = facade.loginUser(new LoginUser("testUser", "testPassword"));
            System.out.println(auth.authToken());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createGameTest() {
        try {
            AuthData auth = facade.registerUser(new UserData("testUser", "testPassword", "testEmail"));
            GameID id = facade.createGame(auth, new CreateGameObj("testGame"));
            System.out.println(new Gson().toJson(id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
