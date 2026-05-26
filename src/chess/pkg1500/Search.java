package chess.pkg1500;

import java.util.*;

class Search {

    public static int[] findBestMoveDepth1(char[][] board, boolean whiteToMove) {
        int bestScore;
        int[] bestMove = null;
        ArrayList<int[]> allMoves = new ArrayList<>();
        Board.generateAllMoves(board, whiteToMove, allMoves);
        ArrayList<int[]> legalMoves = Board.filterLegalMoves(board, allMoves, whiteToMove);
        if (whiteToMove) {
            bestScore = Integer.MIN_VALUE;
        } else {
            bestScore = Integer.MAX_VALUE;
        }
        for (int i = 0; i < legalMoves.size(); i++) {
            int[] currentMove = legalMoves.get(i);
            boolean oldWhiteKingMoved = Board.whiteKingMoved;
            boolean oldBlackKingMoved = Board.blackKingMoved;
            boolean oldWhiteRookMovedKingSide = Board.whiteRookMovedKingSide;
            boolean oldWhiteRookMovedQueenSide = Board.whiteRookMovedQueenSide;
            boolean oldBlackRookMovedKingSide = Board.blackRookMovedKingSide;
            boolean oldBlackRookMovedQueenSide = Board.blackRookMovedQueenSide;

            int oldEnPassantRow = Board.enPassantRow;
            int oldEnPassantCol = Board.enPassantCol;
            Board.makeMove(board, currentMove);
            int score = Evaluation.evaluateBoard(board);
            Board.undoMove(board, currentMove);
            Board.whiteKingMoved = oldWhiteKingMoved;
            Board.blackKingMoved = oldBlackKingMoved;
            Board.whiteRookMovedKingSide = oldWhiteRookMovedKingSide;
            Board.whiteRookMovedQueenSide = oldWhiteRookMovedQueenSide;
            Board.blackRookMovedKingSide = oldBlackRookMovedKingSide;
            Board.blackRookMovedQueenSide = oldBlackRookMovedQueenSide;

            Board.enPassantRow = oldEnPassantRow;
            Board.enPassantCol = oldEnPassantCol;
            if (whiteToMove && score > bestScore) {
                bestScore = score;
                bestMove = currentMove;
            }
            if (!whiteToMove && score < bestScore) {
                bestScore = score;
                bestMove = currentMove;
            }
        }
        return bestMove;
    }  // trial minimax

    public static int minimax(char[][] board, int depth, boolean whiteToMove, int alpha, int beta) {    // actual minimax + alpha beta pruning
        if (depth == 0) {
            return Evaluation.evaluateBoard(board);
        }
        ArrayList<int[]> allMoves = new ArrayList<>();
        Board.generateAllMoves(board, whiteToMove, allMoves);
        ArrayList<int[]> legalMoves = Board.filterLegalMoves(board, allMoves, whiteToMove);
        Collections.sort(legalMoves, (a, b) ->
        moveScore(board, b) - moveScore(board, a));
        if (legalMoves.size() == 0) {
            ArrayList<int[]> enemyMoves = new ArrayList<>();
            Board.generateAllMoves(board, !whiteToMove, enemyMoves);
            if (King.isKingInCheck(board, whiteToMove, enemyMoves)) {
                if (whiteToMove) {
                    return -CHECKMATE_SCORE;
                } else {
                    return CHECKMATE_SCORE;
                }
            } else {
                return 0;
            }
        }
        int bestScore;
        if (whiteToMove) {
            bestScore = Integer.MIN_VALUE;
        } else {
            bestScore = Integer.MAX_VALUE;
        }
        for (int i = 0; i < legalMoves.size(); i++) {

            int[] currentMove = legalMoves.get(i);

            boolean oldWhiteKingMoved = Board.whiteKingMoved;
            boolean oldBlackKingMoved = Board.blackKingMoved;
            boolean oldWhiteRookMovedKingSide = Board.whiteRookMovedKingSide;
            boolean oldWhiteRookMovedQueenSide = Board.whiteRookMovedQueenSide;
            boolean oldBlackRookMovedKingSide = Board.blackRookMovedKingSide;
            boolean oldBlackRookMovedQueenSide = Board.blackRookMovedQueenSide;

            int oldEnPassantRow = Board.enPassantRow;
            int oldEnPassantCol = Board.enPassantCol;

            Board.makeMove(board, currentMove);

            int score = minimax(board, depth - 1, !whiteToMove, alpha, beta);
            Board.undoMove(board, currentMove);

            Board.whiteKingMoved = oldWhiteKingMoved;
            Board.blackKingMoved = oldBlackKingMoved;
            Board.whiteRookMovedKingSide = oldWhiteRookMovedKingSide;
            Board.whiteRookMovedQueenSide = oldWhiteRookMovedQueenSide;
            Board.blackRookMovedKingSide = oldBlackRookMovedKingSide;
            Board.blackRookMovedQueenSide = oldBlackRookMovedQueenSide;

            Board.enPassantRow = oldEnPassantRow;
            Board.enPassantCol = oldEnPassantCol;

            if (whiteToMove) {

                bestScore = Math.max(bestScore, score);

                alpha = Math.max(alpha, bestScore);

            } else {

                bestScore = Math.min(bestScore, score);

                beta = Math.min(beta, bestScore);

            }
            if (beta <= alpha) {
                break;
            }
        }

        return bestScore;
    }

    public static int[] findBestMove(char[][] board, boolean whiteToMove, int depth) {

        int bestScore;
        int[] bestMove = null;

        ArrayList<int[]> allMoves = new ArrayList<>();
        Board.generateAllMoves(board, whiteToMove, allMoves);

        ArrayList<int[]> legalMoves = Board.filterLegalMoves(board, allMoves, whiteToMove);
        Collections.sort(legalMoves, (a, b) ->
        moveScore(board, b) - moveScore(board, a));
        if (whiteToMove) {
            bestScore = Integer.MIN_VALUE;
        } else {
            bestScore = Integer.MAX_VALUE;
        }

        for (int i = 0; i < legalMoves.size(); i++) {

            int[] currentMove = legalMoves.get(i);

            boolean oldWhiteKingMoved = Board.whiteKingMoved;
            boolean oldBlackKingMoved = Board.blackKingMoved;
            boolean oldWhiteRookMovedKingSide = Board.whiteRookMovedKingSide;
            boolean oldWhiteRookMovedQueenSide = Board.whiteRookMovedQueenSide;
            boolean oldBlackRookMovedKingSide = Board.blackRookMovedKingSide;
            boolean oldBlackRookMovedQueenSide = Board.blackRookMovedQueenSide;

            int oldEnPassantRow = Board.enPassantRow;
            int oldEnPassantCol = Board.enPassantCol;

            Board.makeMove(board, currentMove);

            int score = minimax(board, depth - 1, !whiteToMove, Integer.MIN_VALUE, Integer.MAX_VALUE);

            Board.undoMove(board, currentMove);

            Board.whiteKingMoved = oldWhiteKingMoved;
            Board.blackKingMoved = oldBlackKingMoved;
            Board.whiteRookMovedKingSide = oldWhiteRookMovedKingSide;
            Board.whiteRookMovedQueenSide = oldWhiteRookMovedQueenSide;
            Board.blackRookMovedKingSide = oldBlackRookMovedKingSide;
            Board.blackRookMovedQueenSide = oldBlackRookMovedQueenSide;

            Board.enPassantRow = oldEnPassantRow;
            Board.enPassantCol = oldEnPassantCol;

            if (whiteToMove && score > bestScore) {
                bestScore = score;
                bestMove = currentMove;
            }

            if (!whiteToMove && score < bestScore) {
                bestScore = score;
                bestMove = currentMove;
            }

        }

        return bestMove;
    }

    public static int getBestScoreAfterMove(char[][] board, boolean whiteToMove) {
        int bestScore;
        ArrayList<int[]> allMoves = new ArrayList<>();
        Board.generateAllMoves(board, whiteToMove, allMoves);
        ArrayList<int[]> legalMoves = Board.filterLegalMoves(board, allMoves, whiteToMove);
        if (whiteToMove) {
            bestScore = Integer.MIN_VALUE;
        } else {
            bestScore = Integer.MAX_VALUE;
        }
        if (legalMoves.size() == 0) {
            ArrayList<int[]> enemyMoves = new ArrayList<>();
            Board.generateAllMoves(board, !whiteToMove, enemyMoves);

            if (King.isKingInCheck(board, whiteToMove, enemyMoves)) {
                if (whiteToMove) {
                    return -CHECKMATE_SCORE;
                } else {
                    return CHECKMATE_SCORE;
                }
            } else {
                return STALEMATE_SCORE;
            }
        }
        for (int i = 0; i < legalMoves.size(); i++) {
            int[] currentMove = legalMoves.get(i);
            boolean oldWhiteKingMoved = Board.whiteKingMoved;
            boolean oldBlackKingMoved = Board.blackKingMoved;
            boolean oldWhiteRookMovedKingSide = Board.whiteRookMovedKingSide;
            boolean oldWhiteRookMovedQueenSide = Board.whiteRookMovedQueenSide;
            boolean oldBlackRookMovedKingSide = Board.blackRookMovedKingSide;
            boolean oldBlackRookMovedQueenSide = Board.blackRookMovedQueenSide;

            int oldEnPassantRow = Board.enPassantRow;
            int oldEnPassantCol = Board.enPassantCol;
            Board.makeMove(board, currentMove);
            int score = Evaluation.evaluateBoard(board);
            Board.undoMove(board, currentMove);
            Board.whiteKingMoved = oldWhiteKingMoved;
            Board.blackKingMoved = oldBlackKingMoved;
            Board.whiteRookMovedKingSide = oldWhiteRookMovedKingSide;
            Board.whiteRookMovedQueenSide = oldWhiteRookMovedQueenSide;
            Board.blackRookMovedKingSide = oldBlackRookMovedKingSide;
            Board.blackRookMovedQueenSide = oldBlackRookMovedQueenSide;

            Board.enPassantRow = oldEnPassantRow;
            Board.enPassantCol = oldEnPassantCol;
            if (whiteToMove && score > bestScore) {
                bestScore = score;
            }
            if (!whiteToMove && score < bestScore) {
                bestScore = score;
            }
        }
        return bestScore;

    }
    static final int CHECKMATE_SCORE = 100000;
    static final int STALEMATE_SCORE = 0;
    public static int moveScore(char[][] board, int[] move) {

    int toRow = move[2];
    int toCol = move[3];

    char capturedPiece = board[toRow][toCol];

    if (capturedPiece == 'q' || capturedPiece == 'Q')
        return 90;

    if (capturedPiece == 'r' || capturedPiece == 'R')
        return 50;

    if (capturedPiece == 'b' || capturedPiece == 'B')
        return 30;

    if (capturedPiece == 'n' || capturedPiece == 'N')
        return 30;

    if (capturedPiece == 'p' || capturedPiece == 'P')
        return 10;

    return 0;
}
}
