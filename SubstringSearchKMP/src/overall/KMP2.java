// Reference: https://github.com/mission-peace/interview/blob/master/src/com/interview/string/SubstringSearch.java

package overall;

public class KMP2 {

	private int[] lps = null;
	private char[] pattern;
	
	private int counter;
	private int constructCounter;
	private long constructTime = -1;
	private long lastSearchTime = -1;

    
	public KMP2(String pattern) {
		this(pattern.toCharArray());
	}
	
    /**
     * Compute temporary array to maintain size of suffix which is same as prefix
     * Time/space complexity is O(size of pattern)
     */
    public KMP2(char pattern[]){
    	
    	long startTime = System.currentTimeMillis();
    	
    	this.pattern = pattern;
        int [] lps = new int[pattern.length];
        int index =0;

        for(int i=1; i < pattern.length;){
            if(inspect(pattern, i) == inspect(pattern, index)){
                lps[i] = index + 1;
                index++;
                i++;
            }else{
                if(index != 0){
                    index = inspect(lps, index-1);
                }else{
                    lps[i] = 0;
                    i++;
                }
            }
        }
        
        this.lps = lps;
        
        long endTime = System.currentTimeMillis();
        
//        System.out.println();
//        System.out.println("KMP2: Inspect During Construct : " + counter);
//        System.out.println("KMP2: Construct Time: " + (endTime - startTime));
//        System.out.println();
        
        constructTime = endTime - startTime;
		constructCounter = counter;
		counter = 0;
        
        counter = 0;
    }
    
    public int search(String text) {
    	return search(text.toCharArray());
    }
    
    /**
     * KMP algorithm of pattern matching.
     */
    public int search(char [] text){
    	
    	long startTime = System.currentTimeMillis();
        
        //int lps[] = computeTemporaryArray(pattern);
        int i=0;
        int j=0;
        while(i < text.length && j < pattern.length){
            if(inspect(text,i) == inspect(pattern,j)){
                i++;
                j++;
            }else{
                if(j!=0){
                    j = inspect(lps, j-1);
                }else{
                    i++;
                }
            }
        }
        if(j == pattern.length){
//        	System.out.println("String found. Inspect times: " + counter);
        	long endTime = System.currentTimeMillis();
        	lastSearchTime = endTime - startTime;
            return i - j;
        }
        
        long endTime = System.currentTimeMillis();
        lastSearchTime = endTime - startTime;
//        System.out.println("String not found. Inspect times: " + counter);
        return -1;
    }
    
    private int inspect(int[] i, int j) {
    	counter++;
    	return i[j];
    }
    
    private int inspect(char[] i, int j) {
    	counter++;
    	return i[j];
    	
    }
    
	public int searchInspectTimes() { return counter;}
	
	public long searchtimeUsed() {return lastSearchTime;}
	
	public int constructInspectTimes() { return constructCounter;}
	
	public long constructtimeUsed() {return constructTime;}
}
