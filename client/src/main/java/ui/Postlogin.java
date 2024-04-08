package ui;

import chess.ChessGame;
import model.*;
import websocket.WSClient;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import static java.util.Objects.isNull;
import static ui.EscapeSequences.RESET_TEXT_COLOR;
import static ui.EscapeSequences.SET_TEXT_COLOR_BLUE;

public class Postlogin {
    public static void userInterface(int port, AuthData auth) {
        ServerFacade facade = new ServerFacade("http://localhost:" + port);
        String string;
        System.out.print("[LOGGED_IN] >>> ");
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
                case "List", "list", "-li" -> {
                    try {
                        ListGames gameList = facade.listGames(auth);
                        System.out.println(gameList);
                        userInterface(port, auth);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "Create", "create", "-c" -> {
                    System.out.print(SET_TEXT_COLOR_BLUE + "\t" + "create <NAME>" + RESET_TEXT_COLOR + " - to create a game\n");
                    userInterface(port, auth);
                }
                case "Join", "join", "-j" -> {
                    System.out.print(SET_TEXT_COLOR_BLUE + "\tjoin <ID> [WHITE|BLACK|<empty>]" + RESET_TEXT_COLOR + " - to join a game\n");
                    userInterface(port, auth);
                }
                case "Observe", "observe", "-o" -> {
                    System.out.print(SET_TEXT_COLOR_BLUE + "\tobserve <ID>" + RESET_TEXT_COLOR + " - to observe a games\n");
                    userInterface(port, auth);
                }
                case "Logout", "logout", "-l" -> {
                    try {
                        facade.logoutUser(auth);
                        Prelogin.userInterface(port);
                    } catch (IOException e) {
                        System.out.println("IO exception (whatever that means)");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                case "Quit", "quit", "-q" -> {
                    try {
                        facade.logoutUser(auth);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                default -> {
                    System.out.println("Command not recognized -- please type help for a list of commands");
                    userInterface(port, auth);
                }
            }
        } else {
            switch (s) {
                case "Create", "create", "-c" -> {
                    if (strings.length != 2) {
                        System.out.println("Unexpected parameters. Type help for more info");
                        userInterface(port, auth);
                    } else {
                        String gameName = strings[1];
                        try {
                            GameID id = facade.createGame(auth, new CreateGameObj(gameName));
                            if (!isNull(id)) {
                                System.out.println("Your game has been created. The game ID is: " + id.gameID());
                                userInterface(port, auth);
                            }
                        } catch (IOException e) {
                            System.out.println("Sorry, we couldn't create your game.  Please try using a different game name!");
                            userInterface(port, auth);
                        }
                    }
                }

                case "Join", "join", "-j" -> {
                    if (strings.length == 3) {
                        int id = Integer.parseInt(strings[1]);
                        String playerColor = strings[2];
                        try {
                            facade.joinGame(auth, new JoinGame(playerColor, id));
                            System.out.println("Joined successfully!\n");
                            ChessGame.TeamColor color;
                            if (Objects.equals(playerColor, "WHITE")) {
                                color = ChessGame.TeamColor.WHITE;
                            } else {
                                color = ChessGame.TeamColor.BLACK;
                            }
                            WSClient.joinGame(auth, id, color);
                            DrawBoard.testBoards();
                            userInterface(port, auth);
                        } catch (IOException e) {
                            System.out.println("Sorry, we couldn't join your game.  Please check your game id or team color!");
                            userInterface(port, auth);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
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
                "create <NAME>"  + RESET_TEXT_COLOR + " - to create a game\n\t" + SET_TEXT_COLOR_BLUE +
                "list" + RESET_TEXT_COLOR + " - to list all games\n\t" + SET_TEXT_COLOR_BLUE +
                "join <ID> [WHITE|BLACK]" + RESET_TEXT_COLOR + " - to join a game\n\t" + SET_TEXT_COLOR_BLUE +
                "observe <ID>" + RESET_TEXT_COLOR + " - to observe a games\n\t" + SET_TEXT_COLOR_BLUE +
                "logout" + RESET_TEXT_COLOR + " - to logout of your account\n\t" + SET_TEXT_COLOR_BLUE +
                "quit" + RESET_TEXT_COLOR + " - to exit the client\n\t" + SET_TEXT_COLOR_BLUE +
                "help" + RESET_TEXT_COLOR + " - to list possible commands");
    }
}
