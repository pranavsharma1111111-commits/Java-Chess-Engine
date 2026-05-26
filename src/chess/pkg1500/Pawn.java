        package chess.pkg1500;

import java.util.ArrayList;

class Pawn {
    public static void generatePawnMoves(char[][] board, ArrayList<int[]> storeMoves, boolean whiteToMove) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'p') {
                    char current = board[i][j];
                    if(whiteToMove && !Board.isWhite(current)){
                        continue;
                    }
                    if(!whiteToMove && !Board.isBlack(current)){
                        continue;
                    }
                    
                    int newRow = i + 1;
                    if (newRow >= 0 && newRow < 8) {
                        char target = board[newRow][j];
                        if (Board.isEmpty(target)) {
                                storeMoves.add(new int[]{i, j, newRow, j, board[newRow][j], board[i][j]});
                        }
                    }
                    int captLeft = j - 1;
                    int captRight = j + 1;
                    if (newRow >= 0 && newRow < 8) {
                        if (captLeft >= 0) {
                            char captTargetLeft = board[newRow][captLeft];
                            if (Board.isWhite(captTargetLeft)) {
                                storeMoves.add(new int[]{i, j, newRow, captLeft, board[newRow][captLeft], board[i][j]});
                            }
                            if(newRow == Board.enPassantRow && captLeft == Board.enPassantCol){
                                storeMoves.add(new int[]{i, j, newRow, captLeft, board[newRow][captLeft], board[i][j]});
                            }
                        }
                        if (captRight < 8) {
                            char captTargetRight = board[newRow][captRight];
                            if (Board.isWhite(captTargetRight)) {
                                storeMoves.add(new int[]{i, j, newRow, captRight, board[newRow][captRight], board[i][j]});

                            }
                            if(newRow == Board.enPassantRow && captRight == Board.enPassantCol){
                                storeMoves.add(new int[]{i, j, newRow, captRight, board[newRow][captRight], board[i][j]});
                            }
                        }
                    }
                    int move2 = i + 2;
                    if (i == 1 && move2 < 8) {
                        char target = board[move2][j];
                        if (Board.isEmpty(target) && Board.isEmpty(board[newRow][j])) {
                                storeMoves.add(new int[]{i, j, move2, j, board[move2][j], board[i][j]});

                        }
                    }
                } else if (board[i][j] == 'P') {
                    char current = board[i][j];
                    if(whiteToMove && !Board.isWhite(current)){
                        continue;
                    }
                    if(!whiteToMove && !Board.isBlack(current)){
                        continue;
                    }
                    int newRow = i - 1;
                    if (newRow >= 0 && newRow < 8) {
                        char target = board[newRow][j];
                        if (Board.isEmpty(target)) {
                                storeMoves.add(new int[]{i, j, newRow, j, board[newRow][j], board[i][j]});
                        }
                    }
                    int captLeft = j - 1;
                    int captRight = j + 1;

                    if (newRow >= 0 && newRow < 8) {

                        if (captLeft >= 0) {
                            char captTargetLeft = board[newRow][captLeft];
                            if (Board.isBlack(captTargetLeft)) {
                                storeMoves.add(new int[]{i, j, newRow, captLeft, board[newRow][captLeft], board[i][j]});
                            }
                            if(newRow == Board.enPassantRow && captLeft == Board.enPassantCol){
                                storeMoves.add(new int[]{i, j, newRow, captLeft, board[newRow][captLeft], board[i][j]});
                            }
                            
                        }

                        if (captRight < 8) {
                            char captTargetRight = board[newRow][captRight];
                            if (Board.isBlack(captTargetRight)) {
                                storeMoves.add(new int[]{i, j, newRow, captRight, board[newRow][captRight], board[i][j]});
                            }
                            if(newRow == Board.enPassantRow && captRight == Board.enPassantCol){
                                storeMoves.add(new int[]{i, j, newRow, captRight, board[newRow][captRight], board[i][j]});
                            }
                        }
                    }
                    int move2 = i - 2;
                    if (i == 6 && move2 >= 0) {
                        char target = board[move2][j];
                        if (Board.isEmpty(target) && Board.isEmpty(board[newRow][j])) {
                                storeMoves.add(new int[]{i, j, move2, j, board[move2][j], board[i][j]});

                        }
                    }
                }
            }
        }
    }
}
