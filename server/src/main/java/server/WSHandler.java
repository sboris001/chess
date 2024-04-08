package server;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import spark.Spark;
import javax.websocket.OnOpen;
import java.util.*;

@WebSocket
public class WSHandler {

    @OnOpen
    public void onOpen() {
        System.out.println("WebSocket opened");
    }


    Map<Integer, ArrayList<Session>> sessions = new HashMap<>();
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        String[] params = message.split(" ");
        System.out.println(Arrays.toString(params));
        String command = params[0];
        if (Objects.equals(command, "JOIN_GAME")) {
            System.out.println("This actually worked");
            String username = params[1];
            int gameID = Integer.parseInt(params[2]);
            String color = params[3];
            if (sessions.containsKey(gameID)) {
                ArrayList<Session> tempList = sessions.get(gameID);
                tempList.add(session);
                sessions.put(gameID, tempList);
                for (Session sesh : tempList) {
                    sesh.getRemote().sendString("\033[0m" + username + " has joined game " + Integer.toString(gameID) + " as " + color);
                }
            } else {
                ArrayList<Session> tempList = new ArrayList<>();
                tempList.add(session);
                sessions.put(gameID, tempList);
                session.getRemote().sendString("\033[0m" + username + " has joined game " + Integer.toString(gameID) + " as " + color);
            }
        }
    }
}
