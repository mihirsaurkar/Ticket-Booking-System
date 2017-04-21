

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
//import javax.faces.context.FacesContext;
//import javax.servlet.jsp.jstl.sql.Result;
//import javax.servlet.jsp.jstl.sql.ResultSupport;


public class DbmsUserBean {
	private String userName;
	private String password;
	private String dbms;
	private String dbmsHost;
	private String dbSchema;
	private String jdbcDriver;
	private String conURL;
	private String status;
	private int noOfColumns;
	private String query;
	public Connection con;
	private ResultSet rs;
	private Statement stmt;
	private DatabaseMetaData dbMetaData;
	private ResultSetMetaData rsMetaData;
	private Result result;
	private int user=0;
	private Object[][] sData;
	

	public DbmsUserBean(String userName, String password, String dbms, String dbmsHost, String dbSchema) {
		
		this.userName = userName;
		this.password = password;
		this.dbms= dbms;
		this.dbmsHost= dbmsHost;
		this.dbSchema = dbSchema;
				
	}
	
	public Object[][] getsData() {
		return sData;
	}

	public void setsData(Object[][] sData) {
		this.sData = sData;
	}

	private ArrayList<String> dataList;
		
	public ArrayList getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList dataList) {
		this.dataList = dataList;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getJdbcDriver() {
		return jdbcDriver;
	}

	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

	public int getUser() {
		return user;
	}
	public void setUser(int user) {
		this.user = user;
	}
	
	

	// setter Methods to set the variable values for connection to data base
	public void setDbms(String dbms) {
		this.dbms = dbms;
	}
	
	public void setConURL(String conURL) {
		this.conURL = conURL;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setPassword(String password) {
		this.password=password;
	}
			
	
	public void setDbmsHost(String dbmsHost) {
		this.dbmsHost = dbmsHost;
	}

	public void setDbSchema(String dbSchema) {
		this.dbSchema = dbSchema;
	}

	
//end of setter methods
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getNoOfColumns() {
		return noOfColumns;
	}

	public void setNoOfColumns(int noOfColumns) {
		this.noOfColumns = noOfColumns;
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	public DatabaseMetaData getDbMetaData() {
		return dbMetaData;
	}

	public void setDbMetaData(DatabaseMetaData dbMetaData) {
		this.dbMetaData = dbMetaData;
	}

	public ResultSetMetaData getRsMetaData() {
		return rsMetaData;
	}

	public void setRsMetaData(ResultSetMetaData rsMetaData) {
		this.rsMetaData = rsMetaData;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getDbms() {
		return dbms;
	}

	public String getDbmsHost() {
		return dbmsHost;
	}

	public String getDbSchema() {
		return dbSchema;
	}

	public String getConURL() {
		return conURL;
	}

	public boolean getDataBaseConnection()
	{
	
		// select driver and connection URL based on input received.
		
	if(dbms.equalsIgnoreCase("MYSQL"))
	{
		jdbcDriver = "com.mysql.jdbc.Driver";
		conURL = "jdbc:mysql://" +dbmsHost +":3306/"+dbSchema;
		//code for debugging
			//	System.out.println("The connection details generated:");
			//	System.out.println("DRIVER SELECTED: " + this.jdbcDriver);
			//	System.out.println ("CONNECTION URL "+ this.conURL);
	}
	if(dbms.equalsIgnoreCase("DB2"))
	{
		jdbcDriver = "com.ibm.db2.jcc.DB2Driver";
		conURL = "jdbc:db2://" +dbmsHost + ":50000/" +dbSchema;
		//code for debugging
			//	System.out.println("The connection details generated:");
			//	System.out.println("DRIVER SELECTED: " + this.jdbcDriver);
			//	System.out.println ("CONNECTION URL "+ this.conURL);
	}
	if(dbms.equalsIgnoreCase("Oracle"))
	{
		jdbcDriver = "oracle.jdbc.driver.OracleDriver";
		conURL = "jdbc:oracle:thin:@"+dbmsHost +":1521/"+dbSchema;
		//code for debugging
			//	System.out.println("The connection details generated:");
			//	System.out.println("DRIVER SELECTED: " + this.jdbcDriver);
			//	System.out.println ("CONNECTION URL "+ this.conURL);
	}
	
	try
	{
		Class.forName(jdbcDriver);
		//code for debugging
		//System.out.println("Driver Registered...");
		System.out.println("conURL "+ conURL );
		System.out.println("userName "+ userName );
		System.out.println("password "+ password );
		con= DriverManager.getConnection(conURL,userName, password);
		//code for debugging
		//System.out.println("Connected to Database Schema...");
		stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		//code for debugging
		//System.out.println("Statement Ready...");
		dbMetaData = con.getMetaData();
		//code for debugging
		//System.out.println("Meta Data obtained...");
		status = "SUCCESS";
		return true;
		
	}
	catch(ClassNotFoundException ex){
		ex.printStackTrace();
		System.out.println("Driver Not Found for "+dbms+" driver name "+jdbcDriver);
		status = "FAIL";
		return false;
		
	} //end of ClassNotFoundException
	catch (SQLException e) {
		// TODO Auto-generated catch block
		
		System.out.println(e.getSQLState());
		System.out.println(e.getErrorCode());
		e.printStackTrace();
		status = "FAIL";
		return false;
	}//end SQLException
	catch(Exception e)
	{
		e.printStackTrace();
		status = "FAIL";
		return false;
	}//End of Exception
	
	}//end of Method getDataBaseConnection()
	
	public void closeDataBaseConnection()
	{
		try {
			//if(dbMetaData!=null)
			//((Connection) dbMetaData).close();
			//if(rs!=null)		
			//((Connection) rsMetaData).close();
			if (rs!=null)
			rs.close();
			if (stmt!=null)
			stmt.close();
			if(con!=null)
		    con.close();
			status = "FAIL";
			//debugging code
			//System.out.println("connection closed ..Have a good day");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = "SUCCESS";
		}
		
	}//end of Method closeDataBaseConnection
	
	public  int executeQuery(String sqlQuery){
		query=sqlQuery;
		try{
		if(query.toLowerCase().contains("select") )
			{
			if (query.toLowerCase().contains("insert") )
			{
				stmt.executeUpdate(query);
				System.out.println("Query :" + query);
				//Debug code
				System.out.println("SELECT 2 executed");
				System.out.println("Procedure created.");
				return 0;
			}
			else if(query.toLowerCase().contains("exists")  )
				{
				stmt.executeUpdate(query);
				System.out.println("Query :" + query);
				//Debug code
				System.out.println("DDL Query executed...");
				return 0;
			}
			else{ System.out.println("Query :" + query);
			rs=stmt.executeQuery(query);
			//Debug code
			System.out.println("Select Query executed...");
			this.convertToResult();	
			rs.afterLast();
			
			System.out.println("Rows selected " + rs.getRow());
			rsMetaData=rs.getMetaData();
			//Debug code
			//System.out.println("Result Set obtained");
			noOfColumns=rsMetaData.getColumnCount();
			//Debug Code	//Debug code
			System.out.println(noOfColumns+" columns fetched");
			//this.getColumns(tableName);
			
			return noOfColumns;
			}
				
				
		}
		else{
			stmt.executeUpdate(query);
			System.out.println("Query :" + query);
			//Debug code
			System.out.println("DDL Query executed...");
			return 0;
		}
		}catch( SQLException e){
			e.printStackTrace();
		}
		return 0;
		
	}//end of Method executeQuery 
	
	public void convertToResult()
	{
		result=ResultSupport.toResult(rs);
	}//end of method convertToResult
	
	public List<String> getTables(){
		List <String> tableList = null;
		
		try{
			rs=dbMetaData.getTables(null,userName,null,new String[]{"TABLE","VIEW"});
			int noOfRows = rs.getRow();
			tableList= new ArrayList<String>(noOfRows);
			rs.beforeFirst();
			String tableName="";
			if (rs!=null)
			{
				while(rs.next()){
					tableName=rs.getString("TABLE_NAME");
					if(!dbms.equalsIgnoreCase("oracle") || tableName.length()< 4)
						tableList.add(tableName);
					else if (!tableName.substring(0,4).equalsIgnoreCase("BIN$"))
						tableList.add(tableName);
				}//end of while
			}//end of if
			}catch(SQLException e){
			e.printStackTrace();
		}
		return tableList;
	}//end of method getTables
	
	@SuppressWarnings("null")
	public List<String> getColumns(String tableName){
		
		List<String> columnList = new ArrayList<String>(20);
		try{
			rs=dbMetaData.getColumns(null, userName, tableName, null);
			String columnName="";
			if (rs!=null)
			{
				while (rs.next()){
					columnName=rs.getString("COLUMN_NAME");
					//debugging code
					//System.out.println("column name" + columnName);
					columnList.add(columnName);							
				}//end of while
			}//end of if
		}catch(SQLException e){
			e.printStackTrace();
		}
		return columnList;
	}//end of Method getColumns
	
	
	// method to read the result set and convert to arrylist for displaying:
	@SuppressWarnings("rawtypes")
	public void getResultList() 
	{
		//System.out.println("executeResultSet::Query type : "+sqlQuery);
		dataList = new ArrayList(); // decalare as private class variable
		//MessageBean message = new MessageBean();
		
		try
		{
			//System.out.println("inside select : ");
			//rs=statement.executeQuery(sqlQuery);
			//resultSetMetaData = rs.getMetaData();
			//int numofcoloumns=resultSetMetaData.getColumnCount();
			
			System.out.println("Coloumn count : "+ noOfColumns);
			int rows = result.getRowCount();
			System.out.println("No of rows" + rows);
			rs.beforeFirst();
			while(rs.next()){
				for (int i=1;i<=noOfColumns;i++){
					dataList.add(rs.getString(i));
				}
			}
			System.out.println("datalist::"+dataList.size());
		}
		catch(SQLException sqlExp){
			System.out.println("SQL Exception Caught:"+sqlExp.getMessage()+"|"+sqlExp.getErrorCode());
			//message.setErrorMessage(sqlExp.getMessage()); 
			//return dataList;
		}
		catch(Exception exp){
			//message.setErrorMessage(exp.getMessage());
		//	return dataList;
		}
		//return dataList;
	}
	
	public Object[][] getObjectexecuteQuery(String sqlQuery)
	{	query=sqlQuery;
		try {
			rs=stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//Debug code
	System.out.println("Select Query executed...");
	this.result=ResultSupport.toResult(rs);
	this.sData = result.getRowsByIndex();
	
	System.out.println("Length in dbmsuserbean " +  sData.length);	
	
	return sData;
}
	
}
