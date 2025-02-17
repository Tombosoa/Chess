package com.chess.board;

import com.chess.cases.Case;
import com.chess.enums.AlphabeticalReference;
import com.chess.enums.Color;
import com.chess.enums.NumericalReference;
import com.chess.pieces.Piece;
import com.chess.pieces.Position;

public class ChessBoard {
    private final Case[][] board ;

    public ChessBoard() {
        board = new Case[8][8];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    board[i][j] = new Case(NumericalReference.values()[i], AlphabeticalReference.values()[j]);
                    board[i][j].setColor(Color.black);
                } else {
                    board[i][j] = new Case(NumericalReference.values()[i], AlphabeticalReference.values()[j]);
                }
            }
        }
    }

    public Case getCase(int row, int column) {
        return board[row][column];
    }

    public void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j].toString() + " ");
            }
            System.out.println();
        }
    }

    public String[][] getBoardState() {
        String[][] boardState = new String[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardState[i][j] = "";
            }
        }
        return boardState;
    }

    public void setPiece(int row, int column, Piece piece) {
        piece.getPosition().getCurrentPosition().setBusy(true);
        //getCase(row, column).setBusy(true);
        Position position = new Position(getCase(row, column));
        piece.setPosition(position);
        getCase(row, column).addPiece(piece);
    }
}
