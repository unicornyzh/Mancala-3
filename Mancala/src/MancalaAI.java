/**
 * Created by zhiqinhuang on 4/13/17.
 */

import java.util.ArrayList;
import java.util.List;

class MancalaAI {
    int treeDepth; // searching depth in treeDepth number
    List<Node> searchTree;
    boolean AISide; // AI takes which turn
    Evaluation evaluator;

    MancalaAI(boolean side) {
        this.AISide = side; // false means playing as Player A
        evaluator = new Evaluation(side);
        searchTree = new ArrayList<>();
        treeDepth = 5;
    }

    /**
     * initialise search tree and add current faced problem/situation as root node
     *
     * @param currentProblem
     */
    void init(BoardState currentProblem) {
        searchTree.clear();
        searchTree.add(new Node(currentProblem, 0, 0, 0)); // add root node to list
        searchTree.get(0).selfID = 0; // set root node ID as 0
    }

    private void expandNode(Node root) {
        for (int i = 1; i <= BoardState.PITSNUMBER; i++) {
            Node newNode;
            BoardState rootState = root.nodeBoard;
            if (rootState.player == false) {
                if (rootState.pitsA[i - 1] == 0) { // do not move empty pits
                    continue;
                }
                if (rootState.freeTurn == false) {
                    newNode = new Node(rootState.nextMoveState(i, 'a', 0), root.depth + 1, root.selfID, i);
                    searchTree.add(newNode);
                    newNode.selfID = searchTree.indexOf(newNode);
                    root.childrenIndices[i - 1] = newNode.selfID;
                }
            }
            if (rootState.player == false) {
                if (rootState.pitsA[i - 1] == 0) {
                    continue;
                }
                if (rootState.freeTurn == true) {
                    newNode = new Node(rootState.nextMoveState(i, 'a', 0), root.depth + 1, root.selfID, i);
                    searchTree.add(newNode);
                    newNode.selfID = searchTree.indexOf(newNode);
                    root.childrenIndices[i - 1] = newNode.selfID;
                }
            }
            if (rootState.player == true) {
                if (rootState.pitsB[i - 1] == 0) {
                    continue;
                }
                if (rootState.freeTurn == false) {
                    newNode = new Node(rootState.nextMoveState(i, 'b', 0), root.depth + 1, root.selfID, i);
                    searchTree.add(newNode);
                    newNode.selfID = searchTree.indexOf(newNode);
                    root.childrenIndices[i - 1] = newNode.selfID;
                }
            }
            if (rootState.player == true) {
                if (rootState.pitsB[i - 1] == 0) {
                    continue;
                }
                if (rootState.freeTurn == true) {
                    newNode = new Node(rootState.nextMoveState(i, 'b', 0), root.depth + 1, root.selfID, i);
                    searchTree.add(newNode);
                    newNode.selfID = searchTree.indexOf(newNode);
                    root.childrenIndices[i - 1] = newNode.selfID;
                }
            }
        }
    }

    /***
     * build the entire search tree after initialising root node
     * @param node
     */
    void buildTree(Node node) {
        expandNode(node);
        if (node.depth + 1 < treeDepth) {
            for (int eachIndex : node.childrenIndices) {
                if (eachIndex != -1) {
                    buildTree(searchTree.get(eachIndex));
                }
            }
        } else return;
    }

    /**
     * calculate the next move
     *
     * @return
     */
    int think() {
        double maxValue = -Double.MAX_VALUE;
        double eval = Double.MIN_EXPONENT;
        int result = 0; // the pit to move in next step
        int cm = 0;
        for (int nextMove : searchTree.get(0).childrenIndices) {
            if (nextMove != -1) {
                eval = miniMaxAlphaBeta(searchTree.get(nextMove), -Double.MAX_VALUE, Double.MAX_VALUE); // miniMax with alpha-beta pruning
//                eval=miniMax(searchTree.get(nextMove)); // normal version of miniMax algorithm
                cm = searchTree.get(nextMove).pitMoved;
                System.out.println(cm+" "+eval);
            }
            if (eval > maxValue) {
                maxValue = eval;
                result = cm;
            }
        }
        return result;
    }

    /**
     * minimax algorithm
     *
     * @param node
     * @return
     */
    private double miniMax(Node node) {
        double value, result;
        if (isLeaf(node)) {
            result = evaluator.conclude(node.nodeBoard);
            return result;
        } else {
            if (node.nodeBoard.player == AISide) {
                result = -Double.MAX_VALUE;
                for (int index : node.childrenIndices) {
                    if (index != -1) {
                        value = miniMax(searchTree.get(index));
                        if (value > result) {
                            result = value;
                        }
                    }
                }
                return result;
            } else {
                result = Double.MAX_VALUE;
                for (int index : node.childrenIndices) {
                    if (index != -1) {
                        value = miniMax(searchTree.get(index));
                        if (value < result) {
                            result = value;
                        }
                    }
                }
                return result;
            }
        }
    }

    /**
     * alpha-beta version minimax
     *
     * @param node
     * @param alpha
     * @param beta
     * @return
     */
    private double miniMaxAlphaBeta(Node node, double alpha, double beta) {
        double value, result;
        if (isLeaf(node)) {
            result = evaluator.conclude(node.nodeBoard);
            return result;
        } else {
            if (node.nodeBoard.player == AISide) {
                result = alpha;
                for (int index : node.childrenIndices) {
                    if (index != -1) {
                        value = miniMaxAlphaBeta(searchTree.get(index), result, beta);
                        if (value > result) {
                            result = value;
                        }
                        if (result > beta) return beta;
                    }
                }
                return result;
            } else {
                result = beta;
                for (int index : node.childrenIndices) {
                    if (index != -1) {
                        value = miniMaxAlphaBeta(searchTree.get(index), alpha, result);
                        if (value < result) {
                            result = value;
                        }
                        if (result < alpha) return alpha;
                    }
                }
                return result;
            }
        }
    }

    /**
     * check whether a node is leaf node
     *
     * @param node
     * @return
     */
    private boolean isLeaf(Node node) {
        for (int index : node.childrenIndices) {
            if (index != -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * change searching depth
     *
     * @param depth
     */
    void changeParameters(int depth) {
        this.treeDepth = depth;
    }
}