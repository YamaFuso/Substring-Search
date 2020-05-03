package Book;
import java.util.*;

public class main {

	public static void main(String[] args) {
		// if you try print the text, you might not able to see it in console since it is too long
	
		String txt = generateRandomText("binary");
		String pat = generatePattern(txt);
		bruteForce(txt, pat);
		KMP kmp = new KMP(pat);
		//        System.out.println("text: " + txt);
		int offset = kmp.search(txt);
		System.out.println("offset: " + offset);
		//        System.out.print("pattern: ");
		//        for (int i = 0; i < offset; i++)
		//            System.out.print(" ");
		//        System.out.println(pat);
	}

	
    /**
     * Generate a random string between length 2^14-2^22
     * @param alphabet one of a-z, DNA or binary
     * @return the generated string
     */
	public static String generateRandomText(String alphabet){
		Random rand = new Random();
		int length = rand.nextInt(4194304-16384+1)+16384;

		if(alphabet.equals("a-z")) {
			int asciiLeft = 97;
			int asciiright = 122;

			StringBuilder sb = new StringBuilder(length);
			for (int i = 0; i < length; i++) {
				int randomSelect = asciiLeft + rand.nextInt(asciiright - asciiLeft + 1);
				sb.append((char) randomSelect);
			}
			String generatedString = sb.toString();

			return generatedString;
		} else if(alphabet.equals("binary")) {
			int asciiLeft = 48;
			int asciiright = 49;

			StringBuilder sb = new StringBuilder(length);
			for (int i = 0; i < length; i++) {
				int randomSelect = asciiLeft + rand.nextInt(asciiright - asciiLeft + 1);
				sb.append((char) randomSelect);
			}
			String generatedString = sb.toString();

			return generatedString;
		} else if(alphabet.equals("DNA")) {
			char[] DNA = new char[]{'a','c','g','t'};

			StringBuilder sb = new StringBuilder(length);
			for (int i = 0; i < length; i++) {
				int randomSelect = rand.nextInt(4);
				sb.append(DNA[randomSelect]);
			}
			String generatedString = sb.toString();

			return generatedString;
		}
		return "Please enter correct argument";
	}
	
	
	
    /**
     * Generate a pattern from the given text between length 2^14-2^22
     * @param text the text we need to go through
     * @return a pattern that in the text
     */
	public static String generatePattern(String text){
		int textLength = text.length();
		Random rand = new Random();
		char[] c = text.toCharArray();
		int length = rand.nextInt(textLength);
		int position =  rand.nextInt(textLength-length);
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(c[position+i]);
		}
		return sb.toString();
	}


	/**
	 * Brute force search through the text and look for first pattern
	 * Also print the time used and position of the pattern
	 * @param text the text needed to go through
	 * @param pattern the pattern program is looing for
	 * @return t/f pattern is found
	 */
	public static boolean bruteForce(String text, String pattern){
		long startTime = System.currentTimeMillis();
		char[] textChar = text.toCharArray();
		char[] patternChar = pattern.toCharArray();

		boolean found = false;

		long textLength = text.length();
		long patternLength = patternChar.length;
		if(patternLength==0) return false;
		for(int i = 0; i<=textLength-patternLength;i++){
			if(textChar[i]==patternChar[0]){
				found = true;
				for (int j = 0; j < patternLength; j++) {
					if(textChar[i+j]!=patternChar[j]){
						found = false;
						break;
					}
				}
				if(found){
					long endTime = System.currentTimeMillis();
					System.out.println("Total time in ms(Brute Force): "+(endTime-startTime));
					System.out.println("Position: "+i);
					return true;
				}
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Total time in ms: "+(endTime-startTime));
		return false;
	}

}
