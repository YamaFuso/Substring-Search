package overall;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Experimenter {

	BufferedWriter myWriter = null;
	private final int MAX_N = 100;

	/**
	 * Helper function to run the search on different algorithms and record data to files
	 * @param txt : text string
	 * @param pat : pattern string
	 * @param algo : BF1/BF2/KMP1/KMP2
	 * @param writer : writer pointer
	 * @throws IOException
	 */
	private void doSearchAndWriteToFile(String txt, String pat, String algo, BufferedWriter writer) throws IOException{
		if (algo.equals("BF1")) {
			long startTime = System.currentTimeMillis();
			BruteForce bf = new BruteForce(pat);
			int bfIndex = bf.search(txt);
			long endTime = System.currentTimeMillis();
			long bfTime = endTime - startTime;
			writer.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n",  "", pat.length(), bf.totalInspectTimes(), 0,  bf.timeUsed(), 0, bfTime, bfIndex));
		} else if (algo.equals("BF2")) {
			long startTime = System.currentTimeMillis();
			BruteForce2 bf2 = new BruteForce2(pat);
			int bf2Index = bf2.search(txt);
			long endTime = System.currentTimeMillis();
			long bf2Time = endTime - startTime;
			writer.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n",  "", pat.length(), bf2.totalInspectTimes(), 0,  bf2.timeUsed(), 0, bf2Time, bf2Index));
		} else if (algo.equals("KMP1")) {
			long startTime = System.currentTimeMillis();	
			KMP kmp = new KMP(pat);
			int kmpIndex = kmp.search(txt);
			long endTime = System.currentTimeMillis();
			long kmpTime = endTime - startTime;
			writer.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n",  "", pat.length(), kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
		} else if (algo.equals("KMP2")) {
			long startTime = System.currentTimeMillis();	
			KMP2 kmp2 = new KMP2(pat.toCharArray());
			int kmp2Index = kmp2.search(txt.toCharArray());
			long endTime = System.currentTimeMillis();
			long kmp2Time = endTime - startTime;
			writer.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pat.length(), kmp2.searchInspectTimes(),kmp2.constructInspectTimes(), kmp2.searchtimeUsed(), kmp2.constructtimeUsed(), kmp2Time, kmp2Index));
		}
	
	}
	
	/**
	 * Helper function for repeat search on certain mode multiple times (a wrapper for looping)
	 * Added an assumed prerequisite : startIndex and endIndex are power of 2, and the loop will be for (int x = start; x < end; x*=2)
	 * @param startIndex : starting index. Must be greater than 1
	 * @param endIndex : ending index.
	 * @param mode : binary, DNA, or a-z
	 */
	public void doSearch(int startIndex, int endIndex, String mode) {
		
		System.out.println("RUNNING " + mode);
	
		try {
			BufferedWriter writerBF1 = new BufferedWriter(new FileWriter("BF1_search_" + mode + "_condition1" + ".txt", false));
			BufferedWriter writerBF2 = new BufferedWriter(new FileWriter("BF2_search_" + mode + "_condition1" + ".txt", false));
			BufferedWriter writerKMP1 = new BufferedWriter(new FileWriter("KMP1_search_" + mode + "_condition1" + ".txt", false));
			BufferedWriter writerKMP2 = new BufferedWriter(new FileWriter("KMP2_search_" + mode + "_condition1" + ".txt", false));
		
			writerBF1.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));
			writerBF2.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));
			writerKMP1.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));
			writerKMP2.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));
			// Loop for randomize TEXT
			for (int N = startIndex; N <= endIndex; N*=2) {
			
				String txt = Generater.generateRandomText(mode, N);
				writerBF1.write("Length of text: " + txt.length() + "\n");
				writerBF2.write("Length of text: " + txt.length() + "\n");
				writerKMP1.write("Length of text: " + txt.length() + "\n");
				writerKMP2.write("Length of text: " + txt.length() + "\n");
				
				// Loop for randomize PATTERN
				for (int i = startIndex; i <= N; i*=2) {
					
					// Condition 1: Guaranteed Existing Pattern 
					String pattern = Generater.generatePattern(txt, i);
					
					// Condition 2: Random Pattern
//					String pattern = Generater.generateRandomText(mode, i);
					
					doSearchAndWriteToFile(txt, pattern, "BF1", writerBF1);
					doSearchAndWriteToFile(txt, pattern, "BF2", writerBF2);
					doSearchAndWriteToFile(txt, pattern, "KMP1", writerKMP1);
					doSearchAndWriteToFile(txt, pattern, "KMP2", writerKMP2);
				}
			}
			
			writerBF1.close();
			writerBF2.close();
			writerKMP1.close();
			writerKMP2.close();
			
		} catch (IOException e) {System.out.println("FILE IO ERROR" + mode); System.exit(-1);}
		
		System.out.println("FINISHED RUNNING " + mode);
	}

	/**
	 * Helper function to test worst cases
	 * @throws IOException 
	 */
	private void testWorstCase() throws IOException {
		
		// Test With Binary Mode
		testBinaryMode();
		
		// Test With A-Z Mode
		for (int i = 0; i < 3; i++)
			testAtoZMode(i);
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
	private void testBinaryMode() throws IOException {
		
		// Condition 1: TEXT - all 0, 
			// Condition 1.1 - find all 1
			// Condition 1.2 - find (N-2) 0 + 1 + 0
			// Condition 1.3 - find (N/2) 0 + 1 + (N/2)0
			// Condition 1.4 - find random
		
		myWriter = new BufferedWriter(new FileWriter("SearchResult" + "BinaryWorstCaseCondition1" + ".txt", false));

		myWriter.write("--- CONDITION 1.1 FIND ALL ONES ---\n\n");
		myWriter.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int i = 2; i <= 8192; i*=2) {
			myWriter.write("Text Length : " + i + "\n");
			String txt1 = Generater.generateBinaryText("all zeros", i);
			
			for (int j = 2; j <= i; j *=2) {
				
				String pattern = Generater.generateBinaryText("all ones", j);
	
				long startTime = System.currentTimeMillis();	
				KMP kmp = new KMP(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
			myWriter.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pattern.length(), 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			
		}
		
		myWriter.write("--- CONDITION 1.2 FIND (N-2) 0 + 1 + 0 ---\n\n");
		myWriter.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int i = 2; i <= 8192; i*=2) {
			myWriter.write("Text Length : " + i + "\n");
			String txt1 = Generater.generateBinaryText("all zeros", i);
			
			for (int j = 2; j <= i; j *=2) {
				
				String pattern = Generater.generateBinaryText("all zeros", j - 2) + "10";
				
				long startTime = System.currentTimeMillis();	
				KMP kmp = new KMP(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
			myWriter.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pattern.length(), 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			
		}
		
		myWriter.write("--- CONDITION 1.3 FIND N*0 IN (N-1) 0 + 1 + (N-1)0 ---\n\n");
		myWriter.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int i = 2; i <= 8192; i*=2) {
			myWriter.write("Text Length : " + (i*2-1) + "\n");
			String txt1 = Generater.generateBinaryText("all zeros", i-1) + "1" + Generater.generateBinaryText("all zeros", i-1);
			
			for (int j = 2; j <= i; j *=2) {
				
				String pattern = Generater.generateBinaryText("all zeros", j);
				
				long startTime = System.currentTimeMillis();	
				KMP kmp = new KMP(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
			myWriter.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pattern.length(), 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			
		}
		
		myWriter.write("--- CONDITION 1.4 RANDOM ---\n\n");
		myWriter.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int i = 2; i <= 8192; i*=2) {
			myWriter.write("Text Length : " + i + "\n");
			String txt1 = Generater.generateBinaryText("all zeros", i);
			
			for (int j = 2; j <= i; j *=2) {
				
				String pattern = Generater.generateBinaryText("random", j);
				
				long startTime = System.currentTimeMillis();	
				KMP kmp = new KMP(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
			myWriter.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pattern.length(), 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			
		}
		
		myWriter.close();
		
		// Condition 2: TEXT - random
			// Condition 2.1 - find all 1
			// Condition 2.2 - find (N-2) 0 + 1 + 0
			// Condition 2.3 - find (N/2) 0 + 1 + (N/2)0
			// Condition 2.4 - find random
		
		
		myWriter = new BufferedWriter(new FileWriter("SearchResult" + "BinaryWorstCaseCondition2" + ".txt", false));

		
		myWriter.write("--- CONDITION 2.1 FIND ALL ONES ---\n\n");
		myWriter.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int i = 2; i <= 8192; i*=2) {
			myWriter.write("Text Length : " + i + "\n");
			String txt1 = Generater.generateBinaryText("random", i);
			
			for (int j = 2; j <= i; j *=2) {
				
				String pattern = Generater.generateBinaryText("all ones", j);
	
				long startTime = System.currentTimeMillis();	
				KMP kmp = new KMP(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
			myWriter.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pattern.length(), 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			
		}
		
		myWriter.write("--- CONDITION 2.2 FIND (N-2) 0 + 1 + 0 ---\n\n");
		myWriter.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int i = 2; i <= 8192; i*=2) {
			myWriter.write("Text Length : " + i + "\n");
			String txt1 = Generater.generateBinaryText("random", i);
			
			for (int j = 2; j <= i; j *=2) {
				
				String pattern = Generater.generateBinaryText("all zeros", j - 2) + "10";
				
				long startTime = System.currentTimeMillis();	
				KMP kmp = new KMP(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
			myWriter.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pattern.length(), 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			
		}
		
		myWriter.write("--- CONDITION 2.3 FIND N*0 IN (N-1) 0 + 1 + (N-1)0 ---\n\n");
		myWriter.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int i = 2; i <= 8192; i*=2) {
			myWriter.write("Text Length : " + (i*2-1) + "\n");
			String txt1 = Generater.generateBinaryText("random", i-1) + "1" + Generater.generateBinaryText("random", i-1);
			
			for (int j = 2; j <= i; j *=2) {
				
				String pattern = Generater.generateBinaryText("all zeros", j);
				
				long startTime = System.currentTimeMillis();	
				KMP kmp = new KMP(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
			myWriter.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pattern.length(), 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			
		}
		
		myWriter.write("--- CONDITION 2.4 RANDOM ---\n\n");
		myWriter.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int i = 2; i <= 8192; i*=2) {
			myWriter.write("Text Length : " + i + "\n");
			String txt1 = Generater.generateBinaryText("random", i);
			
			for (int j = 2; j <= i; j *=2) {
				
				String pattern = Generater.generateBinaryText("random", j);
				
				long startTime = System.currentTimeMillis();	
				KMP kmp = new KMP(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
			myWriter.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pattern.length(), 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			
		}
		
		myWriter.close();
		
		
		// Condition 3 : Condition 2 + Fixed Text
		String txt2 = Generater.generateRandomText("binary", 8192);

		myWriter = new BufferedWriter(new FileWriter("SearchResult" + "BinaryWorstCaseCondition3" + ".txt", false));

		
		myWriter.write("--- CONDITION 3.1 FIND ALL ONES ---\n\n");
		myWriter.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int j = 2; j <= 8192; j *=2) {
			
			String pattern = Generater.generateBinaryText("all ones", j);

			long startTime = System.currentTimeMillis();	
			KMP kmp = new KMP(pattern);
			int kmpIndex = kmp.search(txt2);
			long endTime = System.currentTimeMillis();
			long kmpTime = endTime - startTime;
			
		myWriter.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pattern.length(), 
					kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
		}
		
		
		myWriter.write("--- CONDITION 3.2 FIND (N-2) 0 + 1 + 0 ---\n\n");
		myWriter.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int j = 2; j <= 8192; j *=2) {
			
			String pattern = Generater.generateBinaryText("all zeros", j - 2) + "10";
			
			long startTime = System.currentTimeMillis();	
			KMP kmp = new KMP(pattern);
			int kmpIndex = kmp.search(txt2);
			long endTime = System.currentTimeMillis();
			long kmpTime = endTime - startTime;
			
		myWriter.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pattern.length(), 
					kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
		}
		
		
		myWriter.write("--- CONDITION 3.3 FIND N*0 IN (N-1) 0 + 1 + (N-1)0 ---\n\n");
		myWriter.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int j = 2; j <= 8192; j *=2) {
				
				String pattern = Generater.generateBinaryText("all zeros", j);
				
				long startTime = System.currentTimeMillis();	
				KMP kmp = new KMP(pattern);
				int kmpIndex = kmp.search(txt2);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
			myWriter.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pattern.length(), 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
		

		
		myWriter.write("--- CONDITION 3.4 RANDOM ---\n\n");
		myWriter.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int j = 2; j <= 8192; j *=2) {
			
			String pattern = Generater.generateBinaryText("random", j);
			
			long startTime = System.currentTimeMillis();	
			KMP kmp = new KMP(pattern);
			int kmpIndex = kmp.search(txt2);
			long endTime = System.currentTimeMillis();
			long kmpTime = endTime - startTime;
			
		myWriter.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pattern.length(), 
					kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
		}
		
		
		
		myWriter.close();
		
		

		// Condition 4: 101010
		myWriter = new BufferedWriter(new FileWriter("SearchResult" + "BinaryWorstCaseCondition4" + ".txt", false));
	
		
		myWriter.write("--- CONDITION 4.1 FIND ALL ONES ---\n\n");
		myWriter.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));
	
		for (int i = 2; i <= 8192; i*=2) {
			myWriter.write("Text Length : " + i + "\n");
			String txt1 = Generater.generateBinaryText("101010", i);
			
			for (int j = 2; j <= i; j *=2) {
				
				String pattern = Generater.generateBinaryText("all ones", j);
	
				long startTime = System.currentTimeMillis();	
				KMP kmp = new KMP(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
			myWriter.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pattern.length(), 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			
		}
		
		myWriter.write("--- CONDITION 4.2 FIND (N-2) 0 + 1 + 0 ---\n\n");
		myWriter.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));
	
		for (int i = 2; i <= 8192; i*=2) {
			myWriter.write("Text Length : " + i + "\n");
			String txt1 = Generater.generateBinaryText("101010", i);
			
			for (int j = 2; j <= i; j *=2) {
				
				String pattern = Generater.generateBinaryText("all zeros", j - 2) + "10";
				
				long startTime = System.currentTimeMillis();	
				KMP kmp = new KMP(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
			myWriter.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pattern.length(), 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			
		}
		
		myWriter.write("--- CONDITION 4.3 FIND N*0 IN (N-1) 0 + 1 + (N-1)0 ---\n\n");
		myWriter.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));
	
		for (int i = 2; i <= 8192; i*=2) {
			String txt1 = Generater.generateBinaryText("101010", i-1) + "1" + Generater.generateBinaryText("101010", i-1);
			myWriter.write("Text Length : " + txt1.length() + "\n");
			
			
			for (int j = 2; j <= i; j *=2) {
				
				String pattern = Generater.generateBinaryText("all zeros", j);
				
				long startTime = System.currentTimeMillis();	
				KMP kmp = new KMP(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
			myWriter.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pattern.length(), 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			
		}
		
		myWriter.write("--- CONDITION 4.4 RANDOM ---\n\n");
		myWriter.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));
	
		for (int i = 2; i <= 8192; i*=2) {
			myWriter.write("Text Length : " + i + "\n");
			String txt1 = Generater.generateBinaryText("101010", i);
			
			for (int j = 2; j <= i; j *=2) {
				
				String pattern = Generater.generateBinaryText("random", j);
				
				long startTime = System.currentTimeMillis();	
				KMP kmp = new KMP(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
			myWriter.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pattern.length(), 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			
		}
		
		myWriter.write("--- CONDITION 4.5 FIND ABB in ABABAB ---\n\n");
		myWriter.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));
	
		for (int i = 2; i <= 8192; i*=2) {
			String txt1 = Generater.generateBinaryText("101010", i) + "100";
			myWriter.write("Text Length : " + txt1.length() + "\n");
			
			String pattern = "001";
			
			long startTime = System.currentTimeMillis();	
			KMP kmp = new KMP(pattern);
			int kmpIndex = kmp.search(txt1);
			long endTime = System.currentTimeMillis();
			long kmpTime = endTime - startTime;
			
			myWriter.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pattern.length(), 
					kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
	
			
		}
		
		myWriter.close();
			
	}
	
	/**
	 * Helper function to find worst case for a-z text + patterns
	 * @param times current looping times for file names
	 * @throws IOException
	 */
	private void testAtoZMode(int times) throws IOException {
		
		// Condition 5: TEXT - RANDOM TEXT, 
			// Condition 5.1 - find all A
			// Condition 5.2 - find (N-2) A + B + A
			// Condition 5.3 - find (N/2) A + B + (N/2)A
			// Condition 5.4 - random text random pattern
		
		myWriter = new BufferedWriter(new FileWriter("SearchResult" + "AtoZWorstCaseCondition" + times + ".txt", false));

		myWriter.write(String.format("--- CONDITION %d.1 FIND ALL As ---\n\n", 5+times));
		myWriter.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int i = 2; i <= 8192; i*=2) {
			String txt1 = Generater.generateRandomText("a-z",i);
			myWriter.write("Text Length : " + txt1.length() + "\n");
			
			for (int j = 2; j <= i; j *=2) {
				
				String pattern = new String(new char[j]).replace("\0", "a");
				
				long startTime = System.currentTimeMillis();	
				KMP kmp = new KMP(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
			myWriter.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pattern.length(), 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			
		}
		
		myWriter.write(String.format("--- CONDITION %d.2 FIND (N-2) A + B + A ---\n\n", 5+times));
		myWriter.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int i = 2; i <= 8192; i*=2) {
			String txt1 = Generater.generateRandomText("a-z",i);
			myWriter.write("Text Length : " + txt1.length() + "\n");
			
			for (int j = 2; j <= i; j *=2) {
				
				String pattern = new String(new char[j - 2]).replace("\0", "a") + "ba";
				
				long startTime = System.currentTimeMillis();	
				KMP kmp = new KMP(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
			myWriter.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pattern.length(), 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			
		}
		
		myWriter.write(String.format("--- CONDITION %d.3 FIND (N-1) A + B + (N-1)A ---\n\n", 5+times));
		myWriter.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int i = 2; i <= 8192; i*=2) {
			String txt1 = Generater.generateRandomText("a-z",i);
			myWriter.write("Text Length : " + txt1.length() + "\n");
			
			
			for (int j = 2; j <= i; j *=2) {
				
				String pattern = new String(new char[j/2]).replace("\0", "a") + "b" + new String(new char[j/2]).replace("\0", "a");
				
				long startTime = System.currentTimeMillis();	
				KMP kmp = new KMP(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
			myWriter.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pattern.length(), 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			
		}
			
		myWriter.write(String.format("--- CONDITION %d.4 RANDOM PATTERN ---\n\n", 5+times));
		myWriter.write(String.format("%s\t %s\t %s\t %s\t %s\t %s\t %s\t %s\t\n", "", "PATTERN LENGTH", "SEARCH INSPECT TIMES", "CONSTRUCT INSPECT TIMES", "SEARCH TIME(ms)", "CONSTRUCT TIME(ms)", "TOTAL TIME(ms)", "FOUND POSITION"));

		for (int i = 2; i <= 8192; i*=2) {
			String txt1 = Generater.generateRandomText("a-z",i);
			myWriter.write("Text Length : " + txt1.length() + "\n");
			
			for (int j = 2; j <= i; j *=2) {
				
				String pattern = Generater.generateRandomText("a-z",j);
				
				long startTime = System.currentTimeMillis();	
				KMP kmp = new KMP(pattern);
				int kmpIndex = kmp.search(txt1);
				long endTime = System.currentTimeMillis();
				long kmpTime = endTime - startTime;
				
			myWriter.write(String.format("%s\t %d\t %d\t %d\t %d\t %d\t %d\t %d\t\n", "", pattern.length(), 
						kmp.searchInspectTimes(), kmp.constructInspectTimes(), kmp.searchtimeUsed(), kmp.constructtimeUsed(), kmpTime, kmpIndex));
			}
			
		}
		
		myWriter.close();
	
	}
}
