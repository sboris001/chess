import chess.ChessBoard;
import server.Server;
import ui.DrawBoard;
import ui.Prelogin;
import java.util.Arrays;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.print(RESET_BG_COLOR);
        System.out.println("♕ Welcome to the 240 Chess Client: Type help to get started. ♕");
        System.out.print("[LOGGED_OUT] >>> ");
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        Prelogin.userInterface(s);
    }
}