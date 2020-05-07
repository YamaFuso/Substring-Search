package overall;



/**
 * Brute force search through the text and look for first pattern
 * Also print the time used and position of the pattern

 * @param pattern the pattern program is looing for
 * @return t/f pattern is found
 */
public class BruteForce {
	
	private String pattern;
	private int counter = -1;
	private long lastSearchTime = -1;
	
	public BruteForce(String pattern) {
		this.pattern = pattern;
	}
	
	/**
	 * Public API For Search
	 * @param text: text
	 * @return find or not
	 */
	public int search(String text){

		long startTime = System.currentTimeMillis();
		char[] textChar = text.toCharArray();
		char[] patternChar = pattern.toCharArray();

		boolean found = false;

		long textLength = text.length();
		long patternLength = patternChar.length;
		
		if(patternLength == 0) { 
			long endTime = System.currentTimeMillis();
			lastSearchTime = endTime - startTime; 
			return -1;
		}
		
		for(int i = 0; i<=textLength-patternLength;i++){
			if(inspect(textChar,i) == inspect(patternChar, 0)){
				found = true;
				for (int j = 0; j < patternLength; j++) {
					if(inspect(textChar , i+j) != inspect (patternChar, j)){
						found = false;
						break;
					}
				}
				if(found){
					long endTime = System.currentTimeMillis();
					lastSearchTime = endTime - startTime;
//					System.out.println("Found : Total time in ms(Brute Force): "+(endTime-startTime));
//					System.out.println("Total Inspection Times: " + counter);
					return i;
				}
			}
		}
		
		long endTime = System.currentTimeMillis();
		lastSearchTime = endTime - startTime;
//		System.out.println("Not Found : Total time in ms: "+(endTime-startTime));
//		System.out.println("Total Inspection Times: " + counter);
		return -1;
	}
	
	private int inspect(char[] array, int index) {
		counter++;
		return array[index];
	}
	
	public int totalInspectTimes() { return counter;}
	
	public long timeUsed() {return lastSearchTime;}
}
