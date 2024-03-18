package ui;

import com.google.gson.Gson;
import model.UserData;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

public class ServerFacade {

    private final String serverUrl;
    public ServerFacade(String url) {
        serverUrl = url;
    }

    public String registerUser(UserData user) throws IOException {
        try {
            URL url = (new URI(serverUrl + "/user")).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(user);
            try (OutputStream reqBody = http.getOutputStream()){
                reqBody.write(reqData.getBytes());
            }
            http.connect();

        } catch (IOException | URISyntaxException e) {
            throw new IOException("Could not register");
        }
        return "";
    }


}
