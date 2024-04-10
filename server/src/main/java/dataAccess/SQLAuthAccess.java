package dataAccess;

import com.google.gson.Gson;
import exceptions.ResponseException;
import model.AuthData;
import model.UserData;

import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLAuthAccess implements AuthAccess{

    public SQLAuthAccess() throws ResponseException, DataAccessException {
        String[] createStatements = {
                """
            CREATE TABLE IF NOT EXISTS  auths (
              authToken varchar(256) NOT NULL,
              username varchar(256) NOT NULL,
              json TEXT DEFAULT NULL,
              PRIMARY KEY (authToken),
              INDEX auth_index (authToken)
            )
            """
        };
        DataAccessUtility.configureDatabase(createStatements);
    }

    private AuthData readAuth(ResultSet rs) throws SQLException {
        var json = rs.getString("json");
        return new Gson().fromJson(json, AuthData.class);
    }

    @Override
    public void createAuth(AuthData auth) throws DataAccessException, ResponseException {
        var statement = "INSERT INTO auths (authToken, username, json) VALUES (?, ?, ?)";
        var json = new Gson().toJson(auth);
        DataAccessUtility.executeUpdate(statement, auth.authToken(), auth.username(), json);
    }

    @Override
    public AuthData getAuth(String authToken) throws ResponseException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT json FROM auths WHERE authToken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    @Override
    public void clearAuths() throws ResponseException {
        var statement = "TRUNCATE auths";
        DataAccessUtility.executeUpdate(statement);
    }

    @Override
    public void deleteAuth(String authToken) throws ResponseException {
        var statement = "DELETE FROM auths WHERE authToken=?";
        DataAccessUtility.executeUpdate(statement, authToken);
    }
}
