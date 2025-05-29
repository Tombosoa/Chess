package com.chess.board;

import com.chess.cases.Case;
import com.chess.enums.AlphabeticalReference;
import com.chess.enums.Color;
import com.chess.enums.NumericalReference;
import com.chess.pieces.Piece;
import com.chess.pieces.Position;
import java.util.ArrayList;
import java.util.List;

public class ChessBoard {
    private final Case[][] board ;
    public List<Piece> getAdjacentPieces(Position position) {
        List<Piece> adjacentPieces = new ArrayList<>();
        int row = position.getCurrentPosition().getRow();
        int col = position.getCurrentPosition().getCol();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                    Case adjacentCase = board[newRow][newCol];
                    if (adjacentCase.getPiece() != null) {
                        adjacentPieces.add(adjacentCase.getPiece());
                    }
                }
            }
        }
        return adjacentPieces;
    }

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

    public List<Piece> getAllPieces() {
        List<Piece> pieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Case currentCase = board[i][j];
                if (currentCase.isBusy() && currentCase.getPiece() != null) {
                    pieces.add(currentCase.getPiece());
                }
            }
        }
        return pieces;
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
