package com.chess.pieces.services;

import com.chess.cases.Case;
import com.chess.enums.AlphabeticalReference;
import com.chess.enums.Color;
import com.chess.enums.NumericalReference;
import com.chess.pieces.Knight;
import com.chess.pieces.Piece;
import com.chess.pieces.Position;

import java.util.ArrayList;
import java.util.List;

public class KnightService {
    public List<Piece> getKnights(){
        List<Piece> knights = new ArrayList<>();

        Case wKnightLCase = new Case(NumericalReference.ONE, AlphabeticalReference.b);
        Position wKnightLPosition = new Position(wKnightLCase);
        Knight wKnightL = new Knight(Color.white, wKnightLPosition);
        knights.add(wKnightL);

        Case wKnightRCase = new Case(NumericalReference.ONE, AlphabeticalReference.g);
        Position wKnightRPosition = new Position(wKnightRCase);
        Knight wKnightR = new Knight(Color.white, wKnightRPosition);
        knights.add(wKnightR);

        Case bKnightLCase = new Case(NumericalReference.EIGHT, AlphabeticalReference.b);
        Position bKnightLPosition = new Position(bKnightLCase);
        Knight bKnightL = new Knight(Color.black, bKnightLPosition);
        knights.add(bKnightL);

        Case bKnightRCase = new Case(NumericalReference.EIGHT, AlphabeticalReference.g);
        Position bKnightRPosition = new Position(bKnightRCase);
        Knight bKnightR = new Knight(Color.black, bKnightRPosition);
        knights.add(bKnightR);

        return knights;
    }
}
