
public class GreedyAI {
    //  BoardState Gstate;
    BoardState next;
    boolean player;
    int pit = -1;
    int n;//n pits

    public GreedyAI() {

    }

    public Result Greedy(BoardState s) {
        player = s.player;
        Result r = new Result();
        int i = 0;
        int max = -1;
        if (!player)   //player A
        {
            if (Refugee.isEnd(s)) {
                r.max = s.mancalaA;
                return r;
            }
            for (i = 1; i <= s.PITSNUMBER; i++) {
                if (s.pitsA[i - 1] == 0) continue;
                next = s.nextMoveState(i, 'a', 0);
                //  System.out.println(i+" "+next.pitsA[i]);
                if (next.player == s.player) {
                    //	 System.out.println("****");
                    r = Greedy(next);
                    r.pit = i;
                } else {
                    r.max = next.mancalaA;
                    r.pit = i;
                }
                if (r.max > max) {
                    max = r.max;
                    pit = r.pit;
                }
                // System.out.println(r.max+" "+r.pit);
            }
        }
        if (player)  //player B
        {
            for (i = 1; i <= s.PITSNUMBER; i++) {
                if (Refugee.isEnd(s)) {
                    r.max = s.mancalaB;
                    return r;
                }
                if (s.pitsB[i - 1] == 0)
                    continue;
                next = s.nextMoveState(i, 'b', 0);
                if (next.player == s.player) {

                    r = Greedy(next);

                    r.pit = i;
                } else {
                    r.max = next.mancalaB;
                    r.pit = i;
                }
                if (r.max > max) {
                    max = r.max;
                    pit = r.pit;

                }
            }


        }
        r.pit = pit;
        r.max = max;
        return r;
    }
}
