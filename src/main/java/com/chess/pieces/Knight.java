package com.chess.pieces;

import com.chess.board.ChessBoard;
import com.chess.cases.Case;
import com.chess.enums.AlphabeticalReference;
import com.chess.enums.Color;
import com.chess.enums.NumericalReference;
import com.chess.enums.PieceName;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{
    public Knight( Color color, Position position, ChessBoard board) {
        super(PieceName.knight, color, 2, position, color.equals(Color.black) ? "blackKnight.png" : "whiteKnight.png", board);
    }

    @Override
    public void move(ChessBoard chessBoard) {

    }

    @Override
    void attack(ChessBoard chessBoard) {

    }

    @Override
    public List<Case> getPossibleMove(Case currentCase) {
        List<Case> possiblesMoves = new ArrayList<>();

        int actualNumericalRef = currentCase.getNumericalReference().getValue();
        int actualAlphabeticalRef = currentCase.getAlphabeticalReference().ordinal();

        if (actualNumericalRef + 1 < 8 && actualAlphabeticalRef + 1 < 8){
            Case firstForward = new Case(NumericalReference.values()[actualNumericalRef + 1], AlphabeticalReference.values()[actualAlphabeticalRef + 1]);
            possiblesMoves.add(firstForward);
        }
        if (actualNumericalRef + 1 < 8 && actualAlphabeticalRef - 1 >= 0){
            Case secondForward = new Case(NumericalReference.values()[actualNumericalRef + 1], AlphabeticalReference.values()[actualAlphabeticalRef - 1]);
            possiblesMoves.add(secondForward);
        }
        if (actualNumericalRef - 3 >= 0 && actualAlphabeticalRef + 1 < 8){
            Case firstBackward = new Case(NumericalReference.values()[actualNumericalRef - 3], AlphabeticalReference.values()[actualAlphabeticalRef + 1]);
            possiblesMoves.add(firstBackward);
        }
        if (actualNumericalRef - 3 >= 0 && actualAlphabeticalRef - 1 >= 0){
            Case secondBackward = new Case(NumericalReference.values()[actualNumericalRef - 3], AlphabeticalReference.values()[actualAlphabeticalRef - 1]);
            possiblesMoves.add(secondBackward);
        }
        if (actualAlphabeticalRef + 2 < 8 && actualNumericalRef < 8){
            Case firstRight = new Case(NumericalReference.values()[actualNumericalRef], AlphabeticalReference.values()[actualAlphabeticalRef + 2]);
            possiblesMoves.add(firstRight);

        }
        if (actualNumericalRef - 2 >= 0 && actualAlphabeticalRef + 2 < 8){
            Case secondRight = new Case(NumericalReference.values()[actualNumericalRef - 2], AlphabeticalReference.values()[actualAlphabeticalRef + 2]);
            possiblesMoves.add(secondRight);
        }
        if (actualAlphabeticalRef - 2 >= 0 && actualNumericalRef < 8){
            Case firstLeft = new Case(NumericalReference.values()[actualNumericalRef], AlphabeticalReference.values()[actualAlphabeticalRef - 2]);
            possiblesMoves.add(firstLeft);
        }
        if (actualNumericalRef - 2 >= 0 && actualAlphabeticalRef - 2 >= 0){
            Case secondLeft = new Case(NumericalReference.values()[actualNumericalRef - 2], AlphabeticalReference.values()[actualAlphabeticalRef - 2]);
            possiblesMoves.add(secondLeft);
        }

        return possiblesMoves;
    }
}
