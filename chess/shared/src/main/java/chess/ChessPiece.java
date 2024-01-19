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

    //Helper function to test if there is a null value or a piece of a different color in the spot of interest.
    //If so it will return a move object for that position to add to your array.  If not it will return a null value
    public ChessMove movable(ChessPiece o, ChessPosition mP, ChessPosition p){
        if (isNull(o) || o.color != color) {
            ChessMove move = new ChessMove(mP, p, null);
            return move;
        }
        return null;
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
        switch (type) {
            case KING -> {
                if (row - 1 > 0) {
                    if (col - 1 > 0) {
                        ChessPosition pos = new ChessPosition(row - 1, col - 1);
                        ChessPiece obstacle = board.getPiece(pos);
                        if (!isNull(movable(obstacle, myPosition, pos))) {
                            moves.add(movable(obstacle, myPosition, pos));
                        }
                    }
                    if (col + 1 < 9) {
                        ChessPosition pos = new ChessPosition(row - 1, col + 1);
                        ChessPiece obstacle = board.getPiece(pos);
                        if (!isNull(movable(obstacle, myPosition, pos))) {
                            moves.add(movable(obstacle, myPosition, pos));
                        }
                    }
                    ChessPosition pos = new ChessPosition(row - 1, col);
                    ChessPiece obstacle = board.getPiece(pos);
                    if (!isNull(movable(obstacle, myPosition, pos))) {
                        moves.add(movable(obstacle, myPosition, pos));
                    }
                }
                if (row + 1 < 9) {
                    if (col - 1 > 0) {
                        ChessPosition pos = new ChessPosition(row + 1, col - 1);
                        ChessPiece obstacle = board.getPiece(pos);
                        if (!isNull(movable(obstacle, myPosition, pos))) {
                            moves.add(movable(obstacle, myPosition, pos));
                        }
                    }
                    if (col + 1 < 9) {
                        ChessPosition pos = new ChessPosition(row + 1, col + 1);
                        ChessPiece obstacle = board.getPiece(pos);
                        if (!isNull(movable(obstacle, myPosition, pos))) {
                            moves.add(movable(obstacle, myPosition, pos));
                        }
                    }
                    ChessPosition pos = new ChessPosition(row + 1, col);
                    ChessPiece obstacle = board.getPiece(pos);
                    if (!isNull(movable(obstacle, myPosition, pos))) {
                        moves.add(movable(obstacle, myPosition, pos));
                    }
                }
                if (col - 1 > 0) {
                    ChessPosition pos = new ChessPosition(row, col - 1);
                    ChessPiece obstacle = board.getPiece(pos);
                    if (!isNull(movable(obstacle, myPosition, pos))) {
                        moves.add(movable(obstacle, myPosition, pos));
                    }
                }
                if (col + 1 < 9) {
                    ChessPosition pos = new ChessPosition(row, col + 1);
                    ChessPiece obstacle = board.getPiece(pos);
                    if (!isNull(movable(obstacle, myPosition, pos))) {
                        moves.add(movable(obstacle, myPosition, pos));
                    }
                }
            }
            case BISHOP -> {
                ChessPosition[] leftUp = {new ChessPosition(row - 1, col - 1), new ChessPosition(row - 2, col - 2), new ChessPosition(row - 3, col - 3), new ChessPosition(row - 4, col - 4), new ChessPosition(row - 5, col - 5), new ChessPosition(row - 6, col - 6), new ChessPosition(row - 7, col - 7)};
                for (ChessPosition checkPosition: leftUp) {
                    if (checkPosition.row > 0 && checkPosition.row < 9 && checkPosition.col > 0 && checkPosition.col < 9){
                        if (isNull(board.getPiece(checkPosition))) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                        } else if (board.getPiece(checkPosition).color != color) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                            break;
                        } else {break;}
                    }
                }
                ChessPosition[] rightUp = {new ChessPosition(row - 1, col + 1), new ChessPosition(row - 2, col + 2), new ChessPosition(row - 3, col + 3), new ChessPosition(row - 4, col + 4), new ChessPosition(row - 5, col + 5), new ChessPosition(row - 6, col + 6), new ChessPosition(row - 7, col + 7)};
                for (ChessPosition checkPosition: rightUp) {
                    if (checkPosition.row > 0 && checkPosition.row < 9 && checkPosition.col > 0 && checkPosition.col < 9){
                        if (isNull(board.getPiece(checkPosition))) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                        } else if (board.getPiece(checkPosition).color != color) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                            break;
                        } else {break;}
                    }
                }
                ChessPosition[] leftDown = {new ChessPosition(row + 1, col - 1), new ChessPosition(row + 2, col - 2), new ChessPosition(row + 3, col - 3), new ChessPosition(row + 4, col - 4), new ChessPosition(row + 5, col - 5), new ChessPosition(row + 6, col - 6), new ChessPosition(row + 7, col - 7)};
                for (ChessPosition checkPosition: leftDown) {
                    if (checkPosition.row > 0 && checkPosition.row < 9 && checkPosition.col > 0 && checkPosition.col < 9){
                        if (isNull(board.getPiece(checkPosition))) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                        } else if (board.getPiece(checkPosition).color != color) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                            break;
                        } else {break;}
                    }
                }
                ChessPosition[] rightDown = {new ChessPosition(row + 1, col + 1), new ChessPosition(row + 2, col + 2), new ChessPosition(row + 3, col + 3), new ChessPosition(row + 4, col + 4), new ChessPosition(row + 5, col + 5), new ChessPosition(row + 6, col + 6), new ChessPosition(row + 7, col + 7)};
                for (ChessPosition checkPosition: rightDown) {
                    if (checkPosition.row > 0 && checkPosition.row < 9 && checkPosition.col > 0 && checkPosition.col < 9){
                        if (isNull(board.getPiece(checkPosition))) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                        } else if (board.getPiece(checkPosition).color != color) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                            break;
                        } else {break;}
                    }
                }
            }
            case QUEEN -> {
                ChessPosition[] leftUp = {new ChessPosition(row - 1, col - 1), new ChessPosition(row - 2, col - 2), new ChessPosition(row - 3, col - 3), new ChessPosition(row - 4, col - 4), new ChessPosition(row - 5, col - 5), new ChessPosition(row - 6, col - 6), new ChessPosition(row - 7, col - 7)};
                for (ChessPosition checkPosition: leftUp) {
                    if (checkPosition.row > 0 && checkPosition.row < 9 && checkPosition.col > 0 && checkPosition.col < 9){
                        if (isNull(board.getPiece(checkPosition))) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                        } else if (board.getPiece(checkPosition).color != color) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                            break;
                        } else {break;}
                    }
                }
                ChessPosition[] rightUp = {new ChessPosition(row - 1, col + 1), new ChessPosition(row - 2, col + 2), new ChessPosition(row - 3, col + 3), new ChessPosition(row - 4, col + 4), new ChessPosition(row - 5, col + 5), new ChessPosition(row - 6, col + 6), new ChessPosition(row - 7, col + 7)};
                for (ChessPosition checkPosition: rightUp) {
                    if (checkPosition.row > 0 && checkPosition.row < 9 && checkPosition.col > 0 && checkPosition.col < 9){
                        if (isNull(board.getPiece(checkPosition))) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                        } else if (board.getPiece(checkPosition).color != color) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                            break;
                        } else {break;}
                    }
                }
                ChessPosition[] leftDown = {new ChessPosition(row + 1, col - 1), new ChessPosition(row + 2, col - 2), new ChessPosition(row + 3, col - 3), new ChessPosition(row + 4, col - 4), new ChessPosition(row + 5, col - 5), new ChessPosition(row + 6, col - 6), new ChessPosition(row + 7, col - 7)};
                for (ChessPosition checkPosition: leftDown) {
                    if (checkPosition.row > 0 && checkPosition.row < 9 && checkPosition.col > 0 && checkPosition.col < 9){
                        if (isNull(board.getPiece(checkPosition))) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                        } else if (board.getPiece(checkPosition).color != color) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                            break;
                        } else {break;}
                    }
                }
                ChessPosition[] rightDown = {new ChessPosition(row + 1, col + 1), new ChessPosition(row + 2, col + 2), new ChessPosition(row + 3, col + 3), new ChessPosition(row + 4, col + 4), new ChessPosition(row + 5, col + 5), new ChessPosition(row + 6, col + 6), new ChessPosition(row + 7, col + 7)};
                for (ChessPosition checkPosition: rightDown) {
                    if (checkPosition.row > 0 && checkPosition.row < 9 && checkPosition.col > 0 && checkPosition.col < 9){
                        if (isNull(board.getPiece(checkPosition))) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                        } else if (board.getPiece(checkPosition).color != color) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                            break;
                        } else {break;}
                    }
                }
                ChessPosition[] up = {new ChessPosition(row - 1, col), new ChessPosition(row - 2, col), new ChessPosition(row - 3, col), new ChessPosition(row - 4, col), new ChessPosition(row - 5, col), new ChessPosition(row - 6, col), new ChessPosition(row - 7, col)};
                for (ChessPosition checkPosition: up) {
                    if (checkPosition.row > 0 && checkPosition.row < 9 && checkPosition.col > 0 && checkPosition.col < 9){
                        if (isNull(board.getPiece(checkPosition))) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                        } else if (board.getPiece(checkPosition).color != color) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                            break;
                        } else {break;}
                    }
                }
                ChessPosition[] down = {new ChessPosition(row + 1, col), new ChessPosition(row + 2, col), new ChessPosition(row + 3, col), new ChessPosition(row + 4, col), new ChessPosition(row + 5, col), new ChessPosition(row + 6, col), new ChessPosition(row + 7, col)};
                for (ChessPosition checkPosition: down) {
                    if (checkPosition.row > 0 && checkPosition.row < 9 && checkPosition.col > 0 && checkPosition.col < 9){
                        if (isNull(board.getPiece(checkPosition))) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                        } else if (board.getPiece(checkPosition).color != color) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                            break;
                        } else {break;}
                    }
                }
                ChessPosition[] right = {new ChessPosition(row, col + 1), new ChessPosition(row, col + 2), new ChessPosition(row, col + 3), new ChessPosition(row, col + 4), new ChessPosition(row, col + 5), new ChessPosition(row, col + 6), new ChessPosition(row, col + 7)};
                for (ChessPosition checkPosition: right) {
                    if (checkPosition.row > 0 && checkPosition.row < 9 && checkPosition.col > 0 && checkPosition.col < 9){
                        if (isNull(board.getPiece(checkPosition))) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                        } else if (board.getPiece(checkPosition).color != color) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                            break;
                        } else {break;}
                    }
                }
                ChessPosition[] left = {new ChessPosition(row, col - 1), new ChessPosition(row, col - 2), new ChessPosition(row, col - 3), new ChessPosition(row, col - 4), new ChessPosition(row, col - 5), new ChessPosition(row, col - 6), new ChessPosition(row, col - 7)};
                for (ChessPosition checkPosition: left) {
                    if (checkPosition.row > 0 && checkPosition.row < 9 && checkPosition.col > 0 && checkPosition.col < 9){
                        if (isNull(board.getPiece(checkPosition))) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                        } else if (board.getPiece(checkPosition).color != color) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                            break;
                        } else {break;}
                    }
                }
            }
            case ROOK -> {
                ChessPosition[] up = {new ChessPosition(row - 1, col), new ChessPosition(row - 2, col), new ChessPosition(row - 3, col), new ChessPosition(row - 4, col), new ChessPosition(row - 5, col), new ChessPosition(row - 6, col), new ChessPosition(row - 7, col)};
                for (ChessPosition checkPosition: up) {
                    if (checkPosition.row > 0 && checkPosition.row < 9 && checkPosition.col > 0 && checkPosition.col < 9){
                        if (isNull(board.getPiece(checkPosition))) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                        } else if (board.getPiece(checkPosition).color != color) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                            break;
                        } else {break;}
                    }
                }
                ChessPosition[] down = {new ChessPosition(row + 1, col), new ChessPosition(row + 2, col), new ChessPosition(row + 3, col), new ChessPosition(row + 4, col), new ChessPosition(row + 5, col), new ChessPosition(row + 6, col), new ChessPosition(row + 7, col)};
                for (ChessPosition checkPosition: down) {
                    if (checkPosition.row > 0 && checkPosition.row < 9 && checkPosition.col > 0 && checkPosition.col < 9){
                        if (isNull(board.getPiece(checkPosition))) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                        } else if (board.getPiece(checkPosition).color != color) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                            break;
                        } else {break;}
                    }
                }
                ChessPosition[] right = {new ChessPosition(row, col + 1), new ChessPosition(row, col + 2), new ChessPosition(row, col + 3), new ChessPosition(row, col + 4), new ChessPosition(row, col + 5), new ChessPosition(row, col + 6), new ChessPosition(row, col + 7)};
                for (ChessPosition checkPosition: right) {
                    if (checkPosition.row > 0 && checkPosition.row < 9 && checkPosition.col > 0 && checkPosition.col < 9){
                        if (isNull(board.getPiece(checkPosition))) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                        } else if (board.getPiece(checkPosition).color != color) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                            break;
                        } else {break;}
                    }
                }
                ChessPosition[] left = {new ChessPosition(row, col - 1), new ChessPosition(row, col - 2), new ChessPosition(row, col - 3), new ChessPosition(row, col - 4), new ChessPosition(row, col - 5), new ChessPosition(row, col - 6), new ChessPosition(row, col - 7)};
                for (ChessPosition checkPosition: left) {
                    if (checkPosition.row > 0 && checkPosition.row < 9 && checkPosition.col > 0 && checkPosition.col < 9){
                        if (isNull(board.getPiece(checkPosition))) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                        } else if (board.getPiece(checkPosition).color != color) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                            break;
                        } else {break;}
                    }
                }
            }
            case KNIGHT -> {
                ChessPosition[] all = {new ChessPosition(row - 2, col - 1), new ChessPosition(row - 2, col + 1), new ChessPosition(row - 1, col + 2), new ChessPosition(row + 1, col + 2), new ChessPosition(row + 2, col + 1), new ChessPosition(row + 2, col - 1), new ChessPosition(row + 1, col - 2), new ChessPosition(row - 1, col - 2)};
                for (ChessPosition checkPosition: all) {
                    if (checkPosition.row > 0 && checkPosition.row < 9 && checkPosition.col > 0 && checkPosition.col < 9){
                        if (isNull(board.getPiece(checkPosition)) || board.getPiece(checkPosition).color != color) {
                            moves.add(new ChessMove(myPosition, checkPosition, null));
                        }
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
