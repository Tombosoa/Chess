package com.chess.pieces;

import com.chess.cases.Case;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class Position {
    private Case currentPosition;
    private Case previousPosition;

    public Position(Case currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(currentPosition.alphabeticalReference())
                .append(currentPosition.numericalReference().getValue());

        if (previousPosition != null) {
            sb.append("-").append(previousPosition.alphabeticalReference()).append(previousPosition.numericalReference().getValue());
        }

        return sb.toString();
    }

}
