package com.chess.pieces;

import com.chess.board.ChessBoard;
import com.chess.cases.Case;
import com.chess.enums.AlphabeticalReference;
import com.chess.enums.Color;
import com.chess.enums.NumericalReference;
import com.chess.enums.PieceName;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class Rook extends Piece{
    public Rook(Color color, Position position) {
        super(PieceName.rook, color, 2, position, color.equals(Color.black) ? "blackRook.png" : "whiteRook.png");
    }

    @Override
    public void move(ChessBoard chessBoard) {

    }

    @Override
    void attack(ChessBoard chessBoard) {

    }

    @Override
    public List<Case> getPossibleMove(Case currentCase) {
        List<Case> possibleMoves = getCases(currentCase);
      /*  if(this.getColor() == Color.white){
            for (int i = currentCase.getNumericalReference().getValue(); i < 8 ; i++) {
                Case toAdd = new Case(NumericalReference.values()[i], currentCase.getAlphabeticalReference());
                if(!toAdd.isBusy() && toAdd.isValid()){
                    possibleMoves.add(toAdd);
                }
            }
        }else {
            for (int i =  currentCase.getNumericalReference().getValue() - 2; i >= 0 ; i--) {
                Case toAdd = new Case(NumericalReference.values()[i], currentCase.getAlphabeticalReference());
                possibleMoves.add(toAdd);
            }
        }*/
        //System.out.println(possibleMoves);
        possibleMoves.remove(currentCase);

        return possibleMoves;
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
