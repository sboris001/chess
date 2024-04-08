package server;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.SQLAuthAccess;
import dataAccess.SQLGameAccess;
import dataAccess.SQLUserAccess;
import exceptions.ResponseException;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import spark.Spark;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.OnOpen;
import java.io.IOException;
import java.util.*;

@WebSocket
public class WSHandler {
    SQLAuthAccess authDAO = new SQLAuthAccess();
    SQLGameAccess gameDAO = new SQLGameAccess();
    SQLUserAccess userDAO = new SQLUserAccess();

    public WSHandler() throws ResponseException, DataAccessException {
    }

    @OnOpen
    public void onOpen() {
        System.out.println("WebSocket opened");
    }



    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
//        String[] params = message.split(" ");
//        System.out.println(Arrays.toString(params));
//        String command = params[0];
//        if (Objects.equals(command, "JOIN_GAME")) {
//            System.out.println("This actually worked");
//            String username = params[1];
//            int gameID = Integer.parseInt(params[2]);
//            String color = params[3];
//            if (sessions.containsKey(gameID)) {
//                ArrayList<Session> tempList = sessions.get(gameID);
//                tempList.add(session);
//                sessions.put(gameID, tempList);
//                for (Session sesh : tempList) {
//                    sesh.getRemote().sendString("\033[0m" + username + " has joined game " + Integer.toString(gameID) + " as " + color);
//                }
//            } else {
//                ArrayList<Session> tempList = new ArrayList<>();
//                tempList.add(session);
//                sessions.put(gameID, tempList);
//                session.getRemote().sendString("\033[0m" + username + " has joined game " + Integer.toString(gameID) + " as " + color);
//            }
//        }
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        switch(command.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(session, message);
//            case MAKE_MOVE -> makeMove();
//            case JOIN_OBSERVER -> joinObserver();
//            case LEAVE -> leave();
//            case RESIGN -> resign();
        }
    }
    Map<Integer, ArrayList<Session>> sessions = new HashMap<>();
    public void joinPlayer(Session session, String message) throws ResponseException, IOException {
        JoinPlayer player = new Gson().fromJson(message, JoinPlayer.class);
        String username = authDAO.getAuth(player.getAuthString()).username();
        int gameID = player.getGameID();
        String color = player.getColor().toString();
        if (sessions.containsKey(gameID)) {
            ArrayList<Session> tempList = sessions.get(gameID);
            tempList.add(session);
            sessions.put(gameID, tempList);
            for (Session sesh : tempList) {
                Notification notification = new Notification("\033[0m" + username + " has joined game " + Integer.toString(gameID) + " as " + color);
                sesh.getRemote().sendString(new Gson().toJson(notification, Notification.class));
            }
        } else {
            ArrayList<Session> tempList = new ArrayList<>();
            tempList.add(session);
            sessions.put(gameID, tempList);
            Notification notification = new Notification("\033[0m" + username + " has joined game " + Integer.toString(gameID) + " as " + color);
            session.getRemote().sendString(new Gson().toJson(notification, Notification.class));
        }
    }
}
