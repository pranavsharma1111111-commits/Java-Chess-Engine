package chess.pkg1500;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GUI extends JFrame {

    JButton[][] squares = new JButton[8][8];
    char[][] board = Board.createBoard();
    int selectedRow = -1;
    int selectedCol = -1;
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

                if ((i + j) % 2 == 0) {
                    squares[i][j].setBackground(new Color(240, 217, 181));
                } else {
                    squares[i][j].setBackground(new Color(181, 136, 99));
                }

                squares[i][j].setFocusPainted(false);
                squares[i][j].setBorderPainted(false);
                squares[i][j].setOpaque(true);

                squares[i][j].setFont(new Font("Arial", Font.BOLD, 28));
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

                if (piece == '.') {
                    squares[i][j].setText("");
                } else {
                    squares[i][j].setText(String.valueOf(piece));
                }
            }
        }
    }

    private void handleClick(int row, int col) {

        if (selectedRow == -1) {
            selectedRow = row;
            selectedCol = col;
            resetSquareColors();
            squares[row][col].setBackground(Color.YELLOW);
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
                    moveFound = true;
                    break;
                }
            }

            if (moveFound) {
                whiteToMove = !whiteToMove;
            }
            if (moveFound && !whiteToMove) {
    int[] aiMove = Search.findBestMove(board,false,6);     // CHANGE DEPTH HERE

    if (aiMove != null) {
        Board.makeMove(board, aiMove);
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
    }

    public static void main(String[] args) {
        new GUI();
    }
}
