package model;

import java.util.ArrayList;

public record ListGames(ArrayList<GameData> games) {

    private ArrayList<String> tokenize(ArrayList<GameData> games) {
        ArrayList<String> order = new ArrayList<>();
        for (GameData game : games) {
            order.add("Game ID: ");
            order.add(String.valueOf(game.gameID()) + " | ");
            order.add("Game Name: ");
            order.add(game.gameName() + " | ");
            order.add("White Username: ");
            order.add(game.whiteUsername() + " | ");
            order.add("Black Username: ");
            order.add(game.blackUsername() + "\n");
        }
        return order;
    }

    @Override
    public String toString() {
        ArrayList<String> order = tokenize(games);
        String finalString = "";
        for (String string : order)  {
            finalString = finalString + string;
        }
        return finalString;
    }
}
