package components;
import java.util.Random;
public class NonStandardTruck extends Truck implements Node{
	private int width;
	private int length;
	private int height;
	public NonStandardTruck () {
		super();
		RandomWidthLenthHight();
		System.out.println("Creating " + toString());
	}
	public NonStandardTruck(String licensePlate, String truckModel, int length, int width, int height) {
		super(licensePlate, truckModel);
		this.length = length;
		this.width = width;
		this.height = height;
		System.out.println("Creating " + toString());
	}
	public void RandomWidthLenthHight() {
		Random rand = new Random();
		width = (rand.nextInt(3)+2)*100;
		length = (rand.nextInt(6)+10)*100;
		height = (rand.nextInt(2)+3)*100;
	}
	public void setWidth(int width) { this.width = width; }
	public void setLength(int length) { this.length = length; }
	public void setHeight(int height) { this.height = height; }
	public int getWidth() { return width; }
	public int getLength() { return length; }
	public int getHeight() { return height; }
	public String toString() { return "NonStandardTruck ["+ super.toString() + ", length=" + length +  ", width=" + width + ", height=" + height + "]"; }
	public boolean equals(Object object) {
		if(object == null || this.getClass() != object.getClass() || !super.equals(object))
			return false; 
		NonStandardTruck other = (NonStandardTruck)object;
		return width == other.width && length == other.length && height == other.height;
	}
	public void collectPackage(Package p) {		
		p.setStatus(Status.DISTRIBUTION);
		p.addTracking(this, Status.DISTRIBUTION);
		System.out.printf("NonStandartTruck " + getTruckID() + " is collecting package " + p.getPackageID());
		System.out.printf("NonStandartTruck " + getTruckID() + " is delivering package " + p.getPackageID() + ", time to arrive: " + getTimeLeft());
	}
	public void deliverPackage (Package p)  {
		int time=Math.abs(p.getDestinationAddress().getStreet()-p.getSenderAddress().getStreet())%10+1;
		this.setTimeLeft(time);
		p.addRecords(Status.DISTRIBUTION, this);
		System.out.println(getName() + " is delivering " + p.getName() + ", time left: "+ this.getTimeLeft()  );
	}
	public void work() {
		if (!this.isAvailable()) {
			Package p=this.getPackages().get(0);
			this.setTimeLeft(this.getTimeLeft()-1);
			if (this.getTimeLeft() == 0) {
				if (p.getStatus() == Status.COLLECTION) {
					System.out.println(getName() + " has collected " + p.getName());
					deliverPackage(p);
				}
				else {
					System.out.println(getName() + " has delivered " + p.getName() + " to the destination");
					this.getPackages().remove(p);
					p.addRecords(Status.DELIVERED, null);
					setAvailable(true);
				}
			}
		}
	}
}