package com.chess.board;

import com.chess.cases.Case;
import com.chess.enums.AlphabeticalReference;
import com.chess.enums.Color;
import com.chess.enums.NumericalReference;
import com.chess.pieces.Pawn;

public class ChessBoard {
    private final Case[][] board;

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

    public static void main(String[] args) {
        ChessBoard bd = new ChessBoard();
        System.out.println(bd.getCase(0, 0));
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

    public void setPiece(int row, int column, Pawn pawn) {
        getCase(row, column).setBusy(true);
    }
}
