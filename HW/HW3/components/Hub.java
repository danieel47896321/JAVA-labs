package components;

import java.util.ArrayList;
import java.util.Random;

/**
 * The Class Hub.
 */
public class Hub extends Branch{
	
	/** The branches. */
	private ArrayList<Branch> branches=new ArrayList<Branch>();
	
	/** The current index. */
	private int currentIndex=0;
	
	/** The mainoffice. */
	private MainOffice mainoffice = MainOffice.getMainOfficeInstance();

	
	/**
	 * Instantiates a new hub.
	 */
	public Hub() {
		super("HUB");
	}
	

	/**
	 * Gets the branches.
	 *
	 * @return the branches
	 */
	public ArrayList<Branch> getBranches() {
		return branches;
	}

	
	/**
	 * Adds the branch.
	 *
	 * @param branch the branch
	 */
	public void add_branch(Branch branch) {
		branches.add(branch);
	}
	
	
	/**
	 * Send truck.
	 *
	 * @param t the t
	 */
	public synchronized void sendTruck(StandardTruck t) {
		synchronized(t) {
			t.notify();
		}
		t.setAvailable(false);
		t.setDestination(branches.get(currentIndex));
		t.load(this, t.getDestination(), Status.BRANCH_TRANSPORT);
		t.setTimeLeft(((new Random()).nextInt(10)+1)*10);
		t.initTime = t.getTimeLeft();
		mainoffice.Report(t.getName() + " is on it's way to " + t.getDestination().getName() + ", time to arrive: "+t.getTimeLeft());
		System.out.println(t.getName() + " is on it's way to " + t.getDestination().getName() + ", time to arrive: "+t.getTimeLeft());	
		currentIndex=(currentIndex+1)%branches.size();
	}
	
	
	/**
	 * Ship non standard.
	 *
	 * @param t the t
	 */
	public synchronized void shipNonStandard(NonStandardTruck t) {
		for (Package p: listPackages) {
			if (p instanceof NonStandardPackage) {
				/*if (((NonStandardPackage) p).getHeight() <= t.getHeight() 
					&& ((NonStandardPackage) p).getLength()<=t.getLength()
					&& ((NonStandardPackage) p).getWidth()<=t.getWidth()){*/
						synchronized(t) {
							t.notify();
						}
						t.collectPackage(p);
						listPackages.remove(p);
						return;
					//}
			}
		}	
	}
	
	
	/**
	 * Work.
	 */
	@Override
	public void work() {

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
			for (Truck t : listTrucks) {
				if (t.isAvailable()){
					if(t instanceof NonStandardTruck) {
						shipNonStandard((NonStandardTruck)t);
					}
					else {
						sendTruck((StandardTruck)t);
					}
				}	
			}
		}
	}



}
