package chess;

import java.util.*;

import static java.util.Objects.isNull;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    ChessGame.TeamColor color;
    PieceType type;
    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
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

    public ArrayList<ChessMove> continuousMoveChecker(ChessPosition[] possibleMoves, ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();

        for (ChessPosition pos : possibleMoves){
            if (pos.getRow() < 9 && pos.getRow() > 0 && pos.getColumn() < 9 && pos.getColumn() > 0){
                ChessPiece obstacle = board.getPiece(pos);
                if (isNull(obstacle)){
                    moves.add(new ChessMove(myPosition, pos, null));
                } else if (obstacle.getTeamColor() != color){
                    moves.add(new ChessMove(myPosition, pos, null));
                    break;
                } else {break;}
            }
        }
        return moves;
    }

    private void kingAndKnightHelper (ChessPosition[] checkMoves, ChessBoard board, HashSet<ChessMove> moves, ChessPosition myPosition) {
        for (ChessPosition pos : checkMoves){
            if (pos.getRow() < 9 && pos.getRow() > 0 && pos.getColumn() < 9 && pos.getColumn() > 0){
                ChessPiece obstacle = board.getPiece(pos);
                if (isNull(obstacle)){
                    moves.add(new ChessMove(myPosition, pos, null));
                } else if (obstacle.color != color){
                    moves.add(new ChessMove(myPosition, pos, null));
                }
            }
        }
    }

    private void pawnHelper (ChessPosition[] promo, ChessPosition pos, HashSet<ChessMove> moves, ChessPosition myPosition) {
        if (Arrays.asList(promo).contains(pos)){
            moves.add(new ChessMove(myPosition, pos, PieceType.QUEEN));
            moves.add(new ChessMove(myPosition, pos, PieceType.ROOK));
            moves.add(new ChessMove(myPosition, pos, PieceType.BISHOP));
            moves.add(new ChessMove(myPosition, pos, PieceType.KNIGHT));
        }else{moves.add(new ChessMove(myPosition, pos, null));}
    }

    private void pawn(ChessBoard board, ChessPosition myPosition, HashSet<ChessMove> moves, int row, int col) {
        ChessPosition[] whiteStart = {new ChessPosition(2, 1), new ChessPosition(2, 2), new ChessPosition(2, 3), new ChessPosition(2, 4), new ChessPosition(2, 5), new ChessPosition(2, 6), new ChessPosition(2, 7), new ChessPosition(2, 8)};
        ChessPosition[] blackStart = {new ChessPosition(7, 1), new ChessPosition(7, 2), new ChessPosition(7, 3), new ChessPosition(7, 4), new ChessPosition(7, 5), new ChessPosition(7, 6), new ChessPosition(7, 7), new ChessPosition(7, 8)};
        ChessPosition[] whitePromo = {new ChessPosition(8, 1), new ChessPosition(8, 2), new ChessPosition(8, 3), new ChessPosition(8, 4), new ChessPosition(8, 5), new ChessPosition(8, 6), new ChessPosition(8, 7), new ChessPosition(8, 8)};
        ChessPosition[] blackPromo = {new ChessPosition(1, 1), new ChessPosition(1, 2), new ChessPosition(1, 3), new ChessPosition(1, 4), new ChessPosition(1, 5), new ChessPosition(1, 6), new ChessPosition(1, 7), new ChessPosition(1, 8)};
        ChessPosition[] whiteDoubleMove = {new ChessPosition(row + 1, col), new ChessPosition(row + 2, col)};
        ChessPosition[] blackDoubleMove = {new ChessPosition(row - 1, col), new ChessPosition(row - 2, col)};
        ChessPosition[] whiteTakes = {new ChessPosition(row + 1, col + 1), new ChessPosition(row + 1, col - 1)};
        ChessPosition[] blackTakes = {new ChessPosition(row - 1, col + 1), new ChessPosition(row - 1, col - 1)};
        if (color == ChessGame.TeamColor.WHITE && Arrays.asList(whiteStart).contains(myPosition)){
            for (ChessPosition pos : whiteDoubleMove){
                ChessPiece obstacle = board.getPiece(pos);
                if (isNull(obstacle)){
                    moves.add(new ChessMove(myPosition, pos, null));
                } else {break;}
            }
        }
        if (color == ChessGame.TeamColor.BLACK && Arrays.asList(blackStart).contains(myPosition)){
            for (ChessPosition pos : blackDoubleMove){
                ChessPiece obstacle = board.getPiece(pos);
                if (isNull(obstacle)){
                    moves.add(new ChessMove(myPosition, pos, null));
                } else {break;}
            }
        }

        if (color == ChessGame.TeamColor.WHITE){
            ChessPosition check = new ChessPosition(row + 1, col);
            if (row + 1 < 9){
                ChessPiece obstacle = board.getPiece(check);
                if (isNull(obstacle)){
                    pawnHelper(whitePromo, check, moves, new ChessPosition(row + 1, col));
                }
                for (ChessPosition pos : whiteTakes){
                    if (pos.getColumn() > 0 && pos.getColumn() < 9){
                        ChessPiece obs = board.getPiece(pos);
                        if (!isNull(obs)){
                            if (obs.color != color){
                                pawnHelper(whitePromo, pos, moves, myPosition);
                            }
                        }
                    }

                }
            }
        }

        if (color == ChessGame.TeamColor.BLACK){
            ChessPosition check = new ChessPosition(row - 1, col);
            if (row - 1 > 0){
                ChessPiece obstacle = board.getPiece(check);
                if (isNull(obstacle)){
                    pawnHelper(blackPromo, check, moves, new ChessPosition(row - 1, col));
                }
                for (ChessPosition pos : blackTakes){
                    if (pos.getColumn() > 0 && pos.getColumn() < 9){
                        ChessPiece obs = board.getPiece(pos);
                        if (!isNull(obs)){
                            if (obs.color != color){
                                pawnHelper(blackPromo, pos, moves, myPosition);
                            }
                        }
                    }
                }
            }
        }
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
        ChessPiece piece = board.getPiece(myPosition);
        PieceType type = piece.getPieceType();
        ChessGame.TeamColor color = piece.getTeamColor();
        HashSet<ChessMove> moves = new HashSet<ChessMove>();
        ChessPosition[] upLeft = {new ChessPosition(row + 1, col - 1), new ChessPosition(row + 2, col - 2), new ChessPosition(row + 3, col - 3), new ChessPosition(row + 4, col - 4), new ChessPosition(row + 5, col - 5), new ChessPosition(row + 6, col - 6), new ChessPosition(row + 7, col - 7), new ChessPosition(row + 8, col - 8)};
        ChessPosition[] upRight = {new ChessPosition(row + 1, col + 1), new ChessPosition(row + 2, col + 2), new ChessPosition(row + 3, col + 3), new ChessPosition(row + 4, col + 4), new ChessPosition(row + 5, col + 5), new ChessPosition(row + 6, col + 6), new ChessPosition(row + 7, col + 7), new ChessPosition(row + 8, col + 8)};
        ChessPosition[] downLeft = {new ChessPosition(row - 1, col - 1), new ChessPosition(row - 2, col - 2), new ChessPosition(row - 3, col - 3), new ChessPosition(row - 4, col - 4), new ChessPosition(row - 5, col - 5), new ChessPosition(row - 6, col - 6), new ChessPosition(row - 7, col - 7), new ChessPosition(row - 8, col - 8)};
        ChessPosition[] downRight = {new ChessPosition(row - 1, col + 1), new ChessPosition(row - 2, col + 2), new ChessPosition(row - 3, col + 3), new ChessPosition(row - 4, col + 4), new ChessPosition(row - 5, col + 5), new ChessPosition(row - 6, col + 6), new ChessPosition(row - 7, col + 7), new ChessPosition(row - 8, col + 8)};
        ChessPosition[] up = {new ChessPosition(row + 1, col), new ChessPosition(row + 2, col), new ChessPosition(row + 3, col), new ChessPosition(row + 4, col), new ChessPosition(row + 5, col), new ChessPosition(row + 6, col), new ChessPosition(row + 7, col), new ChessPosition(row + 8, col)};
        ChessPosition[] right = {new ChessPosition(row, col + 1), new ChessPosition(row, col + 2), new ChessPosition(row, col + 3), new ChessPosition(row, col + 4), new ChessPosition(row, col + 5), new ChessPosition(row, col + 6), new ChessPosition(row, col + 7), new ChessPosition(row, col + 8)};
        ChessPosition[] down = {new ChessPosition(row - 1, col), new ChessPosition(row - 2, col), new ChessPosition(row - 3, col), new ChessPosition(row - 4, col), new ChessPosition(row - 5, col), new ChessPosition(row - 6, col), new ChessPosition(row - 7, col), new ChessPosition(row - 8, col)};
        ChessPosition[] left = {new ChessPosition(row, col - 1), new ChessPosition(row, col - 2), new ChessPosition(row, col - 3), new ChessPosition(row, col - 4), new ChessPosition(row, col - 5), new ChessPosition(row, col - 6), new ChessPosition(row, col - 7), new ChessPosition(row, col - 8)};
        switch(type){
            case KING -> {
                ChessPosition[] checkMoves = {new ChessPosition(row + 1, col - 1),new ChessPosition(row + 1, col), new ChessPosition(row + 1, col + 1), new ChessPosition(row, col + 1), new ChessPosition(row - 1, col + 1), new ChessPosition(row - 1, col), new ChessPosition(row - 1, col - 1), new ChessPosition(row, col - 1)};
                kingAndKnightHelper(checkMoves, board, moves, myPosition);
            }
            case BISHOP -> {
                moves.addAll(continuousMoveChecker(upLeft, board, myPosition));
                moves.addAll(continuousMoveChecker(upRight, board, myPosition));
                moves.addAll(continuousMoveChecker(downLeft, board, myPosition));
                moves.addAll(continuousMoveChecker(downRight, board, myPosition));
            }
            case QUEEN -> {
                moves.addAll(continuousMoveChecker(upLeft, board, myPosition));
                moves.addAll(continuousMoveChecker(upRight, board, myPosition));
                moves.addAll(continuousMoveChecker(downLeft, board, myPosition));
                moves.addAll(continuousMoveChecker(downRight, board, myPosition));
                moves.addAll(continuousMoveChecker(up, board, myPosition));
                moves.addAll(continuousMoveChecker(right, board, myPosition));
                moves.addAll(continuousMoveChecker(down, board, myPosition));
                moves.addAll(continuousMoveChecker(left, board, myPosition));
            }
            case ROOK -> {
                moves.addAll(continuousMoveChecker(up, board, myPosition));
                moves.addAll(continuousMoveChecker(right, board, myPosition));
                moves.addAll(continuousMoveChecker(down, board, myPosition));
                moves.addAll(continuousMoveChecker(left, board, myPosition));
            }
            case KNIGHT -> {
                ChessPosition[] checkMoves = {new ChessPosition(row + 2, col + 1),new ChessPosition(row + 1, col + 2), new ChessPosition(row - 1, col + 2), new ChessPosition(row - 2, col + 1), new ChessPosition(row - 2, col - 1), new ChessPosition(row - 1, col - 2), new ChessPosition(row + 1, col - 2), new ChessPosition(row + 2, col - 1)};
                kingAndKnightHelper(checkMoves, board, moves, myPosition);
            }
            case PAWN -> {
                pawn(board, myPosition, moves, row, col);
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

    @Override
    public String toString() {
        return type.name();
    }
}
