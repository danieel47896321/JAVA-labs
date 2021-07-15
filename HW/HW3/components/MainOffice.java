package components;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

/**
 * The Class MainOffice.
 */
public class MainOffice implements Runnable{
	
	/** The clock. */
	private static int clock=0;
	
	/** The hub. */
	private static Hub hub;
	
	/** The packages. */
	private ArrayList<Package> packages=new ArrayList<Package>();
	
	/** The panel. */
	private JPanel panel;
	
	/** The max packages. */
	private int maxPackages;
	
	/** The thread suspend. */
	private boolean threadSuspend = false;
	
	/** The flag. */
	public static boolean flag = false;
	
	/** The Report number. */
	private static int Report_Number = 1;
	
	/** The lst. */
	private ArrayList<Boolean> lst = new ArrayList<Boolean>();
	
	/** The Main office instance. */
	private static MainOffice MainOfficeInstance = null;
	
	/**
	 * Gets the main office instance.
	 *
	 * @return the main office instance
	 */
	public static MainOffice getMainOfficeInstance() {
		if (MainOfficeInstance == null)
			MainOfficeInstance = new MainOffice();
		return MainOfficeInstance;
	}
	
	/**
	 * Instantiates a new main office.
	 *
	 * @param branches the branches
	 * @param trucksForBranch the trucks for branch
	 * @param panel the panel
	 * @param maxPack the max pack
	 */
	public MainOffice(int branches, int trucksForBranch, JPanel panel, int maxPack) {
		this.panel = panel;
		this.maxPackages = maxPack;
		addHub(trucksForBranch);
		addBranches(branches, trucksForBranch);
		System.out.println("\n\n========================== START ==========================");
	}
	
	/**
	 * Instantiates a new main office.
	 */
	public MainOffice() { }
	
	/**
	 * Report.
	 *
	 * @param report the report
	 */
	public synchronized void Report(String report) {
		lst.add(false);
		File output = new File("tracking.txt");
	    try {
	        FileWriter myWriter = new FileWriter(output,flag);
	        flag=true;
	        myWriter.write("Report Number "+Report_Number+": " +report+"\n");
	        myWriter.close();
	      }
	    catch (IOException e1) { System.out.println("An error occurred.");}
	    Report_Number+=1;
	}
	
	/**
	 * Gets the hub.
	 *
	 * @return the hub
	 */
	public static Hub getHub() {
		return hub;
	}


	/**
	 * Gets the clock.
	 *
	 * @return the clock
	 */
	public static int getClock() {
		return clock;
	}

	/**
	 * Run.
	 */
	@Override
	public void run() {
		Thread hubThrad = new Thread(hub);
		hubThrad.start();
		for (Truck t : hub.listTrucks) {
			Thread trackThread = new Thread(t);
			trackThread.start();
		}
		for (Branch b: hub.getBranches()) {
			Thread branch = new Thread(b);
			for (Truck t : b.listTrucks) {
				Thread trackThread = new Thread(t);
				trackThread.start();
			}
			branch.start();
		}
		while(true) {
		    synchronized(this) {
                while (threadSuspend)
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    }
			tick();
		}
		
	}
	
	
	/**
	 * Prints the report.
	 */
	public void printReport() {
		for (Package p: packages) {
			System.out.println("\nTRACKING " +p);
			for (Tracking t: p.getTracking())
				System.out.println(t);
		}
	}
	
	
	/**
	 * Clock string.
	 *
	 * @return the string
	 */
	public String clockString() {
		String s="";
		int minutes=clock/60;
		int seconds=clock%60;
		s+=(minutes<10) ? "0" + minutes : minutes;
		s+=":";
		s+=(seconds<10) ? "0" + seconds : seconds;
		return s;
	}
	
	
	/**
	 * Tick.
	 */
	public void tick() {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(clockString());
		if (clock++%5==0 && maxPackages>0) {
			addPackage();
			maxPackages--;
		}
		/*branchWork(hub);
		for (Branch b:hub.getBranches()) {
			branchWork(b);
		}*/
		panel.repaint();
	}
	
	
	
	/**
	 * Branch work.
	 *
	 * @param b the b
	 */
	public void branchWork(Branch b) {
		for (Truck t : b.listTrucks) {
			t.work();
		}
		b.work();
	}
	
	
	/**
	 * Adds the hub.
	 *
	 * @param trucksForBranch the trucks for branch
	 */
	public void addHub(int trucksForBranch) {
		hub=new Hub();
		for (int i=0; i<trucksForBranch; i++) {
			Truck t = new StandardTruck();
			hub.addTruck(t);
		}
		Truck t=new NonStandardTruck();
		hub.addTruck(t);
	}
	
	
	/**
	 * Adds the branches.
	 *
	 * @param branches the branches
	 * @param trucks the trucks
	 */
	public void addBranches(int branches, int trucks) {
		for (int i=0; i<branches; i++) {
			Branch branch=new Branch();
			for (int j=0; j<trucks; j++) {
				branch.addTruck(new Van());
			}
			hub.add_branch(branch);		
		}
	}
	
	
	/**
	 * Gets the packages.
	 *
	 * @return the packages
	 */
	public ArrayList<Package> getPackages(){
		return this.packages;
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
	
	
	/**
	 * Sets the suspend.
	 */
	public synchronized void setSuspend() {
	   	threadSuspend = true;
		for (Truck t : hub.listTrucks) {
			t.setSuspend();
		}
		for (Branch b: hub.getBranches()) {
			for (Truck t : b.listTrucks) {
				t.setSuspend();
			}
			b.setSuspend();
		}
		hub.setSuspend();
	}

	
	
	/**
	 * Sets the resume.
	 */
	public synchronized void setResume() {
	   	threadSuspend = false;
	   	notify();
	   	hub.setResume();
		for (Truck t : hub.listTrucks) {
			t.setResume();
		}
		for (Branch b: hub.getBranches()) {
			b.setResume();
			for (Truck t : b.listTrucks) {
				t.setResume();;
			}
		}
	}



}
