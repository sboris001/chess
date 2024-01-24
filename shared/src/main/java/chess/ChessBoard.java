package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    ChessPiece[][] board = new ChessPiece[8][8];
    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int row = position.getRow() - 1;
        int col = position.getColumn() - 1;
        this.board[row][col] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int row = position.getRow() - 1;
        int col = position.getColumn() - 1;
        return this.board[row][col];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        for (ChessPiece[] row : this.board){
            for (int i = 0; i < 8; i++){
                row[i] = null;
            }
        }

        ChessPiece wRook = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPosition wRookPos = new ChessPosition(1, 1);
        addPiece(wRookPos, wRook);

        ChessPiece wKnight = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        ChessPosition wKnightPos = new ChessPosition(1, 2);
        addPiece(wKnightPos, wKnight);

        ChessPiece wBishop = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        ChessPosition wBishopPos = new ChessPosition(1, 3);
        addPiece(wBishopPos, wBishop);

        ChessPiece wQueen = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        ChessPosition wQueenPos = new ChessPosition(1, 4);
        addPiece(wQueenPos, wQueen);

        ChessPiece wKing = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        ChessPosition wKingPos = new ChessPosition(1, 5);
        addPiece(wKingPos, wKing);

        ChessPiece wBishop2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        ChessPosition wBishop2Pos = new ChessPosition(1, 6);
        addPiece(wBishop2Pos, wBishop2);

        ChessPiece wKnight2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        ChessPosition wKnight2Pos = new ChessPosition(1, 7);
        addPiece(wKnight2Pos, wKnight2);

        ChessPiece wRook2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPosition wRook2Pos = new ChessPosition(1, 8);
        addPiece(wRook2Pos, wRook2);

        ChessPiece wPawn1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPosition wPawn1Pos = new ChessPosition(2, 1);
        addPiece(wPawn1Pos, wPawn1);

        ChessPiece wPawn2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPosition wPawn2Pos = new ChessPosition(2, 2);
        addPiece(wPawn2Pos, wPawn2);

        ChessPiece wPawn3 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPosition wPawn3Pos = new ChessPosition(2, 3);
        addPiece(wPawn3Pos, wPawn3);

        ChessPiece wPawn4 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPosition wPawn4Pos = new ChessPosition(2, 4);
        addPiece(wPawn4Pos, wPawn4);

        ChessPiece wPawn5 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPosition wPawn5Pos = new ChessPosition(2, 5);
        addPiece(wPawn5Pos, wPawn5);

        ChessPiece wPawn6 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPosition wPawn6Pos = new ChessPosition(2, 6);
        addPiece(wPawn6Pos, wPawn6);

        ChessPiece wPawn7 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPosition wPawn7Pos = new ChessPosition(2, 7);
        addPiece(wPawn7Pos, wPawn7);

        ChessPiece wPawn8 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPosition wPawn8Pos = new ChessPosition(2, 8);
        addPiece(wPawn8Pos, wPawn8);

        //Black pieces next

        ChessPiece bRook = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPosition bRookPos = new ChessPosition(8, 1);
        addPiece(bRookPos, bRook);

        ChessPiece bKnight = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        ChessPosition bKnightPos = new ChessPosition(8, 2);
        addPiece(bKnightPos, bKnight);

        ChessPiece bBishop = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        ChessPosition bBishopPos = new ChessPosition(8, 3);
        addPiece(bBishopPos, bBishop);

        ChessPiece bQueen = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        ChessPosition bQueenPos = new ChessPosition(8, 4);
        addPiece(bQueenPos, bQueen);

        ChessPiece bKing = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        ChessPosition bKingPos = new ChessPosition(8, 5);
        addPiece(bKingPos, bKing);

        ChessPiece bBishop2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        ChessPosition bBishop2Pos = new ChessPosition(8, 6);
        addPiece(bBishop2Pos, bBishop2);

        ChessPiece bKnight2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        ChessPosition bKnight2Pos = new ChessPosition(8, 7);
        addPiece(bKnight2Pos, bKnight2);

        ChessPiece bRook2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPosition bRook2Pos = new ChessPosition(8, 8);
        addPiece(bRook2Pos, bRook2);

        ChessPiece bPawn1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPosition bPawn1Pos = new ChessPosition(7, 1);
        addPiece(bPawn1Pos, bPawn1);

        ChessPiece bPawn2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPosition bPawn2Pos = new ChessPosition(7, 2);
        addPiece(bPawn2Pos, bPawn2);

        ChessPiece bPawn3 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPosition bPawn3Pos = new ChessPosition(7, 3);
        addPiece(bPawn3Pos, bPawn3);

        ChessPiece bPawn4 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPosition bPawn4Pos = new ChessPosition(7, 4);
        addPiece(bPawn4Pos, bPawn4);

        ChessPiece bPawn5 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPosition bPawn5Pos = new ChessPosition(7, 5);
        addPiece(bPawn5Pos, bPawn5);

        ChessPiece bPawn6 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPosition bPawn6Pos = new ChessPosition(7, 6);
        addPiece(bPawn6Pos, bPawn6);

        ChessPiece bPawn7 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPosition bPawn7Pos = new ChessPosition(7, 7);
        addPiece(bPawn7Pos, bPawn7);

        ChessPiece bPawn8 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPosition bPawn8Pos = new ChessPosition(7, 8);
        addPiece(bPawn8Pos, bPawn8);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Arrays.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "board=" + Arrays.toString(board) +
                '}';
    }
}
