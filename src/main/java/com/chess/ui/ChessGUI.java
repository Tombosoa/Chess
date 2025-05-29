package com.chess.ui;

import com.chess.Chess;
import com.chess.cases.Case;
import com.chess.enums.AlphabeticalReference;
import com.chess.enums.NumericalReference;
import com.chess.pieces.King;
import com.chess.pieces.Piece;
import com.chess.pieces.Position;
import com.chess.stf.Stockfish;
import lombok.Getter;
import lombok.Setter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessGUI extends JFrame {
    private final JPanel[][] squares = new JPanel[8][8];
    private final Chess chess;
    @Setter
    boolean isClicked = false;
    @Getter
    private com.chess.enums.Color currentPlayer = com.chess.enums.Color.white;
    private List<Case> highlightedCases = new ArrayList<>();
    private final Map<Case, Color> originalColors = new HashMap<>();
    private Piece selectedPiece = null;
    private final Stockfish engine = new Stockfish();
    private boolean engineStarted = false;

    @Getter
    @Setter
    private Piece clickedPiece = null;

    public ChessGUI(Chess chess) {
        this.chess = chess;
        setTitle("Chess - Turn of " + currentPlayer.toString() + "s");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));
        this.setPreferredSize(new Dimension(500, 500));
        initializeBoard();

        if (engine.startEngine()) {
            engineStarted = true;
            SwingUtilities.invokeLater(this::suggestBestMove);
        } else {
            System.err.println("Stockfish is not running.");
        }

        pack();
        setVisible(true);
    }

    @Override
    public void dispose() {
        super.dispose();
        if (engineStarted) {
            try {
                engine.stopEngine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == com.chess.enums.Color.white)
                ? com.chess.enums.Color.black
                : com.chess.enums.Color.white;

        setTitle("Chess - Turn of " + currentPlayer + "s");

        if (currentPlayer == com.chess.enums.Color.white) {
            new Thread(() -> {
                try {
                    Thread.sleep(500);
                    SwingUtilities.invokeLater(this::suggestBestMove);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private Piece findPieceAtCase(Case targetCase) {
        return chess.getChessBoard().getCase(
                targetCase.getRow() - 1,
                targetCase.getCol()
        ).getPiece();
    }

    private void initializeBoard() {
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                final int finalRow = row;
                final int finalCol = col;
                squares[row][col] = new JPanel(new BorderLayout());
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
            case black -> new Color(118, 150, 86);
            case white -> new Color(238, 238, 210);
            default -> throw new IllegalArgumentException("Invalid color");
        };
    }

    private void printBoardState() {
        System.out.println("=== Actual board state ===");
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Case currentCase = chess.getChessBoard().getCase(row, col);
                Piece piece = currentCase.getPiece();
                String pieceInfo = (piece != null)
                        ? piece.getColor() + " " + piece.getClass().getSimpleName()
                        : "Empty";
                System.out.printf("Case [%d][%d]: %s | Busy: %s%n",
                        row, col, pieceInfo, currentCase.isBusy());
            }
        }
        System.out.println("============================");
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
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setVerticalAlignment(JLabel.CENTER);
                    squares[row][col].add(label, BorderLayout.CENTER);
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
        if (currentPlayer != com.chess.enums.Color.black) {
            return;
        }

        Case currentCase = chess.getChessBoard().getCase(row, col);
        Piece clicked = currentCase.getPiece();

        if (selectedPiece != null) {
            if (selectedPiece.getPossibleMove(selectedPiece.getPosition().getCurrentPosition()).contains(currentCase)) {
                if (clicked != null && clicked.getColor() == selectedPiece.getColor()) {
                    return;
                }

                if (selectedPiece.getColor() != currentPlayer) {
                    return;
                }

                choosePosition(currentCase, selectedPiece);
                selectedPiece = null;
                switchPlayer();
                checkEndOrCheckState();
                return;
            }
        }

        if (clicked != null && clicked.getColor() == currentPlayer) {
            resetHighlightedCases();
            selectedPiece = clicked;
            handlePieceClick(clicked);
        }
    }

    private void checkEndOrCheckState() {
        for (Piece piece : chess.getChessBoard().getAllPieces()) {
            if (piece instanceof King) {
                King king = (King) piece;
                boolean inCheck = king.isInCheck();
                boolean isCheckmate = king.isCheckMate();

                if (isCheckmate) {
                    String winner = king.getColor() == com.chess.enums.Color.white ? "Blacks" : "White";
                    System.out.println("checkmate");
                    disableBoard();
                    return;
                } else if (inCheck) {
                    Case kingCase = king.getPosition().getCurrentPosition();
                    JPanel square = squares[kingCase.getRow() - 1][kingCase.getCol()];
                    square.setBackground(new Color(255, 0, 0, 180));
                    System.out.println("check");
                }
            }
        }
    }

    private void handlePieceClick(Piece piece) {
        if (isClicked && selectedPiece == piece) {
            setClicked(false);
            resetHighlightedCases();
            selectedPiece = null;
            return;
        }

        setClicked(true);
        selectedPiece = piece;
        resetHighlightedCases();

        List<Case> possibleMoves = piece.getPossibleMove(piece.getPosition().getCurrentPosition());
        highlightedCases = new ArrayList<>();

        setClickedPiece(piece);

        for (Case cs : possibleMoves) {
            Piece p = cs.getPiece();
            JPanel square = squares[cs.getRow() - 1][cs.getCol()];
            originalColors.put(cs, square.getBackground());
            if (p == null) {
                square.setBackground(new Color(249, 255, 66, 142));
            } else if (p.getColor() != piece.getColor()) {
                square.setBackground(new Color(255, 0, 0, 110));
            }

            if (p == null || p.getColor() != piece.getColor()) {
                highlightedCases.add(cs);
            }
        }
    }

    public String generateFEN() {
        StringBuilder fen = new StringBuilder();

        for (int row = 0; row < 8; row++) {
            int emptyCount = 0;
            for (int col = 0; col < 8; col++) {
                Case currentCase = chess.getChessBoard().getCase(7 - row, col);
                Piece piece = currentCase.getPiece();

                if (piece == null) {
                    emptyCount++;
                } else {
                    if (emptyCount > 0) {
                        fen.append(emptyCount);
                        emptyCount = 0;
                    }

                    String pieceType = piece.getClass().getSimpleName().toLowerCase();
                    char symbol;

                    switch (pieceType) {
                        case "king":   symbol = 'k'; break;
                        case "queen":  symbol = 'q'; break;
                        case "rook":   symbol = 'r'; break;
                        case "bishop": symbol = 'b'; break;
                        case "knight": symbol = 'n'; break;
                        case "pawn":   symbol = 'p'; break;
                        default:       symbol = '?'; break;
                    }

                    if ("white".equalsIgnoreCase(piece.getColor().toString())) {
                        symbol = Character.toUpperCase(symbol);
                    }

                    fen.append(symbol);
                }
            }

            if (emptyCount > 0) {
                fen.append(emptyCount);
            }

            if (row < 7) {
                fen.append('/');
            }
        }

        String activeColor = currentPlayer.equals(com.chess.enums.Color.white) ? "w" : "b";
        String castlingRights = "KQkq";
        String enPassant = "-";
        int halfmoveClock = 0;
        int fullmoveNumber = 1;

        fen.append(" ").append(activeColor)
                .append(" ").append(castlingRights)
                .append(" ").append(enPassant)
                .append(" ").append(halfmoveClock)
                .append(" ").append(fullmoveNumber);

        return fen.toString();
    }

    private void resetHighlightedCases() {
        for (Case cs : highlightedCases) {
            if (selectedPiece != null &&
                    selectedPiece.getPosition() != null &&
                    selectedPiece.getPosition().getCurrentPosition() != cs) {
                JPanel square = squares[cs.getRow() - 1][cs.getCol()];
                Color originalColor = originalColors.get(cs);
                if (originalColor != null) {
                    square.setBackground(originalColor);
                }
            }
        }
        highlightedCases.clear();
        originalColors.clear();
    }

    private void choosePosition(Case newCase, Piece piece) {
        Case previousCase = piece.getPosition() != null ?
                piece.getPosition().getCurrentPosition() : null;
        if (previousCase != null) {
            previousCase.removePiece();
        }
        newCase.addPiece(piece);
        piece.setPosition(new Position(newCase));
        refreshBoard();

        if (previousCase != null) {
            JPanel previousPanel = squares[previousCase.getRow() - 1][previousCase.getCol()];
            originalColors.put(previousCase, previousPanel.getBackground());
            previousPanel.setBackground(new Color(227, 227, 106, 255));
            highlightedCases.add(previousCase);
        }
        piece.addToAllMove(new Position(newCase));
    }


    public void suggestBestMove() {
        if (!engineStarted || !engine.isAlive()) {
            System.err.println("Stockfish is down. Attempt to restart...");
            engineStarted = engine.startEngine();
            if (!engineStarted) {
                System.err.println("Stockfish restart failed.");
                return;
            }
        }

        refreshBoard();

        new Thread(() -> {
            try {
                Piece anyPiece = findFirstWhitePiece();
                if (anyPiece == null) return;

                String fen = generateFEN();
                String bestMove = engine.getBestMove(fen);

                SwingUtilities.invokeLater(() -> {
                    if (bestMove != null && bestMove.length() >= 4) {
                        executeEngineMove(bestMove);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private Piece findFirstWhitePiece() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece p = chess.getChessBoard().getCase(row, col).getPiece();
                if (p != null && p.getColor() == com.chess.enums.Color.white) {
                    return p;
                }
            }
        }
        return null;
    }

    private void executeEngineMove(String bestMove) {
        Case from = parseUciToCase(bestMove.substring(0, 2));
        Case to = parseUciToCase(bestMove.substring(2, 4));
        Piece pieceToMove = findPieceAtCase(from);

        if (pieceToMove != null && pieceToMove.getColor() == com.chess.enums.Color.white) {
            choosePosition(to, pieceToMove);
            checkEndOrCheckState();
            switchPlayer();
        }
    }

    private void disableBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                for (MouseListener listener : squares[row][col].getMouseListeners()) {
                    squares[row][col].removeMouseListener(listener);
                }
            }
        }
    }

    private Case parseUciToCase(String uci) {
        char file = uci.charAt(0);
        char rank = uci.charAt(1);

        AlphabeticalReference col = AlphabeticalReference.valueOf(String.valueOf(file).toLowerCase());
        NumericalReference row = NumericalReference.fromValue(Character.getNumericValue(rank));

        int rowIndex = row.getValue() - 1;
        int colIndex = col.ordinal();

        return chess.getChessBoard().getCase(rowIndex, colIndex);
    }

    public static void draw() {
        Chess chess = new Chess();
        SwingUtilities.invokeLater(() -> new ChessGUI(chess));
    }

    public static void main(String[] args) {
        draw();
    }
}