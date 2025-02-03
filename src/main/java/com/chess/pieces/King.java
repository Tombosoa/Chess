package com.chess.pieces;

import com.chess.board.ChessBoard;
import com.chess.cases.Case;
import com.chess.enums.Color;
import com.chess.enums.PieceName;

import java.util.List;

public class King extends Piece{
    public King(Color color, Position position) {
        super(PieceName.king, color, 4, position, color.equals(Color.black) ? "blackKing.png" : "whiteKing.png");
    }

    @Override
    public void move(ChessBoard chessBoard) {

    }

    @Override
    void attack(ChessBoard chessBoard) {

    }

    @Override
    public List<Case> getPossibleMove(Case currentCase) {
        return List.of();
    }
}
