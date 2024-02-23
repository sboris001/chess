package dataAccess;

import model.GameData;

import java.util.ArrayList;

public class MemoryGameAccess implements GameAccess {

    @Override
    public GameData createGame(GameData game) throws DataAccessException {
        return null;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public ArrayList<GameData> listGames() throws DataAccessException {
        return null;
    }

    @Override
    public void updateGame(int gameID, GameData game) throws DataAccessException {

    }

    @Override
    public void clearGames() throws DataAccessException {

    }
}
