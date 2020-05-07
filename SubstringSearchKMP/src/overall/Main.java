package overall;

import java.util.*;
import java.io.*; 

public class Main {

	static BufferedWriter myWriter = null;
	private final static int MAX_N = 100;
	
	public static void main(String[] args) {
		// if you try print the text, you might not able to see it in console since it is too long
		
		// Worst Case
		try {
			testWorstCase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Performance
//		doSearch(1, 100, "binary");
//		doSearch(1, 100, "DNA");
//		doSearch(1, 100, "a-z");
	}

	/**
	 * Helper function to run the search on different algorithms and record data to files
	 * NOTICE: MUST IINITIALIZE MYWRITER AND ASSIGN IT TO FILE BEFORE USE THIS METHOD
	 * NO ERROR CHECKING IS IMPLEMENTED SO FAR
	 * @param txt : the full searched string
	 * @param pat : the searched pattern
	 * @param N : current loop. Used for printing. Can ignore.
	 * @return true if run, false if io exception is caught
	 */
	private static boolean doSearchAndWriteToFile(String txt, String pat, int N){
		
		try {
			myWriter.write(String.format("--- SEARCH RESULT OF N %d ---\n", N));
			myWriter.write("Length of text: " + txt.length() + "\n");
			myWriter.write("Length of pattern: " + pat.length() + "\n");
			myWriter.write("\n");
	
			long startTime = System.currentTimeMillis();
			BruteForce bf = new BruteForce(pat);
			int bfIndex = bf.search(txt);
			long endTime = System.currentTimeMillis();
			long bfTime = endTime - startTime;
			
			startTime = System.currentTimeMillis();
			BruteForce2 bf2 = new BruteForce2(pat);
			int bf2Index = bf2.search(txt);
			endTime = System.currentTimeMillis();
			long bf2Time = endTime - startTime;
			
			startTime = System.currentTimeMillis();	
			KMP kmp = new KMP(pat);
			int kmpIndex = kmp.search(txt);
			endTime = System.currentTimeMillis();
			long kmpTime = endTime - startTime;

			startTime = System.currentTimeMillis();	
			KMP2 kmp2 = new KMP2(pat.toCharArray());
			int kmp2Index = kmp2.search(txt.toCharArray());
			endTime = System.currentTimeMillis();
			long kmp2Time = endTime - startTime;
			
			myWriter.write(String.format("%-20s %-20s %-24s %-20s %-20s %-20s %-20s\n", "MODEL", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));
			myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n", "BRUTE FORCE", bf.totalInspectTimes(), 0,  bf.timeUsed(), 0, bfTime, bfIndex));
			myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n", "BRUTE FORCE2", bf2.totalInspectTimes(), 0,  bf2.timeUsed(), 0, bf2Time, bf2Index));
			myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n", "KMP1", kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n", "KMP2", kmp2.searchInspectTimes(),kmp2.constructInspectTimes(), kmp2.searchtimeUsed(), kmp2.constructtimeUsed(), kmp2Time, kmp2Index));
			myWriter.write("\n");
		
		} catch (IOException e) {System.out.printf("Write Error at N %d", N); return false;}
		
		return true;
	}
	
	/**
	 * Helper function for repeat search on certain mode multiple times (a wrapper for looping)
	 * @param startIndex : starting index. Must be greater than 1
	 * @param endIndex : ending index.
	 * @param mode : binary, DNA, or a-z
	 */
	private static void doSearch(int startIndex, int endIndex, String mode) {
		
		Random rand = new Random();
//		int length = rand.nextInt(4194304-16384+1)+16384;
		int length = 4194304;
		
		System.out.println("RUNNING " + mode);
		
		String txt = Generater.generateRandomText(mode, length);
//		String pat = Generater.generatePattern(txt, 100);
		
//		String pat = Generater.generatePattern(mode, MAX_N);
		
		try {
			myWriter = new BufferedWriter(new FileWriter("SearchResult" + mode + ".txt", false));
		} catch (IOException e) {System.out.println("FILE IO ERROR" + mode); System.exit(-1);}
		
		for (int N = startIndex ; N < endIndex ; N ++) {
//			txt = Generater.generateRandomText(mode);
			String pat =  Generater.generatePattern(txt, N+1);
			doSearchAndWriteToFile(txt, pat.substring(0,N), N);
		}
		
//		int N = 1000000;
//		String pat =  Generater.generatePattern(txt, N+1);
//		doSearchAndWriteToFile(txt, pat.substring(0,N), N);
		
		if (myWriter != null)
			try {
				myWriter.close();
			} catch (IOException e) {
				System.out.println("FILE FAIL TO CLOSE" + mode);
				System.exit(-1);
			}
		
		myWriter = null;
		
		System.out.println("FINISHED RUNNING " + mode);
	}

	/**
	 * Helper function to test worst cases
	 * @throws IOException 
	 */
	private static void testWorstCase() throws IOException {
		
		// Test With Binary Mode
		testBinaryMode();
		
		// Test With A-Z Mode
		testAtoZMode();
	}
	
	/**
	 * Helper function to find worst case for binary text + patterns
	 * @throws IOException 
	 */
	private static void testBinaryMode() throws IOException {
		
		// Condition 1: TEXT - all 0, 
			// Condition 1.1 - find all 1
			// Condition 1.2 - find (N-1) 0 + 1 x 1
			// Condition 1.3 - find (N/2) 0 + 1 + (N/2) 0
			// Condition 1.4 - find random
		
		myWriter = new BufferedWriter(new FileWriter("SearchResult" + "BinaryWorstCase" + ".txt", false));

	
		myWriter.write("--- CONDITION 1.1 FIND ALL ONES ---\n\n");
		myWriter.write(String.format("%-20s %-20s %-24s %-20s %-20s %-20s %-20s\n", "MODEL", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		// Length of Pattern
		for (int i = 2; i <= 8192; i*=2) {
			myWriter.write("Current i : " + i + "\n");
			String txt1 = Generater.generateBinaryText("all zeros", i);
			String pattern = Generater.generateBinaryText("all ones", i);

			long startTime = System.currentTimeMillis();	
			KMP kmp = new KMP(pattern);
			int kmpIndex = kmp.search(txt1);
			long endTime = System.currentTimeMillis();
			long kmpTime = endTime - startTime;
			
			myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n\n", "KMP1", 
					kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
		}
		
		
		
		String txt2 = Generater.generateRandomText("binary", 8192);
		// Condition 2: TEXT - random
			// Condition 1.1 - find all 1
			// Condition 1.2 - find (N-1) 0 + 1 x 1
			// Condition 1.3 - find (N/2) 0 + 1 + (N/2) 0
			// Condition 1.4 - find random
		
		
		myWriter.close();
	}
	
	
	private static void testAtoZMode() {
		
	}
	
}
