package com.chess.pieces;

import com.chess.board.ChessBoard;
import com.chess.cases.Case;
import com.chess.enums.AlphabeticalReference;
import com.chess.enums.Color;
import com.chess.enums.NumericalReference;
import com.chess.enums.PieceName;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece{
    public Bishop(Color color, Position position) {
        super(PieceName.bishop, color, 2, position, color.equals(Color.black) ? "blackBishop.png" : "whiteBishop.png");
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

        int col = currentCase.getNumericalReference().getValue()-1;
        int row = currentCase.getAlphabeticalReference().ordinal();

        for (int i = 1; i < 8; i++) {
            if(col + i < 8  && row + i < 8){
                Case toAdd = new Case(NumericalReference.values()[col+i], AlphabeticalReference.values()[row+i]);
                possiblesMoves.add(toAdd);
            }
            if(col - i >= 0  && row - i >= 0){
                Case toAdd = new Case(NumericalReference.values()[col-i], AlphabeticalReference.values()[row-i]);
                possiblesMoves.add(toAdd);
            }
            if(col + i < 8  && row - i >= 0){
                Case toAdd = new Case(NumericalReference.values()[col+i], AlphabeticalReference.values()[row-i]);
                possiblesMoves.add(toAdd);
            }
            if(col - i >= 0  && row + i < 8){
                Case toAdd = new Case(NumericalReference.values()[col-i], AlphabeticalReference.values()[row+i]);
                possiblesMoves.add(toAdd);
            }
        }
        return  possiblesMoves;
    }
}
