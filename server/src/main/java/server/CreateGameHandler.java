package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import exceptions.BadRequest;
import exceptions.ResponseException;
import exceptions.Unauthorized;
import model.CreateGameObj;
import model.FailureMessage;
import model.GameID;
import service.CreateGameService;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateGameHandler implements Route {
    CreateGameService createGame = new CreateGameService();

    public CreateGameHandler() throws ResponseException, DataAccessException {
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson serializer = new Gson();
        String authToken = request.headers("authorization");
        CreateGameObj gameName = serializer.fromJson(request.body(), CreateGameObj.class);
        try {
            GameID gameID = createGame.makeGame(authToken, gameName);
            return serializer.toJson(gameID);
        } catch (BadRequest badRequest){
            response.status(400);
            FailureMessage fail = new FailureMessage("Error: bad request");
            return serializer.toJson(fail);
        } catch (Unauthorized unauthorized) {
            response.status(401);
            FailureMessage fail = new FailureMessage("Error: unauthorized");
            return serializer.toJson(fail);
        }
    }
}
