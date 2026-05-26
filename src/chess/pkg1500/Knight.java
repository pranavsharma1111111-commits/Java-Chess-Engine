package chess.pkg1500;

import java.util.ArrayList;

class Knight {
    public static void generateKnightMoves(char[][] board, ArrayList<int[]> storeMoves, boolean whiteToMove) {
        int[][] moves = {{-2, -1}, {-2, 1},
        {-1, -2}, {-1, 2},
        {1, -2}, {1, 2},
        {2, -1}, {2, 1}};
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'n' || board[i][j] == 'N') {
                    char current = board[i][j];
                    if (whiteToMove && !Board.isWhite(current)){
                        continue;
                    }
                    if (!whiteToMove && !Board.isBlack(current)){
                        continue;
                    }
                    for (int k = 0; k < moves.length; k++) {
                        int newRow = i + moves[k][0];
                        int newCol = j + moves[k][1];
                        if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                            char target = board[newRow][newCol];
                            if (Board.isEmpty(target)) {
                                storeMoves.add(new int[]{i, j, newRow, newCol, board[newRow][newCol], board[i][j]});
                            } else if (Board.isBlack(current) && Board.isWhite(target)) {
                                storeMoves.add(new int[]{i, j, newRow, newCol, board[newRow][newCol], board[i][j]});
                            } else if (Board.isWhite(current) && Board.isBlack(target)) {
                                storeMoves.add(new int[]{i, j, newRow, newCol, board[newRow][newCol], board[i][j]});
                            }
                        }
                    }
                }
            }
        }
    }
}
