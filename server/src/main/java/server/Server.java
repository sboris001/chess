package server;

import spark.*;


public class Server {
    ClearHandler clear = new ClearHandler();
    RegisterHandler register = new RegisterHandler();
    LoginHandler login = new LoginHandler();
    LogoutHandler logout = new LogoutHandler();
    CreateGameHandler createGame = new CreateGameHandler();
    JoinGameHandler joinGame = new JoinGameHandler();
    ListGamesHandler listGames = new ListGamesHandler();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", clear);
        Spark.post("/user", register);
        Spark.post("/session", login);
        Spark.delete("/session", logout);
        Spark.post("/game", createGame);
        Spark.put("/game", joinGame);
        Spark.get("/game", listGames);


        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
