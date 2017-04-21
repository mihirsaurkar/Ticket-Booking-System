//import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
//import javax.swing.JSplitPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.ImageIcon;

public class BookMyShow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1; 
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;	
	private JTextField textField_7;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)throws IOException {
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

	/**
	 * Create the frame.
	 */
	public BookMyShow() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1320, 759);
		contentPane = new JPanel();
		frame = new JFrame();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		DbAction verify = new DbAction();
			
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			String username = textField.getText();
			String pwd = textField_1.getText();
			String result;
			try {
				result = verify.verifyUser(username, pwd);
			
			if(result.equalsIgnoreCase("Success"))
			{
				if(username.equals("Admin"))
				{
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
					
				}
				else
				{
					SeatArrangement seat =  new SeatArrangement();
					seat.setUsername(username);
					seat.setAvailableSeats(verify.getAvaialableSeats(username));
					//redirect to Seat booking window.
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								
								seat.setVisible(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
			else{
				
				//code for wrong credentials
				JOptionPane.showMessageDialog(frame, "The username and password you have entered are incorrect!");
			}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
			}
			
			//
		});
		
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnLogin.setBounds(337, 578, 97, 25);
		contentPane.add(btnLogin);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(329, 474, 197, 25);
		contentPane.add(textField_1);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUsername.setBounds(174, 387, 97, 16);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPassword.setBounds(174, 476, 97, 16);
		contentPane.add(lblPassword);
		
		JLabel lblRegister = new JLabel("New User?? Register here...");
		lblRegister.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblRegister.setBounds(861, 226, 297, 36);
		contentPane.add(lblRegister);
		
		JLabel lblL = new JLabel("Existing User?? Login here...");
		lblL.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblL.setBounds(215, 231, 297, 26);
		contentPane.add(lblL);
		
		textField_2 = new JTextField();
		textField_2.setBounds(981, 297, 177, 25);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(981, 355, 177, 25);
		contentPane.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(981, 414, 177, 25);
		contentPane.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(981, 474, 177, 25);
		contentPane.add(textField_5);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fn = textField_2.getText();
				String ln = textField_3.getText();
				String email = textField_4.getText();
				String username = textField_5.getText();
				String pwd = textField_6.getText();
				String confpwd = textField_7.getText();
				 int userlength = username.length() ;
				boolean flag = false;
				
				if( userlength > 12 )
				{
					flag = false;
					JOptionPane.showMessageDialog(frame, " Length of username is not between 6 & 12");
					}
				else if (userlength < 6)
				{
					flag = false;
					JOptionPane.showMessageDialog(frame, " Length of username is not between 6 & 12");
				}
				else if((pwd.length() > 12 ))	
				{
						 flag = false;
					 // prompt pass word length incorrect.
					 JOptionPane.showMessageDialog(frame, "pwd is not 6 to 12 characters long");
					 
				}
				else if (pwd.length() <6)
				{
					 flag = false;
					 // prompt pass word length incorrect.
					 JOptionPane.showMessageDialog(frame, "pwd is not 6 to 12 characters long");
				}
				else if(! pwd.equals(confpwd))
				{
					 flag = false;
					 // prompt pass word length incorrect.
					 JOptionPane.showMessageDialog(frame, "Password and Confirm Password do not match.");
					 
				}
				else if( email.contains("@") ){
						if(email.contains("."))
						{
							int pos1 = email.indexOf("@");
							int pos2 = email.indexOf(".");
								if (pos2 > pos1)
								{
									if  ((pos2 - pos1) < 3)
									{ flag = false;
									JOptionPane.showMessageDialog(frame, "Invalid email id.");
									}
									else
									{
										 flag = true;
									}
								}
								else
								{   flag = false;
									JOptionPane.showMessageDialog(frame, "Invalid email id.");
								}
						}
						else
						{	flag = false;
							JOptionPane.showMessageDialog(frame, "Invalid email id.");
						}
				}
				else if (  !email.contains("@")) 
				{
					flag = false;
					JOptionPane.showMessageDialog(frame, "Invalid email id.");
				}
				else 
				{
						String result = "DEFAULT";
						try {
							result = verify.userAvaialable(username);
							System.out.println("RESULT of Query execution :" + result);
						} catch (ClassNotFoundException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if( ! result.equalsIgnoreCase("SUCCESS"))
						{
				
								flag = false ;
					
							JOptionPane.showMessageDialog(frame, "Please Choose a new Username as it already exists.");
						}
						else
						{
							flag = true;
						}
				}
				
				if(flag)
				{
					String result1="DEFAULT";
					try {
						result1=verify.registerUser(fn, ln, email, username, pwd, confpwd);
						System.out.println("RESULT of Query execution :" + result1);
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(result1.equalsIgnoreCase("SUCCESS"))
					{
						//prompt for login
						JOptionPane.showMessageDialog(frame, "User Registered please login now.");
					}
				else{
						//something went wrong.
					JOptionPane.showMessageDialog(frame, "Please Choose a new Username as it already exists.");
				}
				}
							
			}
		});
		btnRegister.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnRegister.setBounds(1007, 661, 110, 25);
		contentPane.add(btnRegister);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(981, 535, 177, 25);
		contentPane.add(textField_6);
		
		JLabel label = new JLabel("Password");
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label.setBounds(812, 538, 97, 16);
		contentPane.add(label);
		
		JLabel label_2 = new JLabel("Email id");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_2.setBounds(812, 417, 97, 16);
		contentPane.add(label_2);
		
		JLabel label_1 = new JLabel("Username");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_1.setBounds(812, 477, 97, 16);
		contentPane.add(label_1);
		
		JLabel label_3 = new JLabel("Last Name");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_3.setBounds(812, 358, 97, 16);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("First Name");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_4.setBounds(812, 300, 97, 16);
		contentPane.add(label_4);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Sima Singh\\BookMyShow.png"));
		lblNewLabel.setBounds(281, 32, 678, 119);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(329, 385, 197, 25);
		contentPane.add(textField);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblConfirmPassword.setBounds(812, 598, 135, 16);
		contentPane.add(lblConfirmPassword);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(981, 595, 177, 25);
		contentPane.add(textField_7);
	}
	
	
}
