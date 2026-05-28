package chess.pkg1500;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame {

    JButton[][] squares = new JButton[8][8];
    char[][] board = Board.createBoard();
    int selectedRow = -1;
    int selectedCol = -1;
    int lastFromRow = -1;
    int lastFromCol = -1;

    int lastToRow = -1;
    int lastToCol = -1;
    ArrayList<int[]> currentLegalMoves = new ArrayList<>();
    boolean whiteToMove = true;

    public GUI() {
        setTitle("Chess");
        setSize(650, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        createBoardUI();
        setVisible(true);
    }

    private void createBoardUI() {

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8, 8));
        boardPanel.setBounds(20, 20, 600, 600);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                squares[i][j] = new JButton();
                squares[i][j].setLayout(new BorderLayout());
                if ((i + j) % 2 == 0) {
                    squares[i][j].setBackground(new Color(240, 217, 181));
                } else {
                    squares[i][j].setBackground(new Color(181, 136, 99));
                }
                
                if (i == 7) {
                    JLabel fileLabel = new JLabel("" + (char) ('a' + j));

                    fileLabel.setFont(new Font("Arial", Font.BOLD, 12));

                    fileLabel.setBorder(new EmptyBorder(0, 0, 2, 4));

                    if ((i + j) % 2 == 0) {
                        fileLabel.setForeground(new Color(181, 136, 99));
                    } else {
                        fileLabel.setForeground(new Color(240, 217, 181));
                    }

                    fileLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                    fileLabel.setVerticalAlignment(SwingConstants.BOTTOM);

                    squares[i][j].add(fileLabel, BorderLayout.SOUTH);
                }

                if (j == 0) {
                    JLabel rankLabel = new JLabel("" + (8 - i));

                    rankLabel.setFont(new Font("Arial", Font.BOLD, 12));

                    rankLabel.setBorder(new EmptyBorder(2, 4, 0, 0));

                    if ((i + j) % 2 == 0) {
                        rankLabel.setForeground(new Color(181, 136, 99));
                    } else {
                        rankLabel.setForeground(new Color(240, 217, 181));
                    }

                    rankLabel.setHorizontalAlignment(SwingConstants.LEFT);

                    rankLabel.setVerticalAlignment(SwingConstants.TOP);

                    squares[i][j].add(rankLabel,BorderLayout.NORTH);
                }
                squares[i][j].setFocusPainted(false);
                squares[i][j].setBorder(null);
                squares[i][j].setContentAreaFilled(false);
                squares[i][j].setOpaque(true);

                squares[i][j].setMargin(
                        new Insets(0, 0, 0, 0));

                squares[i][j].setIconTextGap(0);

                squares[i][j].setHorizontalAlignment(
                        SwingConstants.CENTER);

                squares[i][j].setVerticalAlignment(
                        SwingConstants.CENTER);
                final int row = i;
                final int col = j;

                squares[i][j].addActionListener(e -> handleClick(row, col));

                boardPanel.add(squares[i][j]);
            }
        }

        add(boardPanel);
        updateBoardUI();
    }

    private void updateBoardUI() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                char piece = board[i][j];

                squares[i][j].setIcon(getPieceIcon(piece));
                squares[i][j].setText("");
            }
        }
    }

    private void handleClick(int row, int col) {

        if (selectedRow == -1) {

            selectedRow = row;
            selectedCol = col;

            resetSquareColors();

            squares[row][col].setBackground(Color.YELLOW);

            currentLegalMoves.clear();

            ArrayList<int[]> allMoves = new ArrayList<>();

            Board.generateAllMoves(board, whiteToMove, allMoves);

            ArrayList<int[]> legalMoves = Board.filterLegalMoves(board, allMoves, whiteToMove);

            for (int[] move : legalMoves) {

                if (move[0] == row && move[1] == col) {
                    currentLegalMoves.add(move);
                    int r = move[2];
                    int c = move[3];
                    squares[r][c].setBackground(new Color(120, 200, 120));
                }
            }
        } else {

            int fromRow = selectedRow;
            int fromCol = selectedCol;
            int toRow = row;
            int toCol = col;

            ArrayList<int[]> allMoves = new ArrayList<>();
            Board.generateAllMoves(board, whiteToMove, allMoves);

            ArrayList<int[]> legalMoves = Board.filterLegalMoves(board, allMoves, whiteToMove);

            boolean moveFound = false;

            for (int i = 0; i < legalMoves.size(); i++) {
                int[] move = legalMoves.get(i);

                if (move[0] == fromRow && move[1] == fromCol
                        && move[2] == toRow && move[3] == toCol) {

                    Board.makeMove(board, move);

                    lastFromRow = fromRow;
                    lastFromCol = fromCol;

                    lastToRow = toRow;
                    lastToCol = toCol;

                    moveFound = true;
                    break;
                }
            }

            if (moveFound) {
                whiteToMove = !whiteToMove;
            }
            if (moveFound && !whiteToMove) {
                int[] aiMove = Search.findBestMove(board, false, 5);     // CHANGE DEPTH HERE

                if (aiMove != null) {
                    Board.makeMove(board, aiMove);

                    lastFromRow = aiMove[0];
                    lastFromCol = aiMove[1];

                    lastToRow = aiMove[2];
                    lastToCol = aiMove[3];

                    whiteToMove = !whiteToMove;
                }
            }

            selectedRow = -1;
            selectedCol = -1;

            resetSquareColors();
            updateBoardUI();
        }
    }

    private void resetSquareColors() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if ((i + j) % 2 == 0) {
                    squares[i][j].setBackground(new Color(240, 217, 181));
                } else {
                    squares[i][j].setBackground(new Color(181, 136, 99));
                }
            }
        }

        if (lastFromRow != -1) {

            squares[lastFromRow][lastFromCol]
                    .setBackground(
                            new Color(246, 246, 105));

            squares[lastToRow][lastToCol]
                    .setBackground(
                            new Color(246, 246, 105));
        }
    }

    public static void main(String[] args) {
        new GUI();
    }

    private ImageIcon getPieceIcon(char piece) {

        String file = "";

        switch (piece) {

            case 'K':
                file = "wk.png";
                break;
            case 'Q':
                file = "wq.png";
                break;
            case 'R':
                file = "wr.png";
                break;
            case 'B':
                file = "wb.png";
                break;
            case 'N':
                file = "wn.png";
                break;
            case 'P':
                file = "wp.png";
                break;

            case 'k':
                file = "bk.png";
                break;
            case 'q':
                file = "bq.png";
                break;
            case 'r':
                file = "br.png";
                break;
            case 'b':
                file = "bb.png";
                break;
            case 'n':
                file = "bn.png";
                break;
            case 'p':
                file = "bp.png";
                break;

            default:
                return null;
        }

        java.net.URL location = getClass().getResource("/chess/pkg1500/pieces/" + file);

        if (location == null) {
            return null;
        }

        ImageIcon icon = new ImageIcon(location);

        Image img = icon.getImage();
        Image scaled = img.getScaledInstance(72, 72, Image.SCALE_AREA_AVERAGING);
        return new ImageIcon(scaled);
    }
}
