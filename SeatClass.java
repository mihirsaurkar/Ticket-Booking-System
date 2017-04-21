import java.io.Serializable;
import java.rmi.RemoteException;


public class SeatClass  {
	

	public SeatClass(){
		//null constructor
		this.seatNo= 0;
		this.rowNo="";
		this.userName="";
	}

	private int seatNo;
	private String rowNo;
	private int booked ;
	private String userName;
	
	private SeatClass bookedSeats [];
	
	public int getBooked() {
		return booked;
	}
	public void setBooked(int booked) {
		this.booked = booked;
	}
	public int getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(int seatNo) {
		this.seatNo = seatNo;
	}
	public String getRowNo() {
		return rowNo;
	}
	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public SeatClass[] getBookedSeats()
	{
		// function to fetch booked seats.
		
		// read from file populate each member for seat class
		// row, seat, user
		// add to the array booked seats
		
		return bookedSeats;
	}
	
	 @Override
	    public boolean equals(Object anObject) {
	        if (!(anObject instanceof SeatClass)) {
	            return false;
	        }
	        SeatClass otherSeat = (SeatClass)anObject;
	        return otherSeat.getRowNo().equals(this.getRowNo()) && otherSeat.getSeatNo() == this.seatNo;
	    }

}