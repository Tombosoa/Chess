package com.chess.cases;

import com.chess.enums.AlphabeticalReference;
import com.chess.enums.Color;
import com.chess.enums.NumericalReference;
import com.chess.pieces.Piece;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class Case{
    private NumericalReference numericalReference;
    private AlphabeticalReference alphabeticalReference;
    private boolean isBusy;
    private Color color ;
    private Piece piece;
    public boolean isValid() {
        if (numericalReference.getValue() < 1 || numericalReference.getValue() > 8) {
            return false;
        }
        return alphabeticalReference.isValid();
    }

    public Case(NumericalReference numericalReference, AlphabeticalReference alphabeticalReference) {
        this.numericalReference = numericalReference;
        this.alphabeticalReference = alphabeticalReference;
        this.color = this.color == Color.black ? Color.black : Color.white;
    }

    public int getRow(){
        return numericalReference.getValue();
    }

    public int getCol(){
        return alphabeticalReference.ordinal();
    }

    public void addPiece(Piece piece){
        this.setPiece(piece);
        setBusy(true);
    }

    public void removePiece(){
        setPiece(null);
        setBusy(false);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Case aCase = (Case) object;
        return numericalReference == aCase.numericalReference && alphabeticalReference == aCase.alphabeticalReference;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numericalReference, alphabeticalReference);
    }
}
