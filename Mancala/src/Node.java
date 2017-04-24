import java.util.Arrays;

/**
 * Created by zhiqinhuang on 4/21/17.
 */
class Node {
    BoardState nodeBoard;
    int depth;
    int pitMoved;
    int parentIndex;
    int selfID;
    int[] childrenIndices;

    Node(BoardState nodeBoard,
         int depth,
         int parentIndex,
         int movedIndex
    ) {
        this.nodeBoard = nodeBoard;
        this.depth = depth;
        this.parentIndex = parentIndex;
        childrenIndices = new int[BoardState.PITSNUMBER];
        Arrays.fill(this.childrenIndices,-1);
        this.pitMoved = movedIndex;
    }
}