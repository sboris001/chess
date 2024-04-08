import server.Server;
import ui.Prelogin;
import websocket.WSServer;

import static ui.EscapeSequences.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        var port = server.run(0);
        System.out.print(RESET_BG_COLOR);
        System.out.println("♕ Welcome to the 240 Chess Client: Type help to get started. ♕");
        Prelogin.userInterface(port);
        server.stop();
    }
}