package lab11;

/**
EX 11:
Name : Daniel Elmaliach , ID : 206068629
 */
public class Mercedes extends Thread implements Horse {
	private int Distance;
	public Mercedes(int distance ,int id) {
		super("Mercedes "+id);
		Distance = distance;
	}
	public void run() {
		while(Distance > 0) {
			Distance -= (int)(Math.random()*30);
			try { Thread.sleep(200);} 
			catch (InterruptedException e) { System.out.println(getName()+" Stopped"); return; }
		}
	}
	public int getDistance() { return Distance; }
}