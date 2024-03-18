package ui;

import model.UserData;

import java.util.Objects;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Prelogin {
    static ServerFacade facade = new ServerFacade("localhost:8080");
    public static void userInterface(String string) throws Exception {
        String[] strings = string.split(" ");
        if (strings.length == 1) {
            String s = strings[0];
            if (s.equals("Help") || s.equals("help") || s.equals("-h")) {
                help();
                Scanner in = new Scanner(System.in);
                String command = in.nextLine();
                userInterface(command);
            } else if (!Objects.equals(s, "Quit") & !Objects.equals(s, "quit") & !Objects.equals(s, "-q")) {
                notRecognized();}
        } else if (strings.length == 3) {
            String command = strings[0];
            String username = strings[1];
            String password = strings[2];
            if (command.equals("Login") || command.equals("login") || command.equals("-l")) {
                System.out.println();
            } else {
                notRecognized();
            }
        } else if (strings.length == 4) {
            String command = strings[0];
            String username = strings[1];
            String password = strings[2];
            String email = strings[3];
            if (command.equals("Register") || command.equals("register") || command.equals("-r")) {
                System.out.println();
            } else {
                notRecognized();
            }
        }
    }

    private static void help() {
        System.out.println(SET_TEXT_COLOR_BLUE + "\tregister <USERNAME> <PASSWORD> <EMAIL>"  + RESET_TEXT_COLOR + " - to create an account\n\t" + SET_TEXT_COLOR_BLUE +
                "login <USERNAME> <PASSWORD>" + RESET_TEXT_COLOR + " - to play chess\n\t" + SET_TEXT_COLOR_BLUE + "quit" + RESET_TEXT_COLOR + " - to exit the client\n\t" + SET_TEXT_COLOR_BLUE + "help" + RESET_TEXT_COLOR + " - to list possible commands");
        System.out.print("[LOGGED_OUT] >>> ");
    }

    private static void notRecognized() throws Exception {
        System.out.println("Command not recognized -- please type help for a list of commands");
        System.out.print("[LOGGED_OUT] >>> ");
        Scanner in = new Scanner(System.in);
        String newCommand = in.nextLine();
        userInterface(newCommand);
    }
}
