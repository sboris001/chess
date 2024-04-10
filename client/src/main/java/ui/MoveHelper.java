package ui;

import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

public class MoveHelper {
    public static ChessMove decodeMove(String startPos, String endPos, String promo) {
        String[] pos1 = startPos.split("");
        String[] pos2 = endPos.split("");
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.put("d", 4);
        map.put("e", 5);
        map.put("f", 6);
        map.put("g", 7);
        map.put("h", 8);

        ChessPiece.PieceType promoPiece;

        ChessPosition startPosition = new ChessPosition(Integer.parseInt(pos1[1]), map.get(pos1[0]));
        ChessPosition endPosition = new ChessPosition(Integer.parseInt(pos2[1]), map.get(pos2[0]));
        switch (promo.toLowerCase()) {
            case "rook" -> promoPiece = ChessPiece.PieceType.ROOK;
            case "knight" -> promoPiece = ChessPiece.PieceType.KNIGHT;
            case "bishop" -> promoPiece = ChessPiece.PieceType.BISHOP;
            case "queen" -> promoPiece = ChessPiece.PieceType.QUEEN;
            default -> promoPiece = null;
        }

        return new ChessMove(startPosition, endPosition, promoPiece);
    }

}
