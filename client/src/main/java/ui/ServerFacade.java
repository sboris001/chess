package ui;

import com.google.gson.Gson;
import model.*;

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

    public GameID createGame(AuthData authorization, CreateGameObj game) throws IOException {
        GameID response;
        try {
            URL url = (new URI(serverUrl + "/game")).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("Authorization", authorization.authToken());
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            String reqData = new Gson().toJson(game);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
            http.connect();
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                response = new Gson().fromJson(reader, GameID.class);
            }

        } catch (IOException | URISyntaxException e) {
            throw new IOException("Could not create game");
        }
        return response;
    }

    public void joinGame(AuthData authorization, JoinGame player) throws IOException {
        GameID response;
        try {
            URL url = (new URI(serverUrl + "/game")).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("Authorization", authorization.authToken());
            http.setRequestMethod("PUT");
            http.setDoOutput(true);
            String reqData = new Gson().toJson(player);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
            http.connect();
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                response = new Gson().fromJson(reader, GameID.class);
            }

        } catch (IOException | URISyntaxException e) {
            throw new IOException("Could not join game");
        }
    }

    public ListGames listGames(AuthData authorization) throws IOException {
        ListGames response;
        try {
            URL url = (new URI(serverUrl + "/game")).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("Authorization", authorization.authToken());
            http.setRequestMethod("GET");
            http.setDoOutput(true);
            http.connect();
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                response = new Gson().fromJson(reader, ListGames.class);
            }

        } catch (IOException | URISyntaxException e) {
            throw new IOException("Could not join game");
        }
        return response;
    }

    public void logoutUser(AuthData authorization) throws IOException {
        try {
            URL url = (new URI(serverUrl + "/session")).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("Authorization", authorization.authToken());
            http.setRequestMethod("DELETE");
            http.setDoOutput(true);
            http.connect();
            if (http.getResponseCode() == 200) {
                System.out.println("Successfully logged out!");
            }
        } catch (IOException | URISyntaxException e) {
            throw new IOException("Could not logout");
        }

    }




}
