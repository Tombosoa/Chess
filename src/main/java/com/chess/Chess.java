package com.chess;

import com.chess.board.ChessBoard;
import com.chess.cases.Case;
import com.chess.enums.AlphabeticalReference;
import com.chess.enums.Color;
import com.chess.enums.NumericalReference;
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
        List<Piece> pawns = pawnService.getPawns();
        List<Piece> bishops = bishopService.getBishops();
        List<Piece> rooks = rookService.getRooks();
        List<Piece> knights = knightService.getKnights();
        List<Piece> kings = kingService.getKings();
        List<Piece> queens = queenService.getQueens();

        Case cases = new Case(NumericalReference.FOUR, AlphabeticalReference.e);
        Position position = new Position(cases);
        Piece rook = new Knight(Color.white, position);

        Case bcases = new Case(NumericalReference.FIVE, AlphabeticalReference.f);
        Position bposition = new Position(bcases);
        Piece brook = new Knight(Color.black, bposition);

       // setPieceOnBoard(rook, board);
        setPieceOnBoard(brook);

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
        System.out.println(game.initializeBoard().getCase(1,2));
    }
}
