package websocket;
import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import ui.DrawBoard;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JoinPlayer;

import javax.websocket.*;
import java.net.URI;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class WSClient extends Endpoint {
    DrawBoard drawBoard = new DrawBoard();

    public static void joinGame(AuthData auth, Integer gameID, ChessGame.TeamColor color) throws Exception {
        var ws = new WSClient();
        JoinPlayer player = new JoinPlayer(auth.authToken(), gameID, color);
        var message = new Gson().toJson(player);
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
                        System.out.println(notification.getNotification());
                    }
                    case LOAD_GAME -> {
                        LoadGame loadGame = new Gson().fromJson(message, LoadGame.class);
                        System.out.println();
                        DrawBoard.drawBoard(loadGame.getGame().getBoard(), 1);
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