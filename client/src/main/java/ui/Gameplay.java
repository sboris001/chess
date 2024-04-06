package ui;

import model.*;

import java.io.IOException;
import java.util.Scanner;

import static java.util.Objects.isNull;
import static ui.EscapeSequences.RESET_TEXT_COLOR;
import static ui.EscapeSequences.SET_TEXT_COLOR_BLUE;

public class Gameplay {
    public static void userInterface(int port, AuthData auth) {
        ServerFacade facade = new ServerFacade("http://localhost:" + port);
        String string;
        System.out.print("[IN_GAME] >>> ");
        Scanner in = new Scanner(System.in);
        string = in.nextLine();
        String[] strings = new String[0];
        if (string.isEmpty()) {
            System.out.println("Please enter a command");
            userInterface(port, auth);
        } else {
            strings = string.split(" ");
        }
        String s = strings[0];
        if (strings.length == 1) {
            switch (s) {
                case "Help", "help", "-h" -> {
                    help();
                    userInterface(port, auth);
                }
                case "Redraw", "redraw", "-rd" -> {}
                case "Move", "move", "-m" -> {
                    System.out.print(SET_TEXT_COLOR_BLUE + "\t" + "move <START POS> <END POS>" + RESET_TEXT_COLOR + " - to make a move\n");
                    userInterface(port, auth);
                }
                case "Highlight", "highlight", "-hi" -> {
                    System.out.print(SET_TEXT_COLOR_BLUE + "\t" + "highlight <POS>" + RESET_TEXT_COLOR + " - to highlight legal moves for a specified piece\n");
                    userInterface(port, auth);
                }
                case "Resign", "resign", "-rs" -> {}
                case "Leave", "leave", "-l" -> {}
                default -> {
                    System.out.println("Command not recognized -- please type help for a list of commands");
                    userInterface(port, auth);
                }
            }
        } else {
            switch (s) {
                case "Move", "move", "-m" -> {
                    if (strings.length != 3) {
                        System.out.println("Unexpected parameters. Type help for more info");
                        userInterface(port, auth);
                    } else {
                        String startPos = strings[1];
                        String endPos = strings[2];
                    }
                }

                case "Join", "join", "-j" -> {
                    if (strings.length == 3) {
                        int id = Integer.parseInt(strings[1]);
                        String playerColor = strings[2];
                        try {
                            facade.joinGame(auth, new JoinGame(playerColor, id));
                            System.out.println("Joined successfully!\n");
                            DrawBoard.testBoards();
                            userInterface(port, auth);
                        } catch (IOException e) {
                            System.out.println("Sorry, we couldn't join your game.  Please check your game id or team color!");
                            userInterface(port, auth);
                        }
                    } else {
                        System.out.println("Unexpected parameters. Type help for more info");
                        userInterface(port, auth);
                    }
                }
                case "observe", "Observe", "-o" -> {
                    if (strings.length != 2) {
                        System.out.println("Unexpected parameters. Type help for more info");
                        userInterface(port, auth);
                    } else {
                        int gameID = Integer.parseInt(strings[1]);
                        try {
                            facade.joinGame(auth, new JoinGame(null, gameID));
                            System.out.println("Successfully joined game as an observer!");
                            DrawBoard.testBoards();
                            userInterface(port, auth);
                        } catch (IOException e) {
                            System.out.println("Sorry, we couldn't join that game.  Please check your game id or team color!");
                            userInterface(port, auth);
                        }
                    }
                }
                default -> {
                    System.out.println("Unable to process - please check your inputs and try again!");
                    userInterface(port, auth);
                }
            }
        }
    }


    private static void help() {
        System.out.println(SET_TEXT_COLOR_BLUE + "\t" +
                "redraw"  + RESET_TEXT_COLOR + " - to redraw the chess board\n\t" + SET_TEXT_COLOR_BLUE +
                "move <START POS> <END POS>" + RESET_TEXT_COLOR + " - to make a move\n\t" + SET_TEXT_COLOR_BLUE +
                "highlight <POS>" + RESET_TEXT_COLOR + " - to highlight legal moves for specified piece\n\t" + SET_TEXT_COLOR_BLUE +
                "resign" + RESET_TEXT_COLOR + " - to forfeit the game\n\t" + SET_TEXT_COLOR_BLUE +
                "leave" + RESET_TEXT_COLOR + " - to leave the game\n\t" + SET_TEXT_COLOR_BLUE +
                "help" + RESET_TEXT_COLOR + " - to list possible commands");
    }
}
