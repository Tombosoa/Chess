package com.chess.ui;
import com.chess.board.ChessBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessBoardGUI extends JFrame {
    private final JPanel[][] squares = new JPanel[8][8];
    private final ChessBoard chessBoard;

    public ChessBoardGUI(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
        setTitle("Chess board");
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
                squares[row][col].setBackground(getBackgroundForColor(chessBoard.getCase(row, col).getColor()));
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
                squares[row][col].setBackground(getBackgroundForColor(chessBoard.getCase(row, col).getColor()));
            }
        }
    }

    private void handleSquareClick(int row, int col) {
        System.out.println(chessBoard.getCase(row, col));
    }

    public static void draw(){
        ChessBoard chessBoard = new ChessBoard();
        SwingUtilities.invokeLater(() -> new ChessBoardGUI(chessBoard));
    }
}
