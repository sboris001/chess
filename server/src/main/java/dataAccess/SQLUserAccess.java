package dataAccess;

import com.google.gson.Gson;
import exceptions.ResponseException;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLUserAccess implements UserAccess{

    public SQLUserAccess() throws ResponseException, DataAccessException {
        configureDatabase();
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  users (
              username varchar(256) NOT NULL,
              password varchar(256) NOT NULL,
              email varchar(256) NOT NULL,
              json TEXT DEFAULT NULL,
              PRIMARY KEY (username),
              INDEX username_index (username)
            )
            """
    };

    private void configureDatabase() throws ResponseException, DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new ResponseException(500, String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }


    public String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
    public boolean verifyUser(String username, String providedClearTextPassword) throws ResponseException, DataAccessException {
        // read the previously hashed password from the database
        var hashedPassword = getUser(username).password();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        return hashedPassword.equals(providedClearTextPassword);
        return encoder.matches(providedClearTextPassword, hashedPassword);
    }
    @Override
    public void clearUsers() throws ResponseException {
        var statement = "TRUNCATE users";
        DataAccessUtility.executeUpdate(statement);
    }

    @Override
    public void addUser(UserData user) throws ResponseException {
        var statement = "INSERT INTO users (username, password, email, json) VALUES (?, ?, ?, ?)";
        UserData encryptedUser = new UserData(user.username(), encryptPassword(user.password()), user.email());
        var json = new Gson().toJson(encryptedUser);
        String encryptedPassword = encryptPassword(user.password());
        DataAccessUtility.executeUpdate(statement, user.username(), encryptedPassword, user.email(), json);
    }

    @Override
    public UserData getUser(String username) throws ResponseException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT json FROM users WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readUser(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new ResponseException(500, String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;

    }
    private UserData readUser(ResultSet rs) throws SQLException {
        var json = rs.getString("json");
        return new Gson().fromJson(json, UserData.class);
    }
}
