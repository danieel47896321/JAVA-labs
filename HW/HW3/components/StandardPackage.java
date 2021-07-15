package components;

/**
 * The Class StandardPackage.
 */
public class StandardPackage extends Package {
	
	/** The weight. */
	private double weight;
	
	
	/**
	 * Instantiates a new standard package.
	 *
	 * @param priority the priority
	 * @param senderAddress the sender address
	 * @param destinationAdress the destination adress
	 * @param weight the weight
	 */
	public StandardPackage(Priority priority, Address senderAddress, Address destinationAdress,double weight) {
		super( priority, senderAddress,destinationAdress);
		this.weight=weight;
		System.out.println("Creating " + this);
	}

	
	/**
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	
	/**
	 * Sets the weight.
	 *
	 * @param weight the new weight
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "StandardPackage ["+ super.toString()+", weight=" + weight + "]";
	}
}
