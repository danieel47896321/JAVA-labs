package lab11;

/**
EX 11:
Name : Daniel Elmaliach , ID : 206068629
 */
public class Factory {
	public static final int BMW = 0;
	public static final int MERCEDES = 1;
	public static final int BUGATTI = 2;
	public Horse getHorse(int horseType, int ID, int Distance) {
		if (horseType == BMW) 
			return new BMW(Distance,ID);
		else if (horseType == MERCEDES) 
			return new Mercedes(Distance,ID);
		else if (horseType == BUGATTI)
			return new Bugatti(Distance,ID);
		return null;
	}
}