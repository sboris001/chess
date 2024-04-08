import dataAccess.DataAccessException;
import exceptions.ResponseException;
import server.Server;

public class StartServer {
    public static void main(String[] args) throws ResponseException, DataAccessException {
        System.out.println("♕ 240 Chess Server Started Successfully ");
        Server server = new Server();
        server.run(8080);
    }
}