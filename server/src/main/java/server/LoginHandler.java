package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import exceptions.ResponseException;
import exceptions.Unauthorized;
import model.AuthData;
import model.FailureMessage;
import model.LoginUser;
import model.UserData;
import service.LoginService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginHandler implements Route {
    LoginService login = new LoginService();

    public LoginHandler() throws ResponseException, DataAccessException {
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson serializer = new Gson();
        LoginUser user = serializer.fromJson(request.body(), LoginUser.class);
        try {
            AuthData authorization = login.login(user);
            return serializer.toJson(authorization);
        } catch (Unauthorized unauthorized){
            response.status(401);
            FailureMessage fail = new FailureMessage("Error: unauthorized");
            return serializer.toJson(fail);
        }
    }
}
