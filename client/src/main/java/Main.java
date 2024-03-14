import chess.*;
import ui.DrawBoard;
import static ui.EscapeSequences.*;

public class Main {
    public static void main(String[] args) {
        System.out.print(RESET_BG_COLOR);
        System.out.println("♕ Welcome to the 240 Chess Client: ");
        DrawBoard.testBoards();
    }
}