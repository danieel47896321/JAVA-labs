package lab9;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TennisDriver {
	final static String file_name = "C:\\Users\\Daniel\\eclipse-workspace\\Labs\\Lab9\\lab9\\data.dat";

	public static void main(String [] args) throws IOException {
		Scanner in = new Scanner(System.in);
		System.out.println("Do you want to delete the old ranking (y/n)?");
		String input = in.next();
		if(input.equals("y") || input.equals("Y"))
			new File(file_name).delete();
		in.close();
		
		String [] names = {"Australian Open", "French Open", "Wimbledon", "USA Open", "ATP final"};
		for(int i=0; i<10; i++)
		{
			for(int j=0; j<names.length; j++)
			{
				Tournament t = new Tournament(names[j],file_name);
				t.simulateTournament();
				t.printPlayers();
			}
		}
	}
}