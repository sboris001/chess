package chess;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

import static java.util.Objects.isNull;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    ChessBoard board = new ChessBoard();
    TeamColor turn = TeamColor.WHITE;
    String status;
    public String getStatus() {
        return status;
    }
    public void setStatusInactive() {
        status = "Inactive";
    }
    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.turn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = this.board.getPiece(startPosition);
        ChessBoard original = this.board.deepCopy();
        HashSet<ChessMove> valid = new HashSet<>();
        if (isNull(piece)) {
            return null;
        }
        Collection<ChessMove> allMoves = piece.pieceMoves(this.board, startPosition);
        for (ChessMove move : allMoves){
            this.board.addPiece(startPosition, null);
            this.board.addPiece(move.getEndPosition(), piece);
            if (!isInCheck(piece.getTeamColor())){
                valid.add(move);
                this.board = original.deepCopy();
            } else {this.board = original.deepCopy();}
        }
        return valid;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition start = move.getStartPosition();
        ChessPosition end = move.getEndPosition();
        ChessPiece.PieceType promo = move.getPromotionPiece();

        if (start.getRow() < 1 || start.getRow() > 8 || start.getColumn() < 1 || start.getColumn() > 8 || end.getColumn() < 1 || end.getColumn() > 8 || end.getRow() < 1 || end.getRow() > 8){
            throw new InvalidMoveException("Out of bounds");
        }
        ChessPiece piece = this.board.getPiece(start);
        if (piece.getTeamColor() != turn){
            throw new InvalidMoveException("Not your turn");
        }
        if (validMoves(start).contains(move)){
            this.board.addPiece(start, null);
            if (isNull(promo)){
                this.board.addPiece(end, piece);
                if (this.getTeamTurn() == TeamColor.WHITE){
                    setTeamTurn(TeamColor.BLACK);
                } else {
                    setTeamTurn(TeamColor.WHITE);
                }
            } else {
                this.board.addPiece(end, new ChessPiece(piece.getTeamColor(), promo));
                if (this.getTeamTurn() == TeamColor.WHITE){
                    setTeamTurn(TeamColor.BLACK);
                } else {
                    setTeamTurn(TeamColor.WHITE);
                }
            }
        } else {
            throw new InvalidMoveException("Not a valid move");
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        for (int i = 1; i < 9; i++){
            for (int j = 1; j < 9; j++){
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = this.board.getPiece(position);
                if (!isNull(piece)){
                    if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor){
                        for (int k = 1; k < 9; k++){
                            for (int l = 1; l < 9; l++){
                                ChessPosition pos = new ChessPosition(k, l);
                                ChessPiece enemy = this.board.getPiece(pos);
                                if (!isNull(enemy)){
                                    if (enemy.getTeamColor() != teamColor){
                                        Collection<ChessMove> checkMoves = enemy.pieceMoves(this.board, pos);
                                        for (ChessMove move : checkMoves){
                                            ChessPosition check = move.getEndPosition();
                                            if (check.equals(position)){
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        int counter = 0;
        ChessBoard original = this.board.deepCopy();
        if (isInCheck(teamColor)){
            for (int i = 1; i < 9; i++) {
                for (int j = 1; j < 9; j ++) {
                    ChessPosition pos = new ChessPosition(i, j);
                    ChessPiece piece = this.board.getPiece(pos);
                    if (!isNull(piece)){
                        if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor){
                            Collection<ChessMove> kingMoves = piece.pieceMoves(this.board, pos);
                            for (ChessMove move : kingMoves){
                                ChessPosition newPos = move.getEndPosition();
                                this.board.board[i-1][j-1] = null;
                                this.board.addPiece(newPos, new ChessPiece(teamColor, ChessPiece.PieceType.KING));
                                if (!isInCheck(teamColor)){
                                    return false;
                                } else {
                                    this.board = original.deepCopy();
                                }
                            }
                        }
                    }
                }
            }
        } else {return false;}
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (!isInCheck(teamColor)){
            for (int i = 1; i < 9; i++) {
                for (int j = 1; j < 9; j++) {
                    ChessPosition position = new ChessPosition(i, j);
                    ChessPiece piece = this.board.getPiece(position);
                    if (!isNull(piece)) {
                        if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor){
                            if (validMoves(position).isEmpty()){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(board, chessGame.board) && turn == chessGame.turn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, turn);
    }
}
