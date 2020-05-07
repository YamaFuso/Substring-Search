package overall;



/**
 * Brute force search through the text and look for first pattern
 * Also print the time used and position of the pattern

 * @param pattern the pattern program is looing for
 * @return t/f pattern is found
 */
public class BruteForce2 {
	
	private String pattern;
	private int counter = -1;
	private long lastSearchTime = -1;
	
	public BruteForce2(String pattern) {
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

		long textLength = text.length();
		long patternLength = patternChar.length;
		
		if(patternLength == 0) { 
			long endTime = System.currentTimeMillis();
			lastSearchTime = endTime - startTime; 
			return -1;
		}
		
        int i=0;
        int j=0;
        int k = 0;
        while(i < textLength && j < patternLength){
            if(inspect(textChar,i) == inspect(patternChar, j)){
                i++;
                j++;
            }else{
                j=0;
                k++;
                i = k;
            }
        }
        
        if(j == patternLength){
        	long endTime = System.currentTimeMillis();
    		lastSearchTime = endTime - startTime;
            return k;
        }

		long endTime = System.currentTimeMillis();
		lastSearchTime = endTime - startTime;
		return -1;
	}
	
	private int inspect(char[] array, int index) {
		counter++;
		return array[index];
	}
	
	public int totalInspectTimes() { return counter;}
	
	public long timeUsed() {return lastSearchTime;}
}
