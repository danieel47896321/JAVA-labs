/**
 * 
 */
package components;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;






/**
 * The Class NonStandardTruck.
 */
public class NonStandardTruck extends Truck{
	
	/** The height. */
	private int width, length, height;	
	
	/** The mainoffice. */
	private MainOffice mainoffice = MainOffice.getMainOfficeInstance();


	/**
	 * Instantiates a new non standard truck.
	 */
	public NonStandardTruck() {
		super();
		Random r=new Random();
		width=(r.nextInt(3)+2)*100;
		length=(r.nextInt(6)+10)*100;
		height=(r.nextInt(2)+3)*100;
		System.out.println("Creating "+ this);
	}
	
	
	/**
	 * Instantiates a new non standard truck.
	 *
	 * @param licensePlate the license plate
	 * @param truckModel the truck model
	 * @param length the length
	 * @param width the width
	 * @param height the height
	 */
	public NonStandardTruck(String licensePlate,String truckModel, int length, int width, int height) {
		super(licensePlate,truckModel);
		this.width=width;
		this.length=length;
		this.height=height;
	}

	
	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	
	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	
	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	
	/**
	 * Sets the length.
	 *
	 * @param length the new length
	 */
	public void setLength(int length) {
		this.length = length;
	}

	
	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	
	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
	public void setHeight(int height) {
		this.height = height;
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
			if (!this.isAvailable()) {
				Package p=this.getPackages().get(0);
				this.setTimeLeft(this.getTimeLeft()-1);
				if (this.getTimeLeft()==0) {
					if (p.getStatus()==Status.COLLECTION) {
						mainoffice.Report("NonStandartTruck " + this.getTruckID() + "has collected package "+p.getPackageID());
						System.out.println("NonStandartTruck " + this.getTruckID() + "has collected package "+p.getPackageID());
						deliverPackage(p);
					}
						
					else {
						mainoffice.Report("NonStandartTruck " + this.getTruckID() + "has delivered package "+p.getPackageID() + " to the destination");
						System.out.println("NonStandartTruck " + this.getTruckID() + "has delivered package "+p.getPackageID() + " to the destination");
						this.getPackages().remove(p);
						p.setStatus(Status.DELIVERED);
						p.addTracking(new Tracking(MainOffice.getClock(), null, p.getStatus()));
						setAvailable(true);
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
	@Override
	public void work() {

	}
	
	
	/**
	 * Deliver package.
	 *
	 * @param p the p
	 */
	@Override
	public synchronized void deliverPackage (Package p)  {
		int time=(Math.abs(p.getDestinationAddress().street-p.getDestinationAddress().street)%10+1)*10;
		this.setTimeLeft(time);
		this.initTime = time;
		p.setStatus(Status.DISTRIBUTION);
		p.addTracking(new Tracking(MainOffice.getClock(), this, p.getStatus()));
		mainoffice.Report("NonStandartTruck "+ this.getTruckID() + " is delivering package " + p.getPackageID() + ", time left: "+ this.getTimeLeft()  );
		System.out.println("NonStandartTruck "+ this.getTruckID() + " is delivering package " + p.getPackageID() + ", time left: "+ this.getTimeLeft()  );
	}
	
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "NonStandardTruck ["+ super.toString() + ", length=" + length +  ", width=" + width + ", height="
				+ height + "]";
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
		Color col = null;
		if (p.getStatus()==Status.COLLECTION) {
			start = new Point(1140, 216);
			end = p.getSendPoint();
			col = new Color(255,180,180);
		}
		else if (p.getStatus()==Status.DISTRIBUTION) {
			start = p.getSendPoint();
			end = p.getDestPoint();
			col = Color.RED;
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
				
			g.setColor(col);
			g.fillRect(dX+x1-8, dY+y1-8, 16, 16); 
			g.setColor(Color.BLACK);
			g.fillOval(dX+x1-12, dY+y1-12, 10, 10);
			g.fillOval(dX+x1, dY+y1, 10, 10);
			g.fillOval(dX+x1, dY+y1-12, 10, 10);
			g.fillOval(dX+x1-12, dY+y1, 10, 10);
		}
	}


	
}

