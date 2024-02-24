package server;

import com.google.gson.Gson;
import exceptions.Unauthorized;
import model.LogoutUser;
import service.LogoutService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.Reader;

public class LogoutHandler implements Route {
    LogoutService logout = new LogoutService();
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson serializer = new Gson();
        LogoutUser authToken = serializer.fromJson(request.headers("authorization"), LogoutUser.class);
        try {
            logout.logout(authToken);
            return "";
        } catch (Unauthorized unauthorized){
            response.status(401);
            return "";
        }
    }
}
