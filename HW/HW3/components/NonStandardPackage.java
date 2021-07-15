package components;

/**
 * The Class NonStandardPackage.
 */
public class NonStandardPackage extends Package {
	
	/** The height. */
	private int width, length, height;	
	
	/**
	 * Instantiates a new non standard package.
	 *
	 * @param priority the priority
	 * @param senderAddress the sender address
	 * @param destinationAdress the destination adress
	 * @param width the width
	 * @param length the length
	 * @param height the height
	 */
	public NonStandardPackage(Priority priority, Address senderAddress,Address destinationAdress,int width, int length, int height) {
			super( priority, senderAddress,destinationAdress);
			this.width=width;
			this.length=length;
			this.height=height;	
			System.out.println("Creating " + this);
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
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "NonStandardPackage ["+super.toString() + ", width=" + width + ", length=" + length + ", height=" + height + "]";
	}
	
}
