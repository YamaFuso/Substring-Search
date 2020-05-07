package overall;

public class KMP {

	private String pat;
	private int[][] dfa;
	private int counter;

	public KMP(String pat) { // Build DFA from pattern.
		this.pat = pat;
		int M = pat.length();
		int R = 256;
		dfa = new int[R][M];
		dfa[pat.charAt(0)][0] = 1;
		for (int X = 0, j = 1; j < M; j++) { // Compute dfa[][j].
			for (int c = 0; c < R; c++)
				dfa[c][j] = dfa[c][X]; // Copy mismatch cases.
			dfa[pat.charAt(j)][j] = j + 1; // Set match case.
			X = dfa[pat.charAt(j)][X]; // Update restart state.
		}
		//counter = 0;
	}

	public int search(String txt) { // Simulate operation of DFA on txt.
		int i, j, N = txt.length(), M = pat.length();
		for (i = 0, j = 0; i < N && j < M; i++)
			j = inspect(txt.charAt(i), j);
		if (j == M) {
			System.out.println("String found. Inspect times: " + counter);
			return i - M; // found (hit end of pattern)
		} else {
			System.out.println("String not found. Inspect times: " + counter);
			return N; // not found (hit end of text)
		}
	}

	private int inspect(char i, int j) {
		counter++;
		return dfa[i][j];
	}
}
