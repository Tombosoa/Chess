package com.chess.pieces;

import com.chess.board.ChessBoard;
import com.chess.cases.Case;
import com.chess.enums.AlphabeticalReference;
import com.chess.enums.Color;
import com.chess.enums.NumericalReference;
import com.chess.enums.PieceName;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
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
        List<Case> possibleMoves = new ArrayList<>();

        int col = currentCase.getNumericalReference().getValue() - 1;
        int row = currentCase.getAlphabeticalReference().ordinal();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;

                int newCol = col + i;
                int newRow = row + j;

                if (newCol >= 0 && newCol < 8 && newRow >= 0 && newRow < 8) {
                    Case toAdd = new Case(NumericalReference.values()[newCol], AlphabeticalReference.values()[newRow]);
                    if (toAdd.isValid() && !toAdd.isBusy()) {
                        possibleMoves.add(toAdd);
                    }
                }
            }
        }

        return possibleMoves;
    }
}
