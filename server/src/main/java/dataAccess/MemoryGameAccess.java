package dataAccess;

import model.GameData;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoryGameAccess implements GameAccess {
    ArrayList<GameData> games = new ArrayList<>();
    @Override
    public void createGame(GameData game) throws DataAccessException {
        games.add(game);
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
