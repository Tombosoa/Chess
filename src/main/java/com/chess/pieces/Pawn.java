package com.chess.pieces;

import com.chess.board.ChessBoard;
import com.chess.cases.Case;
import com.chess.enums.Color;
import com.chess.enums.NumericalReference;
import com.chess.enums.PieceName;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
public class Pawn extends Piece{
    private List<Case> positions;

    public Pawn(Color color, Position position) {
        super(PieceName.pawn, color, 1, position, color.equals(Color.black) ? "blackPawn.png" : "whitePawn.png");
    }

    @Override
    public void move(ChessBoard chessBoard) {
        List<Case> possibleMoves = new ArrayList<>();
        if (this.getColor() == Color.white) {
            Case forward = new Case(NumericalReference.increment(this.getPosition().getCurrentPosition().getNumericalReference()), this.getPosition().getCurrentPosition().getAlphabeticalReference());
            if (forward.isValid() && !forward.isBusy()) {
                possibleMoves.add(forward);
                positions.add(forward);
            }
        } else {
            Case backward = new Case(NumericalReference.decrement(this.getPosition().getCurrentPosition().getNumericalReference()), this.getPosition().getCurrentPosition().getAlphabeticalReference());
            if (backward.isValid() && !backward.isBusy()) {
                possibleMoves.add(backward);
                positions.add(backward);
            }
        }

        if (!possibleMoves.isEmpty()) {
            this.getPosition().setCurrentPosition(possibleMoves.getFirst());
        }
    }

    @Override
    public List<Case> getPossibleMove(Case currentCase){
        List<Case> possibleMoves = new ArrayList<>();
        if (this.getColor() == Color.white) {
           if (currentCase.getNumericalReference().getValue() < 4){
               Case forward = new Case(NumericalReference.increment(currentCase.getNumericalReference()), currentCase.getAlphabeticalReference());
               Case secondForward = new Case(NumericalReference.increment(forward.getNumericalReference()), currentCase.getAlphabeticalReference());

              // forward.setColor(Color.inverse(currentCase.getColor()));
               if (forward.isValid() && !forward.isBusy() && secondForward.isValid() && !secondForward.isBusy()) {
                   possibleMoves.add(forward);
                   possibleMoves.add(secondForward);
               }
           }else {
               System.out.println("no way");
           }
        } else {
            if(currentCase.getNumericalReference().getValue() > 5){
                Case backward = new Case(NumericalReference.decrement(currentCase.getNumericalReference()), currentCase.getAlphabeticalReference());
                Case secondBackward = new Case(NumericalReference.decrement(backward.getNumericalReference()), currentCase.getAlphabeticalReference());

                //backward.setColor(Color.inverse(currentCase.getColor()));
                if (backward.isValid() && !backward.isBusy() && secondBackward.isValid() && !secondBackward.isBusy()) {
                    possibleMoves.add(backward);
                    possibleMoves.add(secondBackward);
                }
            }else {
                System.out.println("no way");
            }
        }

       /* if (!possibleMoves.isEmpty()) {
            this.getPosition().setCurrentPosition(possibleMoves.getFirst());
        }*/
        System.out.println(possibleMoves);
        return possibleMoves;
    }

    @Override
    protected void attack(ChessBoard chessBoard) {
        List<Case> possibleAttackPositions = new ArrayList<>();
        if (this.getColor() == Color.white) {
            Case leftCapture = new Case(NumericalReference.decrement(this.getPosition().getCurrentPosition().getNumericalReference()), this.getPosition().getCurrentPosition().getAlphabeticalReference().left());
            if (leftCapture.isValid() && leftCapture.isBusy()) {
                possibleAttackPositions.add(leftCapture);
            }

            Case rightCapture = new Case(NumericalReference.increment(this.getPosition().getCurrentPosition().getNumericalReference()), this.getPosition().getCurrentPosition().getAlphabeticalReference().right());
            if (rightCapture.isValid() && rightCapture.isBusy()) {
                possibleAttackPositions.add(rightCapture);
            }
        } else {
            Case leftCapture = new Case(NumericalReference.increment(this.getPosition().getCurrentPosition().getNumericalReference()), this.getPosition().getCurrentPosition().getAlphabeticalReference().left());
            if (leftCapture.isValid() && leftCapture.isBusy()) {
                possibleAttackPositions.add(leftCapture);
            }

            Case rightCapture = new Case(NumericalReference.decrement(this.getPosition().getCurrentPosition().getNumericalReference()), this.getPosition().getCurrentPosition().getAlphabeticalReference().right());
            if (rightCapture.isValid() && rightCapture.isBusy()) {
                possibleAttackPositions.add(rightCapture);
            }
        }

        if (!possibleAttackPositions.isEmpty()) {
            this.getPosition().setCurrentPosition(possibleAttackPositions.getFirst());
        }
        //System.out.println(possibleAttackPositions);
    }
}
