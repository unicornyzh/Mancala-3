/**
 * Created by zhiqinhuang on 4/16/17.
 */
class Evaluation {
    boolean side;
    Evaluation(boolean side) {
        this.side=side;
    }
    /**
     * update the evaluation value for a known board state
     * positive value means beneficial to player A
     *
     * @param boardState
     */
    int evaluateStore(BoardState boardState) {
        if (side==false) {
            return boardState.mancalaA - boardState.mancalaB;
        }else{
            return boardState.mancalaB - boardState.mancalaA;
        }
    }

    int evaluateCell(BoardState boardState) {
        int marblesInASide = 0, marblesInBSide = 0;
        for (int i = 1; i <= boardState.PITSNUMBER; i++) {
            marblesInASide += boardState.pitsA[i-1];
            marblesInBSide += boardState.pitsB[i-1];
        }
        if (side==false) {
            return marblesInASide - marblesInBSide;
        }else {
            return marblesInBSide-marblesInASide;
        }
    }
    double conclude(BoardState boardState){
        return evaluateStore(boardState);//*0.999+evaluateCell(boardState)*0.001;
    }
}