package com.chess.pieces;

import com.chess.Chess;
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
public class Pawn extends Piece {
    private List<Case> positions;
    private final ChessBoard board;

    //private final ChessBoard cb = new Chess().getChessBoard();

    public Pawn(Color color, Position position) {
        super(PieceName.pawn, color, 1, position, color.equals(Color.black) ? "blackPawn.png" : "whitePawn.png");
        this.positions = new ArrayList<>();
        this.board = new ChessBoard();
    }

    private boolean hasMoved() {
        return !this.getHistoric().isEmpty();
    }

    @Override
    public void move(ChessBoard chessBoard) {
        List<Case> possibleMoves = new ArrayList<>();
        Case forward = null;

        if (this.getColor() == Color.white) {
            forward = new Case(NumericalReference.increment(this.getPosition().getCurrentPosition().getNumericalReference()), this.getPosition().getCurrentPosition().getAlphabeticalReference());
            if (forward.isValid() && !forward.isBusy()) {
                possibleMoves.add(forward);
                positions.add(forward);
            }
        } else {
            forward = new Case(NumericalReference.decrement(this.getPosition().getCurrentPosition().getNumericalReference()), this.getPosition().getCurrentPosition().getAlphabeticalReference());
            if (forward.isValid() && !forward.isBusy()) {
                possibleMoves.add(forward);
                positions.add(forward);
            }
        }

        if (!hasMoved()) {
            Case secondForward = this.getColor() == Color.white
                    ? new Case(NumericalReference.increment(forward.getNumericalReference()), forward.getAlphabeticalReference())
                    : new Case(NumericalReference.decrement(forward.getNumericalReference()), forward.getAlphabeticalReference());

            if (secondForward.isValid() && !secondForward.isBusy()) {
                possibleMoves.add(secondForward);
                positions.add(secondForward);
            }
        }

        if (!possibleMoves.isEmpty()) {
            this.getPosition().setCurrentPosition(possibleMoves.get(0));
            //this.getHistoric().add(this.getPosition());
        }
    }

    @Override
    public List<Case> getPossibleMove(Case currentCase) {
        List<Case> possibleMoves = new ArrayList<>();

        Case forward = this.getColor() == Color.white
                ? new Case(NumericalReference.increment(currentCase.getNumericalReference()), currentCase.getAlphabeticalReference())
                : new Case(NumericalReference.decrement(currentCase.getNumericalReference()), currentCase.getAlphabeticalReference());

        if (forward.isValid() && !forward.isBusy()) {
            possibleMoves.add(forward);
        }

        if (!hasMoved()) {
            Case secondForward = this.getColor() == Color.white
                    ? new Case(NumericalReference.increment(forward.getNumericalReference()), currentCase.getAlphabeticalReference())
                    : new Case(NumericalReference.decrement(forward.getNumericalReference()), currentCase.getAlphabeticalReference());

            if(board.getCase(secondForward.getRow(), secondForward.getCol()).getColor().equals(Color.black)){
                secondForward.setColor(Color.white);
            }else{
                secondForward.setColor(Color.black);
            }
            if (secondForward.isValid() && !secondForward.isBusy()) {
                possibleMoves.add(secondForward);
            }
        }
        /*
        Case leftCapture = this.getColor() == Color.white ?
                board.getCase(currentCase.getRow(), currentCase.getCol()-1) :
                board.getCase(currentCase.getRow() - 1, currentCase.getCol()-1);
        if (leftCapture.isValid() && leftCapture.isBusy()) {
            possibleMoves.add(leftCapture);
        }

        Case rightCapture = this.getColor() == Color.white ?
                board.getCase(currentCase.getRow(), currentCase.getCol()+1) :
                board.getCase(currentCase.getRow()-1, currentCase.getCol()+1);
        if (rightCapture.isValid() && rightCapture.isBusy()) {
            possibleMoves.add(rightCapture);
        }*/
        //System.out.println(board.getCase(currentCase.getRow() - 1, currentCase.getCol()));

        //System.out.println(cb.getCase(currentCase.getRow(),currentCase.getCol()));
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
            this.getPosition().setCurrentPosition(possibleAttackPositions.get(0));
        }
    }
/*
    protected List<Case> attack() {
        List<Case> possibleMoves = new ArrayList<>();
        ChessBoard chessBoard = new Chess().getChessBoard();
        Case leftCapture = this.getColor() == Color.white ?
                chessBoard.getCase(this.getPosition().getCurrentPosition().getRow(), this.getPosition().getCurrentPosition().getCol()-1) :
                board.getCase(this.getPosition().getCurrentPosition().getRow() - 1, this.getPosition().getCurrentPosition().getCol()-1);
        if (leftCapture.isValid() && leftCapture.isBusy()) {
            possibleMoves.add(leftCapture);
        }

        Case rightCapture = this.getColor() == Color.white ?
                board.getCase(this.getPosition().getCurrentPosition().getRow(), this.getPosition().getCurrentPosition().getCol()+1) :
                board.getCase(this.getPosition().getCurrentPosition().getRow()-1, this.getPosition().getCurrentPosition().getCol()+1);
        if (rightCapture.isValid() && rightCapture.isBusy()) {
            possibleMoves.add(rightCapture);
        }
        return possibleMoves;
    }*/
}
