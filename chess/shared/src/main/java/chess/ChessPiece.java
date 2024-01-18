package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static java.util.Objects.isNull;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor color;
    private ChessPiece.PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.color = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
        ChessPiece piece = board.getPiece(myPosition);
        PieceType type = piece.type;
        ChessGame.TeamColor color = piece.color;
        switch (type){
            case KING -> {
                if (row - 1 > 0){
                    if (col - 1 > 0) {
                        ChessPosition pos = new ChessPosition(row - 1, col - 1);
                        ChessPiece obstacle = board.getPiece(pos);
                        if (isNull(obstacle)){
                            ChessMove move = new ChessMove(myPosition, pos, null);
                            moves.add(move);
                        } else if (obstacle.color != color){
                            ChessMove move = new ChessMove(myPosition, pos, null);
                            moves.add(move);
                        }
                    }
                    if (col + 1 < 9) {
                        ChessPosition pos = new ChessPosition(row - 1, col + 1);
                        ChessPiece obstacle = board.getPiece(pos);
                        if (isNull(obstacle)){
                            ChessMove move = new ChessMove(myPosition, pos, null);
                            moves.add(move);
                        } else if (obstacle.color != color){
                            ChessMove move = new ChessMove(myPosition, pos, null);
                            moves.add(move);
                        }
                    }
                    ChessPosition pos = new ChessPosition(row - 1, col);
                    ChessPiece obstacle = board.getPiece(pos);
                    if (isNull(obstacle)){
                        ChessMove move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    } else if (obstacle.color != color){
                        ChessMove move = new ChessMove(myPosition, pos, null);
                        moves.add(move);
                    }
                }
            }
        }
        return moves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return color == that.color && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, type);
    }
}
