package overall;

import java.util.*;
import java.io.*; 

public class Main {

	static BufferedWriter myWriter = null;
	
	public static void main(String[] args) {
		// if you try print the text, you might not able to see it in console since it is too long
		
		
		doSearch(1, 100, "binary");
		doSearch(1, 100, "DNA");
		doSearch(1, 100, "a-z");
	
		


		
		
		
//		System.out.println("--- Initializing ---");
//		
////		String txt = Generater.generateRandomText("binary");
////		String pat = Generater.generatePattern(txt);
//		
//		
//
//		System.out.println();
//		BruteForce bf = new BruteForce(pat);
//		KMP kmp = new KMP(pat);
//		KMP2 kmp2 = new KMP2(pat.toCharArray());
//		
//		System.out.println("--- Initailization Finished ---\n");
//		
//		
//		bf.search(txt);
//		kmp.search(txt);
//		kmp2.search(txt.toCharArray());
//		
//		System.out.println("--- SEARCH RESULT ---");
//		System.out.printf("%-16s %-16s %-16s\n", "MODEL", "INSPECT TIMES", "TIME USED(ms)");
//		System.out.printf("%-16s %-16s %-16s\n", "BRUTE FORCE", bf.totalInspectTimes(), bf.timeUsed());
//		System.out.printf("%-16s %-16s %-16s\n", "KMP1", kmp.totalInspectTimes(), kmp.timeUsed());
//		System.out.printf("%-16s %-16s %-16s\n", "KMP2", kmp2.totalInspectTimes(), kmp2.timeUsed());
		
		
		//        System.out.print("pattern: ");
		//        for (int i = 0; i < offset; i++)
		//            System.out.print(" ");
		//        System.out.println(pat);
	}

	private static boolean doSearchAndWriteToFile(String txt, String pat, int N){
		
		try {
			myWriter.write(String.format("--- SEARCH RESULT OF N %d ---", N));
			myWriter.write("Length of text: " + txt.length() + "\n");
			myWriter.write("Length of pattern: " + pat.length() + "\n");
			myWriter.write("\n");
	
			BruteForce bf = new BruteForce(pat);
			KMP kmp = new KMP(pat);
			KMP2 kmp2 = new KMP2(pat.toCharArray());
			
			bf.search(txt);
			kmp.search(txt);
			kmp2.search(txt.toCharArray());
			
			myWriter.write(String.format("%-16s %-16s %-16s\n", "MODEL", "INSPECT TIMES", "TIME USED(ms)"));
			myWriter.write(String.format("%-16s %-16s %-16s\n", "BRUTE FORCE", bf.totalInspectTimes(), bf.timeUsed()));
			myWriter.write(String.format("%-16s %-16s %-16s\n", "KMP1", kmp.totalInspectTimes(), kmp.timeUsed()));
			myWriter.write(String.format("%-16s %-16s %-16s\n", "KMP2", kmp2.totalInspectTimes(), kmp2.timeUsed()));
			myWriter.write("\n");
		
		} catch (IOException e) {System.out.printf("Write Error at N %d", N); return false;}
		
		return true;
	}
	
	private static void doSearch(int startIndex, int endIndex, String mode) {
		
		String txt;
		String pat;
		
		try {
			myWriter = new BufferedWriter(new FileWriter("SearchResult" + mode + ".txt", false));
		} catch (IOException e) {System.out.println("FILE IO ERROR" + mode); System.exit(-1);}
		
		for (int N = 1 ; N < 100 ; N ++) {
			
			txt = Generater.generateRandomText(mode);
			pat = Generater.generatePattern(N);
			
			doSearchAndWriteToFile(txt, pat, N);
		}
		
		
		if (myWriter != null)
			try {
				myWriter.close();
			} catch (IOException e) {
				System.out.println("FILE FAIL TO CLOSE" + mode);
				System.exit(-1);
			}
		
		myWriter = null;
		
	}
	

	
	
	
	
	
	
//============================================================================================================//	
//	
//    /**
//     * Generate a random string between length 2^14-2^22
//     * @param alphabet one of a-z, DNA or binary
//     * @return the generated string
//     */
//	public static String generateRandomText(String alphabet){
//		Random rand = new Random();
//		int length = rand.nextInt(4194304-16384+1)+16384;
//
//		if(alphabet.equals("a-z")) {
//			int asciiLeft = 97;
//			int asciiright = 122;
//
//			StringBuilder sb = new StringBuilder(length);
//			for (int i = 0; i < length; i++) {
//				int randomSelect = asciiLeft + rand.nextInt(asciiright - asciiLeft + 1);
//				sb.append((char) randomSelect);
//			}
//			String generatedString = sb.toString();
//
//			return generatedString;
//		} else if(alphabet.equals("binary")) {
//			int asciiLeft = 48;
//			int asciiright = 49;
//
//			StringBuilder sb = new StringBuilder(length);
//			for (int i = 0; i < length; i++) {
//				int randomSelect = asciiLeft + rand.nextInt(asciiright - asciiLeft + 1);
//				sb.append((char) randomSelect);
//			}
//			String generatedString = sb.toString();
//
//			return generatedString;
//		} else if(alphabet.equals("DNA")) {
//			char[] DNA = new char[]{'a','c','g','t'};
//
//			StringBuilder sb = new StringBuilder(length);
//			for (int i = 0; i < length; i++) {
//				int randomSelect = rand.nextInt(4);
//				sb.append(DNA[randomSelect]);
//			}
//			String generatedString = sb.toString();
//
//			return generatedString;
//		}
//		return "Please enter correct argument";
//	}
//	
//	
//	
//    /**
//     * Generate a pattern from the given text between length 2^14-2^22
//     * @param text the text we need to go through
//     * @return a pattern that in the text
//     */
//	public static String generatePattern(String text){
//		int textLength = text.length();
//		Random rand = new Random();
//		char[] c = text.toCharArray();
//		int length = rand.nextInt(textLength);
//		int position =  rand.nextInt(textLength-length);
//		StringBuilder sb = new StringBuilder(length);
//		for (int i = 0; i < length; i++) {
//			sb.append(c[position+i]);
//		}
//		return sb.toString();
//	}
//
//
//	/**
//	 * Brute force search through the text and look for first pattern
//	 * Also print the time used and position of the pattern
//	 * @param text the text needed to go through
//	 * @param pattern the pattern program is looing for
//	 * @return t/f pattern is found
//	 */
//	public static boolean bruteForce(String text, String pattern){
//		long startTime = System.currentTimeMillis();
//		char[] textChar = text.toCharArray();
//		char[] patternChar = pattern.toCharArray();
//
//		boolean found = false;
//
//		long textLength = text.length();
//		long patternLength = patternChar.length;
//		if(patternLength==0) return false;
//		for(int i = 0; i<=textLength-patternLength;i++){
//			if(textChar[i]==patternChar[0]){
//				found = true;
//				for (int j = 0; j < patternLength; j++) {
//					if(textChar[i+j]!=patternChar[j]){
//						found = false;
//						break;
//					}
//				}
//				if(found){
//					long endTime = System.currentTimeMillis();
//					System.out.println("Total time in ms(Brute Force): "+(endTime-startTime));
//					System.out.println("Position: "+i);
//					return true;
//				}
//			}
//		}
//		long endTime = System.currentTimeMillis();
//		System.out.println("Total time in ms: "+(endTime-startTime));
//		return false;
//	}
//
}
