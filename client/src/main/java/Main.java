import chess.*;
import ui.DrawBoard;
import static ui.EscapeSequences.*;

public class Main {
    public static void main(String[] args) {
        System.out.print(RESET_BG_COLOR);
        System.out.println("♕ Welcome to the 240 Chess Client: ");
        ChessBoard startGame = new ChessBoard();
        startGame.resetBoard();
        var drawBoard = new DrawBoard();
//        drawBoard.drawBoard(startGame, 1);
//        System.out.print(RESET_BG_COLOR);
//        System.out.print("\n");
        drawBoard.drawBoard(startGame, 2);
    }
}