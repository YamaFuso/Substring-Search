// Reference: Sedgewick, Robert, and Kevin Wayne. Algorithms, Fourth Edition. Addison-Wesley Professional, 2011.

package overall;

public class KMP {

	private String pat;
	private int[][] dfa;
	
	private int counter;
	private int constructCounter;
	private long constructTime = -1;
	private long lastSearchTime = -1;
	
	public KMP(String pat) { // Build DFA from pattern.
		
    	long startTime = System.currentTimeMillis();
		
		this.pat = pat;
		int M = pat.length();
		int R = 256;
		dfa = new int[R][M];
		dfa[inspect(pat,0)][0] = 1;
		for (int X = 0, j = 1; j < M; j++) { // Compute dfa[][j].
			for (int c = 0; c < R; c++)
				dfa[c][j] = dfa[c][X]; // Copy mismatch cases.
			dfa[inspect(pat,j)][j] = j + 1; // Set match case.
			X = dfa[inspect(pat,j)][X]; // Update restart state.
		}
		
		
        long endTime = System.currentTimeMillis();
//        System.out.println();
//        System.out.println("KMP1: Inspect During Construct : " + counter);
//        System.out.println("KMP1: Construct Time: " + (endTime - startTime));
//        System.out.println();
        
        constructTime = endTime - startTime;
		constructCounter = counter;
		counter = 0;
	}

	public int search(String txt) { // Simulate operation of DFA on txt.
		
		long startTime = System.currentTimeMillis();
		
		int i, j, N = txt.length(), M = pat.length();
		for (i = 0, j = 0; i < N && j < M; i++)
			j = inspect(txt.charAt(i), j);
		if (j == M) {
//			System.out.println("String found. Inspect times: " + counter);
			long endTime = System.currentTimeMillis();
			lastSearchTime = endTime - startTime;
			return i - M; // found (hit end of pattern)
		} else {
//			System.out.println("String not found. Inspect times: " + counter);
			long endTime = System.currentTimeMillis();
			lastSearchTime = endTime - startTime;
			return -1; // not found (hit end of text)
		}
	}

	private int inspect(char i, int j) {
		counter++;
		return dfa[i][j];
	}
	
	private char inspect (String pat, int index) {
		counter++;
		return pat.charAt(index);
	}
	
	public int searchInspectTimes() { return counter;}
	
	public long searchtimeUsed() {return lastSearchTime;}
	
	public int constructInspectTimes() { return constructCounter;}
	
	public long constructtimeUsed() {return constructTime;}
	
}
