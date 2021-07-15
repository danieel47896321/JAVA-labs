package components;

/**
 * The Interface Node.
 */
public interface Node {
	
	/**
	 * Collect package.
	 *
	 * @param p the p
	 */
	public void collectPackage(Package p);
	
	/**
	 * Deliver package.
	 *
	 * @param p the p
	 */
	public void deliverPackage(Package p);
	
	/**
	 * Work.
	 */
	public void work();
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();
}
