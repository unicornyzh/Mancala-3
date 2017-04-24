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

    int evaluateCapture(BoardState boardState){
        int couldBeCapturedA=0,couldBeCapturedB=0;
        for (int i = 1; i <= boardState.PITSNUMBER; i++) {
            if ((boardState.pitsA[i-1]==0)&&(boardState.pitsB[BoardState.PITSNUMBER-i]!=0)){
                couldBeCapturedA+=boardState.pitsB[BoardState.PITSNUMBER-1];
            }
            if ((boardState.pitsB[i-1]==0)&&(boardState.pitsA[BoardState.PITSNUMBER-i]!=0)){
                couldBeCapturedB+=boardState.pitsA[BoardState.PITSNUMBER-i];
            }
        }
        if (side==false){
            return couldBeCapturedA-couldBeCapturedB;
        }else{
            return couldBeCapturedB-couldBeCapturedA;
        }
    }
    double conclude(BoardState boardState){
        //return evaluateStore(boardState)*0.999+evaluateCapture(boardState)*0.0005+evaluateCell(boardState)*0.0005;
        return evaluateStore(boardState)*0.99+evaluateCell(boardState)*0.01;
        //return evaluateStore(boardState)*0.999+evaluateCapture(boardState)*0.001;
    }
}