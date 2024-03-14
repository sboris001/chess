import chess.*;
import ui.DrawBoard;

import java.util.Arrays;

import static ui.EscapeSequences.*;

public class Main {
    public static void main(String[] args) {
        System.out.print(RESET_BG_COLOR);
        System.out.println("♕ Welcome to the 240 Chess Client: ");
        DrawBoard.testBoards();
        System.out.println();
        ChessBoard board = new ChessBoard();
        DrawBoard test = new DrawBoard();
        System.out.println();
        test.drawBoard(board, 1);
        System.out.println();
        board.addPiece(new ChessPosition(3, 5), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
        test.drawBoard(board, 2);

    }
}