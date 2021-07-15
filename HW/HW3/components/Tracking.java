package components;

/**
 * The Class Tracking.
 */
public class Tracking {
	
	/** The time. */
	public final int time;
	
	/** The node. */
	public final Node node;
	
	/** The status. */
	public final Status status;
	
	/**
	 * Instantiates a new tracking.
	 *
	 * @param time the time
	 * @param node the node
	 * @param status the status
	 */
	public Tracking(int time, Node node, Status status) {
		super();
		this.time = time;
		this.node = node;
		this.status = status;
	}

	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		String name = (node==null)? "Customer" : node.getName();
		return time + ": " + name + ", status=" + status;
	}

	
	
}
