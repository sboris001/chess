package ui;

import model.AuthData;
import model.LoginUser;
import model.UserData;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import static java.util.Objects.isNull;
import static ui.EscapeSequences.*;

public class Prelogin {
    static ServerFacade facade = new ServerFacade("http://localhost:8080");
    public static void userInterface() throws Exception {
        String string = "";
        System.out.print("[LOGGED_OUT] >>> ");
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
            if (s.equals("Help") || s.equals("help") || s.equals("-h")) {
                help();
                userInterface();
            } else if (s.equals("Login") || s.equals("login") || s.equals("-l")){
                System.out.println(SET_TEXT_COLOR_BLUE +
                        "\tlogin <USERNAME> <PASSWORD>" + RESET_TEXT_COLOR + " - to play chess");
                userInterface();
            } else if (s.equals("Register") || s.equals("register") || s.equals("-r")){
                System.out.println(SET_TEXT_COLOR_BLUE + "\tregister <USERNAME> <PASSWORD> <EMAIL>"  + RESET_TEXT_COLOR + " - to create an account");
                userInterface();
            } else if (!Objects.equals(s, "Quit") & !Objects.equals(s, "quit") & !Objects.equals(s, "-q")) {
                notRecognized();}
        } else if (strings.length == 3) {
            String command = strings[0];
            String username = strings[1];
            String password = strings[2];
            if (command.equals("Login") || command.equals("login") || command.equals("-l")) {
                try {
                    AuthData auth = facade.loginUser(new LoginUser(username, password));
                    if (!isNull(auth)) {
                        System.out.println("Logged in successfully!\n\nLogged in as: "+ username +"\nType help to get started.");
                        Postlogin.userInterface();
                    }
                } catch (IOException e) {
                    System.out.println("Sorry, we couldn't log you in.  Please make sure you are registered and your password is correct!");
                    userInterface();
                }
            } else {
                notRecognized();
            }
        } else if (strings.length == 4) {
            String command = strings[0];
            String username = strings[1];
            String password = strings[2];
            String email = strings[3];
            if (command.equals("Register") || command.equals("register") || command.equals("-r")) {
                try {
                    AuthData auth = facade.registerUser(new UserData(username, password, email));
                    if (!isNull(auth)) {
                        System.out.println("Registered successfully!\n\nLogged in as: "+ username + "\nType help to get started.");
                        Postlogin.userInterface();
                    }
                } catch (IOException e) {
                    System.out.println("Sorry, we couldn't register you.  Please try again with a different username!");
                    userInterface();
                }
            } else {
                notRecognized();
            }
        } else {
            notRecognized();
        }
    }

    private static void help() {
        System.out.println(SET_TEXT_COLOR_BLUE + "\t" +
                "register <USERNAME> <PASSWORD> <EMAIL>"  + RESET_TEXT_COLOR + " - to create an account\n\t" + SET_TEXT_COLOR_BLUE +
                "login <USERNAME> <PASSWORD>" + RESET_TEXT_COLOR + " - to play chess\n\t" + SET_TEXT_COLOR_BLUE +
                "quit" + RESET_TEXT_COLOR + " - to exit the client\n\t" + SET_TEXT_COLOR_BLUE +
                "help" + RESET_TEXT_COLOR + " - to list possible commands");
    }

    private static void notRecognized() throws Exception {
        System.out.println("Command not recognized -- please type help for a list of commands");
        userInterface();
    }
}
