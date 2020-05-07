package overall;

public class KMP2 {

	private int[] lps = null;
	private char[] pattern;
	private int counter;
	
	/**
     * Slow method of pattern matching
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
    	this.pattern = pattern;
        int [] lps = new int[pattern.length];
        int index =0;
        lps[0] = 0;
        for(int i=1; i < pattern.length;){
            if(pattern[i] == pattern[index]){
                lps[i] = index + 1;
                index++;
                i++;
            }else{
                if(index != 0){
                    index = lps[index-1];
                }else{
                    lps[i] =0;
                    i++;
                }
            }
        }
        this.lps = lps;
    }
    
    /**
     * KMP algorithm of pattern matching.
     */
    public int search(char []text){
        
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
        	System.out.println("String found. Inspect times: " + counter);
            return i;
        }
        System.out.println("String not found. Inspect times: " + counter);
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
    
    
    /*    
    public static void main(String args[]){
        
        String str = "abcxabcdabcdabcy";
        String subString = "abcdabcy";
        KMP2 ss = new KMP2();
        boolean result = ss.KMP(str.toCharArray(), subString.toCharArray());
        System.out.print(result);
        
    }
    */
}
