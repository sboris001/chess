package dataAccess;

import exceptions.ResponseException;
import model.GameData;

import java.util.ArrayList;

public interface GameAccess {
    public void createGame(GameData game) throws DataAccessException, ResponseException;
    public GameData getGame(int gameID) throws DataAccessException, ResponseException;
    public ArrayList<GameData> listGames() throws DataAccessException, ResponseException;
    public void updateGame(int gameID, GameData game) throws DataAccessException, ResponseException;
    public void clearGames() throws DataAccessException, ResponseException;
}
