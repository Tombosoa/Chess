package com.chess.enums;

import lombok.Getter;

@Getter
public enum NumericalReference {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8);

    private final int value;

    private NumericalReference(int value) {
        this.value = value;
    }
    public static NumericalReference fromValue(int value) {
        for (NumericalReference nr : values()) {
            if (nr.value == value) {
                return nr;
            }
        }
        throw new IllegalArgumentException("Invalid numerical value: " + value);
    }
    public static NumericalReference increment(NumericalReference reference) {
        int newValue = reference.getValue() - 1;
        if (newValue < 8) {
            newValue += 1;
            return NumericalReference.values()[newValue ];
        }
        throw new IllegalArgumentException("Increment beyond board limits");
    }

    public static NumericalReference decrement(NumericalReference reference) {
        int newValue = reference.getValue() - 1;
        if (newValue >= 1) {
            return NumericalReference.values()[newValue - 1];
        }
        throw new IllegalArgumentException("Decrement below board limits");
    }

    public boolean isValid() {
        return this == ONE || this == TWO || this == THREE || this == FOUR ||
                this == FIVE || this == SIX || this == SEVEN || this == EIGHT;
    }
}
