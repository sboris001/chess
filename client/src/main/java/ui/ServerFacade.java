package ui;

import com.google.gson.Gson;
import model.AuthData;
import model.LoginUser;
import model.UserData;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

public class ServerFacade {

    private final String serverUrl;
    public ServerFacade(String url) {
        serverUrl = url;
    }

    public AuthData registerUser(UserData user) throws IOException {
        AuthData response;
        try {
            URL url = (new URI(serverUrl + "/user")).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(user);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
            http.connect();
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                response = new Gson().fromJson(reader, AuthData.class);
            }

        } catch (IOException | URISyntaxException e) {
            throw new IOException("Could not register");
        }
        return response;
    }

    public AuthData loginUser(LoginUser user) throws IOException {
        AuthData response;
        try {
            URL url = (new URI(serverUrl + "/session")).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            String reqData = new Gson().toJson(user);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
            http.connect();
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                response = new Gson().fromJson(reader, AuthData.class);
            }

        } catch (IOException | URISyntaxException e) {
            throw new IOException("Could not login");
        }
        return response;
    }


}
