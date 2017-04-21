import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;



public class DbAction {
	
	//DbmsUserBean dbmsUserBean;
	 private SeatClass seats;
	 public String verifyUser(String username, String pwd) throws ClassNotFoundException, IOException
	{
		// dbmsUserBean= new DbmsUserBean("root","Mihir","MYSQL","LOCALHOST","theater"); 
		// dbmsUserBean.getDataBaseConnection();
		 
		 String [][] sdata;
		 
		 String sql = "select username, pwd from UserTable where username = '"+username+"' and pwd = '"+pwd+"';";
		 System.out.println("The SQL query in function :" + sql);
		 sdata = connectToServer(sql)	;
		
		 if(sdata.length == 0)
		 {
			 System.out.println("The username or password you have entered is incorrect");
			 //dbmsUserBean.closeDataBaseConnection();
			 return "FAIL";
		 }
		 else
		 {
			 String user;
			 String password;
			 try{
			 user = sdata[0][0].toString();
			 password = sdata[0][1].toString();
			 }
			 catch(Exception e)
			 {
				 return "FAIL";
			 }
			 if (user.equals(username) && password.equals(pwd))
			 {
				// dbmsUserBean.closeDataBaseConnection();
				 return "SUCCESS";
			 }
			 else
			 {
				 System.out.println("The password and username do not match try again");
				 return "FAIL";
			 }
						 
		 }
		 
		
	}
	 
	 public String registerUser(String fn , String ln, String email, String username, String pwd, String confpwd) throws ClassNotFoundException, IOException
		{
		 if (!pwd.equals(confpwd))
		 {
			 
			 System.out.println("The passwords entered do not match please retry");
			 return "FAIL";
		 }
		 else
		 {
		// dbmsUserBean= new DbmsUserBean("root","Mihir","MYSQL","LOCALHOST","theater"); 
		 //dbmsUserBean.getDataBaseConnection();
		 
			
		 	// If not insert the new user name and password.
		 		
		 		connectToServerInserts("insert into UserTable values ('"+fn+"','"+ln+"','"+email+"','"+username+"','"+pwd+"');");
		 		//dbmsUserBean.closeDataBaseConnection();
		 		return "SUCCESS";
		 				 		
		 	}
		 }
		 
	 
	 public /* List<SeatClass>*/ String[][] getBookedSeats() throws ClassNotFoundException, IOException
	 	 {
		 
		 String[][] sdata;
		// dbmsUserBean= new DbmsUserBean("root","Mihir","MYSQL","LOCALHOST","theater"); 
		 //dbmsUserBean.getDataBaseConnection();
		 sdata =  connectToServer("select rowno, seatno, username from bookedseats;");
		 // select rowno, seatno, username from bookedseats;
		
		/* List<SeatClass> bookedseats = new ArrayList <SeatClass> (sdata.length);
		 
		 for(int i = 0 ; i<sdata.length;i++)
		 {
			 	seats= new SeatClass();
			 	seats.setRowNo(sdata[i][0].toString());
				seats.setSeatNo(Integer.parseInt(sdata[i][1].toString()));
				seats.setUserName(sdata[i][1].toString());
				bookedseats.add(seats);			 
		 }
		 
		 //dbmsUserBean.closeDataBaseConnection();*/
		 return sdata;
		 		 
	 }
	 
	 public String ReserveSeats(List<SeatClass> seatstobook, String username) throws ClassNotFoundException, IOException // maintaining session information in multiple clients scenario**
	 {
		 SeatClass seat;
		 //dbmsUserBean= new DbmsUserBean("root","Mihir","MYSQL","LOCALHOST","theater"); 
		 //dbmsUserBean.getDataBaseConnection();
		 
		 if(seatstobook.size() >4)
		 {
			 System.out.println("You cannot book more than 4 seats");
			 return "FAIL";
			 
		 }
		 else
		 {String[][] status = new String[20][20];
			 for(int i = 0 ; i<seatstobook.size();i++)
			 {
				seat = seatstobook.get(i);
				
				
				//String query = " insert into bookedseats values ('"+seat.getRowNo()+"',"+seat.getSeatNo()+",'"+seat.getUserName()+"');";
				 String query = "update theater set status = 'red', username='"+username+"' where  rowno='"+seat.getRowNo()+"'and seatno = "+seat.getSeatNo()+" and status = 'white' or status = 'blue';";
				
				connectToServerInserts(query);
			
			}
			 return "SUCCESS";
		  } 

	 }
	 
	 public String userAvaialable(String username) throws ClassNotFoundException, IOException
	 {
		 //dbmsUserBean= new DbmsUserBean("root","Mihir","MYSQL","LOCALHOST","theater"); 
		 //dbmsUserBean.getDataBaseConnection();
		 
		// String sql = "select distinct username from Usertable where username = '"+username+"';";
		 String result = "SUCCESS";
		 //Object[][] sdata =dbmsUserBean.getObjectexecuteQuery(sql);
		 Object[][] sdata = connectToServer("select distinct username from Usertable where username = '"+username+"';");
		 System.out.println("username "+sdata[0][0].toString() );
		 
		 if ((sdata[0][0].toString()).equalsIgnoreCase(username) )
		 {
			 //dbmsUserBean.closeDataBaseConnection();
			 System.out.println("MATCHED in DATABASE");
			 result= "FAIL";
			
		 }
		 else if(sdata[0][0].toString() == null)
		 {
			 //dbmsUserBean.closeDataBaseConnection();
			 System.out.println("Does not Exist");
			 result= "SUCCESS";
		 }
		 else if (sdata[0][0].toString() == " ")
		 {
			 System.out.println("is empty string");
			 result = "SUCCESS";
		 }
		 
		 return result;
		 
	 }
	 
	 public int getAvaialableSeats(String username) throws ClassNotFoundException, IOException{
		 
		 //dbmsUserBean= new DbmsUserBean("root","Mihir","MYSQL","LOCALHOST","theater"); 
		 //dbmsUserBean.getDataBaseConnection();
		 String sql = " select 4-count(*) from theater where username = '"+username+"' and status ='red';";
		 
		 //Object[][] sdata = dbmsUserBean.getObjectexecuteQuery(sql);
		 Object[][] sdata = connectToServer(sql);
		 int availableseats =  Integer.parseInt(sdata[0][0].toString());
		// dbmsUserBean.closeDataBaseConnection();
		 return availableseats;
		 
	 }
	 
	 public String[] getUserNames() throws ClassNotFoundException, IOException{
		 
		// dbmsUserBean= new DbmsUserBean("root","Mihir","MYSQL","LOCALHOST","theater"); 
		 //dbmsUserBean.getDataBaseConnection();
		 String [] s = null;
		 //Object[][] sdata = dbmsUserBean.getObjectexecuteQuery("select distinct username from usertable");
		 Object[][] sdata = connectToServer("select distinct username from usertable");
		 
		 for (int i = 0; i < sdata.length ; i++)
		 {
			 s[i] = sdata[i][0].toString();
		 }
		// dbmsUserBean.closeDataBaseConnection();
		 return s;
	 }
	 
	 public /* List <SeatClass>*/ String[][] getSeatsforUser(String username) throws ClassNotFoundException, IOException{

		 //dbmsUserBean= new DbmsUserBean("root","Mihir","MYSQL","LOCALHOST","theater"); 
		 //dbmsUserBean.getDataBaseConnection();
		// List <SeatClass> s = new ArrayList<>(10) ;
		 String[][] sdata;
		
		// Object[][] sdata = dbmsUserBean.getObjectexecuteQuery("select rowno, seatno from theater where username='"+username+"';");
		 
		 sdata = connectToServer("select rowno, seatno from theater where username='"+username+"' and status = 'red';");
		/* for (int i = 0; i < sdata.length ; i++)
		 {
			 SeatClass seat  = new SeatClass();
				seat.setRowNo(sdata[i][0].toString());
				seat.setSeatNo(Integer.parseInt(sdata[i][1].toString()));
				s.add(seat);
			 
		 }*/
		// dbmsUserBean.closeDataBaseConnection();
		 return sdata;
	 }
	 
	 

	public /* List <SeatClass>*/ String[][] getAllBookedSeats() throws ClassNotFoundException, IOException{

		 //dbmsUserBean= new DbmsUserBean("root","Mihir","MYSQL","LOCALHOST","theater"); 
		 //dbmsUserBean.getDataBaseConnection();
		//List<SeatClass>  s = new ArrayList<>(10)  ;
		String[][] sdata;
		
		//Object[][] sdata = dbmsUserBean.getObjectexecuteQuery("select username, rowno, seatno from theater where status = 'red';");
		 sdata = connectToServer("select username, rowno, seatno from theater where status = 'red';");
		System.out.println("LENGTH of SEATS BOOKED "+ sdata.length);
		/*for (int i = 0; i < sdata.length ; i++)
		 {			 
			
			SeatClass seat = new SeatClass();
			seat.setUserName(sdata[i][0].toString());
			seat.setRowNo(sdata[i][1].toString());
			seat.setSeatNo(Integer.parseInt(sdata[i][2].toString()));
			s.add(seat);
		 }
		 */
		 
		 //dbmsUserBean.closeDataBaseConnection();
		 return sdata;
	 }
	 
	 @SuppressWarnings("null")
	public static String[][] connectToServer(String sql) throws IOException, ClassNotFoundException {

	        // Get the server address from a dialog box.
		// String sql = sql;
		 	String[][] sdata = new String[20][20];
		 	System.out.println("The Query in server call "+ sql);
	        String serverAddress = "10.1.235.86";
	        // Make connection and initialize streams
	        @SuppressWarnings("resource")
			Socket socket = new Socket(serverAddress, 9898);
	        System.out.println("Socket and port acquired");
	       
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(socket.getInputStream()));
	       
	        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	        
	                
	        System.out.println("Connected ");
	        
	        out.println(sql);
	        
	        String msg;
	        msg =  in.readLine();
	        
	        System.out.println(" from SERVER " +msg);
	       
	       
	        
	        msg = in.readLine();
	        System.out.println(" from SERVER " +msg);
	        int len = Integer.parseInt(msg);
	        System.out.println("length "+len);
	        int ylen ;
        	for (int i = 0 ; i < len; i++)
        	{	
        			msg = in.readLine();
        			System.out.println(" from SERVER " +msg);
        			ylen = Integer.parseInt(msg);
        			//sdata[i] = new String[ylen];  			
        		for(int j = 0; j < ylen; j++)
        		{
        			msg = in.readLine();
        			System.out.println("from SERVER "+ msg);
        			sdata[i][j] = msg;
        		}
        	}
	        
	        
	        
	        return sdata;
	       
	    } 
	 
	 public void holdSeats(String row, int seat,String username){
		 
		 String sql = "update theater set status='blue', username = '"+username+"' where rowno ='"+row+"' and seatno = "+seat+";";
		 
		 
		 try {
			connectToServerInserts(sql);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 
	 }
	 
	 public void unholdSeats(String row, int seat,String username){

		 String sql = "update theater set status='white', username = null where rowno ='"+row+"' and seatno = "+seat+";";
		 
		 
		 try {
			connectToServerInserts(sql);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 
	 }
	 
	 public void resetSeats(String username){

		 String sql = "update theater set status='white' where username='"+username+"' and status = 'blue';";
		 
		 
		 try {
			connectToServerInserts(sql);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 
	 }
	 
	 public static void connectToServerInserts(String sql) throws IOException, ClassNotFoundException {

	        // Get the server address from a dialog box.
		 	Object[][] sdata;
	        String serverAddress = "10.1.235.86";
	        // Make connection and initialize streams
	        Socket socket = new Socket(serverAddress, 9898);
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(socket.getInputStream()));
	        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

	       // ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
	       // ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());
	        System.out.println("Socket and port acquired");
	        
	        System.out.println("The Query in server call "+ sql);
	        
	         out.println(sql);
	         
	       //  sdata = (Object[][]) inFromServer.readObject();
	        
	        
	        } 
}