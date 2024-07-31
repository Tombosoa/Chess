package com.chess.ui;

import com.chess.Chess;
import com.chess.board.ChessBoard;
import com.chess.pieces.Piece;
import com.chess.pieces.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessGUI extends JFrame {
    private final JPanel[][] squares = new JPanel[8][8];
    private final Chess chess;

    public ChessGUI(Chess chess) {
        this.chess = chess;
        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));
        this.setPreferredSize(new Dimension(500, 500));
        initializeBoard();

        pack();
        setVisible(true);
    }

    private void initializeBoard() {
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                final int finalRow = row;
                final int finalCol = col;
                squares[row][col] = new JPanel();
                squares[row][col].setBackground(getBackgroundForColor(chess.getChessBoard().getCase(row, col).getColor()));
                squares[row][col].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleSquareClick(finalRow, finalCol);
                    }
                });
                add(squares[row][col]);
            }
        }
        refreshBoard();
    }

    private Color getBackgroundForColor(com.chess.enums.Color color) {
        return switch (color) {
            case black -> Color.BLACK;
            case white -> Color.WHITE;
            default -> throw new IllegalArgumentException("Invalid color");
        };
    }

    private void refreshBoard() {
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                squares[row][col].setBackground(getBackgroundForColor(chess.getChessBoard().getCase(row, col).getColor()));
                Position position = new Position(chess.getChessBoard().getCase(row, col));
                Piece piece = chess.getChessBoard().getCase(row, col).getPiece();

                position.getCurrentPosition().addPiece(piece);
                ImageIcon icon = position.getCurrentPosition().getPiece().getImageIcon();
                JLabel label = new JLabel(icon);
                squares[row][col].add(label);
            }
        }
    }

    private void handleSquareClick(int row, int col) {
        System.out.println(chess.getChessBoard().getCase(row, col));
        System.out.println(chess.getChessBoard().getCase(row, col).getPiece());
    }

    public static void draw(){
        Chess chess = new Chess();
        SwingUtilities.invokeLater(() -> new ChessGUI(chess));
    }

    public static void main(String[] args) {
        draw();
    }
}
