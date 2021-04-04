/*
 * ----------Lab1 Ex4--------------
 * Name: Daniel Elmaliach ,ID: 206068629
 */
package lab1;

public class PrimeNumbers {
	public static void main(String[] args) {
		System.out.print("Prime Number between 2-200: \n");
		System.out.print(2);
		for(int i = 3 ; i <= 200 ; i++) { 
			int Flag = 0;
			for(int j = 2 ; j <i ; j++) { 
				if(i%j == 0)
					Flag = 1;
			} //end inner for
			if(Flag == 0) 
				System.out.print(","+i);
		} //end for
	} //end main
} //end PrimeNumbers
