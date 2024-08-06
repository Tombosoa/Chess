package com.chess.pieces;

import com.chess.cases.Case;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Position {
    private Case currentPosition;
    private Case previousPosition;

    public Position(Case currentPosition) {
        this.currentPosition = currentPosition;
        if(currentPosition.getPiece() != null){
            currentPosition.setBusy(true);
        }
    }

    public void setCurrentPosition(Case newPosition) {
        this.previousPosition = this.currentPosition;
        this.currentPosition = newPosition;
    }

    public Case getCurrentPosition() {
        return currentPosition;
    }

    public void setPreviousPosition(Case previousPosition) {
        this.previousPosition = previousPosition;
    }

    public Case getPreviousPosition() {
        return this.previousPosition;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(currentPosition.getAlphabeticalReference())
                .append(currentPosition.getNumericalReference().getValue());

        if (previousPosition != null) {
            sb.append("-").append(previousPosition.getAlphabeticalReference()).append(previousPosition.getNumericalReference().getValue());
        }

        return sb.toString();
    }
}
