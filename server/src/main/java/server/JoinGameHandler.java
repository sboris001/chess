package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import exceptions.AlreadyTaken;
import exceptions.BadRequest;
import exceptions.ResponseException;
import exceptions.Unauthorized;
import model.FailureMessage;
import model.JoinGame;
import service.JoinGameService;
import spark.Request;
import spark.Response;
import spark.Route;

public class JoinGameHandler implements Route {
    JoinGameService joinGame = new JoinGameService();

    public JoinGameHandler() throws ResponseException, DataAccessException {
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String authToken = request.headers("authorization");
        Gson serializer = new Gson();
        JoinGame player = serializer.fromJson(request.body(), JoinGame.class);
        try {
            joinGame.joinGame(authToken, player);
            return "";
        } catch (BadRequest badRequest) {
            response.status(400);
            FailureMessage fail = new FailureMessage("Error: bad request");
            return serializer.toJson(fail);
        } catch (Unauthorized unauthorized) {
            response.status(401);
            FailureMessage fail = new FailureMessage("Error: unauthorized");
            return serializer.toJson(fail);
        } catch (AlreadyTaken alreadyTaken) {
            response.status(403);
            FailureMessage fail = new FailureMessage("Error: already taken");
            return serializer.toJson(fail);
        }
    }
}
