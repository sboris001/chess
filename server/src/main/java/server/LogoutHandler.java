package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import exceptions.ResponseException;
import exceptions.Unauthorized;
import model.FailureMessage;
import service.LogoutService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route {
    LogoutService logout;

    public LogoutHandler() throws ResponseException, DataAccessException {
        try {
             logout = new LogoutService();
        } catch (DataAccessException | ResponseException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String authToken = request.headers("authorization");
        Gson serializer = new Gson();
        try {
            logout.logout(authToken);
            return "";
        } catch (Unauthorized unauthorized){
            response.status(401);
            FailureMessage fail = new FailureMessage("Error: unauthorized");
            return serializer.toJson(fail);
        }
    }
}
