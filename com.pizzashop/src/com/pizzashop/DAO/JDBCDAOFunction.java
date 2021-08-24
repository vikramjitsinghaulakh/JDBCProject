package com.pizzashop.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.time.LocalDateTime;

import com.pizzashop.model.PizzaOrder;
import com.pizzshop.utility.ReadProperties;




public class JDBCDAOFunction implements DAOInterface{


	private Connection LocalDatabaseConnection;
	private PreparedStatement QueryPreparedStatement;
	private Statement QueryStatement;
	private String QueryString = null;
	private ResultSet QueryResultSet = null ;
	private int ResultCount = 0 ;

	// Other variables for Methods//

	String QueryTableName;
	String QueryConditionField;
	String QueryCondtionValue;
	String QueryUpdateField;
	String QueryUpdateValue;
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());


	int localCustomerId;
	String localOrderDetails;
	String localOrderType;
	int localOrderQuantity;
	double localOrderTotal;

	String localSearchField;
	String localDataField;
	String localtableName;
	String localSearchValue;

	//variable to get pizza price from the properties files
	double pizzaPrice;

	//calling readprop method to read the data from the price properties file
	//static Properties readPriceProp =  ReadProperties.readConfig("./com.pizzashop/config/price.properties");


	public JDBCDAOFunction()
	{
		LocalDatabaseConnection =  DatabaseConection.getConnected();

		if(LocalDatabaseConnection.equals(null))
		{
			System.exit(0);
		}
	}




	@Override
	public String registerUser( String FirstName , String LastName, String Address,String EmailAddress, 
			String ContactNumber, int pincode , int Status)  {


		String queryFName = FirstName;
		String queryLName = LastName;
		String queryAddress = Address;
		String queryEmailAddress = EmailAddress;
		String queryContactNumber = ContactNumber;
		int queryPincode = pincode;
		int QueryStatus = Status;

		QueryString = "insert into customer(FirstName ,LastName , Address ,EmailAddress ,ContactNumber, Pincode ,Status)"
				+ "   values (?,?,?,?,?,?,?)";

		try {

			LocalDatabaseConnection.setAutoCommit(false);
			QueryPreparedStatement = LocalDatabaseConnection.prepareStatement(QueryString);


			QueryPreparedStatement.setString(1, queryFName);
			QueryPreparedStatement.setString(2, queryLName);
			QueryPreparedStatement.setString(3, queryAddress);
			QueryPreparedStatement.setString(4, queryEmailAddress);
			QueryPreparedStatement.setString(5, queryContactNumber);
			QueryPreparedStatement.setInt(6, queryPincode);
			QueryPreparedStatement.setInt(7, QueryStatus);

			ResultCount = QueryPreparedStatement.executeUpdate();


			if(ResultCount>0)
			{
				LocalDatabaseConnection.commit();
				return "Record successfully inserted!!";

			}



		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
			try {
				LocalDatabaseConnection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return "Record not inserted!!";
	}


	@Override
	public String deleteRecord(String TableName , String ConditionField , String CondtionValue)
	{
		QueryTableName = TableName;
		QueryConditionField = ConditionField;
		QueryCondtionValue = CondtionValue;

		QueryString= "Delete from  "+QueryTableName+" where "+QueryConditionField+"= ?";

		try 
		{
			QueryPreparedStatement = LocalDatabaseConnection.prepareStatement(QueryString);
			QueryPreparedStatement.setString(1, QueryCondtionValue);
			ResultCount = QueryPreparedStatement.executeUpdate();
			if(ResultCount >0)
			{
				return "Record Deleted Successfully!!!";
			}
		} 
		catch(SQLException e)
		{
			e.getStackTrace();
			System.out.println(e.getMessage());
		}
		return "Failed to Delete the record!!!";

	}

	@Override
	public String viewSingleRecord(String tableName , String dataField ,String searchField,  String searchValue)
	{
		// this method will be used to select a single record from the table//

		localtableName = tableName;
		localDataField = dataField;
		localSearchValue = searchValue;
		localSearchField = searchField;

		QueryString = "select "+ localDataField +" from "+localtableName +" where " +localSearchField+ " = ?";
		
		System.out.println("View Single record: "+QueryString);
		try
		{
			QueryPreparedStatement = LocalDatabaseConnection.prepareStatement(QueryString);
			QueryPreparedStatement.setString(1,searchValue );
			QueryResultSet = QueryPreparedStatement.executeQuery();
			while(QueryResultSet.next())
			{

				return(QueryResultSet.getString(localDataField));
			}

		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;

	}


	/* This method creates a pizza order*/
	@Override
	public String placeNewOrder( int CustomerId , String OrderDetails, String OrderType ,  int OrderQuantity )
	{

		localCustomerId = CustomerId;
		localOrderDetails = OrderDetails;
		localOrderType = OrderType;
		localOrderQuantity = OrderQuantity;
		System.out.println("Place new order Order details: "+OrderDetails);
		String itemId = viewSingleRecord(  "items" ,"itemid" ,"itemdescription",  OrderDetails);

		
		System.out.println("Item ID in place new order : "+itemId);
		
			int itemIdVal = Integer.parseInt(itemId);
			
			System.out.println("Item value from place new order: "+itemIdVal);
			
			double price = calculateTotalAmount(OrderQuantity ,OrderDetails );
			
			System.out.println("Item price : "+price);
					
		QueryString = "INSERT INTO orderpizza (CustomerId,itemid, OrderDetails, OrderType, OrderDate, OrderQuantity, ordertotal) VALUES (?, ?, ?, ?, ?, ? ,?)";
		
//System.out.println(QueryString);
		try
		{
			LocalDatabaseConnection.setAutoCommit(false);
			QueryPreparedStatement = LocalDatabaseConnection.prepareStatement(QueryString);
			QueryPreparedStatement.setInt(1,localCustomerId );
			QueryPreparedStatement.setInt(2,itemIdVal );
			QueryPreparedStatement.setString(3, localOrderDetails);
			QueryPreparedStatement.setString(4, localOrderType);
			QueryPreparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

			QueryPreparedStatement.setInt(6, localOrderQuantity);
			QueryPreparedStatement.setDouble(7, price);
			ResultCount = QueryPreparedStatement.executeUpdate();

			if(ResultCount>0)
			{
				LocalDatabaseConnection.commit();
				return "Your order has been Placed";

			}
		}
		catch(SQLException e)
		{
			e.getStackTrace();
			System.err.println(e.getMessage());
			try {
				LocalDatabaseConnection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.err.println(e1.getMessage());
			}
		}

		return "Failed to place order"; 

	}

	@Override
	public List<PizzaOrder> viewAllRecrods() {
		//this method is for viewing all the record from the table//
		QueryString = "SELECT OrderID, OrderDetails, OrderType, OrderDate, CustomerID, OrderQuantity FROM orderpizza";

		List<PizzaOrder> queryResult = new ArrayList<PizzaOrder>();
		try
		{
			QueryStatement = LocalDatabaseConnection.createStatement();
			QueryResultSet = QueryStatement.executeQuery(QueryString);


			System.out.println("\n\tOrderId" + "\tOrderDetails"+ "\tOrderType" + "\tOrderDate" + "\tCustomerID" +"\tOrderQuantity"
					+ "\n\t================================================================================");

			while(QueryResultSet.next())
			{
				int orderID = QueryResultSet.getInt("OrderID");
				String orderDetails = QueryResultSet.getString("OrderDetails");
				String orderType = QueryResultSet.getString("OrderType");
				String orderDate = QueryResultSet.getString("OrderDate");
				int customerID = QueryResultSet.getInt("CustomerID");
				int quantity = QueryResultSet.getInt("OrderQuantity");
				queryResult.add( new PizzaOrder(orderID,orderDetails,orderType,orderDate,customerID,quantity));		

			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();

		}
		return queryResult;

	}


	//This method delete the record set & clean the session//
	@Override
	public void KillSession() 
	{
		try 
		{
			QueryResultSet.close();
			LocalDatabaseConnection.close();
		} 
		catch (SQLException e)
		{

			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}


	@Override
	public String updateRecord(String TableName, String UpdateField, String UpdateValue, String ConditionField,
			String CondtionValue) {


		QueryTableName = TableName;
		String QueryUpdateField = UpdateField;
		String QueryUpdateValue = UpdateValue;
		QueryConditionField = ConditionField;
		QueryCondtionValue = CondtionValue;

		QueryString= "update "+QueryTableName+" set "+QueryUpdateField+"= ? where "+QueryConditionField+"= ?";

		try {

			QueryPreparedStatement = LocalDatabaseConnection.prepareStatement(QueryString);
			QueryPreparedStatement.setString(1, QueryUpdateValue);
			QueryPreparedStatement.setString(2, CondtionValue);
			ResultCount= QueryPreparedStatement.executeUpdate();

			if(ResultCount>0)
			{
				return "Record successfully updated";
			}
		} 
		catch (SQLException e) {

			e.printStackTrace();

		}



		return "Failed to update the record ";
	}

	@Override
	public String updateRecord(String TableName ,String UpdateField , int UpdateValue, String ConditionField , String CondtionValue)
	{

		QueryTableName = TableName;
		String QueryUpdateField = UpdateField;
		int QueryUpdateValue = UpdateValue;
		QueryConditionField = ConditionField;
		QueryCondtionValue = CondtionValue;

		QueryString= "update "+QueryTableName+" set "+QueryUpdateField+"= ? where "+QueryConditionField+"= ?";

		try {

			QueryPreparedStatement = LocalDatabaseConnection.prepareStatement(QueryString);
			QueryPreparedStatement.setInt(1, QueryUpdateValue);
			QueryPreparedStatement.setString(2, CondtionValue);
			ResultCount= QueryPreparedStatement.executeUpdate();

			if(ResultCount>0)
			{
				return "Record successfully updated";
			}
		} 
		catch (SQLException e) {

			e.printStackTrace();

		}



		return "Failed to update the record ";

	}




	@Override
	public double calculateTotalAmount(int productQuantity ,  String itemName) {
		// TODO Auto-generated method stub
		String localItemName = itemName;

		//String localPizzaPrice = readPriceProp.getProperty(itemName);
		//System.out.println("Item name:  "+itemID );
		String localPizzaPrice = viewSingleRecord( "items" ,"price" ,"itemDescription",localItemName);
		System.out.println("Here: "+localPizzaPrice );
		
		double pizzaPrice = Double.parseDouble(localPizzaPrice);

		
		System.out.println(" Price : "+ productQuantity *pizzaPrice);
		return (localOrderTotal = productQuantity *pizzaPrice );

	}




}
