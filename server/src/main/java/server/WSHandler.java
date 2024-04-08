package server;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import spark.Spark;
import javax.websocket.OnOpen;

@WebSocket
public class WSHandler {

    @OnOpen
    public void onOpen() {
        System.out.println("WebSocket opened");
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        session.getRemote().sendString("WebSocket response: " + message);
    }
}
