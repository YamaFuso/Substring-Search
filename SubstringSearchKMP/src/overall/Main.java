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
	 * 
	 * I just dont want to refactor this code... 
	 * Hardcoded all the possibilities we can come up with
	 * 
	 * 		// Condition 1: TEXT - all 0, 
			// Condition 1.1 - find all 1
			// Condition 1.2 - find (N-2) 0 + 1 + 0
			// Condition 1.3 - find (N/2) 0 + 1 + (N/2)0
			// Condition 1.4 - find random
			
	 * 		// Condition 2: TEXT - random
			// Condition 2.1 - find all 1
			// Condition 2.2 - find (N-2) 0 + 1 + 0
			// Condition 2.3 - find (N/2) 0 + 1 + (N/2)0
			// Condition 2.4 - find random
			
	 *      // Condition 3 : Condition 2 + Fixed Text
			String txt2 = Generater.generateRandomText("binary", 8192);
	 *
	 * 		// Condition 4: 101010
	 * 
	 * If you want to switch between KMP1 and KMP2 -> Select all body of this method, search for KMP/KMP2, 
	 * case sensitive, then replace with another one
	 */
	private static void testBinaryMode() throws IOException {
		
		// Condition 1: TEXT - all 0, 
			// Condition 1.1 - find all 1
			// Condition 1.2 - find (N-2) 0 + 1 + 0
			// Condition 1.3 - find (N/2) 0 + 1 + (N/2)0
			// Condition 1.4 - find random
		
		myWriter = new BufferedWriter(new FileWriter("SearchResult" + "BinaryWorstCaseCondition1" + ".txt", false));

		myWriter.write("--- CONDITION 1.1 FIND ALL ONES ---\n\n");
		myWriter.write(String.format("%-20s %-20s %-24s %-20s %-20s %-20s %-20s\n", "MODEL", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int i = 2; i <= 8192; i*=2) {
			myWriter.write("Text Length : " + i + "\n");
			String txt1 = Generater.generateBinaryText("all zeros", i);
			
			for (int j = 2; j <= i; j *=2) {
				myWriter.write("Pattern Length : " + j + "\n");
				String pattern = Generater.generateBinaryText("all ones", j);
	
				long startTime = System.currentTimeMillis();	
				KMP2 kmp = new KMP2(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
				myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n\n", "KMP21", 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			myWriter.write("\n");
		}
		
		myWriter.write("--- CONDITION 1.2 FIND (N-2) 0 + 1 + 0 ---\n\n");
		myWriter.write(String.format("%-20s %-20s %-24s %-20s %-20s %-20s %-20s\n", "MODEL", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int i = 2; i <= 8192; i*=2) {
			myWriter.write("Text Length : " + i + "\n");
			String txt1 = Generater.generateBinaryText("all zeros", i);
			
			for (int j = 2; j <= i; j *=2) {
				myWriter.write("Pattern Length : " + j + "\n");
				String pattern = Generater.generateBinaryText("all zeros", j - 2) + "10";
				
				long startTime = System.currentTimeMillis();	
				KMP2 kmp = new KMP2(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
				myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n\n", "KMP21", 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			myWriter.write("\n");
		}
		
		myWriter.write("--- CONDITION 1.3 FIND N*0 IN (N-1) 0 + 1 + (N-1)0 ---\n\n");
		myWriter.write(String.format("%-20s %-20s %-24s %-20s %-20s %-20s %-20s\n", "MODEL", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int i = 2; i <= 8192; i*=2) {
			myWriter.write("Text Length : " + (i*2-1) + "\n");
			String txt1 = Generater.generateBinaryText("all zeros", i-1) + "1" + Generater.generateBinaryText("all zeros", i-1);
			
			for (int j = 2; j <= i; j *=2) {
				myWriter.write("Pattern Length : " + j + "\n");
				String pattern = Generater.generateBinaryText("all zeros", j);
				
				long startTime = System.currentTimeMillis();	
				KMP2 kmp = new KMP2(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
				myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n\n", "KMP21", 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			myWriter.write("\n");
		}
		
		myWriter.write("--- CONDITION 1.4 RANDOM ---\n\n");
		myWriter.write(String.format("%-20s %-20s %-24s %-20s %-20s %-20s %-20s\n", "MODEL", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int i = 2; i <= 8192; i*=2) {
			myWriter.write("Text Length : " + i + "\n");
			String txt1 = Generater.generateBinaryText("all zeros", i);
			
			for (int j = 2; j <= i; j *=2) {
				myWriter.write("Pattern Length : " + j + "\n");
				String pattern = Generater.generateBinaryText("random", j);
				
				long startTime = System.currentTimeMillis();	
				KMP2 kmp = new KMP2(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
				myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n\n", "KMP21", 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			myWriter.write("\n");
		}
		
		myWriter.close();
		
		// Condition 2: TEXT - random
			// Condition 2.1 - find all 1
			// Condition 2.2 - find (N-2) 0 + 1 + 0
			// Condition 2.3 - find (N/2) 0 + 1 + (N/2)0
			// Condition 2.4 - find random
		
		
		myWriter = new BufferedWriter(new FileWriter("SearchResult" + "BinaryWorstCaseCondition2" + ".txt", false));

		
		myWriter.write("--- CONDITION 2.1 FIND ALL ONES ---\n\n");
		myWriter.write(String.format("%-20s %-20s %-24s %-20s %-20s %-20s %-20s\n", "MODEL", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int i = 2; i <= 8192; i*=2) {
			myWriter.write("Text Length : " + i + "\n");
			String txt1 = Generater.generateBinaryText("random", i);
			
			for (int j = 2; j <= i; j *=2) {
				myWriter.write("Pattern Length : " + j + "\n");
				String pattern = Generater.generateBinaryText("all ones", j);
	
				long startTime = System.currentTimeMillis();	
				KMP2 kmp = new KMP2(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
				myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n\n", "KMP21", 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			myWriter.write("\n");
		}
		
		myWriter.write("--- CONDITION 2.2 FIND (N-2) 0 + 1 + 0 ---\n\n");
		myWriter.write(String.format("%-20s %-20s %-24s %-20s %-20s %-20s %-20s\n", "MODEL", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int i = 2; i <= 8192; i*=2) {
			myWriter.write("Text Length : " + i + "\n");
			String txt1 = Generater.generateBinaryText("random", i);
			
			for (int j = 2; j <= i; j *=2) {
				myWriter.write("Pattern Length : " + j + "\n");
				String pattern = Generater.generateBinaryText("all zeros", j - 2) + "10";
				
				long startTime = System.currentTimeMillis();	
				KMP2 kmp = new KMP2(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
				myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n\n", "KMP21", 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			myWriter.write("\n");
		}
		
		myWriter.write("--- CONDITION 2.3 FIND N*0 IN (N-1) 0 + 1 + (N-1)0 ---\n\n");
		myWriter.write(String.format("%-20s %-20s %-24s %-20s %-20s %-20s %-20s\n", "MODEL", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int i = 2; i <= 8192; i*=2) {
			myWriter.write("Text Length : " + (i*2-1) + "\n");
			String txt1 = Generater.generateBinaryText("random", i-1) + "1" + Generater.generateBinaryText("random", i-1);
			
			for (int j = 2; j <= i; j *=2) {
				myWriter.write("Pattern Length : " + j + "\n");
				String pattern = Generater.generateBinaryText("all zeros", j);
				
				long startTime = System.currentTimeMillis();	
				KMP2 kmp = new KMP2(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
				myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n\n", "KMP21", 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			myWriter.write("\n");
		}
		
		myWriter.write("--- CONDITION 2.4 RANDOM ---\n\n");
		myWriter.write(String.format("%-20s %-20s %-24s %-20s %-20s %-20s %-20s\n", "MODEL", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int i = 2; i <= 8192; i*=2) {
			myWriter.write("Text Length : " + i + "\n");
			String txt1 = Generater.generateBinaryText("random", i);
			
			for (int j = 2; j <= i; j *=2) {
				myWriter.write("Pattern Length : " + j + "\n");
				String pattern = Generater.generateBinaryText("random", j);
				
				long startTime = System.currentTimeMillis();	
				KMP2 kmp = new KMP2(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
				myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n\n", "KMP21", 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			myWriter.write("\n");
		}
		
		myWriter.close();
		
		
		// Condition 3 : Condition 2 + Fixed Text
		String txt2 = Generater.generateRandomText("binary", 8192);

		myWriter = new BufferedWriter(new FileWriter("SearchResult" + "BinaryWorstCaseCondition3" + ".txt", false));

		
		myWriter.write("--- CONDITION 3.1 FIND ALL ONES ---\n\n");
		myWriter.write(String.format("%-20s %-20s %-24s %-20s %-20s %-20s %-20s\n", "MODEL", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int j = 2; j <= 8192; j *=2) {
			myWriter.write("Pattern Length : " + j + "\n");
			String pattern = Generater.generateBinaryText("all ones", j);

			long startTime = System.currentTimeMillis();	
			KMP2 kmp = new KMP2(pattern);
			int kmpIndex = kmp.search(txt2);
			long endTime = System.currentTimeMillis();
			long kmpTime = endTime - startTime;
			
			myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n\n", "KMP21", 
					kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
		}
		myWriter.write("\n");
		
		myWriter.write("--- CONDITION 3.2 FIND (N-2) 0 + 1 + 0 ---\n\n");
		myWriter.write(String.format("%-20s %-20s %-24s %-20s %-20s %-20s %-20s\n", "MODEL", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int j = 2; j <= 8192; j *=2) {
			myWriter.write("Pattern Length : " + j + "\n");
			String pattern = Generater.generateBinaryText("all zeros", j - 2) + "10";
			
			long startTime = System.currentTimeMillis();	
			KMP2 kmp = new KMP2(pattern);
			int kmpIndex = kmp.search(txt2);
			long endTime = System.currentTimeMillis();
			long kmpTime = endTime - startTime;
			
			myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n\n", "KMP21", 
					kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
		}
		myWriter.write("\n");
		
		myWriter.write("--- CONDITION 3.3 FIND N*0 IN (N-1) 0 + 1 + (N-1)0 ---\n\n");
		myWriter.write(String.format("%-20s %-20s %-24s %-20s %-20s %-20s %-20s\n", "MODEL", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int j = 2; j <= 8192; j *=2) {
				myWriter.write("Pattern Length : " + j + "\n");
				String pattern = Generater.generateBinaryText("all zeros", j);
				
				long startTime = System.currentTimeMillis();	
				KMP2 kmp = new KMP2(pattern);
				int kmpIndex = kmp.search(txt2);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
				myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n\n", "KMP21", 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
		myWriter.write("\n");

		
		myWriter.write("--- CONDITION 3.4 RANDOM ---\n\n");
		myWriter.write(String.format("%-20s %-20s %-24s %-20s %-20s %-20s %-20s\n", "MODEL", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int j = 2; j <= 8192; j *=2) {
			myWriter.write("Pattern Length : " + j + "\n");
			String pattern = Generater.generateBinaryText("random", j);
			
			long startTime = System.currentTimeMillis();	
			KMP2 kmp = new KMP2(pattern);
			int kmpIndex = kmp.search(txt2);
			long endTime = System.currentTimeMillis();
			long kmpTime = endTime - startTime;
			
			myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n\n", "KMP21", 
					kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
		}
		myWriter.write("\n");
		
		
		myWriter.close();
		
		

	// Condition 4: 101010
	myWriter = new BufferedWriter(new FileWriter("SearchResult" + "BinaryWorstCaseCondition4" + ".txt", false));

	
	myWriter.write("--- CONDITION 4.1 FIND ALL ONES ---\n\n");
	myWriter.write(String.format("%-20s %-20s %-24s %-20s %-20s %-20s %-20s\n", "MODEL", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

	for (int i = 2; i <= 8192; i*=2) {
		myWriter.write("Text Length : " + i + "\n");
		String txt1 = Generater.generateBinaryText("101010", i);
		
		for (int j = 2; j <= i; j *=2) {
			myWriter.write("Pattern Length : " + j + "\n");
			String pattern = Generater.generateBinaryText("all ones", j);

			long startTime = System.currentTimeMillis();	
			KMP2 kmp = new KMP2(pattern);
			int kmpIndex = kmp.search(txt1);
			long endTime = System.currentTimeMillis();
			long kmpTime = endTime - startTime;
			
			myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n\n", "KMP21", 
					kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
		}
		myWriter.write("\n");
	}
	
	myWriter.write("--- CONDITION 4.2 FIND (N-2) 0 + 1 + 0 ---\n\n");
	myWriter.write(String.format("%-20s %-20s %-24s %-20s %-20s %-20s %-20s\n", "MODEL", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

	for (int i = 2; i <= 8192; i*=2) {
		myWriter.write("Text Length : " + i + "\n");
		String txt1 = Generater.generateBinaryText("101010", i);
		
		for (int j = 2; j <= i; j *=2) {
			myWriter.write("Pattern Length : " + j + "\n");
			String pattern = Generater.generateBinaryText("all zeros", j - 2) + "10";
			
			long startTime = System.currentTimeMillis();	
			KMP2 kmp = new KMP2(pattern);
			int kmpIndex = kmp.search(txt1);
			long endTime = System.currentTimeMillis();
			long kmpTime = endTime - startTime;
			
			myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n\n", "KMP21", 
					kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
		}
		myWriter.write("\n");
	}
	
	myWriter.write("--- CONDITION 4.3 FIND N*0 IN (N-1) 0 + 1 + (N-1)0 ---\n\n");
	myWriter.write(String.format("%-20s %-20s %-24s %-20s %-20s %-20s %-20s\n", "MODEL", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

	for (int i = 2; i <= 8192; i*=2) {
		String txt1 = Generater.generateBinaryText("101010", i-1) + "1" + Generater.generateBinaryText("101010", i-1);
		myWriter.write("Text Length : " + txt1.length() + "\n");
		
		
		for (int j = 2; j <= i; j *=2) {
			myWriter.write("Pattern Length : " + j + "\n");
			String pattern = Generater.generateBinaryText("all zeros", j);
			
			long startTime = System.currentTimeMillis();	
			KMP2 kmp = new KMP2(pattern);
			int kmpIndex = kmp.search(txt1);
			long endTime = System.currentTimeMillis();
			long kmpTime = endTime - startTime;
			
			myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n\n", "KMP21", 
					kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
		}
		myWriter.write("\n");
	}
	
	myWriter.write("--- CONDITION 4.4 RANDOM ---\n\n");
	myWriter.write(String.format("%-20s %-20s %-24s %-20s %-20s %-20s %-20s\n", "MODEL", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

	for (int i = 2; i <= 8192; i*=2) {
		myWriter.write("Text Length : " + i + "\n");
		String txt1 = Generater.generateBinaryText("101010", i);
		
		for (int j = 2; j <= i; j *=2) {
			myWriter.write("Pattern Length : " + j + "\n");
			String pattern = Generater.generateBinaryText("random", j);
			
			long startTime = System.currentTimeMillis();	
			KMP2 kmp = new KMP2(pattern);
			int kmpIndex = kmp.search(txt1);
			long endTime = System.currentTimeMillis();
			long kmpTime = endTime - startTime;
			
			myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n\n", "KMP21", 
					kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
		}
		myWriter.write("\n");
	}
	
	myWriter.write("--- CONDITION 4.5 FIND ABB in ABABAB ---\n\n");
	myWriter.write(String.format("%-20s %-20s %-24s %-20s %-20s %-20s %-20s\n", "MODEL", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

	for (int i = 2; i <= 8192; i*=2) {
		String txt1 = Generater.generateBinaryText("101010", i) + "100";
		myWriter.write("Text Length : " + txt1.length() + "\n");
		
		String pattern = "001";
		
		long startTime = System.currentTimeMillis();	
		KMP2 kmp = new KMP2(pattern);
		int kmpIndex = kmp.search(txt1);
		long endTime = System.currentTimeMillis();
		long kmpTime = endTime - startTime;
		
		myWriter.write(String.format("%-20s %-20d %-24d %-20d %-20d %-20d %-20d\n\n", "KMP21", 
				kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));

		myWriter.write("\n");
	}
	
	myWriter.close();
		
	}
	
	
	private static void testAtoZMode() throws IOException {
		
		myWriter = new BufferedWriter(new FileWriter("SearchResult" + "AtoZWorstCase" + ".txt", false));
		
		String str = "abcdefghijklmnopqrstuvwxy";
		String pat = str + "z";
		
		String txt = new String(new char[200]).replace("\0", str) + "z";
		
		doSearchAndWriteToFile(txt, pat, 1);
		
		myWriter.close();
		
	}
	
}
