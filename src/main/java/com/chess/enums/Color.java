package com.chess.enums;

public enum Color {
    white,
    black;

    public static Color inverse(Color color){
        if (color.equals(Color.black)){
            return Color.white;
        }else{
            return Color.black;
        }
    }
}
