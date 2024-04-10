package dataAccess;

import com.google.gson.Gson;
import exceptions.ResponseException;
import model.GameData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLGameAccess implements GameAccess{

    public SQLGameAccess() throws ResponseException, DataAccessException {
        String[] createStatements = {
                """
            CREATE TABLE IF NOT EXISTS  games (
              gameID int NOT NULL,
              whiteUsername varchar(256),
              blackUsername varchar(256),
              gameName varchar(256),
              json TEXT DEFAULT NULL,
              PRIMARY KEY (gameID),
              INDEX auth_index (gameID)
            )
            """
        };
        DataAccessUtility.configureDatabase(createStatements);
    }


    private GameData readGame(ResultSet rs) throws SQLException {
        var json = rs.getString("json");
        return new Gson().fromJson(json, GameData.class);
    }

    @Override
    public void createGame(GameData game) throws DataAccessException, ResponseException {
        var statement = "INSERT INTO games (gameID, whiteUsername, blackUsername, gameName, json) VALUES (?, ?, ?, ?, ?)";
        var json = new Gson().toJson(game);
        DataAccessUtility.executeUpdate(statement, game.gameID(), game.whiteUsername(), game.blackUsername(), game.gameName(), json);
    }

    @Override
    public GameData getGame(int gameID) throws ResponseException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT json FROM games WHERE gameID=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    @Override
    public ArrayList<GameData> listGames() throws DataAccessException, ResponseException {
        ArrayList<GameData> gameList = new ArrayList<>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT json FROM games";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        gameList.add(readGame(rs));
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
        }
        return gameList;
    }

    @Override
    public void updateGame(int gameID, GameData game) throws  ResponseException {
        var statement = "UPDATE games SET gameID=?, whiteUsername=?, blackUsername=?, gameName=?, json=? WHERE gameID=?";
        String whiteUsername = game.whiteUsername();
        String blackUsername = game.blackUsername();
        String gameName = game.gameName();
        Gson serializer = new Gson();
        DataAccessUtility.executeUpdate(statement, gameID, whiteUsername, blackUsername, gameName, serializer.toJson(game), gameID);
    }

    @Override
    public void clearGames() throws ResponseException {
        var statement = "TRUNCATE games";
        DataAccessUtility.executeUpdate(statement);
    }

}
