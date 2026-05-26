package chess.pkg1500;
public class Evaluation{
    public static int evaluateBoard(char[][] board){
        int score = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                char current = board[i][j];
                if(Board.isWhite(current)){
                    if(current == 'P'){
                        score += 1;
                    }
                    else if(current == 'N'){
                        score += 3;
                    }
                    else if(current == 'B'){
                        score += 3;
                    }
                    else if(current == 'R'){
                        score += 5;
                    }
                    else if(current == 'Q'){
                        score += 9;
                    }
                    else if(current == 'K'){
                        score += 0;
                    }
                }
                if(Board.isBlack(current)){
                    if(current == 'p'){
                        score -= 1;
                    }
                    else if(current == 'n'){
                        score -= 3;
                    }
                    else if(current == 'b'){
                        score -= 3;
                    }
                    else if(current == 'r'){
                        score -= 5;
                    }
                    else if(current == 'q'){
                        score -= 9;
                    }
                    else if(current == 'k'){
                        score -= 0;
                    }
                }
            }
        }
        return score;
    }
}