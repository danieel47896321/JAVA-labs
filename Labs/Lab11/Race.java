package lab11;

/**
EX 11:
Name : Daniel Elmaliach , ID : 206068629
 */
public class Race {
	private static Race RaceInstance = null;
	private static int Distance = 300;
	private Horse horses[];
	private Factory horseFactory = new Factory();
	public Race() { horses = new Horse[10];}
	public static Race getRaceInstance() {
		if (RaceInstance == null)
			RaceInstance = new Race();
		return RaceInstance;
	}
	public void prepareRace() {
		for (int i = 0; i < horses.length; i++) 
			horses[i] = horseFactory.getHorse(i%3, (i+1), Distance);
	}
	public void startRace() {
		boolean Flag = true;
		int first , second, third;
		String firstName = "", secondName = "", thirdName = "";
		for (int i = 0; i < horses.length; i++) 
			horses[i].start();
		try { Thread.sleep(1000);}
		catch (InterruptedException e1) { e1.printStackTrace(); }
		while(Flag) {
			first = Distance;
			second = Distance;
			third = Distance;
			for (int i = 0; i < horses.length; i++) {
				if (horses[i].getDistance() < first) {
					third = second;
					thirdName = secondName;
					second = first;
					secondName = firstName;
					first = horses[i].getDistance();
					firstName = horses[i].getName();				
				} else if (horses[i].getDistance() < second) {
					third = second;
					thirdName = secondName;
					second = horses[i].getDistance();
					secondName = horses[i].getName();
				} else if (horses[i].getDistance() < third) {
					third = horses[i].getDistance();
					thirdName = horses[i].getName();
				}
			}
			if (first < 0)
				first = 0;
			if (second < 0)
				second = 0;
			if (third < 0)
				third = 0;
			System.out.println("\n1: " + firstName + " | Distance Left: "+ first);
			System.out.println("2: " + secondName + " | Distance Left: "+ second);
			System.out.println("3: " + thirdName + " | Distance Left: "+ third);
			if (first == 0 && second == 0 && third == 0)
				Flag = false;
		}
		System.out.println("\n---- Stop the rest of the horses\n");
		for (int i = 0; i < horses.length; i++) 
			((Thread)horses[i]).interrupt();
		try { Thread.sleep(200);}
		catch (InterruptedException e) { e.printStackTrace(); }
		System.out.println("\n\n------------------RACE IS FINISH----------------");
	}
}
