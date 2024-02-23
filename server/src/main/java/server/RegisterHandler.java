package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import exceptions.AlreadyTaken;
import exceptions.BadRequest;
import model.AuthData;
import model.FailureMessage;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import service.RegisterService;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegisterHandler implements Route {
    RegisterService register = new RegisterService();
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson serializer = new Gson();
        UserData user = serializer.fromJson(request.body(), UserData.class);
        try {
            AuthData authorization = register.registerUser(user);
            return serializer.toJson(authorization);
        } catch (AlreadyTaken reRegistered) {
            response.status(403);
            FailureMessage fail = new FailureMessage("Error: already taken");
            return serializer.toJson(fail);
        } catch (BadRequest missing) {
            response.status(400);
            FailureMessage fail = new FailureMessage("Error: bad request");
            return serializer.toJson(fail);
        }
    }
}
