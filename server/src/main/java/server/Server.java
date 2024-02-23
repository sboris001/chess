package server;

import com.google.gson.Gson;
import dataAccess.MemoryAuthAccess;
import dataAccess.MemoryGameAccess;
import dataAccess.MemoryUserAccess;
import model.UserData;
import spark.*;

import java.util.ArrayList;

public class Server {
    ClearHandler clear = new ClearHandler();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", clear::handle);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
