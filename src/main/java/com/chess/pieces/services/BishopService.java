package com.chess.pieces.services;

import com.chess.board.ChessBoard;
import com.chess.cases.Case;
import com.chess.enums.AlphabeticalReference;
import com.chess.enums.Color;
import com.chess.enums.NumericalReference;
import com.chess.pieces.Bishop;
import com.chess.pieces.Piece;
import com.chess.pieces.Position;

import java.util.ArrayList;
import java.util.List;

public class BishopService {
    public List<Piece> getBishops(ChessBoard board){
        List<Piece> bishopList = new ArrayList<>();

        Case whiteLeftBishop = new Case(NumericalReference.ONE, AlphabeticalReference.c);
        Position whiteLeftBishopPosition = new Position(whiteLeftBishop);
        Bishop whiteLBishop = new Bishop(Color.white, whiteLeftBishopPosition, board);
        bishopList.add(whiteLBishop);

        Case whiteRightBishop = new Case(NumericalReference.ONE, AlphabeticalReference.f);
        Position whiteRightBishopPosition = new Position(whiteRightBishop);
        Bishop whiteRBishop = new Bishop(Color.white, whiteRightBishopPosition, board);
        bishopList.add(whiteRBishop);

        Case blackLeftBishop = new Case(NumericalReference.EIGHT, AlphabeticalReference.c);
        Position blackLeftBishopPosition = new Position(blackLeftBishop);
        Bishop blackLBishop = new Bishop(Color.black, blackLeftBishopPosition, board);
        bishopList.add(blackLBishop);

        Case blackRightBishop = new Case(NumericalReference.EIGHT, AlphabeticalReference.f);
        Position blackRightBishopPosition = new Position(blackRightBishop);
        Bishop blackRBishop = new Bishop(Color.black, blackRightBishopPosition, board);
        bishopList.add(blackRBishop);

        return bishopList;
    }
}
