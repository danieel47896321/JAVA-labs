package components;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

/**
 * The Class Customer.
 */
public class Customer implements Runnable{
	
	/** The clock. */
	private static int clock=0;
	
	/** The count. */
	private static int count=0;
	
	/** The packages. */
	private ArrayList<Package> packages=new ArrayList<Package>();
	
	/** The panel. */
	private JPanel panel;
	
	/** The max packages. */
	private int maxPackages;
	
	/** The thread suspend. */
	@SuppressWarnings("unused")
	private boolean threadSuspend = false;
	
	/** The flag. */
	public static boolean flag = false;
	
	/** The hub. */
	@SuppressWarnings("static-access")
	private static Hub hub = MainOffice.getMainOfficeInstance().getHub();
	
	/**
	 * Instantiates a new customer.
	 *
	 * @param panel the panel
	 * @param maxPack the max pack
	 */
	public Customer(JPanel panel, int maxPack) {
		this.panel = panel;
		this.maxPackages = maxPack;
	}
	
	/**
	 * Run.
	 */
	@SuppressWarnings("static-access")
	public void run() {
		while(count<5) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(MainOffice.getMainOfficeInstance().getClock());
			if (clock++%5==0 && maxPackages>0) {
				addPackage();
				maxPackages--;
			}
			panel.repaint();
			count++;
		}
	}
	
	/**
	 * Read file.
	 */
	public void readFile() {
		
		
	}
	
	/**
	 * Adds the package.
	 */
	public void addPackage() {
		Random r = new Random();
		Package p;
		Branch br;
		Priority priority=Priority.values()[r.nextInt(3)];
		Address sender = new Address(r.nextInt(hub.getBranches().size()), r.nextInt(999999)+100000);
		Address dest = new Address(r.nextInt(hub.getBranches().size()), r.nextInt(999999)+100000);

		switch (r.nextInt(3)){
		case 0:
			p = new SmallPackage(priority,  sender, dest, r.nextBoolean() );
			br = hub.getBranches().get(sender.zip);
			br.addPackage(p);
			p.setBranch(br); 
			break;
		case 1:
			p = new StandardPackage(priority,  sender, dest, r.nextFloat()+(r.nextInt(9)+1));
			br = hub.getBranches().get(sender.zip); 
			br.addPackage(p);
			p.setBranch(br); 
			break;
		case 2:
			p=new NonStandardPackage(priority,  sender, dest,  r.nextInt(1000), r.nextInt(500), r.nextInt(400));
			hub.addPackage(p);
			break;
		default:
			p=null;
			return;
		}
		
		this.packages.add(p);
		
	}
}
