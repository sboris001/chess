package server;
import chess.*;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.SQLAuthAccess;
import dataAccess.SQLGameAccess;
import exceptions.ResponseException;
import model.AuthData;
import model.GameData;
import model.GameID;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.userCommands.JoinObserver;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.MakeMove;
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
            case MAKE_MOVE -> makeMove(session, message);
//            case LEAVE -> leave();
//            case RESIGN -> resign();
        }
    }
    Map<Integer, ArrayList<Session>> sessions = new HashMap<>();
    public void joinPlayer(Session session, String message) throws ResponseException, IOException, DataAccessException {
        int errorCount = 0;
        JoinPlayer player = new Gson().fromJson(message, JoinPlayer.class);
        if (isNull(authDAO.getAuth(player.getAuthString()))) {
            Error error = new Error("\nError - Unauthorized\n[LOGGED_IN] >>> ");
            session.getRemote().sendString(new Gson().toJson(error, Error.class));
        } else {
            String username = authDAO.getAuth(player.getAuthString()).username();
            int gameID = player.getGameID();
            ChessGame.TeamColor color = player.getColor();
            GameData checkGame = gameDAO.getGame(gameID);
            if (!isNull(checkGame)) {
                String white = checkGame.whiteUsername();
                String black = checkGame.blackUsername();
                if (color.equals(ChessGame.TeamColor.BLACK)) {
                    if (!Objects.equals(username, black)) {
                        errorCount += 1;
                    }
                } else if (!Objects.equals(username, white)) {
                    errorCount += 1;
                }
            } else {
                errorCount += 100;
            }
            if (errorCount > 50) {
                Error error = new Error("\nError - Game ID does not exist.  Please check your ID and try again\n[LOGGED_IN] >>> ");
                session.getRemote().sendString(new Gson().toJson(error, Error.class));
            } else if (errorCount > 0) {
                Error error = new Error("\nError - User does not match.  Please check your team and try again\n[LOGGED_IN] >>> ");
                session.getRemote().sendString(new Gson().toJson(error, Error.class));
            } else {
                joinHelper(gameID, session, username, color, player);
            }
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
        if (isNull(authDAO.getAuth(observer.getAuthString()))) {
            Error error = new Error("\nError - Unauthorized\n[LOGGED_IN] >>> ");
            session.getRemote().sendString(new Gson().toJson(error, Error.class));
        } else {
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

    public void makeMove(Session session, String message) throws IOException, ResponseException, DataAccessException, InvalidMoveException {

        MakeMove moveJson = new Gson().fromJson(message, MakeMove.class);
        String authString = moveJson.getAuthString();
        Integer gameID = moveJson.getGameID();
        ChessMove move = moveJson.getMove();
        ChessPosition startPos = move.getStartPosition();
        String username = authDAO.getAuth(authString).username();
        GameData gameData = gameDAO.getGame(gameID);
        ChessGame game = gameData.game();
        ChessGame.TeamColor color;
        Collection<ChessMove> validMoves = game.validMoves(startPos);
        if (!validMoves.contains(move)) {
            Error error = new Error("\nError - Invalid move.  Please check your inputs and try again\n[LOGGED_IN] >>> ");
            session.getRemote().sendString(new Gson().toJson(error, Error.class));
        } else {
            if (Objects.equals(gameData.whiteUsername(), username)){
                color = ChessGame.TeamColor.WHITE;
            } else {
                color = ChessGame.TeamColor.BLACK;
            }
            game.makeMove(move);
            GameData newGameData = new GameData(gameID, gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), game);
            gameDAO.updateGame(gameID, newGameData);
            int count = 0;
            ArrayList<Session> tempList = sessions.get(gameID);
            for (Session sesh : tempList) {
                if (sesh != session) {
                    Notification notification = new Notification("\n\033[0mNotification:  " + username + " has moved a piece.\n[IN_GAME] >>> ");
                    sesh.getRemote().sendString(new Gson().toJson(notification, Notification.class));
                    LoadGame loadGame = new LoadGame(game, color);
                    sesh.getRemote().sendString(new Gson().toJson(loadGame, LoadGame.class));
                    if (count < 1) {
                        LoadGame loadGame2 = new LoadGame(game, color);
                        session.getRemote().sendString(new Gson().toJson(loadGame2, LoadGame.class));
                        count += 1;
                    }
                }
            }
        }


    }
}


