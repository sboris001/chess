package server;
import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.SQLAuthAccess;
import dataAccess.SQLGameAccess;
import exceptions.ResponseException;
import model.GameData;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.userCommands.JoinObserver;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.UserGameCommand;
import java.io.IOException;
import java.util.*;

import static java.util.Objects.isNull;

@WebSocket
public class WSHandler {
    SQLAuthAccess authDAO = new SQLAuthAccess();
    SQLGameAccess gameDAO = new SQLGameAccess();

    public WSHandler() throws ResponseException, DataAccessException {
    }


    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        switch(command.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(session, message);
            case JOIN_OBSERVER -> joinObserver(session, message);
//            case MAKE_MOVE -> makeMove();
//            case LEAVE -> leave();
//            case RESIGN -> resign();
        }
    }
    Map<Integer, ArrayList<Session>> sessions = new HashMap<>();
    public void joinPlayer(Session session, String message) throws ResponseException, IOException, DataAccessException {
        JoinPlayer player = new Gson().fromJson(message, JoinPlayer.class);
        String username = authDAO.getAuth(player.getAuthString()).username();
        int gameID = player.getGameID();
        ChessGame.TeamColor color = player.getColor();
        GameData checkGame = gameDAO.getGame(gameID);
        String white = checkGame.whiteUsername();
        String black = checkGame.blackUsername();
        if (color.equals(ChessGame.TeamColor.BLACK)) {
            if (!Objects.equals(username, black)) {
                Error error = new Error("\nError - Game ID does not exist.  Please check your ID and try again\n[LOGGED_IN] >>> ");
                session.getRemote().sendString(new Gson().toJson(error, Error.class));
            } else {
                joinHelper(gameID, session, username, color, player);
            }
        } else if (!Objects.equals(username, white)) {
            Error error = new Error("\nError - Game ID does not exist.  Please check your ID and try again\n[LOGGED_IN] >>> ");
            session.getRemote().sendString(new Gson().toJson(error, Error.class));
        } else {
            joinHelper(gameID, session, username, color, player);
        }



    }

    private void joinHelper(int gameID, Session session, String username, ChessGame.TeamColor color, JoinPlayer player) throws IOException, ResponseException, DataAccessException {
        if (sessions.containsKey(gameID)) {
            ArrayList<Session> tempList = sessions.get(gameID);
            tempList.add(session);
            sessions.put(gameID, tempList);
            for (Session sesh : tempList) {
                if (sesh != session) {
                    Notification notification = new Notification("\n\033[0mNotification:  " + username + " has joined game " + gameID + " as " + color.toString() + "\n[IN_GAME] >>> ");
                    sesh.getRemote().sendString(new Gson().toJson(notification, Notification.class));
                    GameData game = gameDAO.getGame(gameID);
                    LoadGame loadGame = new LoadGame(game.game(), player.getColor());
                    session.getRemote().sendString(new Gson().toJson(loadGame, LoadGame.class));
                }
            }
        } else {
            ArrayList<Session> tempList = new ArrayList<>();
            tempList.add(session);
            sessions.put(gameID, tempList);
            GameData game = gameDAO.getGame(gameID);
            LoadGame loadGame = new LoadGame(game.game(), player.getColor());
            session.getRemote().sendString(new Gson().toJson(loadGame, LoadGame.class));
        }
    }

    public void joinObserver(Session session, String message) throws ResponseException, IOException, DataAccessException {
        JoinObserver observer = new Gson().fromJson(message, JoinObserver.class);
        String username = authDAO.getAuth(observer.getAuthString()).username();
        Integer gameID = observer.getGameID();
        int count = 0;
        if (isNull(gameDAO.getGame(gameID))) {
            Error error = new Error("\nError - Game ID does not exist.  Please check your ID and try again\n[LOGGED_IN] >>> ");
            session.getRemote().sendString(new Gson().toJson(error, Error.class));
        } else {
            if (sessions.containsKey(gameID)) {
                ArrayList<Session> tempList = sessions.get(gameID);
                tempList.add(session);
                sessions.put(gameID, tempList);
                for (Session sesh : tempList) {
                    if (sesh != session) {
                        Notification notification = new Notification("\n\033[0mNotification:  " + username + " has joined game " + gameID + " as an observer\n[IN_GAME] >>> ");
                        sesh.getRemote().sendString(new Gson().toJson(notification, Notification.class));
                        if (count < 1) {
                            GameData game = gameDAO.getGame(gameID);
                            LoadGame loadGame = new LoadGame(game.game(), null);
                            session.getRemote().sendString(new Gson().toJson(loadGame, LoadGame.class));
                            count += 1;
                        }
                    }
                }
            } else {
                ArrayList<Session> tempList = new ArrayList<>();
                tempList.add(session);
                sessions.put(gameID, tempList);
                GameData game = gameDAO.getGame(gameID);
                LoadGame loadGame = new LoadGame(game.game(), null);
                session.getRemote().sendString(new Gson().toJson(loadGame, LoadGame.class));
            }
        }

    }
}
