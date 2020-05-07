package overall;

import java.util.Random;

public class Generate {

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
}
