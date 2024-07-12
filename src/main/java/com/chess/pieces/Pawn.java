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

@ToString
@Getter
public class Pawn extends Piece{
    public Pawn(Color color, Position position) {
        super(PieceName.pawn, color, 1, position);
    }

    @Override
    protected void move(ChessBoard chessBoard) {
        List<Case> possibleMoves = new ArrayList<>();
        if (this.getColor() == Color.white) {
            Case forward = new Case(NumericalReference.increment(this.getPosition().getCurrentPosition().getNumericalReference()), this.getPosition().getCurrentPosition().getAlphabeticalReference());
            if (forward.isValid() && !forward.isBusy()) {
                possibleMoves.add(forward);
            }
        } else {
            Case backward = new Case(NumericalReference.decrement(this.getPosition().getCurrentPosition().getNumericalReference()), this.getPosition().getCurrentPosition().getAlphabeticalReference());
            if (backward.isValid() && !backward.isBusy()) {
                possibleMoves.add(backward);
            }
        }

        if (!possibleMoves.isEmpty()) {
            this.getPosition().setCurrentPosition(possibleMoves.getFirst());
        }

        System.out.println(possibleMoves);
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

        System.out.println(possibleAttackPositions);
    }

    public static void main(String[] args) {
        ChessBoard bd = new ChessBoard();
        Case cases = new Case(NumericalReference.TWO, AlphabeticalReference.e);
        Case cases1 = new Case(NumericalReference.TWO, AlphabeticalReference.f);

        Position position = new Position(cases);
        // Position position1 = new Position(cases1);

        Pawn pion = new Pawn(Color.white, position);
       // Pawn pion1 = new Pawn(Color.white, position1);

         pion.move(bd);
        // pion.attack();
        pion.move(bd);
        pion.move(bd);
        pion.move(bd);
    }
}
