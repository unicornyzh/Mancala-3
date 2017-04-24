/**
 * Created by zhiqinhuang on 4/9/17.
 */
public class Mancala {
    public static void main(String[] args) {
        BoardState.PITSNUMBER = 9;//Integer.parseInt(args[0]);
        BoardState boardState = new BoardState();
        MancalaAI playerA = new MancalaAI(false);
        GreedyAI playerB = new GreedyAI();
        Result r = new Result();
        int AIMove=1+(int)(Math.random()*BoardState.PITSNUMBER);
        Board.printBoard(boardState);
        for (; ; ) {
        	
            if (!boardState.player) {
                playerA.init(boardState);
                playerA.buildTree(playerA.searchTree.get(0));
                AIMove=playerA.think();
                
                System.out.println("playerA move position: " + AIMove);
                //if(AIMove==0) break;
            } else {
                r = playerB.Greedy(boardState);
                System.out.println("playerB move position: " + r.pit + " " + r.max);
            }
            if (!boardState.player)
                boardState.updateMove(AIMove, 'a', 0);
            else
                boardState.updateMove(r.pit, 'b', 0);
            if (Refugee.isEnd(boardState)) {
                showResult(boardState);
                break;
            }
            Board.printBoard(boardState);
        }
    }

    /**
     * print game result on command line
     *
     * @param boardState
     */
    static void showResult(BoardState boardState) {
        int[] result = Refugee.judge(boardState);
        System.out.println();
        if (result[0] > result[1]) {
            System.out.println("player A won");
        }
        if (result[0] < result[1]) {
            System.out.println("player B won");
        }
        if (result[0] == result[1]) {
            System.out.println("draw");
        }
        System.out.println("    " + result[0] + ":" + result[1]);
    }
}
