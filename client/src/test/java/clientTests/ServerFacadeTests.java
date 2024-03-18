package clientTests;

import model.AuthData;
import model.LoginUser;
import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;

import java.io.IOException;
import java.net.*;


public class ServerFacadeTests {

    private static Server server;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
        System.out.println("Server Stopped");
    }

    @BeforeEach
    public void clearDB() {
        try{
            URL url = (new URI("http://localhost:8080" + "/db")).toURL();
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
        ServerFacade facade = new ServerFacade("http://localhost:" + "8080");
        try {
            facade.registerUser(new UserData("fred", "pw", "maile"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void loginTest() {
        ServerFacade facade = new ServerFacade("http://localhost:" + "8080");
        try {
            facade.registerUser(new UserData("fred", "pw", "maile"));
            AuthData auth = facade.loginUser(new LoginUser("fred", "pw"));
            System.out.println(auth.authToken());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
