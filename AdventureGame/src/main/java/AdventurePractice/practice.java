package AdventurePractice;

public class practice {

	public static void main(String[] args) {
//		int[] arr = new int[] {1,2,3,4};
//		shiftArray(arr);
//		for(int num : arr) {
//			System.out.print(num);
//		}
	System.out.println(stringToint("356"));	

	}
	
	//how do you turn a string of a number into an int without parsing?
	
	  public static int stringToint( String str ){
	        int i = 0, number = 0;
	        boolean isNegative = false;
	        int len = str.length();
	        if( str.charAt(0) == '-' ){
	            isNegative = true;
	            i = 1;
	        }
	        while( i < len ){
	            number *= 10;
	            number += ( str.charAt(i++) - '0' ); //'0' subtracts the ascii key value from the char, resulting in the digit
	        }
	        if( isNegative )
	        number = -number;
	        return number;
	    }  
	
	public static int[] shiftArray(int[] arr) {
		//[1,2,3,4]
		int temp = arr[arr.length -1];
		for(int i = arr.length - 1; i > 0; i--) {
			arr[i] = arr[i-1];
		}
		arr[0] = temp;
		
		return arr;
	}
	
	/*
	Exercise 5: FizzArray3
	Create an integer array method called FizzArray3  that takes in two integers "start" and "end"
	Given start and end numbers, return a new array containing the sequence of integers from start up to but
	  not including end, so start=5 and end=10 yields {5, 6, 7, 8, 9}. The end number will be greater or equal  
	  to the start number. Note that a length-0 array is valid. 
	  
	fizzArray3(5, 10) → [5, 6, 7, 8, 9]
	fizzArray3(11, 18) → [11, 12, 13, 14, 15, 16, 17]
	fizzArray3(1, 3) → [1, 2]
	*/

}
