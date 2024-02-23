package dataAccess;

import model.AuthData;

import java.util.HashMap;

public class MemoryAuthAccess implements AuthAccess{
    public static HashMap<String, AuthData> auths = new HashMap<>();
    @Override
    public void createAuth(AuthData auth) throws DataAccessException {
        String token = auth.authToken();
        auths.put(token, auth);
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return auths.get(authToken);
    }

    @Override
    public void clearAuths() throws DataAccessException {
        auths.clear();
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        auths.remove(authToken);
    }
}
