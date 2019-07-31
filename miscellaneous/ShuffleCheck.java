package miscellaneous;

public class ShuffleCheck {
	
	public static boolean isShuffle(String x, String y, String z) {
		boolean[][] dpMatrix = new boolean[x.length()+1][y.length()+1];
		
		dpMatrix[0][0] = true;
		for (int i = 1; i < dpMatrix.length; i++) {
			dpMatrix[i][0] = dpMatrix[i-1][0] && x.charAt(i-1) == z.charAt(i-1);
		}
		for (int j = 1; j < dpMatrix[0].length; j++) {
			dpMatrix[0][j] = dpMatrix[0][j-1] && y.charAt(j-1) == z.charAt(j-1);
		}
		
		for (int i = 1; i < dpMatrix.length; i++) {
			for (int j = 1; j < dpMatrix[i].length; j++) {
				dpMatrix[i][j] = ( (x.charAt(i-1) == z.charAt(i+j-1)) && dpMatrix[i-1][j] ) ||
						( (y.charAt(j-1) == z.charAt(i+j-1) ) && dpMatrix[i][j-1] );
			}
		}
		
		return dpMatrix[x.length()][y.length()];
	}
	
	public static void main(String[] args) {
		System.out.println(isShuffle("chips","chocolate","cchocohilaptes"));
	}

}
