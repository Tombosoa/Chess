package com.chess.pieces.services;

import com.chess.cases.Case;
import com.chess.enums.AlphabeticalReference;
import com.chess.enums.Color;
import com.chess.enums.NumericalReference;
import com.chess.pieces.Piece;
import com.chess.pieces.Position;
import com.chess.pieces.Queen;

import java.util.ArrayList;
import java.util.List;

public class QueenService {
    public List<Piece> getQueens(){
        List<Piece> queens = new ArrayList<>();

        Case wQueenCase = new Case(NumericalReference.ONE, AlphabeticalReference.d);
        Position wQueenPosition = new Position(wQueenCase);
        Queen wQueen = new Queen(Color.white, wQueenPosition);
        queens.add(wQueen);

        Case bQueenCase = new Case(NumericalReference.EIGHT, AlphabeticalReference.d);
        Position bQueenPosition = new Position(bQueenCase);
        Queen bQueen = new Queen(Color.black, bQueenPosition);
        queens.add(bQueen);

        return queens;
    }
}
