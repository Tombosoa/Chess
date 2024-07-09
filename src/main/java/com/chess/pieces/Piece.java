package com.chess.pieces;

import com.chess.enums.Color;
import com.chess.enums.PieceName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Setter
@Getter
@ToString
public abstract class Piece {
    private final PieceName name;
    private final Color color;
    private final int point;
    private Position position;

    abstract void move();

    abstract void attack();
}
