package dataAccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthAccess implements AuthAccess{
    HashMap<String, AuthData> auths = new HashMap<>();
    @Override
    public void createAuth(AuthData auth) throws DataAccessException {
        String username = auth.username();
        auths.put(username, auth);
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth() throws DataAccessException {

    }
}
