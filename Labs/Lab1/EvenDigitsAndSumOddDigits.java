/*
 * ----------Lab1 Ex3--------------
 * Name: Daniel Elmaliach ,ID: 206068629
 */
package lab1;
import java.util.Scanner;

public class EvenDigitsAndSumOddDigits {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Please enter number:");
		int number=input.nextInt(),AllDigits[];
		AllDigits=DigitsArr(number);
		PrintEvenDigits(AllDigits);
		SumOddDigits(AllDigits);
		input.close();
	} // end main
	
	public static int[] DigitsArr(int number) {
		int temp[] = {0,0,0,0,0,0,0,0,0,0};
		while(number!=0) {
			temp[number%10]+=1;
			number/=10;
		}
		return temp;
	}
	
	public static void PrintEvenDigits(int AllDigits[]) {
		System.out.print("The even digits is: ");
		for(int i=0;i<9;i++) {
			if(0<AllDigits[i] && AllDigits[i]%2==0)
				System.out.print(i+",");
		}
	} // end PrintEvenDigits
	
	public static void SumOddDigits(int AllDigits[]) {
		int sum=0;
		for(int i=0;i<9;i++) {
			if(0<AllDigits[i] && AllDigits[i]%2!=0)
				sum+=i;
		}
		System.out.print("\nThe sum of even digits is: "+sum);
	} // end SumOddDigits
	
} //end EvenDigitsAndSumOddDigits