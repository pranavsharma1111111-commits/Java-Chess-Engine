package chess.pkg1500;

import java.util.ArrayList;

class King {

    public static void generateKingMoves(char[][] board, ArrayList<int[]> storeMoves, boolean whiteToMove) {
        int[][] moves = {
            {1, 0}, {0, 1}, {0, -1}, {-1, 0},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                char piece = board[i][j];

                if (piece != 'K' && piece != 'k') {
                    continue;
                }

                if (whiteToMove && !Board.isWhite(piece)) {
                    continue;
                }
                if (!whiteToMove && !Board.isBlack(piece)) {
                    continue;
                }

                // Normal king moves
                for (int k = 0; k < moves.length; k++) {
                    int newRow = i + moves[k][0];
                    int newCol = j + moves[k][1];

                    if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                        char target = board[newRow][newCol];

                        if (Board.isEmpty(target)
                                || (Board.isWhite(piece) && Board.isBlack(target))
                                || (Board.isBlack(piece) && Board.isWhite(target))) {

                            storeMoves.add(new int[]{i, j, newRow, newCol, target, piece});
                        }
                    }
                }

                // White castling
                if (piece == 'K' && whiteToMove) {
                    // Kingside
                    if (!Board.whiteKingMoved
                            && !Board.whiteRookMovedKingSide
                            && board[7][7] == 'R'
                            && Board.isEmpty(board[7][5])
                            && Board.isEmpty(board[7][6])
                            && !isSquareAttacked(board, 7, 4, false)
                            && !isSquareAttacked(board, 7, 5, false)
                            && !isSquareAttacked(board, 7, 6, false)) {

                        storeMoves.add(new int[]{7, 4, 7, 6, board[7][6], board[7][4]});
                    }

                    // Queenside
                    if (!Board.whiteKingMoved
                            && !Board.whiteRookMovedQueenSide
                            && board[7][0] == 'R'
                            && Board.isEmpty(board[7][1])
                            && Board.isEmpty(board[7][2])
                            && Board.isEmpty(board[7][3])
                            && !isSquareAttacked(board, 7, 4, false)
                            && !isSquareAttacked(board, 7, 3, false)
                            && !isSquareAttacked(board, 7, 2, false)) {

                        storeMoves.add(new int[]{7, 4, 7, 2, board[7][2], board[7][4]});
                    }
                }

                // Black castling
                // Black castling
                if (piece == 'k' && !whiteToMove) {
                    // Kingside
                    if (!Board.blackKingMoved
                            && !Board.blackRookMovedKingSide
                            && board[0][7] == 'r'
                            && Board.isEmpty(board[0][5])
                            && Board.isEmpty(board[0][6])
                            && !isSquareAttacked(board, 0, 4, true)
                            && !isSquareAttacked(board, 0, 5, true)
                            && !isSquareAttacked(board, 0, 6, true)) {

                        storeMoves.add(new int[]{0, 4, 0, 6, board[0][6], board[0][4]});
                    }

                    // Queenside
                    if (!Board.blackKingMoved
                            && !Board.blackRookMovedQueenSide
                            && board[0][0] == 'r'
                            && Board.isEmpty(board[0][1])
                            && Board.isEmpty(board[0][2])
                            && Board.isEmpty(board[0][3])
                            && !isSquareAttacked(board, 0, 4, true)
                            && !isSquareAttacked(board, 0, 3, true)
                            && !isSquareAttacked(board, 0, 2, true)) {

                        storeMoves.add(new int[]{0, 4, 0, 2, board[0][2], board[0][4]});
                    }
                }
            }
        }
    }

    public static boolean isKingInCheck(char[][] board, boolean whiteToMove, ArrayList<int[]> enemyMoves) {
        int kingRow = -1;
        int kingCol = -1;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (whiteToMove && board[i][j] == 'K') {
                    kingRow = i;
                    kingCol = j;
                }
                if (!whiteToMove && board[i][j] == 'k') {
                    kingRow = i;
                    kingCol = j;
                }
            }
        }

        if (whiteToMove) {
            return isSquareAttacked(board, kingRow, kingCol, false);
        } else {
            return isSquareAttacked(board, kingRow, kingCol, true);
        }
    }

    public static boolean isSquareAttacked(char[][] board, int row, int col, boolean byWhite) {

        // 1. Pawn attacks
        if (byWhite) {
            // white pawns attack upward
            if (row + 1 < 8 && col - 1 >= 0 && board[row + 1][col - 1] == 'P') {
                return true;
            }
            if (row + 1 < 8 && col + 1 < 8 && board[row + 1][col + 1] == 'P') {
                return true;
            }
        } else {
            // black pawns attack downward
            if (row - 1 >= 0 && col - 1 >= 0 && board[row - 1][col - 1] == 'p') {
                return true;
            }
            if (row - 1 >= 0 && col + 1 < 8 && board[row - 1][col + 1] == 'p') {
                return true;
            }
        }

        // 2. Knight attacks
        int[][] knightMoves = {
            {-2, -1}, {-2, 1},
            {-1, -2}, {-1, 2},
            {1, -2}, {1, 2},
            {2, -1}, {2, 1}
        };

        for (int i = 0; i < knightMoves.length; i++) {
            int newRow = row + knightMoves[i][0];
            int newCol = col + knightMoves[i][1];

            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                char piece = board[newRow][newCol];
                if (byWhite && piece == 'N') {
                    return true;
                }
                if (!byWhite && piece == 'n') {
                    return true;
                }
            }
        }

        // 3. King attacks
        int[][] kingMoves = {
            {1, 0}, {0, 1}, {0, -1}, {-1, 0},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        for (int i = 0; i < kingMoves.length; i++) {
            int newRow = row + kingMoves[i][0];
            int newCol = col + kingMoves[i][1];

            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                char piece = board[newRow][newCol];
                if (byWhite && piece == 'K') {
                    return true;
                }
                if (!byWhite && piece == 'k') {
                    return true;
                }
            }
        }

        // 4. Rook / Queen attacks (straight lines)
        int[][] rookDirections = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
        };

        for (int i = 0; i < rookDirections.length; i++) {
            int newRow = row + rookDirections[i][0];
            int newCol = col + rookDirections[i][1];

            while (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                char piece = board[newRow][newCol];

                if (piece != '.') {
                    if (byWhite && (piece == 'R' || piece == 'Q')) {
                        return true;
                    }
                    if (!byWhite && (piece == 'r' || piece == 'q')) {
                        return true;
                    }
                    break;
                }

                newRow += rookDirections[i][0];
                newCol += rookDirections[i][1];
            }
        }

        // 5. Bishop / Queen attacks (diagonals)
        int[][] bishopDirections = {
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        for (int i = 0; i < bishopDirections.length; i++) {
            int newRow = row + bishopDirections[i][0];
            int newCol = col + bishopDirections[i][1];

            while (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                char piece = board[newRow][newCol];

                if (piece != '.') {
                    if (byWhite && (piece == 'B' || piece == 'Q')) {
                        return true;
                    }
                    if (!byWhite && (piece == 'b' || piece == 'q')) {
                        return true;
                    }
                    break;
                }

                newRow += bishopDirections[i][0];
                newCol += bishopDirections[i][1];
            }
        }

        return false;
    }
}
