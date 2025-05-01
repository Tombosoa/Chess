package com.chess;

import com.chess.board.ChessBoard;
import com.chess.pieces.*;
import com.chess.pieces.services.*;

import java.util.List;

public class Chess {
    private final ChessBoard board;
    BishopService bishopService = new BishopService();
    RookService rookService = new RookService();
    PawnService pawnService = new PawnService();
    KnightService knightService = new KnightService();
    KingService kingService = new KingService();
    QueenService queenService = new QueenService();
    public Chess() {
        board = new ChessBoard();
        initializeBoard();
    }

    public void setPieceOnBoard(Piece piece){
        board.setPiece( piece.getPosition().getCurrentPosition().getNumericalReference().getValue() - 1 , piece.getPosition().getCurrentPosition().getAlphabeticalReference().ordinal(), piece);
    }

    public void setMultiplePiece(List<Piece> pieces){
        for (Piece piece : pieces){
            setPieceOnBoard(piece);
        }
    }

    public ChessBoard initializeBoard() {
        List<Piece> pawns = pawnService.getPawns(board);
        List<Piece> bishops = bishopService.getBishops(board);
        List<Piece> rooks = rookService.getRooks(board);
        List<Piece> knights = knightService.getKnights(board);
        List<Piece> kings = kingService.getKings(board);
        List<Piece> queens = queenService.getQueens(board);

       // setPieceOnBoard(rook, board);
       //Test for Knight: setPieceOnBoard(brook);

        setMultiplePiece(bishops);
        setMultiplePiece(rooks);
        setMultiplePiece(pawns);
        setMultiplePiece(knights);
        setMultiplePiece(kings);
        setMultiplePiece(queens);

        return board;
    }

    public ChessBoard getChessBoard(){
        return board;
    }

    public static void main(String[] args) {
        Chess game = new Chess();
        System.out.println(game.initializeBoard().getCase(4,5));
    }
}
