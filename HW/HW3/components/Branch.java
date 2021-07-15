package components;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class Branch.
 */
public class Branch implements Node, Runnable {
	
	/** The counter. */
	private static int counter=0;
	
	/** The branch id. */
	private int branchId;
	
	/** The branch name. */
	private String branchName;
	
	/** The unsafe list packages. */
	protected ArrayList <Package> unsafeListPackages = new ArrayList<Package>();
	
	/** The list packages. */
	protected List<Package> listPackages = unsafeListPackages; //Collections.synchronizedList(unsafeListPackages);
	
	/** The list trucks. */
	protected ArrayList <Truck> listTrucks = new ArrayList<Truck>();
	
	/** The hub point. */
	private Point hubPoint;
	
	/** The branch point. */
	private Point branchPoint;
	
	/** The thread suspend. */
	protected boolean threadSuspend = false;
	
	/**
	 * Instantiates a new branch.
	 */
	public Branch() {
		this("Branch "+counter);
	}
	
	/**
	 * Instantiates a new branch.
	 *
	 * @param branchName the branch name
	 */
	public Branch(String branchName) {
		this.branchId=counter++;
		this.branchName=branchName;
		System.out.println("\nCreating "+ this);
	}
	
	/**
	 * Instantiates a new branch.
	 *
	 * @param branchName the branch name
	 * @param plist the plist
	 * @param tlist the tlist
	 */
	public Branch(String branchName, Package[] plist, Truck[] tlist) {
		this.branchId=counter++;
		this.branchName=branchName;
		addPackages(plist);
		addTrucks(tlist);
	}
	
	/**
	 * Instantiates a new branch.
	 *
	 * @param other the other
	 */
	public Branch(Branch other) {
		this.unsafeListPackages = other.unsafeListPackages;
		this.listPackages = other.listPackages;
		this.listTrucks = other.listTrucks;
		
	}
	
	/**
	 * Gets the packages.
	 *
	 * @return the packages
	 */
	public synchronized List <Package> getPackages(){
		return this.listPackages;
	}
	
	/**
	 * Prints the branch.
	 */
	public void printBranch() {
		System.out.println("\nBranch name: "+branchName);
		System.out.println("Packages list:");
		for (Package pack: listPackages)
			System.out.println(pack);
		System.out.println("Trucks list:");
		for (Truck trk: listTrucks)
			System.out.println(trk);
	}
	
	
	/**
	 * Adds the package.
	 *
	 * @param pack the pack
	 */
	public synchronized void addPackage(Package pack) {
		listPackages.add(pack);
	}
	
	
	/**
	 * Gets the trucks.
	 *
	 * @return the trucks
	 */
	public ArrayList <Truck> getTrucks(){
		return this.listTrucks;
	}
	
	/**
	 * Adds the truck.
	 *
	 * @param trk the trk
	 */
	public void addTruck(Truck trk) {
		listTrucks.add(trk);
	}
	
	
	/**
	 * Gets the hub point.
	 *
	 * @return the hub point
	 */
	public Point getHubPoint() {
		return hubPoint;
	}
	
	/**
	 * Gets the branch point.
	 *
	 * @return the branch point
	 */
	public Point getBranchPoint() {
		return branchPoint;
	}
	
	/**
	 * Adds the packages.
	 *
	 * @param plist the plist
	 */
	public synchronized void addPackages(Package[] plist) {
		for (Package pack: plist)
			listPackages.add(pack);
	}
	
	
	/**
	 * Adds the trucks.
	 *
	 * @param tlist the tlist
	 */
	public void addTrucks(Truck[] tlist) {
		for (Truck trk: tlist)
			listTrucks.add(trk);
	}

	
	/**
	 * Gets the branch id.
	 *
	 * @return the branch id
	 */
	public int getBranchId() {
		return branchId;
	}
	
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return branchName;
	}

	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Branch " + branchId + ", branch name:" + branchName + ", packages: " + listPackages.size()
				+ ", trucks: " + listTrucks.size();
	}

	
	/**
	 * Collect package.
	 *
	 * @param p the p
	 */
	@Override
	public synchronized void  collectPackage(Package p) {
		for (Truck v : listTrucks) {
			if (v.isAvailable()) {
				synchronized(v) {
					v.notify();
				}
				v.collectPackage(p);
				return;
			}
		}
	}

	/**
	 * Deliver package.
	 *
	 * @param p the p
	 */
	@Override
	public synchronized void deliverPackage(Package p) {
		for (Truck v : listTrucks) {
			if (v.isAvailable()) {
				synchronized(v) {
					v.notify();
				}
				v.deliverPackage(p);
				return;
			}
		}	
	}

	/**
	 * Work.
	 */
	@Override
	public void work() {	
		/*for (Package p: listPackages) {
			if (p.getStatus()==Status.CREATION) {
				collectPackage(p);
			}
			if (p.getStatus()==Status.DELIVERY) {
				deliverPackage(p);
			}
		}*/	
	}

	
	/**
	 * Are packages in branch.
	 *
	 * @return true, if successful
	 */
	private boolean arePackagesInBranch() {
		for(Package p: listPackages) {
			if (p.getStatus() == Status.BRANCH_STORAGE)
				return true;
		}
		return false;
	}
	
	/**
	 * Paint component.
	 *
	 * @param g the g
	 * @param y the y
	 * @param y2 the y 2
	 */
	public void paintComponent(Graphics g, int y, int y2) {
		if (arePackagesInBranch())
			g.setColor(new Color(0,0,153));
		else
			g.setColor(new Color(51,204,255));
   		g.fillRect(20, y, 40, 30);
   		
   		g.setColor(new Color(0,102,0));
   		g.drawLine(60, y+15, 1120, y2);
   		branchPoint = new Point(60,y+15);
   		hubPoint = new Point(1120,y2);
	}

	/**
	 * Run.
	 */
	@Override
	public void run() {
		while(true) {
		    synchronized(this) {
                while (threadSuspend)
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    }
			synchronized(this) {
				for (Package p: listPackages) {
						if (p.getStatus()==Status.CREATION) {
							collectPackage(p);
						}
						if (p.getStatus()==Status.DELIVERY) {
							deliverPackage(p);
						}
				}
			}
		}
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
}
