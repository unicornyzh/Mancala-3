
public class test {
	static MancalaAI playerA = new MancalaAI(false);
	static Result r=new Result();
	public static void main(String[] args){
		int[] nValues = {10,20,30,40,50,60,70};
		 for (int n : nValues)
		 {
			 int AIMove=0;
			 BoardState.PITSNUMBER=n;
			 BoardState boardState=new BoardState();
			 boardState.player=false;
			 playerA.changeParameters(3);
			 playerA.init(boardState);
             playerA.buildTree(playerA.searchTree.get(0));
             double t1 = System.currentTimeMillis();
             AIMove=playerA.think();     
			 double t2 = System.currentTimeMillis();
		     t1 = System.currentTimeMillis();
             AIMove=playerA.think();     
			 t2 = System.currentTimeMillis();
			 System.out.println(n + " " + (t2 - t1)+" "+"playerA move position: "+AIMove);
		 }
	}
}
