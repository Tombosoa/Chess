package com.chess.pieces.services;

import com.chess.board.ChessBoard;
import com.chess.cases.Case;
import com.chess.enums.AlphabeticalReference;
import com.chess.enums.Color;
import com.chess.enums.NumericalReference;
import com.chess.pieces.Pawn;
import com.chess.pieces.Piece;
import com.chess.pieces.Position;
import java.util.ArrayList;
import java.util.List;

public class PawnService {
    public List<Piece> getPawns(ChessBoard board) {
        List<Piece> pawns = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Case wCase = new Case(NumericalReference.TWO, AlphabeticalReference.values()[i]);
            wCase.setColor(Color.white);

            Position wPosition = new Position(wCase);
            Pawn whitePawn = new Pawn(Color.white, wPosition, board);

            Case bCase = new Case(NumericalReference.SEVEN, AlphabeticalReference.values()[i]);
            bCase.setColor(Color.black);

            Position bPosition = new Position(bCase);
            Pawn blackPawn = new Pawn(Color.black, bPosition, board);

            pawns.add(whitePawn);
            pawns.add(blackPawn);
        }
        return pawns;
    }
}
