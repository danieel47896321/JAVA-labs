package Program;

import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import components.*;
import components.Package;
import java.util.*;

/**
 * The Class PostSystemPanel.
 */
public class PostSystemPanel extends JPanel implements ActionListener {
   
   /** The Constant serialVersionUID. */
   private static final long serialVersionUID = 1L;
   
   /** The frame. */
   private Main frame;
   
   /** The p 1. */
   private JPanel p1;
   
   /** The b num. */
   private JButton[] b_num;
   
   /** The names. */
   private String[] names = {"Create system","Start","Stop","Resume","All packages info","Branch info"};
   
   /** The scroll pane. */
   private JScrollPane scrollPane;
   
   /** The is table visible. */
   private boolean isTableVisible = false;
   
   /** The is table 2 visible. */
   private boolean isTable2Visible = false;
   
   /** The color ind. */
   @SuppressWarnings("unused")
private int colorInd = 0;
   
   /** The started. */
   private boolean started = false;
   
   /** The game. */
   private MainOffice game = null;
   
   /** The packages number. */
   private int packagesNumber;
   
   /** The branches number. */
   static int branchesNumber;
   
   /** The trucks number. */
   @SuppressWarnings("unused")
private int trucksNumber;
   
   /**
    * Instantiates a new post system panel.
    *
    * @param f the f
    */
   public PostSystemPanel(Main f) {
	    frame = f;
	    isTableVisible = false;
	    setBackground(new Color(255,255,255));
	    p1=new JPanel();
		p1.setLayout(new GridLayout(1,7,0,0));
		p1.setBackground(new Color(0,150,255));
		b_num=new JButton[names.length];
		
		for(int i=0;i<names.length;i++) {
		    b_num[i]=new JButton(names[i]);
		    b_num[i].addActionListener(this);
		    b_num[i].setBackground(Color.lightGray);
		    p1.add(b_num[i]);		
		}

		setLayout(new BorderLayout());
		add("South", p1);
   }	
   
   
   /**
    * Creates the new post system.
    *
    * @param branches the branches
    * @param trucks the trucks
    * @param packages the packages
    */
   public void createNewPostSystem(int branches, int trucks, int packages) {
	   if (started) return;
	   game = new MainOffice(branches, trucks, this, packages);
	   packagesNumber = packages;
	   trucksNumber = trucks;
	   branchesNumber = branches;
	   
	   repaint();
   }
   

   /**
    * Paint component.
    *
    * @param g the g
    */
   public void paintComponent(Graphics g) {
	   	super.paintComponent(g);	
	   	   	
	   	if (game==null) return;
	   	
	   	@SuppressWarnings("static-access")
		Hub hub = game.getHub();
	   	ArrayList<Branch> branches = hub.getBranches();
	   	
	   	int offset = 403/(branchesNumber-1);
	   	int y=100;
	   	int y2=246;
	   	int offset2 = 140/(branchesNumber-1);
	   	
	   	g.setColor(new Color(0,102,0));
	   	g.fillRect(1120, 216, 40, 200);
	   	
	   	
	   	for (Branch br: branches) {
	   		br.paintComponent(g,y,y2);
	   		y+=offset;
	   		y2+=offset2;
	   	}
	   	
	   	
	   	int x = 150;
	   	int offset3 = (1154-300)/(packagesNumber-1);
	   	
	   	for (Package p: game.getPackages()) {
	   		p.paintComponent(g,x,offset);
	   		x+=offset3;
	   	}
	   	
	   	
		for (Branch br: branches) {
			for(Truck tr: br.getTrucks()) {
				tr.paintComponent(g);
			}
	   	}
		
		for(Truck tr: hub.getTrucks()) {
			tr.paintComponent(g);
		}
   	
   }
   
   
   
   /**
    * Sets the color index.
    *
    * @param ind the new color index
    */
   public void setColorIndex(int ind) {
	   this.colorInd = ind;
	   repaint();
   }
   
   
   /**
    * Sets the backgr.
    *
    * @param num the new backgr
    */
   public void setBackgr(int num) {
	   switch(num) {
	   case 0:
		   setBackground(new Color(255,255,255));
		   break;
	   case 1:
		   setBackground(new Color(0,150,255));
		   break;

	   }
	   repaint();
   }
   
   
   
   /**
    * Adds the.
    */
   public void add(){
	   CreatePostSystemlDialog dial = new CreatePostSystemlDialog(frame,this,"Create post system");
	   dial.setVisible(true);
   }
   
   

   /**
    * Start.
    */
   public void start() {
	   if (game==null || started) return;
	   Thread t = new Thread(game);
	   started = true;
	   t.start();
   }
   
	/**
	 * Resume.
	 */
	public void resume() {
		if (game == null) return;
		game.setResume();
   }

 	/**
	  * Stop.
	  */
	 public void stop() {
 		if (game == null) return;
	    game.setSuspend();
 	}

 	
    /**
     * Info.
     */
    public void info() {
 	   if (game == null || !started) return;
	   if(isTable2Visible == true) {
		   scrollPane.setVisible(false);
		   isTable2Visible = false;
	   }
 	   if(isTableVisible == false) {
 			 int i=0;
 			 String[] columnNames = {"Package ID", "Sender", "Destination", "Priority", "Staus"};
 			 ArrayList<Package> packages = game.getPackages();
 			 String [][] data = new String[packages.size()][columnNames.length];
 			 for(Package p : packages) {
 		    	  data[i][0] = ""+p.getPackageID();
 		    	  data[i][1] = ""+p.getSenderAddress();
 		    	  data[i][2] = ""+p.getDestinationAddress();
 		    	  data[i][3] = ""+p.getPriority();
 		    	  data[i][4] = ""+p.getStatus();
 		    	  i++;
 			 }
 			 JTable table = new JTable(data, columnNames);
 		     scrollPane = new JScrollPane(table);
 		     scrollPane.setSize(450,table.getRowHeight()*(packages.size())+24);
 		     add( scrollPane, BorderLayout.CENTER );
 		     isTableVisible = true;
 	   }
 	   else
 		   isTableVisible = false;
 	   
 	   scrollPane.setVisible(isTableVisible);
       repaint();
    }
    
   
   /**
    * Branch info.
    */
   @SuppressWarnings("static-access")
public void branchInfo() {
	   if (game == null || !started) return;
   
	   if(scrollPane!=null) scrollPane.setVisible(false);
	   isTableVisible = false;
	   isTable2Visible = false;
	   String[] branchesStrs = new String[game.getHub().getBranches().size()+1];
	   branchesStrs[0] = "Sorting center";
	   for(int i=1; i<branchesStrs.length; i++)
		   branchesStrs[i] = "Branch "+i;
	   @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cb = new JComboBox(branchesStrs);
	   String[] options = { "OK", "Cancel" };
	   String title = "Choose branch";
	   int selection = JOptionPane.showOptionDialog(null, cb, title,
	        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,
	        options[0]);

	   if (selection==1) return;
	   //System.out.println(cb.getSelectedIndex());
	   if(isTable2Visible == false) {
			 int i=0;
			 String[] columnNames = {"Package ID", "Sender", "Destination", "Priority", "Staus"};
			 @SuppressWarnings("unused")
			Branch branch;
			 List<Package> packages = null;
			 int size=0;
			 if(cb.getSelectedIndex()==0) {
				 packages = game.getHub().getPackages();
				 size = packages.size();
			 }
			 else {
				 packages = game.getHub().getBranches().get(cb.getSelectedIndex()-1).getPackages();
				 size = packages.size();
				 int diff = 0;
				 for(Package p : packages) {
					 if (p.getStatus()==Status.BRANCH_STORAGE) {
						 diff++;
					 }
				 }
				 size = size - diff/2;
			 }
			 String [][] data = new String[size][columnNames.length];
			 for(Package p : packages) {
				 boolean flag = false;
				 for(int j=0; j<i; j++)
					 if (data[j][0].equals(""+p.getPackageID())) {
						 flag = true;
						 break;
					 }
				 if (flag) continue;
		    	 data[i][0] = ""+p.getPackageID();
		    	 data[i][1] = ""+p.getSenderAddress();
		    	 data[i][2] = ""+p.getDestinationAddress();
		    	 data[i][3] = ""+p.getPriority();
		    	 data[i][4] = ""+p.getStatus();
		    	 i++;
			 }
			 JTable table = new JTable(data, columnNames);
		     scrollPane = new JScrollPane(table);
		     scrollPane.setSize(450,table.getRowHeight()*(size)+24);
		     add( scrollPane, BorderLayout.CENTER );
		     isTable2Visible = true;
	   }
	   else
		   isTable2Visible = false;
	   
	   scrollPane.setVisible(isTable2Visible);
       repaint();
   }

   
   /**
    * Destroy.
    */
   public void destroy(){  	        
      System.exit(0);
   }
   
   
   /**
    * Action performed.
    *
    * @param e the e
    */
   public void actionPerformed(ActionEvent e) {
	if(e.getSource() == b_num[0]) 
		add();
	else if(e.getSource() == b_num[1]) 
		start();
	else if(e.getSource() == b_num[2])  
		stop();
	else if(e.getSource() == b_num[3])  
		resume(); 
	else if(e.getSource() == b_num[4])  
		info();
	else if(e.getSource() == b_num[5])  
		branchInfo();
   }

}