package websocket;
import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import model.AuthData;
import ui.DrawBoard;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;
import javax.websocket.*;
import java.net.URI;


public class WSClient extends Endpoint {
    static WSClient ws;

    static {
        try {
            ws = new WSClient();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void joinGame(AuthData auth, Integer gameID, ChessGame.TeamColor color) throws Exception {
        JoinPlayer player = new JoinPlayer(auth.authToken(), gameID, color);
        var message = new Gson().toJson(player);
        ws.send(message);
    }
    public static void observeGame(AuthData auth, Integer gameID) throws Exception {
        JoinObserver observer = new JoinObserver(auth.authToken(), gameID);
        var message = new Gson().toJson(observer);
        ws.send(message);
    }

    public static void makeMove(AuthData auth, Integer gameID, ChessMove move) throws Exception {
        MakeMove makeMove = new MakeMove(auth.authToken(), gameID, move);
        var message = new Gson().toJson(makeMove);
        ws.send(message);
    }

    public static void resign(AuthData auth, Integer gameID) throws Exception {
        Resign resign = new Resign(auth.authToken(), gameID);
        var message = new Gson().toJson(resign);
        ws.send(message);
    }

    public static void leave(AuthData auth, Integer gameID) throws Exception {
        Leave leave = new Leave(auth.authToken(), gameID);
        var message = new Gson().toJson(leave);
        ws.send(message);
    }

    public Session session;

    public WSClient() throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                switch (serverMessage.getServerMessageType()) {
                    case NOTIFICATION -> {
                        Notification notification = new Gson().fromJson(message, Notification.class);
                        System.out.print(notification.getNotification());
                    }
                    case LOAD_GAME -> {
                        LoadGame loadGame = new Gson().fromJson(message, LoadGame.class);
                        System.out.println();
                        ChessGame.TeamColor color = loadGame.getColor();
                        if (color == ChessGame.TeamColor.WHITE || color == null) {
                            DrawBoard.drawBoard(loadGame.getGame().getBoard(), 2);
                            System.out.print("[IN_GAME] >>> ");
                        } else if (color == ChessGame.TeamColor.BLACK){
                            DrawBoard.drawBoard(loadGame.getGame().getBoard(), 1);
                            System.out.print("[IN_GAME] >>> ");
                        }
                    }
                    case ERROR -> {
                        Error error = new Gson().fromJson(message, Error.class);
                        System.out.print(error.getError());
                    }
                }

            }
        });
    }

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}