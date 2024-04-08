package websocket;
import chess.ChessGame;
import model.AuthData;
import webSocketMessages.userCommands.JoinPlayer;

import javax.websocket.*;
import java.net.URI;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class WSClient extends Endpoint {

    public static void main(String[] args) throws Exception {
        var ws = new WSClient();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a message you want to echo");
        while (true) {
            try {
                ws.send(scanner.nextLine());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static void joinGame(AuthData auth, Integer gameID, ChessGame.TeamColor color) throws Exception {
        var ws = new WSClient();
        JoinPlayer player = new JoinPlayer(auth.authToken(), gameID, color);
        String username = auth.username();
        String message = "JOIN_GAME " + username + " " + gameID + " " + color;
        ws.send(message);
    }

    public Session session;

    public WSClient() throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                System.out.println(message);
            }
        });
    }

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}