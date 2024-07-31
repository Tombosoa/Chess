package com.chess.pieces;

import com.chess.board.ChessBoard;
import com.chess.cases.Case;
import com.chess.enums.AlphabeticalReference;
import com.chess.enums.Color;
import com.chess.enums.NumericalReference;
import com.chess.enums.PieceName;
import lombok.*;

import javax.swing.*;
import java.util.Objects;

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

    public Piece(PieceName name, Color color, int point, Position position) {
        this.name = name;
        this.color = color;
        this.point = point;
        this.position = position;
        position.getCurrentPosition().setPiece(this);
    }

    public Piece(PieceName name, Position position, int point, Color color, String img) {
        this.name = name;
        this.img = img;
        this.position = position;
        this.point = point;
        this.color = color;
        position.getCurrentPosition().setPiece(this);
    }
    public ImageIcon getImageIcon() {
        if (this.img != null) {
            return new ImageIcon("chessUtils/" + this.img);
        }
        return null;
    }
    abstract void move(ChessBoard chessBoard);

    abstract void attack(ChessBoard chessBoard);

    public static void main(String[] args) {
        Case cases = new Case(NumericalReference.TWO, AlphabeticalReference.e);

        Position position = new Position(cases);

        Pawn pion = new Pawn(Color.white, position);
        System.out.println(pion.getPosition().getCurrentPosition().getPiece());
    }
}
