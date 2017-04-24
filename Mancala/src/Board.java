/**
 * Created by zhiqinhuang on 4/9/17.
 */
class Board {
    /**
     * print current state of the board
     * @param boardState
     */
    static void printBoard(BoardState boardState){
        for (int i=BoardState.PITSNUMBER-1;i>=0;i--){
            System.out.print(boardState.pitsB[i]+" ");
        }
        System.out.println();
        System.out.print(boardState.mancalaB+"  "+boardState.mancalaA);
        System.out.println();
        for (int i=0;i<boardState.PITSNUMBER;i++){
            System.out.print(boardState.pitsA[i]+" ");
        }
        System.out.println();
    }
}
