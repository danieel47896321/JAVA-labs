package components;
public class Address {
	private final int zip;
	private final int street;
	public Address(int zip, int street) {
		this.zip = zip;
		this.street = street;
	}
	public int getZip() { return zip; }
	public int getStreet() { return street; }
	public String toString() { return zip + "-" + street; }
	public boolean equals(Object object) {
		if(object == null || getClass() != object.getClass()) 
			return false;
		Address other = (Address)object;
		return this.zip == other.zip && this.street == other.street;
	}
}
