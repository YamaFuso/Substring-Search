package overall;

public class BruteForce {
	
	
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
