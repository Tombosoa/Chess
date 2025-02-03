package com.chess.ui;

import com.chess.Chess;
import com.chess.cases.Case;
import com.chess.pieces.Piece;
import com.chess.pieces.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessGUI extends JFrame {
    private final JPanel[][] squares = new JPanel[8][8];
    private final Chess chess;
    boolean isClicked = false;

    private List<Case> highlightedCases = new ArrayList<>();
    private final Map<Case, Color> originalColors = new HashMap<>();
    private Piece selectedPiece = null;

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public boolean isClicked() {
        return isClicked;
    }

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
                chess.getChessBoard().getCase(row, col).setColor(chess.getChessBoard().getCase(row, col).getColor());
                squares[row][col].addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        refreshSquare(finalRow, finalCol);
                    }
                });

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
                refreshSquare(row, col);
            }
        }
    }

    private void refreshSquare(int row, int col) {
        squares[row][col].removeAll();
        squares[row][col].setBackground(getBackgroundForColor(chess.getChessBoard().getCase(row, col).getColor()));
        Piece piece = chess.getChessBoard().getCase(row, col).getPiece();

        if (piece != null) {
            ImageIcon icon = loadImageIcon(piece.getImg());
            Position position = new Position(chess.getChessBoard().getCase(row, col));
            piece.setPosition(position);
            if (icon != null) {
                int width = squares[row][col].getWidth();
                int height = squares[row][col].getHeight();

                if (width > 0 && height > 0) {
                    Image img = icon.getImage();
                    Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    ImageIcon resizedIcon = new ImageIcon(resizedImg);

                    JLabel label = new JLabel(resizedIcon);
                    squares[row][col].add(label);
                }
            }
        }

        squares[row][col].revalidate();
        squares[row][col].repaint();
    }

    private ImageIcon loadImageIcon(String fileName) {
        java.net.URL imgURL = getClass().getClassLoader().getResource("images/" + fileName);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + fileName);
            return null;
        }
    }

    private void handleSquareClick(int row, int col) {
        Case currentCase = chess.getChessBoard().getCase(row, col);
        Piece piece = chess.getChessBoard().getCase(row, col).getPiece();

        if (!currentCase.isBusy()){
                if(selectedPiece.getPossibleMove(selectedPiece.getPosition().getCurrentPosition()).contains(currentCase)){
                    choosePosition(currentCase, selectedPiece);
                    resetPreviousSquare(selectedPiece);
                }
            System.out.println("there is no piece");
        }else{
            handlePieceClick(piece);
        }
    }

    private void resetPreviousSquare(Piece piece){
        JPanel square = squares[piece.getPosition().getCurrentPosition().getRow() - 1][piece.getPosition().getCurrentPosition().getCol()];
        ImageIcon icon = loadImageIcon(piece.getImg());

        if (icon != null) {
            Image img = icon.getImage();
            int width = square.getWidth();
            int height = square.getHeight();
            Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImg);

            JLabel label = new JLabel(resizedIcon);
            square.remove(label);
            System.out.println("remove the label at " + piece.getPosition().getCurrentPosition());
        }

        refreshSquare(piece.getPosition().getCurrentPosition().getRow() - 1, piece.getPosition().getCurrentPosition().getCol());
    }

    private void handlePieceClick(Piece piece) {
        Position currentPosition = piece.getPosition();
        if (selectedPiece != null && selectedPiece != piece) {
            resetHighlightedCases();
            setClicked(false);
        }

        selectedPiece = piece;

        if (!isClicked) {
            setClicked(true);
            highlightedCases = piece.getPossibleMove(piece.getPosition().getCurrentPosition());

            //System.out.println("Possibles moves");
            for (Case cs : highlightedCases) {
                JPanel square = squares[cs.getRow() - 1][cs.getCol()];

                originalColors.put(cs, square.getBackground());
                square.setBackground(Color.ORANGE);
            }
        } else {
            setClicked(false);
            resetHighlightedCases();
            piece.setPosition(currentPosition);
        }
    }

    private void resetHighlightedCases() {
        for (Case cs : highlightedCases) {
            JPanel square = squares[cs.getRow() - 1][cs.getCol()];

            Color originalColor = originalColors.get(cs);
            if (originalColor != null) {
                square.setBackground(originalColor);
            }
        }
        highlightedCases.clear();
        originalColors.clear();
    }

    private void choosePosition(Case cases, Piece piece){
        JPanel square = squares[cases.getRow() - 1][cases.getCol()];
        square.setBackground(Color.RED);

        cases.addPiece(piece);
        refreshSquare(cases.getRow() - 1, cases.getCol());

        System.out.println("you choose: " + cases);
    }

    public static void draw() {
        Chess chess = new Chess();
        SwingUtilities.invokeLater(() -> new ChessGUI(chess));
    }

    public static void main(String[] args) {
        draw();
    }
}
