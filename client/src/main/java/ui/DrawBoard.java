package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static java.util.Objects.isNull;
import static ui.EscapeSequences.*;

public class DrawBoard {
    public DrawBoard() {}
    private static final String EMPTY = "   ";
    private static final String P = " P "; // Pawn
    private static final String B = " B "; // Bishop
    private static final String N = " N "; // Knight
    private static final String R = " R "; // Rook
    private static final String Q = " Q "; // Queen
    private static final String K = " K "; // King



    public void drawBoard(ChessBoard board, int orientation) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        headerFooter(out, orientation);
        String[] rowPiece = new String[8];
        String[] rowColor = new String[8];
        for (int i = 1; i < 9; i++) {
            if (i > 1) {
                printRow(out, orientation, i-1, rowPiece, rowColor);
            }
            for (int j = 1; j < 9; j++) {
                ChessPosition pos = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(pos);
                if (isNull(piece)) {
                    rowPiece[j-1] = EMPTY;
                    rowColor[j-1] = "";
                } else{
                    ChessPiece.PieceType type = board.getPiece(pos).getPieceType();
                    ChessGame.TeamColor color = board.getPiece(pos).getTeamColor();
                    switch (type){
                        case null -> rowPiece[j-1] = EMPTY;
                        case KING -> rowPiece[j-1] = K;
                        case PAWN -> rowPiece[j-1] = P;
                        case BISHOP -> rowPiece[j-1] = B;
                        case ROOK -> rowPiece[j-1] = R;
                        case KNIGHT -> rowPiece[j-1] = N;
                        case QUEEN -> rowPiece[j-1] = Q;
                    }
                    switch (color) {
                        case WHITE -> rowColor[j-1] = "blue";
                        case BLACK -> rowColor[j-1] = "red";
                        case null -> rowColor[j-1] = "";
                    }
                }
            }
        }
        printRow(out, orientation, 8, rowPiece, rowColor);
        headerFooter(out, orientation);
    }

    private static void headerFooter(PrintStream out, int orientation) {
        setGrey(out);
        out.print(EMPTY);
        String[] alpha = {" a ", " b ", " c ", " d ", " e ", " f ", " g ", " h "};
        if (orientation == 1) {
            for (int i = alpha.length - 1; i >= 0; i--) {
                out.print(alpha[i]);
            }
        } else if (orientation == 2) {
            for (String s : alpha) {
                out.print(s);
            }
        }
        out.print(EMPTY);
        out.print(RESET_BG_COLOR);
        out.print("\n");

    }

    private static void printRow(PrintStream out, int orientation, int row, String[] pieces, String[] colors) {
        setGrey(out);
        out.printf(" %d ", row);
        printList(out, orientation, pieces, row, colors);
        setGrey(out);
        out.printf(" %d ", row);
        out.print(RESET_BG_COLOR);
        out.print("\n");
    }

    private static void checkColor(PrintStream out, int i, String[] colors) {
        if (Objects.equals(colors[i], "blue")) {
            out.print(SET_TEXT_COLOR_BLUE);
        } else if (Objects.equals(colors[i], "red")) {
            out.print(SET_TEXT_COLOR_RED);
        } else {
            out.print(SET_TEXT_COLOR_BLACK);
        }
    }

    private static void printList(PrintStream out, int orientation, String[] pieces, int row, String[] colors) {
        if (orientation == 1) {
            if (row % 2 != 0) {
                for (int i = 0; i < pieces.length; i++) {
                    checkColor(out, i, colors);
                    if (i % 2 != 0) {
                        out.print(SET_BG_COLOR_BLACK);
                        out.print(pieces[i]);
                    } else {
                        out.print(SET_BG_COLOR_WHITE);
                        out.print(pieces[i]);
                    }
                }
            } else {
                for (int i = 0; i < pieces.length; i++) {
                    checkColor(out, i, colors);
                    if (i % 2 != 0) {
                        out.print(SET_BG_COLOR_WHITE);
                        out.print(pieces[i]);
                    } else {
                        out.print(SET_BG_COLOR_BLACK);
                        out.print(pieces[i]);
                    }
                }
            }
        } else if (orientation == 2) {
            if (row % 2 != 0) {
                for (int i = 0; i < pieces.length; i++) {
                    checkColor(out, i, colors);
                    if (i % 2 != 0) {
                        out.print(SET_BG_COLOR_BLACK);
                        out.print(pieces[i]);
                    } else {
                        out.print(SET_BG_COLOR_WHITE);
                        out.print(pieces[i]);
                    }
                }
            } else {
                for (int i = 0; i < pieces.length; i++) {
                    checkColor(out, i, colors);
                    if (i % 2 != 0) {
                        out.print(SET_BG_COLOR_WHITE);
                        out.print(pieces[i]);
                    } else {
                        out.print(SET_BG_COLOR_BLACK);
                        out.print(pieces[i]);
                    }
                }
            }
        }

    }

    private static void setGrey(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(SET_TEXT_BOLD);
    }

}
