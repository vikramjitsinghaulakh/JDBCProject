package com.pizzashop.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.time.LocalDateTime;

import com.pizzashop.model.PizzaOrder;
import com.pizzshop.utility.ReadProperties;




public class PizzaOrderDAOImplementation implements PizzaOrderDAOInterface{


	private Connection LocalDatabaseConnection;
	private PreparedStatement QueryPreparedStatement;
	private Statement QueryStatement;

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
	int localOrderID;
	String localOrderDetails;
	String localOrderType;
	int localOrderQuantity;
	double localOrderTotal;
	String localOrderStatus;
	String localOrderDate;

	String localSearchField;
	String localDataField;
	String localtableName;
	String localSearchValue;
	int localSearchValueInt;

	// This variable will be used to get the returned value from & value the results
	String searchResult;

	//variable to get pizza price from the properties files
	double pizzaPrice;


	//Queries to be executed
	private String placeOrderQuery = "INSERT INTO orderpizza (CustomerId,itemid, OrderDetails, OrderType, OrderDate, OrderQuantity, ordertotal,OrderStatus ) VALUES (?, ?, ?, ?, ?, ? ,?,?)";
	private String placeOrderUpdateQuery  = "Update items set quantity = ?";
	

	private String deleteRecordQuery= "Delete from  "+QueryTableName+" where "+QueryConditionField+"= ?";
	private String viewSingleRecordQuery = "select "+ localDataField +" from "+localtableName +" where " +localSearchField+ " = ?";
	private String viewAllRecrodsQuery = "SELECT OrderID, OrderDetails, OrderType, OrderDate, CustomerID, OrderQuantity , orderstatus FROM orderpizza";
	private String updateQuery= "update "+QueryTableName+" set "+QueryUpdateField+"= ? where "+QueryConditionField+"= ?";

	//query to cancel the order

	private String cancelOrderQuery = "update orderpizza set orderstatus = ? where orderid = ?";


	//calling readprop method to read the data from the price properties file
	//static Properties readPriceProp =  ReadProperties.readConfig("./com.pizzashop/config/price.properties");


	public PizzaOrderDAOImplementation()
	{
		LocalDatabaseConnection =  DatabaseConection.getConnected();

		if(LocalDatabaseConnection.equals(null))
		{
			System.exit(0);
		}
	}




	
	@Override
	public String deleteRecord(String TableName , String ConditionField , String CondtionValue)
	{
		QueryTableName = TableName;
		QueryConditionField = ConditionField;
		QueryCondtionValue = CondtionValue;

		try 
		{
			QueryPreparedStatement = LocalDatabaseConnection.prepareStatement(deleteRecordQuery);
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


	// this method will be used to select a single record from the table//
	//wil be implementing list //
	
	
	@Override
	public String viewSingleRecord(String tableName , String dataField ,String searchField,  String searchValue)
	{


		localtableName = tableName;
		localDataField = dataField;
		localSearchValue = searchValue;
		localSearchField = searchField;

		String viewSingleRecordQuery = "select "+ localDataField +" from "+localtableName +" where " +localSearchField+ " = ?";

		try
		{
			QueryPreparedStatement = LocalDatabaseConnection.prepareStatement(viewSingleRecordQuery);
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

	//overloading view single record methd to pass integer value
	@Override
	public String viewSingleRecord(String tableName , String dataField ,String searchField,  int searchValue)
	{


		localtableName = tableName;
		localDataField = dataField;
		localSearchField = searchField;
		localSearchValueInt = searchValue;

		String viewSingleRecordQuery = "select "+ localDataField +" from "+localtableName +" where " +localSearchField+ " = ?";

		try
		{
			QueryPreparedStatement = LocalDatabaseConnection.prepareStatement(viewSingleRecordQuery);
			QueryPreparedStatement.setInt(1,localSearchValueInt );
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
	//need to make sure if items are less cnt place order & stay error msg
	@Override
	public String placeNewOrder( int CustomerId , String OrderDetails, String OrderType ,  int OrderQuantity , String OrderStatus)
	{

		localCustomerId = CustomerId;
		localOrderDetails = OrderDetails;
		localOrderType = OrderType;
		localOrderQuantity = OrderQuantity;
		localOrderStatus = OrderStatus;
		System.out.println("Place new order Order details: "+OrderDetails);
		String itemId = viewSingleRecord("items" ,"itemid" ,"itemdescription",  OrderDetails);


		System.out.println("Item ID in place new order : "+itemId);

		int itemIdVal = Integer.parseInt(itemId);

		//getting the current quantity from item table
		String currentQuantity = viewSingleRecord(  "items" ,"quantity" ,"itemdescription",  OrderDetails);
		int curerntQuantityVal = Integer.parseInt(currentQuantity);

		System.out.println("Item value from place new order: "+itemIdVal);

		double price = calculateTotalAmount(OrderQuantity ,OrderDetails );


		System.out.println("Item price : "+price);



		//System.out.println(QueryString);
		try
		{
			LocalDatabaseConnection.setAutoCommit(false);
			QueryPreparedStatement = LocalDatabaseConnection.prepareStatement(placeOrderQuery);
			QueryPreparedStatement.setInt(1,localCustomerId );
			QueryPreparedStatement.setInt(2,itemIdVal );
			QueryPreparedStatement.setString(3, localOrderDetails);
			QueryPreparedStatement.setString(4, localOrderType);
			QueryPreparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
			QueryPreparedStatement.setInt(6, localOrderQuantity);
			QueryPreparedStatement.setDouble(7, price);
			QueryPreparedStatement.setString(8, localOrderStatus);
			ResultCount = QueryPreparedStatement.executeUpdate();
			int newqty = curerntQuantityVal  - localOrderQuantity ; 
			System.out.println("curernt val: "+curerntQuantityVal  +" order val: "+ localOrderQuantity+" updaed value : " +newqty+" Item id: " +itemIdVal);

			if(ResultCount>0)
			{
				LocalDatabaseConnection.commit();
				if(updateRecord("items","quantity",newqty ,"itemid",itemIdVal)!=null)

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


		List<PizzaOrder> queryResult = new ArrayList<PizzaOrder>();
		try
		{
			QueryStatement = LocalDatabaseConnection.createStatement();
			QueryResultSet = QueryStatement.executeQuery(viewAllRecrodsQuery);


			System.out.println("\n\tOrderId" + "\tOrderDetails"+ "\tOrderType" + "\tOrderDate" + "\tCustomerID" +"\tOrderQuantity"+"\tOrderQuantity"
					+ "\n\t================================================================================");

			while(QueryResultSet.next())
			{
				localOrderID = QueryResultSet.getInt("OrderID");
				localOrderDetails = QueryResultSet.getString("OrderDetails");
				localOrderType = QueryResultSet.getString("OrderType");
				localOrderDate = QueryResultSet.getString("OrderDate");
				localCustomerId = QueryResultSet.getInt("CustomerID");
				localOrderQuantity = QueryResultSet.getInt("OrderQuantity");
				localOrderStatus = QueryResultSet.getString("orderStatus");
				queryResult.add( new PizzaOrder(localOrderID,localOrderDetails,localOrderType,localOrderDate,localCustomerId,localOrderQuantity , localOrderStatus));		

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



		try {

			QueryPreparedStatement = LocalDatabaseConnection.prepareStatement(updateQuery);
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

		//QueryString= "update "+QueryTableName+" set "+QueryUpdateField+"= ? where "+QueryConditionField+"= ?";

		try {

			QueryPreparedStatement = LocalDatabaseConnection.prepareStatement(updateQuery);
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
	public double calculateTotalAmount(int productQuantity ,  String itemName)
	{
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




	@Override
	public String updateRecord(String TableName ,String UpdateField , int UpdateValue, String ConditionField , int CondtionValue)
	{

		QueryTableName = TableName;
		String QueryUpdateField = UpdateField;
		int QueryUpdateValue = UpdateValue;
		QueryConditionField = ConditionField;
		int QueryCondtionValue = CondtionValue;

		String QueryString= "update "+QueryTableName+" set "+QueryUpdateField+" = ? where "+QueryConditionField+" = ?";

		System.out.println(QueryString +"QueryUpdateValue : "+QueryUpdateValue +"QueryCondtionValue:"+ QueryCondtionValue);

		try {
			LocalDatabaseConnection.setAutoCommit(false);
			QueryPreparedStatement = LocalDatabaseConnection.prepareStatement(QueryString);
			QueryPreparedStatement.setInt(1, QueryUpdateValue);
			QueryPreparedStatement.setInt(2, QueryCondtionValue);
			ResultCount= QueryPreparedStatement.executeUpdate();

			if(ResultCount>0)
			{
				LocalDatabaseConnection.commit();
				return "Record successfully updated";
			}
		} 
		catch (SQLException e) {

			e.printStackTrace();

		}
		return null;
	}



	/* Canceling the order
	 * this function takes the Order ID & update the order status to cancel
	 * Order can only be cancelled with in 10 min of placing order 
	 *  
	 *  */

	@Override
	public String cancelOrder(int OrderID )
	{
		localOrderID = OrderID;


		System.out.println(cancelOrderQuery + "  " + localOrderID);

		try {
			LocalDatabaseConnection.setAutoCommit(false);
			QueryPreparedStatement = LocalDatabaseConnection.prepareStatement(cancelOrderQuery);
			QueryPreparedStatement.setString(1, "Cancelled");
			QueryPreparedStatement.setInt(2, localOrderID);
			ResultCount = QueryPreparedStatement.executeUpdate();
			

			System.out.println(ResultCount);
			if(ResultCount >0)
			{
				LocalDatabaseConnection.commit();
				return "Your order has been cancelled";
			}

		} 
		catch (SQLException e)
		{
			try 
			{
				LocalDatabaseConnection.rollback();
			} 
			catch (SQLException e1) {

				e1.printStackTrace();
			}
			e.printStackTrace();
			e.getMessage();
		}


		/*
		 * Future logic count the time. After 5 min of placing order. order cannot be cancelled
		 */

		/*searchResult = viewSingleRecord("orderpizza" , "orderdate" ,"orderid",  localOrderID);

		System.out.println(searchResult);
		String splitString[] = searchResult.split(" ");
		System.out.println("after spliting" +splitString[1]);

		SimpleDateFormat dFormat = new SimpleDateFormat("HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.setTime(dFormat.parse(splitString[1]));
		//String totalMinute = c.get(Calendar.MINUTE);

		query = select orderid , extract(epoch from (now() - orderdate)) as date_diff from orderpizza where orderid = 42;
		System.out.println("Mintues from the order placed: "+c.get(Calendar.MINUTE));*/

		return "Failed to Cancel order";

	}




}
