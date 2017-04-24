/**
 * Created by zhiqinhuang on 4/8/17.
 */
class BoardState implements Cloneable {
    static int PITSNUMBER; // the number of pits of each player
    int mancalaA, mancalaB;
    int[] pitsA = new int[PITSNUMBER], pitsB = new int[PITSNUMBER];
    boolean player; // the player in turn, false -> player A
    boolean freeTurn;

    /**
     * initialize board
     */
    BoardState() {
        this.mancalaA = 0;
        this.mancalaB = 0;
        this.player =true; // player A
        for (int i = 0; i < PITSNUMBER; i++) {
            this.pitsA[i] = 4;
            this.pitsB[i] = 4;
        }
        boolean freeTurn = false;
    }

    @Override
    public BoardState clone() throws CloneNotSupportedException {
        BoardState state;
        state = (BoardState) super.clone();
        state.pitsA = pitsA.clone();
        state.pitsB = pitsB.clone();
        return state;
    }

    /**
     * move marbles in n-th pit
     * which side moves
     * initial @param fromMancala as 0
     */
    void updateMove(int n, char side, int fromMancala) {
        if (n < 0 || n > BoardState.PITSNUMBER) {
            System.out.println("Pit does not exist. Range from 1 to " + BoardState.PITSNUMBER);
            return;
        }
        if (n != 0) {
            if (side == 'a') {
                if (this.pitsA[n - 1] == 0) {
                    System.out.println("No marbles in this pit");
                    return;
                }
                int surplus = this.moveInPitsA(n, 0);
                if (surplus > 1) {
                    this.mancalaA += 1;
                    this.updateMove(0, 'b', surplus - 1);
                }
                if (surplus == 1) {
                    this.mancalaA += 1;
                    this.freeTurn = true;
                }
                if (surplus == 0) {
                    this.player = !this.player;
                    this.freeTurn = false;
                }
            }
            if (side == 'b') {
                if (this.pitsB[n - 1] == 0) {
                    System.out.println("No marbles in this pit");
                    return;
                }
                int surplus = this.moveInPitsB(n, 0);
                if (surplus > 1) {
                    this.mancalaB += 1;
                    this.updateMove(0, 'a', surplus - 1);
                }
                if (surplus == 1) {
                    this.mancalaB += 1;
                    this.freeTurn = true;
                }
                if (surplus == 0) {
                    this.player = !this.player;
                    this.freeTurn = false;
                }
            }
        } else {
            if (side == 'a') {
                int surplus = this.moveInPitsA(n, fromMancala);
                if (surplus > 1) {
                    if (!this.player) {// is player A's turn
                        this.mancalaA += 1;
                        this.updateMove(0, 'b', surplus - 1);
                    } else {
                        this.updateMove(0, 'b', surplus);
                    }
                }
                if (surplus == 1) {
                    if (!this.player) {
                        this.mancalaA += 1;
                        this.freeTurn = true;
                    } else {
                        this.updateMove(0, 'b', 1);
                    }
                }
                if (surplus == 0) {
                    this.player = !this.player;
                    this.freeTurn = false;
                }
            }
            if (side == 'b') {
                int surplus = this.moveInPitsB(n, fromMancala);
                if (surplus > 1) {
                    if (this.player) {// is player A's turn
                        this.mancalaB += 1;
                        this.updateMove(0, 'a', surplus - 1);
                    } else {
                        this.updateMove(0, 'a', surplus);
                    }
                }
                if (surplus == 1) {
                    if (this.player) {
                        this.mancalaB += 1;
                        this.freeTurn = true;
                    } else {
                        this.updateMove(0, 'a', 1);
                    }
                }
                if (surplus == 0) {
                    this.player = !this.player;
                    this.freeTurn = false;
                }
            }
        }
    }

    /**
     * move marbles in pits of player A
     *
     * @param n move the n-th pit
     *          0 means from mancala
     *          fromMancala marbles from Mancala
     * @return the marbles left when it reaches the end
     */
    private int moveInPitsA(int n, int fromMancala) {
        if (n != 0) {
            int marbleNumber = this.pitsA[n - 1];
            this.pitsA[n - 1] = 0;
            int pitsLeft = BoardState.PITSNUMBER - n;
            if (marbleNumber <= pitsLeft) { // no marbles left to mancala and opposite pits
                if (pitsA[n + marbleNumber - 1] == 0 && this.player == false && pitsB[pitsLeft - marbleNumber] != 0) {
                    for (int i = n; i < n + marbleNumber; i++) {
                        this.pitsA[i] += 1;
                    }
                    this.mancalaA += this.pitsA[n + marbleNumber - 1] + this.pitsB[pitsLeft - marbleNumber];
                    this.pitsA[n + marbleNumber - 1] = 0;
                    this.pitsB[pitsLeft - marbleNumber] = 0;
                } else {
                    for (int i = n; i < n + marbleNumber; i++) {
                        this.pitsA[i] += 1;
                    }
                }
                return 0;
            } else {
                for (int i = n; i < BoardState.PITSNUMBER; i++) {
                    this.pitsA[i] += 1;
                }
                return marbleNumber - pitsLeft;
            }
        } else {
            int pitsLeft = BoardState.PITSNUMBER;
            if (fromMancala <= pitsLeft) { // no marbles left to mancala and opposite pits
                if (pitsA[fromMancala - 1] == 0 && this.player == false && pitsB[pitsLeft - fromMancala]!=0) {
                    for (int i = n; i < n + fromMancala; i++) {
                        this.pitsA[i] += 1;
                    }
                    this.mancalaA += this.pitsA[fromMancala - 1] + this.pitsB[pitsLeft - fromMancala];
                    this.pitsA[fromMancala - 1] = 0;
                    this.pitsB[pitsLeft - fromMancala] = 0;
                } else {
                    for (int i = n; i < n + fromMancala; i++) {
                        this.pitsA[i] += 1;
                    }
                }
                return 0;
            } else {
                for (int i = 0; i < BoardState.PITSNUMBER; i++) {
                    this.pitsA[i] += 1;
                }
                return fromMancala - pitsLeft;
            }
        }
    }

    /**
     * move marbles in pits of player B
     *
     * @param n move the n-th pit
     * @return the marbles left when it reaches the end
     */
    private int moveInPitsB(int n, int fromMancala) {
        if (n != 0) {
            int marbleNumber = this.pitsB[n - 1];
            this.pitsB[n - 1] = 0;
            int pitsLeft = BoardState.PITSNUMBER - n;
            if (marbleNumber <= pitsLeft) { // no marbles left to mancala and opposite pits
                if (pitsB[n + marbleNumber - 1] == 0 && this.player == true && pitsA[pitsLeft - marbleNumber] != 0) {
                    for (int i = n; i < n + marbleNumber; i++) {
                        this.pitsB[i] += 1;
                    }
                    this.mancalaB += this.pitsB[n + marbleNumber - 1] + this.pitsA[pitsLeft - marbleNumber];
                    this.pitsB[n + marbleNumber - 1] = 0;
                    this.pitsA[pitsLeft - marbleNumber] = 0;
                } else {
                    for (int i = n; i < n + marbleNumber; i++) {
                        this.pitsB[i] += 1;
                    }
                }
                return 0;
            } else {
                for (int i = n; i < BoardState.PITSNUMBER; i++) {
                    this.pitsB[i] += 1;
                }
                return marbleNumber - pitsLeft;
            }
        } else {
            int pitsLeft = BoardState.PITSNUMBER;
            if (fromMancala <= pitsLeft) { // no marbles left to mancala and opposite pits
                if (pitsB[fromMancala - 1] == 0 && this.player == true && pitsA[pitsLeft - fromMancala] != 0) {
                    for (int i = n; i < n + fromMancala; i++) {
                        this.pitsB[i] += 1;
                    }
                    this.mancalaB += this.pitsB[fromMancala - 1] + this.pitsA[pitsLeft - fromMancala];
                    this.pitsB[fromMancala - 1] = 0;
                    this.pitsA[pitsLeft - fromMancala] = 0;
                } else {
                    for (int i = n; i < n + fromMancala; i++) {
                        this.pitsB[i] += 1;
                    }
                }
                return 0;
            } else {
                for (int i = 0; i < BoardState.PITSNUMBER; i++) {
                    this.pitsB[i] += 1;
                }
                return fromMancala - pitsLeft;
            }
        }
    }

    /**
     * return state after one move for search tree
     * must initialize @param fromMancala as 0
     *
     * @param n           pit number
     * @param side        who takes next move
     * @param fromMancala
     * @return
     */
    BoardState nextMoveState(int n, char side, int fromMancala) {
        BoardState nextState = null;
        try {
            nextState = this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            System.out.println("Failed to generate new node");
        }
        if (n < 0 || n > BoardState.PITSNUMBER) {
            System.out.println("Pit does not exist. Range from 1 to " + BoardState.PITSNUMBER);
            return nextState;
        }
        if (n != 0) {
            if (side == 'a') {
                if (nextState.pitsA[n - 1] == 0) {
                    System.out.println("No marbles in this pit");
                    return nextState;
                }
                int surplus = nextState.moveInPitsA(n, 0);
                if (surplus > 1) {
                    nextState.mancalaA += 1;
                    nextState.updateMove(0, 'b', surplus - 1);
                }
                if (surplus == 1) {
                    nextState.mancalaA += 1;
                    nextState.freeTurn = true;
                }
                if (surplus == 0) {
                    nextState.player = !nextState.player;
                    nextState.freeTurn = false;
                }
            }
            if (side == 'b') {
                if (nextState.pitsB[n - 1] == 0) {
                    System.out.println("No marbles in this pit");
                    return nextState;
                }
                int surplus = nextState.moveInPitsB(n, 0);
                if (surplus > 1) {
                    nextState.mancalaB += 1;
                    nextState.updateMove(0, 'a', surplus - 1);
                }
                if (surplus == 1) {
                    nextState.mancalaB += 1;
                    nextState.freeTurn = true;
                }
                if (surplus == 0) {
                    nextState.player = !nextState.player;
                    nextState.freeTurn = false;
                }
            }
        } else {
            if (side == 'a') {
                int surplus = nextState.moveInPitsB(n, fromMancala);
                if (surplus > 1) {
                    if (!nextState.player) {// is player A's turn
                        nextState.mancalaA += 1;
                        nextState.updateMove(0, 'b', surplus - 1);
                    } else {
                        nextState.updateMove(0, 'b', surplus);
                    }
                }
                if (surplus == 1) {
                    if (!nextState.player) {
                        nextState.mancalaA += 1;
                        nextState.freeTurn = true;
                    } else {
                        nextState.updateMove(0, 'b', 1);
                    }
                }
                if (surplus == 0) {
                    nextState.player = !nextState.player;
                    nextState.freeTurn = false;
                }
            }
            if (side == 'b') {
                int surplus = nextState.moveInPitsB(n, fromMancala);
                if (surplus > 1) {
                    if (nextState.player) {// is player A's turn
                        nextState.mancalaB += 1;
                        nextState.updateMove(0, 'a', surplus - 1);
                    } else {
                        nextState.updateMove(0, 'a', surplus);
                    }
                }
                if (surplus == 1) {
                    if (nextState.player) {
                        nextState.mancalaB += 1;
                        nextState.freeTurn = true;
                    } else {
                        nextState.updateMove(0, 'a', 1);
                    }
                }
                if (surplus == 0) {
                    nextState.player = !nextState.player;
                    nextState.freeTurn = false;
                }
            }
        }
        return nextState;
    }
}