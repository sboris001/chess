package dataAccess;

import model.GameData;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoryGameAccess implements GameAccess {
    HashMap<Integer, GameData> games = new HashMap<>();
    @Override
    public void createGame(GameData game) throws DataAccessException {
        int gameID = game.gameID();
        games.put(gameID, game);
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return games.get(gameID);
    }

    @Override
    public ArrayList<GameData> listGames() throws DataAccessException {
        return new ArrayList<GameData>(games.values());
    }

    @Override
    public void updateGame(int gameID, GameData game) throws DataAccessException {
        if (games.containsKey(gameID)) {
            games.put(gameID, game);
        } else {
            throw new DataAccessException("Game does not exist");
        }
    }

    @Override
    public void clearGames() throws DataAccessException {
        games.clear();
    }
}
