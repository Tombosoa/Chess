package com.chess.cases;

import com.chess.enums.AlphabeticalReference;
import com.chess.enums.NumericalReference;

public record Case(NumericalReference numericalReference, AlphabeticalReference alphabeticalReference){
    public boolean isValid() {
        if (numericalReference.getValue() < 1 || numericalReference.getValue() > 8) {
            return false;
        }

        return alphabeticalReference.isValid();
    }
}
