package com.chess.pieces;

import com.chess.board.ChessBoard;
import com.chess.cases.Case;
import com.chess.enums.AlphabeticalReference;
import com.chess.enums.Color;
import com.chess.enums.NumericalReference;
import com.chess.enums.PieceName;
import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece{
    public Queen( Color color,  Position position, ChessBoard board) {
        super(PieceName.queen, color, 4, position, color.equals(Color.black) ? "blackQueen.png" : "whiteQueen.png", board);
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
        List<Case> possibleMoves = getCases(currentCase);
        possibleMoves.remove(currentCase);

        possiblesMoves.addAll(possibleMoves);

        return possiblesMoves;
    }
    private static List<Case> getCases(Case currentCase) {
        List<Case> possibleMoves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Case toAddCol = new Case(NumericalReference.values()[i], currentCase.getAlphabeticalReference());
            Case toAddRow = new Case(currentCase.getNumericalReference(), AlphabeticalReference.values()[i]);
            if(!toAddCol.isBusy() && toAddCol.isValid() && toAddRow.isValid() && !toAddRow.isBusy()){
                possibleMoves.add(toAddCol);
                possibleMoves.add(toAddRow);
            }
        }
        possibleMoves.remove(currentCase);

        return possibleMoves;
    }
}

