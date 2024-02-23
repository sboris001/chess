package dataAccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthAccess implements AuthAccess{
    HashMap<String, String> auths = new HashMap<>();
    @Override
    public void createAuth(AuthData auth) throws DataAccessException {
        String username = auth.username();
        String token = auth.authToken();
        auths.put(username, token);
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth() throws DataAccessException {

    }
}
