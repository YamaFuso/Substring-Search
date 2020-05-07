package overall;

public class KMP2 {

	private int[] lps = null;
	private char[] pattern;
	
	private int counter;
	private long lastSearchTime = -1;
	
	
	/**
     * Slow method of pattern matching // another brute force
     */
    public boolean hasSubstring(char[] text, char[] pattern){
        int i=0;
        int j=0;
        int k = 0;
        while(i < text.length && j < pattern.length){
            if(text[i] == pattern[j]){
                i++;
                j++;
            }else{
                j=0;
                k++;
                i = k;
            }
        }
        if(j == pattern.length){
            return true;
        }
        return false;
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
                    index = lps[index-1];
                }else{
                    lps[i] = 0;
                    i++;
                }
            }
        }
        
        this.lps = lps;
        
        long endTime = System.currentTimeMillis();
        
        System.out.println();
        System.out.println("KMP2: Inspect During Construct : " + counter);
        System.out.println("KMP2: Construct Time: " + (endTime - startTime));
        System.out.println();
        
        counter = 0;
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
    
	public int totalInspectTimes() { return counter;}
	
	public long timeUsed() {return lastSearchTime;}
}
