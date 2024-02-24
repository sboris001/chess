package server;

import com.google.gson.Gson;
import exceptions.Unauthorized;
import model.FailureMessage;
import model.ListGames;
import service.ListGamesService;
import spark.Request;
import spark.Response;
import spark.Route;

public class ListGamesHandler implements Route {
    ListGamesService listGames = new ListGamesService();
    @Override
    public Object handle(Request request, Response response) throws Exception {
        String authToken = request.headers("authorization");
        Gson serializer = new Gson();
        try {
            ListGames games = listGames.listGames(authToken);
            return serializer.toJson(games);
        } catch (Unauthorized unauthorized) {
            response.status(401);
            FailureMessage fail = new FailureMessage("Error: unauthorized");
            return serializer.toJson(fail);
        }
    }
}
