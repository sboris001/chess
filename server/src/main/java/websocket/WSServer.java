package websocket;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import spark.Spark;
import javax.websocket.OnOpen;

@WebSocket
public class WSServer {
    public static void start() {
        Spark.port(8080);
        Spark.webSocket("/connect", WSServer.class);
        Spark.get("/echo/:msg", (req, res) -> "HTTP response: " + req.params(":msg"));
    }

    @OnOpen
    public void onOpen() {
        System.out.println("WebSocket opened");
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        session.getRemote().sendString("WebSocket response: " + message);
    }
}
