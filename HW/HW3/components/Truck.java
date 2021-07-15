package components;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;


/**
 * The Class Truck.
 */
public  abstract class Truck implements Node,Runnable {
	
	/** The count ID. */
	private static int countID=2000;
	
	/** The truck ID. */
	final private int truckID;
	
	/** The license plate. */
	private String licensePlate;
	
	/** The truck model. */
	private String truckModel;
	
	/** The available. */
	private boolean available=true;
	
	/** The time left. */
	private int timeLeft=0;
	
	/** The packages. */
	private ArrayList<Package> packages=new ArrayList<Package>();
	
	/** The init time. */
	protected int initTime;
	
	/** The thread suspend. */
	protected boolean threadSuspend = false;
	
	/** The mainoffice. */
	private MainOffice mainoffice = MainOffice.getMainOfficeInstance();

		
	
	/**
	 * Instantiates a new truck.
	 */
	//default random constructor
	public Truck() {
		truckID=countID++;
		Random r= new Random();
		licensePlate=(r.nextInt(900)+100)+"-"+(r.nextInt(90)+10)+"-"+(r.nextInt(900)+100);
		truckModel="M"+r.nextInt(5);
	}

	/**
	 * Instantiates a new truck.
	 *
	 * @param licensePlate the license plate
	 * @param truckModel the truck model
	 */
	public Truck(String licensePlate,String truckModel) {
		truckID=countID++;
		this.licensePlate=licensePlate;
		this.truckModel=truckModel;
	}
	
	
	/**
	 * Gets the packages.
	 *
	 * @return the packages
	 */
	public ArrayList<Package> getPackages() {
		return packages;
	}


	/**
	 * Gets the time left.
	 *
	 * @return the time left
	 */
	public int getTimeLeft() {
		return timeLeft;
	}

	
	/**
	 * Sets the time left.
	 *
	 * @param timeLeft the new time left
	 */
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}


	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "truckID=" + truckID + ", licensePlate=" + licensePlate + ", truckModel=" + truckModel + ", available= " + available ;
	}


	/**
	 * Collect package.
	 *
	 * @param p the p
	 */
	@Override
	public synchronized void collectPackage(Package p) {
		setAvailable(false);
		int time=(p.getSenderAddress().street%10+1)*10;
		this.setTimeLeft(time);
		this.initTime = time;
		this.packages.add(p);
		p.setStatus(Status.COLLECTION);
		p.addTracking(new Tracking(MainOffice.getClock(), this, p.getStatus()));
		mainoffice.Report(getName() + " is collecting package " + p.getPackageID() + ", time to arrive: "+ getTimeLeft() );

		System.out.println(getName() + " is collecting package " + p.getPackageID() + ", time to arrive: "+ getTimeLeft()  );
	}


	/**
	 * Deliver package.
	 *
	 * @param p the p
	 */
	@Override
	public synchronized void deliverPackage(Package p) {}


	/**
	 * Checks if is available.
	 *
	 * @return true, if is available
	 */
	public boolean isAvailable() {
		return available;
	}
	

	/**
	 * Gets the truck ID.
	 *
	 * @return the truck ID
	 */
	public int getTruckID() {
		return truckID;
	}

	
	/**
	 * Sets the available.
	 *
	 * @param available the new available
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.getClass().getSimpleName()+" "+ truckID;
	}
	
	/**
	 * Sets the suspend.
	 */
	public synchronized void setSuspend() {
	   	threadSuspend = true;
	}

	/**
	 * Sets the resume.
	 */
	public synchronized void setResume() {
	   	threadSuspend = false;
	   	notify();
	}
	
	/**
	 * Paint component.
	 *
	 * @param g the g
	 */
	public abstract void paintComponent(Graphics g);
	
}
