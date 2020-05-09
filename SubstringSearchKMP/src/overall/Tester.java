package overall;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public class Tester {

	public static void main(String[] args) {
		
//		System.out.println(256*4194304);
//		System.out.println(Integer.MAX_VALUE - 8);
		
		int patternLength = 4194304;
		int charAsc = 10;
		
//		IntBuffer array = ByteBuffer.allocateDirect(256 * patternLength * 4)
//                .order(ByteOrder.nativeOrder()).asIntBuffer();
//		
//		array.put(charAsc * patternLength, 1);
		
//		int i = 256 * 2097152;
		int[] array1 = new int [256 * 1500000];
//		int[][] array2 = new int [256][2097152];
	}
}
