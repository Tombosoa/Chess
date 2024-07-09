package com.chess.enums;

public enum AlphabeticalReference {
    a, b, c, d, e, f, g, h;
    public AlphabeticalReference left() {
        switch (this) {
            case a: return a;
            default: return values()[ordinal() - 1];
        }
    }

    public AlphabeticalReference right() {
        switch (this) {
            case h: return h;
            default: return values()[ordinal() + 1];
        }
    }

    public boolean isValid() {
        return this == a || this == b || this == c || this == d ||
                this == e || this == f || this == g || this == h;
    }
}
