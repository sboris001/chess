package server;

import dataAccess.DataAccessException;
import exceptions.ResponseException;
import spark.*;


public class Server {
    ClearHandler clear;
    RegisterHandler register;
    LoginHandler login;
    LogoutHandler logout;
    CreateGameHandler createGame;
    JoinGameHandler joinGame;
    ListGamesHandler listGames;
    {
        try {
            logout = new LogoutHandler();
            login = new LoginHandler();
            register = new RegisterHandler();
            clear = new ClearHandler();
            createGame = new CreateGameHandler();
            joinGame = new JoinGameHandler();
            listGames = new ListGamesHandler();
        } catch (ResponseException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }



    public Server() {
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);
        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        Spark.webSocket("/connect", WSHandler.class);
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
