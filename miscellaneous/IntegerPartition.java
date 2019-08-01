package miscellaneous;

import java.util.ArrayList;

public class IntegerPartition {
	private int S;
	private int[] a;
	private ArrayList<Integer> I;
	
	public IntegerPartition(int[] array){
		a = array;
		S = 0;
		for (int num : array) {
			S += num;
		}
		S /= 2;
		I = new ArrayList<Integer>();
	}
	
	public ArrayList<Integer> computePartition(){
		if ( a.length % 2 == 1) { I = null; printFail(); return I;}
		int[][] dp = new int[S+1][a.length+1];
		int[][] parent = new int[S+1][a.length+1];
		
		for (int i = 0; i < S+1; i++) {
			for (int j = 0; j < a.length+1; j++) {
				if (i == 0 || j == 0) { 
					dp[i][j] = 0;
					parent[i][j] = 0;
				}
				else if (a[j-1] > i) {
					dp[i][j] = dp[i][j-1];
				}else {
					int keep = dp[i-a[j-1]][j-1] + a[j-1];
					int exclude = dp[i][j-1];
					if (keep > exclude) {
						dp[i][j] = keep;
						parent[i][j] = i-a[j-1];
					}else {
						dp[i][j] = exclude;
						parent[i][j] = i;
					}
				}
			}
		}
		generatePartition(dp, parent, S, a.length);
		return checkPartition();
	}
	
	private void generatePartition(int[][] dp, int[][] parent, int i, int j) {
		if (j == 0) {
			return; 
		}
		
		generatePartition(dp, parent, parent[i][j], j-1);
		
		if ( parent[i][j] < i ) {
			I.add(a[j-1]);
		}		
	}
	
	private void printFail() {
		System.out.println("No partition possible.");
	}
	
	private ArrayList<Integer> checkPartition() {
		int sum = 0;
		for (int i : I) {
			sum += i;
		}
		if ( sum != S ) { 
			printFail();
			return null;
		}
		return I;
	}
	
	public ArrayList<Integer> getPartition(){
		if ( I == null ) { printFail(); return null;} 
		return I;
	}
	
	public static void main(String[] args) {
		int[] array = {1,2,3,4,5,6,7,8};
		IntegerPartition myPartition = new IntegerPartition(array);
		myPartition.computePartition();
		System.out.println(myPartition.getPartition());
	}
}
