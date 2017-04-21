import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;


public class AdminReport extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField;
	//private String table = null;
	private JLabel lblNewLabel;
	private JLabel lblEnterUsername;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminReport frame = new AdminReport();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public AdminReport() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 760, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(64, 11, 395, 292);
		contentPane.add(lblNewLabel);
		
		JButton btnGetAllBooked = new JButton("Get all booked seats ");
		btnGetAllBooked.addActionListener(this);
		btnGetAllBooked.setActionCommand("bookedSeats");
		btnGetAllBooked.setBounds(59, 368, 195, 23);
		contentPane.add(btnGetAllBooked);
		
		JButton btnGetSeatBooked = new JButton("Get seats booked by a user");
		btnGetSeatBooked.addActionListener(this);
		btnGetSeatBooked.setActionCommand("bookedSeatsbyUser");
		btnGetSeatBooked.setBounds(437, 357, 241, 23);
		contentPane.add(btnGetSeatBooked);
		
		textField = new JTextField();
		textField.setBounds(469, 301, 176, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblEnterUsername = new JLabel("Enter username");
		lblEnterUsername.setBounds(510, 332, 105, 14);
		contentPane.add(lblEnterUsername);
}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String ac = e.getActionCommand();
		DbAction dbaction = new DbAction();
		if(ac.equals("bookedSeats"))
		{
			// code to create table for all booked seats.
			//List<SeatClass> s;
			String[][] s;
			try {
				s = dbaction.getAllBookedSeats();
				//String table ;
				//	table = " <HTML> <table>";
					lblNewLabel.setText("<HTML> <table> <TR><TH> USERNAME </TH> <TH> ROW </TH> <TH> SEAT NO</TH> </TR>");
					for(int i = 0 ; i < s.length ; i++)
					{
						//table = table + "<tr><td>"+s.get(i).getUserName()+"</td>"+s.get(i).getRowNo()+"</td>"+s.get(i).getSeatNo()+"</td></tr>";
						if(s[i][0].isEmpty() || s[i][0] == null)
						{
							continue;
						}
						
						lblNewLabel.setText(lblNewLabel.getText() + "<TR><TD> " + s[i][0]+ "</TD><TD> "+s[i][1]+"</TD><TD> "+s[i][2]+ "</TD></TR>");
					}
					lblNewLabel.setText(lblNewLabel.getText()+"</TABLE></HTML>");
				//	table = table + " </table> </HTML>";
					
					//System.out.println("table " + table);
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 
			
		}
		else if(ac.equals("bookedSeatsbyUser"))
				{
					String username = textField.getText();
					System.out.println("USER NAME " + username);
					//List<SeatClass> s;
					String[][] s;
					try {
						s = dbaction.getSeatsforUser(username);
						lblNewLabel.setText("<HTML> <table> <TR><TH> ROW </TH> <TH> SEAT NO</TH> </TR>");
						for(int i = 0 ; i < s.length ; i++)
						{
							try{
							if(s[i][0].isEmpty() || s[i][0] == null)
							{
								continue;
							}
							}
							catch(Exception e3)
							{
								continue;
							}
							//table = table + "<tr><td>"+s.get(i).getUserName()+"</td>"+s.get(i).getRowNo()+"</td>"+s.get(i).getSeatNo()+"</td></tr>";
							lblNewLabel.setText(lblNewLabel.getText() + "<TR><TD> "+s[i][0]+"</TD><TD> "+s[i][1]+ "</TD></TR>");
						}
						lblNewLabel.setText(lblNewLabel.getText()+"</TABLE></HTML>");
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				
				}
	}
}
