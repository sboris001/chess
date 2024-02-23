package dataAccess;

import model.GameData;

import java.util.ArrayList;

public interface GameAccess {
    public GameData createGame(GameData game) throws DataAccessException;
    public GameData getGame(int gameID) throws DataAccessException;
    public ArrayList<GameData> listGames() throws DataAccessException;
    public void updateGame(int gameID, GameData game) throws DataAccessException;
    public void clearGames() throws DataAccessException;
}
