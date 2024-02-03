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
    TeamColor turn;
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
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
//        ChessPosition start = move.getStartPosition();
//        ChessPosition end = move.getEndPosition();
//        ChessPiece.PieceType promo = move.getPromotionPiece();
//        this.board.
        
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
        throw new RuntimeException("Not implemented");
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



}
