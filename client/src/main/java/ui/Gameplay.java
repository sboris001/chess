package ui;

import chess.ChessMove;
import model.*;
import websocket.WSClient;

import java.util.Scanner;

import static ui.EscapeSequences.RESET_TEXT_COLOR;
import static ui.EscapeSequences.SET_TEXT_COLOR_BLUE;

public class Gameplay {
    public static void userInterface(int port, AuthData auth, int gameID) throws Exception {
        ServerFacade facade = new ServerFacade("http://localhost:" + port);
        String string;
        System.out.print("[IN_GAME] >>> ");
        Scanner in = new Scanner(System.in);
        string = in.nextLine();
        String[] strings = new String[0];
        if (string.isEmpty()) {
            System.out.println("Please enter a command");
            userInterface(port, auth, gameID);
        } else {
            strings = string.split(" ");
        }
        String s = strings[0];
        if (strings.length == 1) {
            switch (s) {
                case "Help", "help", "-h" -> {
                    help();
                    userInterface(port, auth, gameID);
                }
                case "Redraw", "redraw", "-rd" -> {}
                case "Move", "move", "-m" -> {
                    System.out.print(SET_TEXT_COLOR_BLUE + "\t" + "move <START POS> <END POS> <PROMO PIECE (IF APPLICABLE)>" + RESET_TEXT_COLOR + " - to make a move\n");
                    userInterface(port, auth, gameID);
                }
                case "Highlight", "highlight", "-hi" -> {
                    System.out.print(SET_TEXT_COLOR_BLUE + "\t" + "highlight <POS>" + RESET_TEXT_COLOR + " - to highlight legal moves for a specified piece\n");
                    userInterface(port, auth, gameID);
                }
                case "Resign", "resign", "-rs" -> {}
                case "Leave", "leave", "-l" -> {
                    WSClient.leave(auth, gameID);
                    Postlogin.userInterface(port, auth);
                }
                default -> {
                    System.out.println("Command not recognized -- please type help for a list of commands");
                    userInterface(port, auth, gameID);
                }
            }
        } else {
            switch (s) {
                case "Move", "move", "-m" -> {
                    if (strings.length != 3 && strings.length != 4) {
                        System.out.println("Unexpected parameters. Type help for more info");
                        userInterface(port, auth, gameID);
                    } else if (strings.length == 4) {
                        String pieceType = strings[3];
                        String startPos = strings[1];
                        String endPos = strings[2];
                        ChessMove move = MoveHelper.decodeMove(startPos, endPos, pieceType);
                        WSClient.makeMove(auth, gameID, move);
                        userInterface(port, auth, gameID);
                    } else {
                        String pieceType = "null";
                        String startPos = strings[1];
                        String endPos = strings[2];
                        ChessMove move = MoveHelper.decodeMove(startPos, endPos, pieceType);
                        WSClient.makeMove(auth, gameID, move);
                        userInterface(port, auth, gameID);
                    }
                }


                case "Highlight", "highlight", "-hi" -> {
                    if (strings.length != 2) {
                        System.out.println("Unexpected parameters. Type help for more info");
                        userInterface(port, auth, gameID);
                    } else {
                        String pos = strings[1];
                    }
                }
                default -> {
                    System.out.println("Unable to process - please check your inputs and try again!");
                    userInterface(port, auth, gameID);
                }
            }
        }
    }


    private static void help() {
        System.out.println(SET_TEXT_COLOR_BLUE + "\t" +
                "redraw"  + RESET_TEXT_COLOR + " - to redraw the chess board\n\t" + SET_TEXT_COLOR_BLUE +
                "move <START POS> <END POS> <PROMO PIECE (IF APPLICABLE)>" + RESET_TEXT_COLOR + " - to make a move\n\t" + SET_TEXT_COLOR_BLUE +
                "highlight <POS>" + RESET_TEXT_COLOR + " - to highlight legal moves for specified piece\n\t" + SET_TEXT_COLOR_BLUE +
                "resign" + RESET_TEXT_COLOR + " - to forfeit the game\n\t" + SET_TEXT_COLOR_BLUE +
                "leave" + RESET_TEXT_COLOR + " - to leave the game\n\t" + SET_TEXT_COLOR_BLUE +
                "help" + RESET_TEXT_COLOR + " - to list possible commands");
    }
}
