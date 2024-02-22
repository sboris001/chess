package server;

import spark.*;

import java.util.ArrayList;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        // Spark.post("/user", this::registrationHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }



    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
