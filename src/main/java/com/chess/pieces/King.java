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
    private final ChessBoard board;

    public King(Color color, Position position, ChessBoard board) {
        super(PieceName.king, color, 4, position, color.equals(Color.black) ? "blackKing.png" : "whiteKing.png", board);
        this.board = board;
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
                    Case dToAdd = board.getCase(toAdd.getRow() - 1, toAdd.getCol());

                    if (dToAdd.isValid() && (!dToAdd.isBusy() ||
                            (dToAdd.getPiece() != null && dToAdd.getPiece().getColor() != this.getColor()))) {
                        possibleMoves.add(dToAdd);
                    }
                }
            }
        }

        return possibleMoves;
    }

    public boolean isInCheck() {
        Case kingCase = this.getPosition().getCurrentPosition();

        for (Piece piece : board.getAllPieces()) {
            if (piece.getColor() != this.getColor()) {
                List<Case> opponentMoves = piece.getPossibleMove(piece.getPosition().getCurrentPosition());
                if (opponentMoves.contains(kingCase)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCheckMate() {
        if (!isInCheck()) return false;

        for (Case possibleMove : getPossibleMove(getPosition().getCurrentPosition())) {
            Piece capturedPiece = possibleMove.getPiece();
            Case originalPosition = getPosition().getCurrentPosition();

            possibleMove.addPiece(this);
            originalPosition.removePiece();
            this.getPosition().setCurrentPosition(possibleMove);

            boolean stillInCheck = isInCheck();

            this.getPosition().setCurrentPosition(originalPosition);
            originalPosition.addPiece(this);
            possibleMove.removePiece();
            if (capturedPiece != null) {
                possibleMove.addPiece(capturedPiece);
            }

            if (!stillInCheck) {
                return false;
            }
        }

        List<Piece> attackers = getAttackers();
        if (attackers.size() == 1) {
            Piece attacker = attackers.get(0);
            Case attackerCase = attacker.getPosition().getCurrentPosition();

            for (Piece ally : board.getAllPieces()) {
                if (ally.getColor() == this.getColor() && !(ally instanceof King)) {
                    List<Case> allyMoves = ally.getPossibleMove(ally.getPosition().getCurrentPosition());
                    if (allyMoves.contains(attackerCase)) {
                        Case originalAllyPosition = ally.getPosition().getCurrentPosition();

                        attackerCase.removePiece();
                        attackerCase.addPiece(ally);
                        originalAllyPosition.removePiece();
                        ally.getPosition().setCurrentPosition(attackerCase);

                        boolean stillInCheckAfterCapture = isInCheck();

                        ally.getPosition().setCurrentPosition(originalAllyPosition);
                        originalAllyPosition.addPiece(ally);
                        attackerCase.removePiece();
                        attackerCase.addPiece(attacker);

                        if (!stillInCheckAfterCapture) {
                            return false;
                        }
                    }
                }
            }

            if (!(attacker instanceof Knight) && !(attacker instanceof Pawn)) {
                Case kingCase = getPosition().getCurrentPosition();
                List<Case> path = getPathBetween(attackerCase, kingCase);

                for (Case blockCase : path) {
                    for (Piece ally : board.getAllPieces()) {
                        if (ally.getColor() == this.getColor() && !(ally instanceof King)) {
                            List<Case> allyMoves = ally.getPossibleMove(ally.getPosition().getCurrentPosition());
                            if (allyMoves.contains(blockCase)) {
                                Case originalAllyPosition = ally.getPosition().getCurrentPosition();

                                blockCase.addPiece(ally);
                                originalAllyPosition.removePiece();
                                ally.getPosition().setCurrentPosition(blockCase);

                                boolean stillInCheckAfterBlock = isInCheck();

                                ally.getPosition().setCurrentPosition(originalAllyPosition);
                                originalAllyPosition.addPiece(ally);
                                blockCase.removePiece();

                                if (!stillInCheckAfterBlock) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    private List<Piece> getAttackers() {
        List<Piece> attackers = new ArrayList<>();
        Case kingCase = getPosition().getCurrentPosition();

        for (Piece piece : board.getAllPieces()) {
            if (piece.getColor() != this.getColor()) {
                List<Case> opponentMoves = piece.getPossibleMove(piece.getPosition().getCurrentPosition());
                if (opponentMoves.contains(kingCase)) {
                    attackers.add(piece);
                }
            }
        }
        return attackers;
    }

    private List<Case> getPathBetween(Case from, Case to) {
        List<Case> path = new ArrayList<>();

        int fromRow = from.getRow() - 1;
        int fromCol = from.getCol();
        int toRow = to.getRow() - 1;
        int toCol = to.getCol();

        int rowStep = Integer.compare(toRow, fromRow);
        int colStep = Integer.compare(toCol, fromCol);

        int currentRow = fromRow + rowStep;
        int currentCol = fromCol + colStep;

        while (currentRow != toRow || currentCol != toCol) {
            path.add(board.getCase(currentRow, currentCol));
            currentRow += rowStep;
            currentCol += colStep;
        }

        return path;
    }
}