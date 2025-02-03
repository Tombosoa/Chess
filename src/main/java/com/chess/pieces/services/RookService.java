package com.chess.pieces.services;

import com.chess.cases.Case;
import com.chess.enums.AlphabeticalReference;
import com.chess.enums.Color;
import com.chess.enums.NumericalReference;
import com.chess.pieces.Piece;
import com.chess.pieces.Position;
import com.chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;

public class RookService {
    public List<Piece> getRooks(){
        List<Piece> rooks = new ArrayList<>();

        Case wRookLCase = new Case(NumericalReference.ONE, AlphabeticalReference.a);
        Position wRookLPosition = new Position(wRookLCase);
        Rook wRookL = new Rook(Color.white, wRookLPosition);
        rooks.add(wRookL);

        Case wRookRCase = new Case(NumericalReference.ONE, AlphabeticalReference.h);
        Position wRookRPosition = new Position(wRookRCase);
        Rook wRookR = new Rook(Color.white, wRookRPosition);
        rooks.add(wRookR);

        Case bRookLCase = new Case(NumericalReference.EIGHT, AlphabeticalReference.a);
        Position bRookLPosition = new Position(bRookLCase);
        Rook bRookL = new Rook(Color.black, bRookLPosition);
        rooks.add(bRookL);

        Case bRookRCase = new Case(NumericalReference.EIGHT, AlphabeticalReference.h);
        Position bRookRPosition = new Position(bRookRCase);
        Rook bRookR = new Rook(Color.black, bRookRPosition);
        rooks.add(bRookR);

        return rooks;
    }
}
