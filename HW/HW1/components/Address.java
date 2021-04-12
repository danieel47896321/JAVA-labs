package components;
/**
 * @author Daniel Elmaliach
 * @ID 206068629
 * The Class Address.
 */
public class Address {
	
	/** The zip. */
	private int zip;
	
	/** The street. */
	private int street;
	
	/**
	 * Instantiates a new address.
	 *
	 * @param zip the zip
	 * @param street the street
	 */
	public Address(int zip, int street) {
		this.zip = zip;
		this.street = street;
	}
	/**
	 * Sets the zip.
	 *
	 * @param zip the new zip
	 */
	public void setZip(int zip) { this.zip = zip; }

	/**
	 * Sets the street.
	 *
	 * @param street the new street
	 */
	public void setStreet(int street) { this.street = street; }
		
		/**
		 * Gets the zip.
		 *
		 * @return the zip
		 */
	public int getZip() { return zip; }
	
	/**
	 * Gets the street.
	 *
	 * @return the street
	 */
	public int getStreet() { return street; }
		
		/**
		 * To string.
		 *
		 * @return the string
		 */
	public String toString() { return "Adress=" + zip + "-" + street; }
		
		/**
		 * Equals.
		 *
		 * @param object the object
		 * @return true, if successful
		 */
	public boolean equals(Object object) {
		if(object == null || getClass() != object.getClass()) 
			return false;
		Address other = (Address)object;
		return this.zip == other.zip && this.street == other.street;
	}
}


