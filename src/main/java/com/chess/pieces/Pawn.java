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
public class Pawn extends Piece {
    private List<Case> positions;
    private final ChessBoard board;

    public Pawn(Color color, Position position, ChessBoard board) {
        super(PieceName.pawn, color, 1, position, color.equals(Color.black) ? "blackPawn.png" : "whitePawn.png", board);
        this.positions = new ArrayList<>();
        this.board = board;
    }

    private boolean hasMoved() {
        return !this.getHistoric().isEmpty();
    }

    @Override
    public void move(ChessBoard chessBoard) {

    }

    @Override
    public List<Case> getPossibleMove(Case currentCase) {
        List<Case> possibleMoves = new ArrayList<>();

        Case forward = this.getColor() == Color.white
                ? new Case(NumericalReference.increment(currentCase.getNumericalReference()), currentCase.getAlphabeticalReference())
                : new Case(NumericalReference.decrement(currentCase.getNumericalReference()), currentCase.getAlphabeticalReference());
        Case dForward = board.getCase(forward.getRow() - 1, forward.getCol());

        if (dForward.isValid() && !dForward.isBusy()) {
            possibleMoves.add(forward);
        }

        if (!hasMoved()) {
            Case secondForward = this.getColor() == Color.white
                    ? new Case(NumericalReference.increment(forward.getNumericalReference()), currentCase.getAlphabeticalReference())
                    : new Case(NumericalReference.decrement(forward.getNumericalReference()), currentCase.getAlphabeticalReference());
            Case dSecondForward = board.getCase(secondForward.getRow() - 1, secondForward.getCol());
            if(board.getCase(secondForward.getRow(), secondForward.getCol()).getColor().equals(Color.black)){
                secondForward.setColor(Color.white);
            }else{
                secondForward.setColor(Color.black);
            }

            if (dSecondForward.isValid() && !dSecondForward.isBusy()) {
                if(!dForward.isBusy()){
                    possibleMoves.add(secondForward);
                }
            }
        }

        try {
            int leftRow = this.getColor() == Color.white ? currentCase.getRow() : currentCase.getRow() - 2;
            int leftCol = currentCase.getCol() - 1;

            if (leftCol >= 0) {
                Case leftCapture = board.getCase(leftRow, leftCol);
                if (leftCapture != null && leftCapture.isValid() && leftCapture.isBusy()
                        && leftCapture.getPiece().getColor() != this.getColor()) {
                    possibleMoves.add(leftCapture);
                }
            }
        } catch (Exception ignored) {

        }

        try {
            int rightRow = this.getColor() == Color.white ? currentCase.getRow() : currentCase.getRow() - 2;
            int rightCol = currentCase.getCol() + 1;

            if (rightCol < 8) {
                Case rightCapture = board.getCase(rightRow, rightCol);
                if (rightCapture != null && rightCapture.isValid() && rightCapture.isBusy()
                        && rightCapture.getPiece().getColor() != this.getColor()) {
                    possibleMoves.add(rightCapture);
                }
            }
        } catch (Exception ignored) {

        }

        return possibleMoves;
    }

    @Override
    protected void attack(ChessBoard chessBoard) {

    }

}
