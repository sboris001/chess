package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static ui.EscapeSequences.*;

public class DrawBoard {
    public DrawBoard() {}
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    private static final String EMPTY = "   ";
    private static final String P = " P "; // Pawn
    private static final String B = " B "; // Bishop
    private static final String N = " N "; // Knight
    private static final String R = " R "; // Rook
    private static final String Q = " Q "; // Queen
    private static final String K = " K "; // King
    private static final String[] empty = {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY};


    public void drawBoard(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        headerFooter(out, 1);
        printRow(out, 1, 1,new String[] {R, N, B, K, Q, B, N, R}, "red");
        printRow(out,1, 2, new String[] {P, P, P, P, P, P, P, P}, "red");
        printRow(out,1, 3, empty);
        printRow(out,1, 4, empty);
        printRow(out,1, 5, empty);
        printRow(out,1, 6, empty);
        printRow(out,1, 7, new String[] {P, P, P, P, P, P, P, P}, "blue");
        printRow(out,1, 8, new String[] {R, N, B, K, Q, B, N, R}, "blue");
        headerFooter(out, 1);
        out.println();
        headerFooter(out, 2);
        printRow(out,2, 8, new String[] {R, N, B, K, Q, B, N, R}, "blue");
        printRow(out,2, 7, new String[] {P, P, P, P, P, P, P, P}, "blue");
        printRow(out,2, 6, empty);
        printRow(out,2, 5, empty);
        printRow(out,2, 4, empty);
        printRow(out,2, 3, empty);
        printRow(out,2, 2, new String[] {P, P, P, P, P, P, P, P}, "red");
        printRow(out,2, 1, new String[] {R, N, B, K, Q, B, N, R}, "red");
        headerFooter(out, 2);
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

    private static void printRow(PrintStream out, int orientation, int row, String[] pieces, String ... color) {
        setGrey(out);
        out.printf(" %d ", row);
        String text = color.length > 0 ? color[0] : "";
        if (Objects.equals(text, "red")) {
            out.print(SET_TEXT_COLOR_RED);
        } else if (Objects.equals(text, "blue")) {
            out.print(SET_TEXT_COLOR_BLUE);
        }
        printList(out, orientation, pieces, row);
        setGrey(out);
        out.printf(" %d ", row);
        out.print(RESET_BG_COLOR);
        out.print("\n");
    }


    private static void printList(PrintStream out, int orientation, String[] pieces, int row) {
        if (orientation == 1) {
            if (row % 2 != 0) {
                for (int i = 0; i < pieces.length; i++) {
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
            if (row % 2 == 0) {
                for (int i = 0; i < pieces.length; i++) {
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
