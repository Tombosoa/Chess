package com.chess.pieces;

import com.chess.cases.Case;
import com.chess.enums.AlphabeticalReference;
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
    public Pawn(Color color, Position position) {
        super(PieceName.pawn, color, 1, position);
    }

    @Override
    protected void move() {
        List<Case> possibleMoves = new ArrayList<>();
        if (this.getColor() == Color.white) {
            Case forward = new Case(NumericalReference.increment(this.getPosition().getCurrentPosition().numericalReference()), this.getPosition().getCurrentPosition().alphabeticalReference());
            possibleMoves.add(forward);
        } else {
            Case backward = new Case(this.getPosition().getCurrentPosition().numericalReference().decrement(this.getPosition().getCurrentPosition().numericalReference()), this.getPosition().getCurrentPosition().alphabeticalReference());
            possibleMoves.add(backward);
        }
        System.out.println(possibleMoves);
    }

    @Override
    protected void attack() {
        List<Case> possibleAttackPositions = new ArrayList<>();
        if (this.getColor() == Color.white) {
            Case leftCapture = new Case(this.getPosition().getCurrentPosition().numericalReference().increment(this.getPosition().getCurrentPosition().numericalReference()), this.getPosition().getCurrentPosition().alphabeticalReference().left());
            if (leftCapture.isValid()) {
                possibleAttackPositions.add(leftCapture);
            }

            Case rightCapture = new Case(this.getPosition().getCurrentPosition().numericalReference().increment(this.getPosition().getCurrentPosition().numericalReference()), this.getPosition().getCurrentPosition().alphabeticalReference().right());
            if (rightCapture.isValid()) {
                possibleAttackPositions.add(rightCapture);
            }
        } else {
            Case leftCapture = new Case(this.getPosition().getCurrentPosition().numericalReference().decrement(this.getPosition().getCurrentPosition().numericalReference()), this.getPosition().getCurrentPosition().alphabeticalReference().left());
            if (leftCapture.isValid()) {
                possibleAttackPositions.add(leftCapture);
            }

            Case rightCapture = new Case(this.getPosition().getCurrentPosition().numericalReference().decrement(this.getPosition().getCurrentPosition().numericalReference()), this.getPosition().getCurrentPosition().alphabeticalReference().right());
            if (rightCapture.isValid()) {
                possibleAttackPositions.add(rightCapture);
            }
        }
        System.out.println(possibleAttackPositions);
    }

    public static void main(String[] args) {
        Case cases = new Case(NumericalReference.TWO, AlphabeticalReference.e);
        Position position = new Position(cases);
        Pawn pion = new Pawn(Color.white, position);
         pion.move();
        // pion.attack();
        System.out.println(pion.getPosition().getCurrentPosition());
    }
}
