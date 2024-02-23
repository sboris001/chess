package server;

import com.google.gson.Gson;
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
        register.registerUser(user);
        return null;
    }
}
