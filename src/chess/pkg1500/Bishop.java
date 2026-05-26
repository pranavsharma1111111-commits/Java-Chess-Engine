package chess.pkg1500;
import java.util.ArrayList;
class Bishop {
    public static void generateBishopMoves(char[][] board, ArrayList<int[]> storeMoves, boolean whiteToMove) {
        int[][] moves = {{-1, -1},
        {-1, 1},
        {1, -1},
        {1, 1}};
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                char piece = board[i][j];
                if (piece == 'B' || piece == 'b') {
                    char current = board[i][j];
                    if(whiteToMove && !Board.isWhite(current)){
                        continue;
                    }
                    if(!whiteToMove && !Board.isBlack(current)){
                        continue;
                    }
                    for (int k = 0; k < moves.length; k++) {
                        int newRow = i + moves[k][0];
                        int newCol = j + moves[k][1];
                        while (newRow >= 0 && newRow < board.length && newCol >= 0 && newCol < board[0].length) {
                            char target = board[newRow][newCol];
                            if (Board.isEmpty(target)) {
                                storeMoves.add(new int[]{i, j, newRow, newCol, board[newRow][newCol], board[i][j]});
                            } else if ((Board.isBlack(target) && Board.isWhite(current)) || (Board.isWhite(target) && Board.isBlack(current))) {
                                storeMoves.add(new int[]{i, j, newRow, newCol, board[newRow][newCol], board[i][j]});
                                break;
                            } else {
                                break;
                            }
                            newRow = newRow + moves[k][0];
                            newCol = newCol + moves[k][1];
                        }
                    }
                }
            }
        }
    }
}
