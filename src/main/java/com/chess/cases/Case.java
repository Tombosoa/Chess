package com.chess.cases;

import com.chess.enums.AlphabeticalReference;
import com.chess.enums.Color;
import com.chess.enums.NumericalReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Case{
    private NumericalReference numericalReference;
    private AlphabeticalReference alphabeticalReference;
    private boolean isBusy;
    private Color color ;
    public boolean isValid() {
        if (numericalReference.getValue() < 1 || numericalReference.getValue() > 8) {
            return false;
        }
        return alphabeticalReference.isValid();
    }

    public Case(NumericalReference numericalReference, AlphabeticalReference alphabeticalReference) {
        this.numericalReference = numericalReference;
        this.alphabeticalReference = alphabeticalReference;
        this.isBusy = false;
        this.color = Color.white;
    }
}
