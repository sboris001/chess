import ui.Prelogin;

import static ui.EscapeSequences.*;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.print(RESET_BG_COLOR);
        System.out.println("♕ Welcome to the 240 Chess Client: Type help to get started. ♕");
        Prelogin.userInterface(8080);
    }
}