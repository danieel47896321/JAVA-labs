package components;

/**
 * The Class SmallPackage.
 */
public class SmallPackage extends Package {
	
	/** The acknowledge. */
	private boolean acknowledge;
	
	
	/**
	 * Instantiates a new small package.
	 *
	 * @param priority the priority
	 * @param senderAddress the sender address
	 * @param destinationAdress the destination adress
	 * @param acknowledge the acknowledge
	 */
	public SmallPackage(Priority priority, Address senderAddress,Address destinationAdress, boolean acknowledge){
		super(priority, senderAddress,destinationAdress);
		this.acknowledge=acknowledge;
		System.out.println("Creating " + this);

	}
	
	
	/**
	 * Checks if is acknowledge.
	 *
	 * @return true, if is acknowledge
	 */
	public boolean isAcknowledge() {
		return acknowledge;
	}
	
	
	/**
	 * Sets the acknowledge.
	 *
	 * @param acknowledge the new acknowledge
	 */
	public void setAcknowledge(boolean acknowledge) {
		this.acknowledge = acknowledge;
	}
	
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "SmallPackage ["+ super.toString() +", acknowledge=" + acknowledge + "]";
	}
	
		
}
