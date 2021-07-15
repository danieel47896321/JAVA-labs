package components;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


/**
 * The Class Package.
 */
public abstract class Package {
	
	/** The count ID. */
	private static int countID=1000;
	
	/** The package ID. */
	final private int packageID;
	
	/** The priority. */
	private Priority priority;
	
	/** The status. */
	private Status status;
	
	/** The sender address. */
	private Address senderAddress;
	
	/** The destination address. */
	private Address destinationAddress;
	
	/** The tracking. */
	private ArrayList<Tracking> tracking = new ArrayList<Tracking>();
	
	/** The branch. */
	private Branch branch = null;
	
	/** The send point. */
	private Point sendPoint;
	
	/** The dest point. */
	private Point destPoint;
	
	/** The b in point. */
	private Point bInPoint;
	
	/** The b out point. */
	private Point bOutPoint;

	
	
	/**
	 * Instantiates a new package.
	 *
	 * @param priority the priority
	 * @param senderAddress the sender address
	 * @param destinationAdress the destination adress
	 */
	public Package(Priority priority, Address senderAddress,Address destinationAdress) {
		packageID = countID++;
		this.priority=priority;
		this.status=Status.CREATION;
		this.senderAddress=senderAddress;
		this.destinationAddress=destinationAdress;
		tracking.add(new Tracking( MainOffice.getClock(), null, status));
	}	

	/**
	 * Sets the branch.
	 *
	 * @param branch the new branch
	 */
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	
	/**
	 * Gets the branch.
	 *
	 * @return the branch
	 */
	public Branch getBranch() {
		return this.branch;
	}
	
	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	public Priority getPriority() {
		return priority;
	}

	
	/**
	 * Sets the priority.
	 *
	 * @param priority the new priority
	 */
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	
	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	
	/**
	 * Gets the package ID.
	 *
	 * @return the package ID
	 */
	public int getPackageID() {
		return packageID;
	}
	
	
	
	/**
	 * Gets the sender address.
	 *
	 * @return the sender address
	 */
	public Address getSenderAddress() {
		return senderAddress;
	}

	
	/**
	 * Sets the sender address.
	 *
	 * @param senderAddress the new sender address
	 */
	public void setSenderAddress(Address senderAddress) {
		this.senderAddress = senderAddress;
	}

	
	/**
	 * Gets the destination address.
	 *
	 * @return the destination address
	 */
	public Address getDestinationAddress() {
		return destinationAddress;
	}

	
	/**
	 * Sets the destination address.
	 *
	 * @param destinationAdress the new destination address
	 */
	public void setDestinationAddress(Address destinationAdress) {
		this.destinationAddress = destinationAdress;
	}

	
	/**
	 * Adds the tracking.
	 *
	 * @param node the node
	 * @param status the status
	 */
	public void addTracking(Node node, Status status) {
		tracking.add(new Tracking(MainOffice.getClock(), node, status));
	}
	
	
	/**
	 * Adds the tracking.
	 *
	 * @param t the t
	 */
	public void addTracking(Tracking t) {
		tracking.add(t);
	}
	
	
	/**
	 * Gets the tracking.
	 *
	 * @return the tracking
	 */
	public ArrayList<Tracking> getTracking() {
		return tracking;
	}

	
	/**
	 * Prints the tracking.
	 */
	public void printTracking() {
		for (Tracking t: tracking)
			System.out.println(t);
	}
	
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "packageID=" + packageID + ", priority=" + priority + ", status=" + status + ", startTime="
				+ ", senderAddress=" + senderAddress + ", destinationAddress=" + destinationAddress;
	}
	
	/**
	 * Gets the send point.
	 *
	 * @return the send point
	 */
	public Point getSendPoint() {
		return sendPoint;
	}
	
	/**
	 * Gets the dest point.
	 *
	 * @return the dest point
	 */
	public Point getDestPoint() {
		return destPoint;
	}
	
	/**
	 * Gets the b in point.
	 *
	 * @return the b in point
	 */
	public Point getBInPoint() {
		return bInPoint;
	}
	
	/**
	 * Gets the b out point.
	 *
	 * @return the b out point
	 */
	public Point getBOutPoint() {
		return bOutPoint;
	}


	/**
	 * Paint component.
	 *
	 * @param g the g
	 * @param x the x
	 * @param offset the offset
	 */
	public void paintComponent(Graphics g, int x, int offset) {
		if (status==Status.CREATION || (branch==null && status == Status.COLLECTION))
			g.setColor(new Color(204,0,0));
		else
			g.setColor(new Color(255,180,180));
   		g.fillOval(x, 20, 30, 30);
   		
   		if (status==Status.DELIVERED)
   			g.setColor(new Color(204,0,0));
   		else
   			g.setColor(new Color(255,180,180));
   		g.fillOval(x, 583, 30, 30);
   		
		
   		if (branch!=null) {
	   		g.setColor(Color.BLUE);
	   		g.drawLine(x+15,50,40,100+offset*this.senderAddress.getZip());
	   		sendPoint = new Point(x+15,50);
	   		bInPoint = new Point(40, 100+offset*this.senderAddress.getZip());
	   		g.drawLine(x+15,583,40,130+offset*this.destinationAddress.getZip());
	   		destPoint = new Point(x+15,583);
	   		bOutPoint = new Point(40,130+offset*this.destinationAddress.getZip());
	   		
   		}
   		else {
   			g.setColor(Color.RED);
   			g.drawLine(x+15,50,x+15,583);
   			g.drawLine(x+15,50,1140, 216);
   			sendPoint = new Point(x+15,50);
   			destPoint = new Point(x+15,583);
   			
   		}
	}

	
	
	
}
