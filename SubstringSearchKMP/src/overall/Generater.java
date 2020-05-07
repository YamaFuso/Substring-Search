package overall;

import java.util.Random;

public class Generater {

	/**
     * Generate a random string between length 2^14-2^22
     * @param alphabet one of a-z, DNA or binary
     * @return the generated string
     */
	public static String generateRandomText(String alphabet) {
		Random rand = new Random();
		int length = rand.nextInt(4194304-16384+1)+16384;
		
		return generateRandomText(alphabet, length);
	}
	
	public static String generateRandomText(String alphabet, int length){
		Random rand = new Random();

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
			
			return generateBinaryText("random", length);
			
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
     * Generate a pattern from the given text between length 2^14-2^22
     * @param text the text we need to go through
     * @return a pattern that in the text
     */
	public static String generatePattern(String text, int length){
		int textLength = text.length();
		Random rand = new Random();
		char[] c = text.toCharArray();
		int position =  rand.nextInt(textLength-length);
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(c[position+i]);
		}
		return sb.toString();
	}
	
	/**
	 * Generate a random pattern of given length
	 * @param length
	 * @return
	 */
	public static String generatePattern(int length) {
		
		int asciiLeft = 48;
		int asciiright = 49;
		
		Random rand = new Random();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int randomSelect = asciiLeft + rand.nextInt(asciiright - asciiLeft + 1);
			sb.append((char) randomSelect);
		}
		
		return sb.toString();
	}
	
	/**
	 * Helper function to generate binary text
	 * @param mode : 1. "all zero" 2. "random"
	 * @param length
	 * @return
	 */
	public static String generateBinaryText(String mode, int length) {
		
		int asciiLeft = 48;
		int asciiright = 49;
		String generatedString = null;
		StringBuilder sb = new StringBuilder(length);
		
		if (mode.equals("all zeros")) {
			for (int i = 0; i < length; i++) {
				sb.append((char) asciiLeft);
			}
		} else if (mode.equals("all ones")) {
			for (int i = 0; i < length; i++) {
				sb.append((char) asciiright);
			}
		} else if (mode.equals("101010")) {
			for (int i = 0; i < length; i+=2) {
				sb.append((char) asciiLeft);
				sb.append((char) asciiright);
			}
		} else if (mode.equals("random")) {
			Random rand = new Random();
			for (int i = 0; i < length; i++) {
				int randomSelect = asciiLeft + rand.nextInt(asciiright - asciiLeft + 1);
				sb.append((char) randomSelect);
			}
		} 
		
		generatedString = sb.toString();

		return generatedString;
	}
}
