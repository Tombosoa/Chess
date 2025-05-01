package com.chess.pieces;

import com.chess.board.ChessBoard;
import com.chess.cases.Case;
import com.chess.enums.Color;
import com.chess.enums.PieceName;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    private final ChessBoard board;
    public Knight(Color color, Position position, ChessBoard board) {
        super(PieceName.knight, color, 3, position,
                color.equals(Color.black) ? "blackKnight.png" : "whiteKnight.png", board);
        this.board = board;
    }

    @Override
    public List<Case> getPossibleMove(Case currentCase) {
        List<Case> possibleMoves = new ArrayList<>();
        int row = currentCase.getNumericalReference().getValue() - 1;
        int col = currentCase.getAlphabeticalReference().ordinal();

        int[][] knightMoves = {
                {2, 1}, {2, -1},
                {-2, 1}, {-2, -1},
                {1, 2}, {1, -2},
                {-1, 2}, {-1, -2}
        };

        for (int[] move : knightMoves) {
            int newRow = row + move[0];
            int newCol = col + move[1];

            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                Case targetCase = board.getCase(newRow, newCol);

                if (targetCase != null && targetCase.isValid() &&
                        (!targetCase.isBusy() || targetCase.getPiece().getColor() != this.getColor())) {
                    possibleMoves.add(targetCase);
                }
            }
        }

        return possibleMoves;
    }
}