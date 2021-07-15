package components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

/**
 * The Class StandardTruck.
 */
public class StandardTruck extends Truck{
	
	/** The max weight. */
	private int maxWeight;
	
	/** The destination. */
	private Branch destination=null;
	
	/** The source. */
	private Branch source = null;
	
	/** The mainoffice. */
	private MainOffice mainoffice = MainOffice.getMainOfficeInstance();

	
	/**
	 * Instantiates a new standard truck.
	 */
	public StandardTruck() {
		super();
		maxWeight=((new Random()).nextInt(2)+2)*100;
		System.out.println("Creating "+ this);

	}



	/**
	 * Instantiates a new standard truck.
	 *
	 * @param licensePlate the license plate
	 * @param truckModel the truck model
	 * @param maxWeight the max weight
	 */
	public StandardTruck(String licensePlate,String truckModel,int maxWeight) {
		super(licensePlate,truckModel);
		this.maxWeight=maxWeight;
	}
	
	
	/**
	 * Gets the destination.
	 *
	 * @return the destination
	 */
	public Branch getDestination() {
		return destination;
	}
	
	
	/**
	 * Sets the destination.
	 *
	 * @param destination the new destination
	 */
	public void setDestination(Branch destination) {
		this.destination = destination;
	}
	

	/**
	 * Gets the max weight.
	 *
	 * @return the max weight
	 */
	public int getMaxWeight() {
		return maxWeight;
	}

	
	/**
	 * Sets the max weight.
	 *
	 * @param maxWeight the new max weight
	 */
	public void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}

	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "StandartTruck ["+ super.toString() +",maxWeight=" + maxWeight + "]";
	}
	
	
	/**
	 * Unload.
	 *
	 * @param dest the dest
	 */
	public void unload (Branch dest) {
		Status status;
		synchronized(dest) {
			if (dest==MainOffice.getHub())
				status=Status.HUB_STORAGE;
			else 
				status=Status.DELIVERY;
			
			for (Package p: getPackages()) {
				p.setStatus(status);
				dest.addPackage(p);
				p.addTracking(dest, status);	
			}
			getPackages().removeAll(getPackages());
			mainoffice.Report("StandardTruck " + getTruckID() + " unloaded packages at " + destination.getName());

			System.out.println("StandardTruck " + getTruckID() + " unloaded packages at " + destination.getName());
		}
	}
	
	
	/**
	 * Load.
	 *
	 * @param sender the sender
	 * @param dest the dest
	 * @param status the status
	 */
	public void load (Branch sender, Branch dest, Status status) {
		double totalWeight=0;
		synchronized(sender) {
			for (int i=0; i< sender.listPackages.size();i++) {
				Package p=sender.listPackages.get(i);
				if (p.getStatus()==Status.BRANCH_STORAGE || (p.getStatus()==Status.HUB_STORAGE && MainOffice.getHub().getBranches().get(p.getDestinationAddress().zip)==dest)) {
					if (p instanceof SmallPackage && totalWeight+1<=maxWeight || totalWeight+((StandardPackage)p).getWeight()<=maxWeight) {
						getPackages().add(p);
						sender.listPackages.remove(p);
						i--;
						p.setStatus(status);
						p.addTracking(this, status);
					}
				}
			}
			mainoffice.Report(this.getName() + " loaded packages at " + sender.getName());

			System.out.println(this.getName() + " loaded packages at " + sender.getName());
		}
	}
	

	/**
	 * Run.
	 */
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    synchronized(this) {
                while (threadSuspend)
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    }
			if (!isAvailable()) {
				setTimeLeft(getTimeLeft()-1);
				if (getTimeLeft()==0) {
					mainoffice.Report("StandardTruck "+ getTruckID()+ " arrived to " + destination.getName());

					System.out.println("StandardTruck "+ getTruckID()+ " arrived to " + destination.getName());
					unload(destination);
					if (destination==MainOffice.getHub()) {
						setAvailable(true);
					}
						
					else {
						load(destination, MainOffice.getHub(), Status.HUB_TRANSPORT);
						setTimeLeft(((new Random()).nextInt(6)+1)*10);
						this.initTime = this.getTimeLeft();
						source = destination;
						destination=MainOffice.getHub();
						mainoffice.Report(this.getName() + " is on it's way to the HUB, time to arrive: "+ getTimeLeft());

						System.out.println(this.getName() + " is on it's way to the HUB, time to arrive: "+ getTimeLeft());
					}			
				}
			}
			else
				synchronized(this) {
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}
	}
	
	
	/**
	 * Work.
	 */
	public void work() {

	}


	/**
	 * Paint component.
	 *
	 * @param g the g
	 */
	@Override
	public void paintComponent(Graphics g) {
		Point start=null;
		Point end=null;
		Color col = new Color(102,255,102);
		if (this.getPackages()==null || destination==null) return;

		if (this.getPackages().size()==0) {
			if (destination!=MainOffice.getHub()) {
				end = this.destination.getBranchPoint();
				start = this.destination.getHubPoint();
			}
			else {
				start = this.source.getBranchPoint();
				end = this.source.getHubPoint();
			}
		}
		else {			
			Package p = this.getPackages().get(getPackages().size()-1);
			col = new Color(0,102,0);
			if (p.getStatus()==Status.HUB_TRANSPORT) {
				start = this.source.getBranchPoint();
				end = this.source.getHubPoint();
			}
			else if (p.getStatus()==Status.BRANCH_TRANSPORT){
				end = this.destination.getBranchPoint();
				start = this.destination.getHubPoint();
			}
		}
		
		
		if (start!=null) {
			int x2 = start.getX();
			int y2 = start.getY();
			int x1 = end.getX();
			int y1 = end.getY();
				
			double ratio = (double) this.getTimeLeft()/this.initTime;
			//System.out.println(x2+" "+x1+" "+ratio);
			@SuppressWarnings("unused")
			double length = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
			int dX = (int) (ratio*(x2-x1));
			int dY = (int) (ratio*(y2-y1));
				
			g.setColor(col);
			g.fillRect(dX+x1-8, dY+y1-8, 16, 16); 
			g.setColor(Color.BLACK);
			g.setFont(new Font("Courier", Font.BOLD,13));
			if (this.getPackages().size()>0)
				g.drawString(""+this.getPackages().size(), dX+x1-3, dY+y1-8-5);
			g.fillOval(dX+x1-12, dY+y1-12, 10, 10);
			g.fillOval(dX+x1, dY+y1, 10, 10);
			g.fillOval(dX+x1, dY+y1-12, 10, 10);
			g.fillOval(dX+x1-12, dY+y1, 10, 10);
		}
		
	}



	
}
