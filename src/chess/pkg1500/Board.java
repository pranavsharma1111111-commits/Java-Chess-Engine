package chess.pkg1500;

import java.util.ArrayList;

class Board {

    public static void main(String[] args) {
        char[][] board = createBoard();
        boolean whiteToMove = true;

        int[] bestMove = Search.findBestMoveDepth2(board, whiteToMove);

        if (bestMove != null) {
            System.out.println("Best move:");
            System.out.println(bestMove[0] + " " + bestMove[1] + " -> " + bestMove[2] + " " + bestMove[3]);
            System.out.println(Evaluation.evaluateBoard(board));
            makeMove(board, bestMove);
            printBoard(board);
        } else {
            System.out.println("checkMate");
        }
    }

    public static char[][] createBoard() {
        char[][] board = {
        {'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'},
        {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
        {'.', '.', '.', '.', '.', '.', '.', '.'},
        {'.', '.', '.', '.', '.', '.', '.', '.'},
        {'.', '.', '.', '.', '.', '.', '.', '.'},
        {'.', '.', '.', '.', '.', '.', '.', '.'},
        {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
        {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'}};           //THE CHESS BOARD
        return board;
    }

    public static void printBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();                            //PRINTING THE BOARD
        }
    }

    public static boolean isBlack(char piece) {
        return piece >= 'a' && piece <= 'z';
    }

    public static boolean isWhite(char piece) {
        return piece >= 'A' && piece <= 'Z';
    }

    public static boolean isEmpty(char piece) {
        return piece == '.';
    }

    public static void makeMove(char[][] board, int[] move) {
        int fromRow = move[0];
        int fromCol = move[1];
        int toRow = move[2];
        int toCol = move[3];

        char movingPiece = board[fromRow][fromCol];
        char capturedPiece = board[toRow][toCol];

        board[toRow][toCol] = movingPiece;
        board[fromRow][fromCol] = '.';

        // Promotion
        if (movingPiece == 'P' && toRow == 0) {
            board[toRow][toCol] = 'Q';
        } else if (movingPiece == 'p' && toRow == 7) {
            board[toRow][toCol] = 'q';
        }

        // Castling Rook Movement
        if (movingPiece == 'K') {
            if (fromRow == 7 && fromCol == 4 && toRow == 7 && toCol == 6) {
                board[7][5] = board[7][7];
                board[7][7] = '.';
            } else if (fromRow == 7 && fromCol == 4 && toRow == 7 && toCol == 2) {
                board[7][3] = board[7][0];
                board[7][0] = '.';
            }
        }

        if (movingPiece == 'k') {
            if (fromRow == 0 && fromCol == 4 && toRow == 0 && toCol == 6) {
                board[0][5] = board[0][7];
                board[0][7] = '.';
            } else if (fromRow == 0 && fromCol == 4 && toRow == 0 && toCol == 2) {
                board[0][3] = board[0][0];
                board[0][0] = '.';
            }
        }

        if (movingPiece == 'K') {
            whiteKingMoved = true;
        }
        if (movingPiece == 'k') {
            blackKingMoved = true;
        }
        if (movingPiece == 'R' && fromRow == 7 && fromCol == 7) {
            whiteRookMovedKingSide = true;
        }
        if (movingPiece == 'R' && fromRow == 7 && fromCol == 0) {
            whiteRookMovedQueenSide = true;
        }
        if (movingPiece == 'r' && fromRow == 0 && fromCol == 7) {
            blackRookMovedKingSide = true;
        }
        if (movingPiece == 'r' && fromRow == 0 && fromCol == 0) {
            blackRookMovedQueenSide = true;
        }
        // en passant
        enPassantRow = -1;
        enPassantCol = -1;
        if ((movingPiece == 'p' || movingPiece == 'P') && Math.abs(toRow - fromRow) == 2) {
            enPassantRow = (fromRow + toRow) / 2;
            enPassantCol = fromCol;
        }
        if (movingPiece == 'P' && fromCol != toCol && capturedPiece == '.') {
            board[toRow + 1][toCol] = '.';
        }
        if (movingPiece == 'p' && fromCol != toCol && capturedPiece == '.') {
            board[toRow - 1][toCol] = '.';
        }
    }

    public static void undoMove(char[][] board, int[] move) {
        int fromRow = move[0];
        int fromCol = move[1];
        int toRow = move[2];
        int toCol = move[3];
        char captured = (char) move[4];
        char movedPiece = (char) move[5];

        board[fromRow][fromCol] = movedPiece;
        board[toRow][toCol] = captured;
        //castling
        if (movedPiece == 'K') {
            if (fromRow == 7 && fromCol == 4 && toRow == 7 && toCol == 6) {
                board[7][7] = board[7][5];
                board[7][5] = '.';
            } else if (fromRow == 7 && fromCol == 4 && toRow == 7 && toCol == 2) {
                board[7][0] = board[7][3];
                board[7][3] = '.';
            }
        }

        if (movedPiece == 'k') {
            if (fromRow == 0 && fromCol == 4 && toRow == 0 && toCol == 6) {
                board[0][7] = board[0][5];
                board[0][5] = '.';
            } else if (fromRow == 0 && fromCol == 4 && toRow == 0 && toCol == 2) {
                board[0][0] = board[0][3];
                board[0][3] = '.';
            }
        }
        //en passant
        if (movedPiece == 'P' && fromCol != toCol && captured == '.') {
            board[toRow + 1][toCol] = 'p';
        }
        if (movedPiece == 'p' && fromCol != toCol && captured == '.') {
            board[toRow - 1][toCol] = 'P';
        }
    }

    public static ArrayList<int[]> filterLegalMoves(char[][] board, ArrayList<int[]> allMoves, boolean whiteToMove) {
        ArrayList<int[]> legalMoves = new ArrayList<>();

        for (int i = 0; i < allMoves.size(); i++) {
            int[] currentMove = allMoves.get(i);

            // Save old flag values
            boolean oldWhiteKingMoved = whiteKingMoved;
            boolean oldBlackKingMoved = blackKingMoved;
            boolean oldWhiteRookMovedKingSide = whiteRookMovedKingSide;
            boolean oldWhiteRookMovedQueenSide = whiteRookMovedQueenSide;
            boolean oldBlackRookMovedKingSide = blackRookMovedKingSide;
            boolean oldBlackRookMovedQueenSide = blackRookMovedQueenSide;
            int oldEnPassantRow = enPassantRow;
            int oldEnPassantCol = enPassantCol;

            Board.makeMove(board, currentMove);

            ArrayList<int[]> enemyMoves = new ArrayList<>();
            boolean enemyTurn = !whiteToMove;

            generateAllMoves(board, enemyTurn, enemyMoves);

            if (!King.isKingInCheck(board, whiteToMove, enemyMoves)) {
                legalMoves.add(currentMove);
            }

            Board.undoMove(board, currentMove);

            whiteKingMoved = oldWhiteKingMoved;
            blackKingMoved = oldBlackKingMoved;
            whiteRookMovedKingSide = oldWhiteRookMovedKingSide;
            whiteRookMovedQueenSide = oldWhiteRookMovedQueenSide;
            blackRookMovedKingSide = oldBlackRookMovedKingSide;
            blackRookMovedQueenSide = oldBlackRookMovedQueenSide;
            enPassantRow = oldEnPassantRow;
            enPassantCol = oldEnPassantCol;
        }

        return legalMoves;
    }

    public static void checkmate(ArrayList<int[]> legalMoves, char[][] board, boolean whiteToMove) {
        if (legalMoves.size() == 0) {
            ArrayList<int[]> enemyMoves = new ArrayList<>();
            boolean enemyTurn = !whiteToMove;
            generateAllMoves(board, enemyTurn, enemyMoves);
            if (King.isKingInCheck(board, whiteToMove, enemyMoves)) {
                System.out.println("CheckMate");
            } else {
                System.out.println("StaleMate");
            }
        }
    }

    public static void generateAllMoves(char[][] board, boolean whiteToMove, ArrayList<int[]> storeMoves) {
        Pawn.generatePawnMoves(board, storeMoves, whiteToMove);
        Knight.generateKnightMoves(board, storeMoves, whiteToMove);
        Bishop.generateBishopMoves(board, storeMoves, whiteToMove);
        Rook.generateRookMoves(board, storeMoves, whiteToMove);
        Queen.generateQueenMoves(board, storeMoves, whiteToMove);
        King.generateKingMoves(board, storeMoves, whiteToMove);
    }
    public static boolean whiteKingMoved = false;
    public static boolean whiteRookMovedKingSide = false;
    public static boolean blackKingMoved = false;
    public static boolean blackRookMovedKingSide = false;
    public static boolean whiteRookMovedQueenSide = false;
    public static boolean blackRookMovedQueenSide = false;
    static int enPassantRow = -1;
    static int enPassantCol = -1;
}
