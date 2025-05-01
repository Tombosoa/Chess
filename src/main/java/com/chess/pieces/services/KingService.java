package com.chess.pieces.services;

import com.chess.board.ChessBoard;
import com.chess.cases.Case;
import com.chess.enums.AlphabeticalReference;
import com.chess.enums.Color;
import com.chess.enums.NumericalReference;
import com.chess.pieces.King;
import com.chess.pieces.Piece;
import com.chess.pieces.Position;

import java.util.ArrayList;
import java.util.List;

public class KingService {
    public List<Piece> getKings(ChessBoard board){
        List<Piece> kings = new ArrayList<>();

        Case wKingCase = new Case(NumericalReference.ONE, AlphabeticalReference.e);
        Position wKingPosition = new Position(wKingCase);
        King wKing = new King(Color.white, wKingPosition, board);
        kings.add(wKing);

        Case bKingCase = new Case(NumericalReference.EIGHT, AlphabeticalReference.e);
        Position bKingPosition = new Position(bKingCase);
        King bKing = new King(Color.black, bKingPosition, board);
        kings.add(bKing);

        return kings;
    }
}
