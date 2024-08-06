package com.chess;

import com.chess.board.ChessBoard;
import com.chess.cases.Case;
import com.chess.enums.AlphabeticalReference;
import com.chess.enums.Color;
import com.chess.enums.NumericalReference;
import com.chess.pieces.Pawn;
import com.chess.pieces.Position;
import com.chess.ui.ChessBoardGUI;

import java.util.List;
import java.util.*;

public class Chess {
    private final ChessBoard board;
    private final Case[][] boards;

    public Chess() {
        board = new ChessBoard();
        boards = new Case[8][8];
        initializeBoard();
    }

    public ChessBoard initializeBoard() {
        List<Pawn> whitePawns = new ArrayList<>();
        List<Pawn> blackPawns = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Case wCase = new Case(NumericalReference.EIGHT, AlphabeticalReference.values()[i]);

            Position wPosition = new Position(wCase);
            Pawn whitePawn = new Pawn(Color.white, wPosition);

            Case bCase = new Case(NumericalReference.ONE, AlphabeticalReference.values()[i]);

            Position bPosition = new Position(bCase);
            Pawn blackPawn = new Pawn(Color.black, bPosition);

            whitePawns.add(whitePawn);
            blackPawns.add(blackPawn);

            board.setPiece(1, i, whitePawn);
            board.setPiece(6, i, blackPawn);
        }
        return board;
    }

    public void displayBoard() {
        board.printBoard();
    }

    public Case getItCase(int row, int column) {
        return boards[row][column];
    }

    public ChessBoard getChessBoard(){
        return board;
    }

    public static void main(String[] args) {
        Chess game = new Chess();
       // ChessBoardGUI.draw();
        //game.displayBoard();
        System.out.println(game.initializeBoard().getCase(1,2));
    }
}
