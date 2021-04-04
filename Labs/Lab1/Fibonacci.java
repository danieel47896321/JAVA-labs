/*
 * ----------Lab1 Ex2--------------
 * Name: Daniel Elmaliach ,ID: 206068629
 */
package lab1;
import java.util.Scanner;

public class Fibonacci {
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		System.out.print("Enter N to Print N values of Fibonacci:");
		int N = input.nextInt();
		int a0 = 1 , a1 = 1 , an = 0;
		System.out.print(a0+","+a1);
		for ( int i = 2 ; i < N ; ++i ) {
				an=a0+a1;
				System.out.print(","+an);
				a0=a1;
				a1=an;
		} //end for
		input.close();
	} //end main
} //end Fibonacci
