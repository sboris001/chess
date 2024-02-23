package server;

import spark.*;


public class Server {
    ClearHandler clear = new ClearHandler();
    RegisterHandler register = new RegisterHandler();
    LoginHandler login = new LoginHandler();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", clear::handle);
        Spark.post("/user", register::handle);
        Spark.post("/session", login::handle);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
