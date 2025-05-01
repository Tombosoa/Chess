package com.chess.pieces;

import com.chess.board.ChessBoard;
import com.chess.cases.Case;
import com.chess.enums.Color;
import com.chess.enums.PieceName;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    private final ChessBoard board;
    public Rook(Color color, Position position, ChessBoard board) {
        super(PieceName.rook, color, 5, position,
                color.equals(Color.black) ? "blackRook.png" : "whiteRook.png", board);
        this.board = board;
    }

    @Override
    public List<Case> getPossibleMove(Case currentCase) {
        List<Case> possibleMoves = new ArrayList<>();
        int row = currentCase.getNumericalReference().getValue() - 1;
        int col = currentCase.getAlphabeticalReference().ordinal();

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

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