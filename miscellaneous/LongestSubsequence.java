package miscellaneous;
//non-object oriented static class for finding longest matching subsequences and substrings of arbitrary arrays
//making this static was a mistake ><
public class LongestSubsequence {
	private static final int MATCH = 0;
	private static final int SWAP = 1;
	private static final int INSERT = 2;
	private static final int DELETE = 3;
	
	public static Answer getEdits(int[] pattern, int[] text) {
		int[][] dpMatrix = new int[pattern.length+1][text.length+1];
		int[][] edits = new int[pattern.length+1][text.length+1];
		
		for (int i = 0; i < dpMatrix.length; i++) {
			dpMatrix[i][0] = i;
			edits[i][0] = DELETE;
		}
		for (int j = 0; j < dpMatrix[0].length; j++) {
			dpMatrix[0][j] = j;
			edits[0][j] = INSERT;
		}
		edits[0][0] = -1;
		
		int[] cost = new int[4];
		for (int i = 1; i < dpMatrix.length; i++) {
			for (int j = 1; j < dpMatrix[i].length; j++) {
				cost[MATCH] = dpMatrix[i-1][j-1] + match(pattern[i-1],text[j-1]);
				cost[SWAP] = dpMatrix[i-1][j-1] + 5; //set to impossible cost for subsequence
				cost[INSERT] = dpMatrix[i][j-1] + 1;
				cost[DELETE] = dpMatrix[i-1][j] + 1;
				
				dpMatrix[i][j] = cost[MATCH];
				for (int k = 0; k < cost.length; k++) {
					if (cost[k] < dpMatrix[i][j]) { 
						dpMatrix[i][j] = cost[k];
						edits[i][j] = k;
					}
				}
			}
		}
		
		String editSequence = makeEditSequence(edits,edits.length-1,edits[0].length-1).toString();
		return new Answer(dpMatrix, edits, dpMatrix[dpMatrix.length-1][dpMatrix[0].length-1], editSequence, text, pattern);
	}
	
	static int match(int p, int t) {
		return (p == t ? 0:5); //non-match set to impossible cost
	}
	
	static StringBuilder makeEditSequence(int[][] edits, int i, int j) {
		StringBuilder result;
		if (edits[i][j] == -1) { return new StringBuilder(); }
		
		if (edits[i][j] == MATCH) { result = makeEditSequence(edits, i-1, j-1); }
		else if (edits[i][j] == SWAP) { result = makeEditSequence(edits, i-1, j-1); }
		else if (edits[i][j] == INSERT) { result = makeEditSequence(edits, i, j-1); }
		else if (edits[i][j] == DELETE) { result = makeEditSequence(edits, i-1, j); }
		else { throw new IllegalArgumentException("Non-edit sequence number requested."); }
		
		return result.append(num2String(edits[i][j]));
	}
	
	private static String num2String(int num) {
		if (num == MATCH) {return "M";}
		if (num == SWAP) {return "S";}
		if (num == INSERT) { return "I";}
		if (num == DELETE) { return "D";}
		else { throw new IllegalArgumentException("Non-edit sequence number requested."); }
	}
	
	public static int[] str2IntArray(String str) {
		int[] result = new int[str.length()];
		for (int i = 0; i < result.length; i++) {
			result[i] = (int) str.charAt(i);
		}
		return result;
	}
	
	public static int[] getLongestSubstring(Answer ans) {
		if (ans.bestEdits == null) { return null; }
		int count = 0;
		int max = 0;
		int index = 0;
		int bestIndex = 0;
		for (int i = 0; i < ans.bestEdits.length(); i ++) {
			char c = ans.bestEdits.charAt(i);
			if (c == 'D') { count = 0; }
			else if (c == 'M') { count++; index++; }
			else if (c == 'I') { count = 0; index++; }
			if (count > max) { max = count; bestIndex = index; }
		}
		index = bestIndex - max;
		int[] result = new int[max];
		for (int i = 0; i < max; i++) {
			result[i] = ans.text[index+i]; 
		}
		return result;
	}
	
	public static String intArray2String(int[] array) {
		char[] result = new char[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = (char) array[i];
		}
		return new String(result);
	}
	
	public static class Answer {
		int[] text;
		int[] pattern;
		int[][] costMatrix;
		int[][] editMatrix;
		int bestCost;
		String bestEdits;
		
		private Answer(int[][] cM, int[][] eM, int bC, String bE, int[] _text, int[] _pattern) {
			text = new int[_text.length];
			System.arraycopy(_text, 0, text, 0, _text.length);
			pattern = new int[_pattern.length];
			System.arraycopy(_pattern, 0, pattern, 0, _pattern.length);
			costMatrix = new int[cM.length][cM[0].length];
			for (int i = 0; i < cM.length; i++) {
				System.arraycopy(cM[i], 0, costMatrix[i], 0, cM[i].length);
			}
			editMatrix = new int[eM.length][eM[0].length];
			for (int i = 0; i < cM.length; i++) {
				System.arraycopy(eM[i], 0, editMatrix[i], 0, eM[i].length);
			}
			bestCost = bC;
			bestEdits = bE;
		}	
	}
	
	public static void main(String[] args) {
		int[] p = LongestSubsequence.str2IntArray("abcdefghjkj");
		int[] t = LongestSubsequence.str2IntArray("cdedbafghjzx");
		Answer result = LongestSubsequence.getEdits(p,t);
		int[] substring = LongestSubsequence.getLongestSubstring(result);
		System.out.println("Edit string: "+result.bestEdits);
		System.out.println("Pattern: "+LongestSubsequence.intArray2String(p));
		System.out.println("Text: "+LongestSubsequence.intArray2String(t));
		System.out.println("Longest substring: "+LongestSubsequence.intArray2String(substring));
	}
}
