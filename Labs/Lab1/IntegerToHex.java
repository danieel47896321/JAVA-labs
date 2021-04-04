/*
 * ----------Lab1 Ex5--------------
 * Name: Daniel Elmaliach ,ID: 206068629
 */
package lab1;
import java.util.Scanner;

public class IntegerToHex {
		public static void main(String[] args) {
			Scanner input = new Scanner(System.in);
			System.out.print("Enter integer number: ");
			int number=input.nextInt();
			String hex = Integer.toHexString(number).toUpperCase();
			if('A'<=hex.charAt(0)&& hex.charAt(0)<='Z' )
				System.out.print("The number in hex:"+0+hex);
			else
				System.out.print("The number in hex:"+hex);
			input.close();
		} //end main
} //end IntegerToHex