import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JScrollPane;

public class SeatArrangement extends JFrame  implements ActionListener  {

	private JPanel contentPane;
	private JButton button[][]= new JButton[5][20];
	private static final String row[] = {"A","B","C","D","E"};
	private int countSeats = 0;
	private int availableSeats;
	private String username;
	public JLabel label1;
	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	List<SeatClass> seatsToBook = new ArrayList <SeatClass> (availableSeats);
	List<SeatClass> toRemove = new ArrayList <SeatClass> (availableSeats);
	static SeatArrangement seat;
	/**
	 * Launch the application.
	 */
	// to be commented.
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 seat= new SeatArrangement();
					seat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public SeatArrangement() throws ClassNotFoundException, IOException {
		//contentPane.setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1320, 759);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		String color = "black";
		 label1 = new JLabel();
			label1.setBounds(939, 29, 400, 40);
			contentPane.add(label1);
		// have to fetch the status of each seat displayed.
		
		//JButton [][] button= new JButton[5][20];
		
		// DbmsUserBean dbmsUserBean= new DbmsUserBean("root","Mihir","MYSQL","LOCALHOST","theater"); 
		// dbmsUserBean.getDataBaseConnection();
		
		 Object[][] sdata;
		
		int yaxis=175, width = 60, height = 30; 
		
		
		
		for(int i = 0; i<5 ; i++)
		{	int xaxis = 40;
			JLabel lblAll = new JLabel(row[i]);
			lblAll.setForeground(Color.BLACK);
			lblAll.setFont(new Font("Dialog", Font.BOLD, 18));
			lblAll.setBounds(xaxis,yaxis, 272, 40);
		contentPane.add(lblAll);
			//int xaxis = 40;
			
			for(int j =0 ; j<20 ; j++)
			{	xaxis = xaxis+60;
				int seatno = j+1; 
				String rowno = row[i];
				String sql = "select status from theater where rowno='"+rowno+"' and seatno = "+seatno+";";
				// dbmsUserBean.getDataBaseConnection();
				 //sdata = dbmsUserBean.getObjectexecuteQuery(sql);
				 sdata =  DbAction.connectToServer(sql);
				 //dbmsUserBean.closeDataBaseConnection();
				if(sdata.length>0)
				{
					color= sdata[0][0].toString();
				}
				
				button[i][j] = new JButton("<HTML><body bgcolor = '"+color+"'>"+seatno+"</HTML>");
				button[i][j].setBounds(xaxis, yaxis, width, height);
				button[i][j].addActionListener(this);
				button[i][j].setActionCommand(color+","+rowno+","+seatno);
				contentPane.add(button[i][j]);
				//xaxis = xaxis+50;
				//dbmsUserBean.closeDataBaseConnection();
			}
			yaxis = yaxis + 50;
		}
		
		
		JLabel lblAll = new JLabel("ALL EYES THIS WAY PLEASE");
		lblAll.setForeground(Color.RED);
		lblAll.setFont(new Font("Dialog", Font.BOLD, 18));
		lblAll.setBounds(430, 66, 272, 40);
		contentPane.add(lblAll);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnConfirm.setBounds(477, 530, 117, 25);
		btnConfirm.addActionListener(this);
		btnConfirm.setActionCommand("confirm");		
		contentPane.add(btnConfirm);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addActionListener(this);
		btnLogout.setActionCommand("logout");	
		btnLogout.addActionListener(this);
		btnLogout.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnLogout.setBounds(732, 530, 117, 25);
		contentPane.add(btnLogout);
		
		
		
		
	}

	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		
		 DbAction dbAction = new DbAction();
		 String msg= "SHOPPING CART :";
		
		
		
		Object source = event.getSource();
		
		if (source instanceof JButton) {
			
			
		    JButton button = (JButton) source;
		    String btn = event.getActionCommand();
		    System.out.println(" action command " + btn);
		    
		    String name = button.getText();
		    
		    if(name.equalsIgnoreCase("Confirm"))
		    {
		    	/*for(int k = 0; k<seatsToBook.size() ;k++)
		    	{
		    		String ms = seatsToBook.get(k).getRowNo();
		    		int seatnum = seatsToBook.get(k).getSeatNo();
		    		System.out.println("SEAT "+ rownum + " " +  seatnum);
		    	}
		    	*/
		    	String result = "FAIL";
				try {
					result = dbAction.ReserveSeats(seatsToBook,username);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	if (result.equalsIgnoreCase("Success"))
		    	{
		    		System.out.println("seats booked");
		    		//SwingUtilities.updateComponentTreeUI(seat);
		    		
		    		this.setVisible(false); //this will close frame i.e. NewJFrame

		    		try {
						new SeatArrangement().setVisible(true);
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    		 
		    		Component frame = null;
					JOptionPane.showMessageDialog(frame, "Seats are Booked Redirecting to LOGIN page");
		    		
		    		 EventQueue.invokeLater(new Runnable() {
		    				public void run() {
		    					try {
		    						BookMyShow frame = new BookMyShow();
		    						frame.setVisible(true);
		    					} catch (Exception e) {
		    						e.printStackTrace();
		    					}
		    				}
		    			});
		    	}
		    	else if (result.equalsIgnoreCase("fail"))
		    	{
		    		Component frame = null;
		    		JOptionPane.showMessageDialog(frame, "Seats were not booked because of some error. Please try again later.");
		    	}
		    	
		    }
		    else if(name.equals("logout") || btn.equals("logout") )
		    {
		    	dbAction.resetSeats(username);
		    	EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							BookMyShow frame = new BookMyShow();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
		    }
		    else{
		    	
		    if(countSeats < availableSeats)
			{   
		    	SeatClass seat = null;
				seat = new SeatClass();
		   
		    	String seatDetails = event.getActionCommand();
		    
		    	String[] details = seatDetails.split(",");
		    	
		    	
		    	
		   
		    	String btntext = button.getText();
		    
		    	System.out.println(" Button Text " + btntext);
		    
		    	if(details[0].equalsIgnoreCase("White") && btntext.contains("white"))
			    	{
		    			button.setText("<HTML> <body bgcolor='green'> A </body> </HTML>");
			    		int duplicate = 2;
			    		seat.setRowNo(details[1]);
					    
				    	seat.setSeatNo(Integer.parseInt(details[2]));
				    	//code to hold the seat.
				    	
				    	dbAction.holdSeats(details[1], Integer.parseInt(details[2]),username);
				    	
				    	if(seatsToBook.size()>0)
				    	{
				    	for(int k = 0; k<seatsToBook.size() ;k++)
				    	{
				    		String rownum = seatsToBook.get(k).getRowNo();
				    		int seatnum = seatsToBook.get(k).getSeatNo();
				    		
				    		if (!seatsToBook.get(k).equals(seat))
				    			duplicate = 0;
				    		else
				    			duplicate = 1;
				    	}
				    	 if (duplicate == 0)
				    	{seatsToBook.add(seat);
			    		countSeats++;
				    	}
				    	}
				    	else
				    	{
				    		seatsToBook.add(seat);
				    		countSeats++;
				    	}
		    		}
		    	else if(btntext.equalsIgnoreCase("<HTML> <body bgcolor='green'> A </body> </HTML>"))
		    	{
		    		SeatClass seatunselect = null;
					seatunselect = new SeatClass();
		    		seatunselect.setRowNo(details[1]);
				    
		    		seatunselect.setSeatNo(Integer.parseInt(details[2]));
		    		// unhold the seat
		    		dbAction.unholdSeats(details[1], Integer.parseInt(details[2]), username);
		    		button.setText("<HTML> <body bgcolor='white'> "+details[2]+" </body> </HTML>");
		    		
		    		//seatsToBook.remove(seatunselect);
		    		toRemove.add(seatunselect);
		    		
		    		countSeats--;
		    		
		    		
		    		
		    	}
		    	else
		    		{
		    		button.setText("<HTML> <body bgcolor='red'> U</body> </HTML>");
		    		//countSeats --;
		    		}
		    	
		     
		    	
		    	System.out.println("List size " + seatsToBook.size());
		    }
		    else
		    {
		    	String seatDetails = event.getActionCommand();
			    
		    	String[] details = seatDetails.split(",");
		    	
		    	String btntext = button.getText();
		    	
		    	if(btntext.equalsIgnoreCase("<HTML> <body bgcolor='green'> A </body> </HTML>"))
		    	{
		    		SeatClass seatunselect = null;
					seatunselect = new SeatClass();
		    		seatunselect.setRowNo(details[1]);
				    
		    		seatunselect.setSeatNo(Integer.parseInt(details[2]));
		    		button.setText("<HTML> <body bgcolor='white'> "+details[2]+" </body> </HTML>");
		    		
		    		//seatsToBook.remove(seatunselect);
		    		toRemove.add(seatunselect);
		    		
		    		
		    		
		    	countSeats--;
		    		
		    		
		    }
		  }
		    System.out.println("count is " + countSeats);
		   
		    
		    
		    
		    if(seatsToBook.removeAll(toRemove))
		    { System.out.println("Seats Removed");
		     msg = "SHOPPING CART :";
		    }
		    
		   
		    
		    for(int k = 0; k<seatsToBook.size() ;k++)
	    	{
	    		String rownum = seatsToBook.get(k).getRowNo();
	    	
	    		int seatnum = seatsToBook.get(k).getSeatNo();
	    	   msg = msg + rownum+":"+seatnum;
	    		System.out.println("SEATS SELECTED - " + rownum+" "+ seatnum);
	    	}
		    
		    label1.setText(msg);
			
		   
		   }
		    
		  
		}
		 
	}
}
