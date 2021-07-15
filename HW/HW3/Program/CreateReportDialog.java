package Program;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * The Class CreateReportDialog.
 */
public class CreateReportDialog extends JDialog  implements  ActionListener {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The p. */
	private JPanel p;
    
    /** The ok. */
    private JButton ok;
    
    /** The Head. */
    private JLabel Head;
    
    /** The Area. */
    private TextArea Area;
    
    /** The File name. */
    private String FileName;
 
    /**
     * Instantiates a new creates the report dialog.
     *
     * @param parent the parent
     * @param title the title
     * @param filename the filename
     */
    public CreateReportDialog(Main parent,String title,String filename) {
    	super((Main)parent,title,true);
    	FileName = filename;
    	this.setBounds(parent.getX()+120,parent.getY()+ 200, 600, 300);
		setBackground(new Color(100,230,255));
		p = new JPanel();
		Area = new TextArea(10,10);
		Area.setBounds(18, 40, 550, 180);
		Area.setVisible(true);
		this.add(Area);
		Head = new JLabel("Report Info: ");
		Head.setBounds(250, -5, 100, 50);
		Head.setLayout(null);
		Head.setVisible(true);
		this.add(Head);
		//p.setLayout(new GridLayout(1,2,5,5));
		ok=new JButton("Refresh");
		ok.addActionListener(this);
		ok.setBackground(Color.lightGray);
		p.add(ok);		
		setLayout(new BorderLayout());
		add("South" , p);
		OpenTable(FileName);
    }
    
    /**
     * Open table.
     *
     * @param FileName the file name
     */
    public void OpenTable(String FileName) {
    	File input = new File(FileName);
	    try {  
	        FileReader myReader = new FileReader(input);
	        BufferedReader buffer = new BufferedReader(myReader);        
	        String line;
	        while((line=buffer.readLine())!=null) {
	        	Area.setText(Area.getText()+line+"\n");
	        }
	        myReader.close();
	      }
	    catch (IOException e1) { System.out.println("An error occurred.");}	
    }

    /**
     * Action performed.
     *
     * @param e the e
     */
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource() == ok) {
    		Area.setText("");
    		OpenTable(FileName);
    	}
    }
}
