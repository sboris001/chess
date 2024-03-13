import chess.*;
import ui.DrawBoard;
import static ui.EscapeSequences.*;

public class Main {
    public static void main(String[] args) {
        System.out.print(RESET_BG_COLOR);
        System.out.println("♕ 240 Chess Client: ");
        var drawBoard = new DrawBoard();
        drawBoard.drawBoard(args);
    }
}