package components;

import java.awt.Color;
import java.awt.Graphics;

/**
 * The Class Van.
 */
public class Van extends Truck{
	
	/** The mainoffice. */
	private MainOffice mainoffice = MainOffice.getMainOfficeInstance();

	/**
	 * Instantiates a new van.
	 */
	public Van() {
		super();
		System.out.println("Creating " + this);
	}
	
	
	/**
	 * Instantiates a new van.
	 *
	 * @param licensePlate the license plate
	 * @param truckModel the truck model
	 */
	public Van(String licensePlate,String truckModel) {
		super(licensePlate,truckModel);
	}
	

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Van ["+ super.toString() + "]";
	}
	
	
	/**
	 * Deliver package.
	 *
	 * @param p the p
	 */
	@Override
	public synchronized void deliverPackage(Package p) {
		this.getPackages().add(p);
		setAvailable(false);
		int time=(p.getDestinationAddress().street%10+1)*10;
		this.setTimeLeft(time);
		this.initTime = time;
		p.setStatus(Status.DISTRIBUTION);
		p.addTracking(new Tracking(MainOffice.getClock(), this, p.getStatus()));
		mainoffice.Report("Van "+ this.getTruckID() + " is delivering package " + p.getPackageID() + ", time left: "+ this.getTimeLeft() );

		System.out.println("Van "+ this.getTruckID() + " is delivering package " + p.getPackageID() + ", time left: "+ this.getTimeLeft()  );
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
			Branch branch=null;
			if (!this.isAvailable()) {
				this.setTimeLeft(this.getTimeLeft()-1);
				if (this.getTimeLeft()==0){
					for (Package p : this.getPackages()) {
						if (p.getStatus()==Status.COLLECTION) {
							branch=MainOffice.getHub().getBranches().get(p.getSenderAddress().zip);
							synchronized(branch) {
								p.setStatus(Status.BRANCH_STORAGE);
								mainoffice.Report("Van " + this.getTruckID() + " has collected package " +p.getPackageID()+" and arrived back to branch " + branch.getBranchId() );

								System.out.println("Van " + this.getTruckID() + " has collected package " +p.getPackageID()+" and arrived back to branch " + branch.getBranchId());
								branch.addPackage(p);
							}
						}
						else {
							p.setStatus(Status.DELIVERED);
							branch=MainOffice.getHub().getBranches().get(p.getDestinationAddress().zip);
							synchronized(branch) {
								branch.listPackages.remove(p);
								branch=null;
								mainoffice.Report("Van " + this.getTruckID() + " has delivered package "+p.getPackageID() + " to the destination" );
								System.out.println("Van " + this.getTruckID() + " has delivered package "+p.getPackageID() + " to the destination");
								if (p instanceof SmallPackage && ((SmallPackage)p).isAcknowledge()) {
									System.out.println("Acknowledge sent for package "+p.getPackageID());
								}
							}
						}
						p.addTracking(new Tracking(MainOffice.getClock(), branch, p.getStatus()));
	
					}
					this.getPackages().removeAll(getPackages());
					this.setAvailable(true);
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
	@Override
	public void work() {

	}


	/**
	 * Paint component.
	 *
	 * @param g the g
	 */
	@Override
	public void paintComponent(Graphics g) {
		if (isAvailable()) return;
		Package p = this.getPackages().get(getPackages().size()-1);	
		Point start=null;
		Point end=null;
		if (p.getStatus()==Status.COLLECTION) {
			start = p.getSendPoint();
			end = p.getBInPoint();
		}
		else if (p.getStatus()==Status.DISTRIBUTION) {
			start = p.getBOutPoint();
			end = p.getDestPoint();
		}
		
		if (start!=null) {
			int x2 = start.getX();
			int y2 = start.getY();
			int x1 = end.getX();
			int y1 = end.getY();
				
			double ratio = (double) this.getTimeLeft()/this.initTime;
			@SuppressWarnings("unused")
			double length = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
			int dX = (int) (ratio*(x2-x1));
			int dY = (int) (ratio*(y2-y1));
				
			g.setColor(Color.BLUE);
			g.fillRect(dX+x1-8, dY+y1-8, 16, 16); 
			g.setColor(Color.BLACK);
			g.fillOval(dX+x1-12, dY+y1-12, 10, 10);
			g.fillOval(dX+x1, dY+y1, 10, 10);
			g.fillOval(dX+x1, dY+y1-12, 10, 10);
			g.fillOval(dX+x1-12, dY+y1, 10, 10);
		}
			
		
	}



}
