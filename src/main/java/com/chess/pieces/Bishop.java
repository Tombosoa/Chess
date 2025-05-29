package com.chess.pieces;

import com.chess.board.ChessBoard;
import com.chess.cases.Case;
import com.chess.enums.Color;
import com.chess.enums.PieceName;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    private final ChessBoard board;
    public Bishop(Color color, Position position, ChessBoard board) {
        super(PieceName.bishop, color, 2, position,
                color.equals(Color.black) ? "blackBishop.png" : "whiteBishop.png", board);
        this.board = board;
    }

    @Override
    public List<Case> getPossibleMove(Case currentCase) {
        List<Case> possibleMoves = new ArrayList<>();
        int row = currentCase.getNumericalReference().getValue() - 1;
        int col = currentCase.getAlphabeticalReference().ordinal();

        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            while (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                Case targetCase = board.getCase(newRow, newCol);

                if (targetCase == null || !targetCase.isValid()) {
                    break;
                }

                possibleMoves.add(targetCase);

                if (targetCase.isBusy()) {
                    break;
                }

                newRow += dir[0];
                newCol += dir[1];
            }
        }

        return possibleMoves;
    }
}