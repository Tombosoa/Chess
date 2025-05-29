package com.chess.pieces;

import com.chess.board.ChessBoard;
import com.chess.cases.Case;
import com.chess.enums.Color;
import com.chess.enums.PieceName;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@ToString
public abstract class Piece {
    private final PieceName name;
    private final Color color;
    private final int point;
    private Position position;
    private String img;
    private final ChessBoard chessBoard;

    private final List<Position> historic = new ArrayList<>();

    public void addToAllMove(Position position){
        historic.add(position);
    }

    public abstract List<Case> getPossibleMove(Case currentCase);
}
