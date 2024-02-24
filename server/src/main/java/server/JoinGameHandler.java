package server;

import com.google.gson.Gson;
import model.AuthData;
import spark.Request;
import spark.Response;
import spark.Route;

public class JoinGameHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        String authToken = request.headers("authorization");
        Gson serializer = new Gson();

        return null;
    }
}
