package ui;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import static ui.EscapeSequences.RESET_TEXT_COLOR;
import static ui.EscapeSequences.SET_TEXT_COLOR_BLUE;

public class Postlogin {

    static ServerFacade facade = new ServerFacade("http://localhost:8080");
    public static void userInterface() throws Exception {
        String string = "";
        System.out.print("[LOGGED_IN] >>> ");
        Scanner in = new Scanner(System.in);
        string = in.nextLine();
        String[] strings = new String[0];
        if (string.isEmpty()) {
            System.out.println("Please enter a command");
            userInterface();
        } else {
            strings = string.split(" ");
        }
        if (strings.length == 1) {
            String s = strings[0];
            switch (s) {
                case "Help", "help", "-h" -> {
                    help();
                    userInterface();
                }
                case "List", "list", "-li" -> {
                    //REPLACE THIS WITH CODE TO DO THE LIST STUFF
                }
                case "Create", "create", "-c" -> {
                    System.out.print(SET_TEXT_COLOR_BLUE + "\t" + "create <NAME>" + RESET_TEXT_COLOR + " - to create a game\n");
                    userInterface();
                }
                case "Join", "join", "-j" -> {
                    System.out.print(SET_TEXT_COLOR_BLUE + "\tjoin <ID> [WHITE|BLACK|<empty>]" + RESET_TEXT_COLOR + " - to join a game\n");
                    userInterface();
                }
                case "Observe", "observe", "-o" -> {
                    System.out.print(SET_TEXT_COLOR_BLUE + "\tobserve <ID>" + RESET_TEXT_COLOR + " - to observe a games\n");
                    userInterface();
                }
            }
        } else {
            String s = strings[0];
            switch (s) {
                case "Create", "create", "-c" -> {
                    if (strings.length != 2) {
                        System.out.println("Unexpected parameters. Type help for more info");
                        userInterface();
                    } else {
                        System.out.println("A game will be created once I implement that");
                    }
                }

                case "Join", "join", "-j" -> {
                    if (strings.length == 2) {
                        System.out.println("This should join a game as an observer (No team field was given)");
                    } else if (strings.length == 3) {
                        System.out.println("This should join a game as a player (Team field given");
                    } else {
                        System.out.println("Unexpected parameters. Type help for more info");
                        userInterface();
                    }
                }
            }

        }
    }


    private static void help() {
        System.out.println(SET_TEXT_COLOR_BLUE + "\t" +
                "create <NAME>"  + RESET_TEXT_COLOR + " - to create a game\n\t" + SET_TEXT_COLOR_BLUE +
                "list" + RESET_TEXT_COLOR + " - to list all games\n\t" + SET_TEXT_COLOR_BLUE +
                "join <ID> [WHITE|BLACK|<empty>]" + RESET_TEXT_COLOR + " - to join a game\n\t" + SET_TEXT_COLOR_BLUE +
                "observe <ID>" + RESET_TEXT_COLOR + " - to observe a games\n\t" + SET_TEXT_COLOR_BLUE +
                "logout" + RESET_TEXT_COLOR + " - to logout of your account\n\t" + SET_TEXT_COLOR_BLUE +
                "quit" + RESET_TEXT_COLOR + " - to exit the client\n\t" + SET_TEXT_COLOR_BLUE +
                "help" + RESET_TEXT_COLOR + " - to list possible commands");
    }
}
