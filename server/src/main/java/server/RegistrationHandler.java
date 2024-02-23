package server;

import com.google.gson.Gson;
import dataAccess.MemoryUserAccess;
import model.UserData;
import spark.Request;
import spark.Response;

public class RegistrationHandler {
    private Object register(Request req, Response res) {
        UserData user = new Gson().fromJson(req.body(),UserData.class);
        return null;
    }
}
