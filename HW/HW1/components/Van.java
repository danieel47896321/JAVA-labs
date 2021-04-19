package components;
public class Van extends Truck{
	public Van() {
		super();
		System.out.println("Creating " + toString());
	}
	public Van(String licensePlate, String truckModel) {
		super(licensePlate, truckModel);
		System.out.println("Creating " + toString());
	}
	public String toString() { return "Van [" + super.toString() + "]"; }
	public boolean equals(Object object) {
		if(object == null || this.getClass() != object.getClass() || !super.equals(object)) 
			return false; 
		Van other = (Van)object;
		return this.equals(other);
	}
	public void deliverPackage(Package p) {	
		addPackage(p);
		setAvailable(false);
		int time=p.getDestinationAddress().getStreet()%10+1;
		this.setTimeLeft(time);
		p.addRecords(Status.DISTRIBUTION, this);
		System.out.println(getName() + " is delivering " + p.getName() + ", time left: "+ getTimeLeft()  );
	}

	public void work() {		
		if (!isAvailable()) {
			setTimeLeft(getTimeLeft()-1);
			if (this.getTimeLeft() == 0){
				for (Package p : this.getPackages()) {
					if (p.getStatus() == Status.COLLECTION) {
						p.addRecords(Status.BRANCH_STORAGE,p.getSenderBranch());
						System.out.println(getName() + " has collected " + p.getName() + " and arrived back to " + p.getSenderBranch().getName());
					}
					else {
						p.addRecords(Status.DELIVERED, null);
						p.getDestBranch().removePackage(p);
						System.out.println(getName() + " has delivered " + p.getName() + " to the destination");
						if (p instanceof SmallPackage && ((SmallPackage)p).isAcknowledge()) 
							System.out.println("Acknowledge sent for " + p.getName());
					}
				}
				this.getPackages().removeAll(getPackages());
				this.setAvailable(true);
			}
		}
	}
}