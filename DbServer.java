import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//import CapitalizeServer.Capitalizer;

public class DbServer {
	
	
	public static void main(String[] args) throws IOException {
		
		// TODO Auto-generated method stub
		
		 System.out.println("The Db server is running.");
	        int clientNumber = 0;
	        ServerSocket listener = new ServerSocket(9898);
	        try {
	            while (true) {
	                new Dbservice(listener.accept(), clientNumber++).start();
	            }
	        } finally {
	            listener.close();
	        }

	}

	private static class Dbservice extends Thread{
		 private Socket socket;
	        private int clientNumber;
	        DbmsUserBean dbuser ;
	    	//SeatClass seats;
	    	
	        public Dbservice(Socket socket, int clientNumber) {
	            this.socket = socket;
	            this.clientNumber = clientNumber;
	            System.out.println("New connection with client# " + clientNumber + " at " + socket);
	        }
		
	        
	        public void run(){
	        	try{
	        		
	        		Object[][] sdata;
	        		int len = 0;
	        		int ylen=0;
	        		BufferedReader in = new BufferedReader(
	                        new InputStreamReader(socket.getInputStream()));
	                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	                String sql = in.readLine();
	                System.out.println("SQL "+sql);
	                out.println("Hello, you are client #" + clientNumber + ".");
	                
	               
	               
	                
	             
	                    dbuser = new DbmsUserBean("root","Mihir","MYSQL","LOCALHOST","theater");
                    	dbuser.getDataBaseConnection();
                    	System.out.println("Database Connected");
                    	if (sql.contains("select"))
                    	{
                    	sdata = dbuser.getObjectexecuteQuery(sql);
                    	
                    	len = sdata.length;
                    	
                    	out.println(len);
                    	for (int i = 0 ; i < sdata.length; i++)
                    	{
                    			ylen = sdata[i].length;
                    			out.println(ylen);
                    		for(int j = 0; j < sdata[i].length; j++)
                    		{
                    			
                    			out.println(sdata[i][j]);
                    			System.out.println(sdata[i][j]);
                    		}
                    	}
                       
                    	
                    	}
                    	else
                    	{
                    		dbuser.executeQuery(sql);
                    	}
                    	
                    	
                    	dbuser.closeDataBaseConnection();
	              
	        	}
	        	catch (IOException e) {
	                System.out.println("Error handling client# " + clientNumber + ": " + e);
	        }
	        	finally {
	                try {
	                    socket.close();
	                } catch (IOException e) {
	                    System.out.println("Couldn't close a socket, what's going on?");
	                }
	                System.out.println("Connection with client# " + clientNumber + " closed");
	            }
	}
	}
}
