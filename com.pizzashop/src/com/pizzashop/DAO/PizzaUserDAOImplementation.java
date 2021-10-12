package com.pizzashop.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.QueryResult;
import com.pizzashop.model.PizzaUser;

public class PizzaUserDAOImplementation implements PizzaUserDAOInterface {


	/*
	 * This class implements the all the functions for user
	 * Registering User
	 * Updating user record ( Only contact number , email address & address will be updated ) 
	 * Deactivating User (Updating the Status to 0)
	 * 
	 */

	// Variable declaration //


	private String queryFName , queryLName , queryAddress , queryEmailAddress , queryContactNumber ,queryUpdateField, queryUpdateFieldValue , QueryConditionValue;

	private int queryPincode, QueryStatus , ResultCount , queryUpdateFieldValueInt;

	private Connection LocalDatabaseConnection;
	private PreparedStatement QueryPreparedStatement;
	private Statement QueryStatement;
	private ResultSet QueryResult;

	//Query to register new user//

	private String registerUserQuery = "insert into customer(FirstName ,LastName , Address ,EmailAddress ,ContactNumber, Pincode ,Status)"
			+ "   values (?,?,?,?,?,?,?)";

	//Query to update user record  //

	private String updateStringFeilds = null;

	//Query to View all the users
	private String viewAllUser = "select * from customer order by customerid";


	public PizzaUserDAOImplementation()
	{
		LocalDatabaseConnection = DatabaseConection.getConnected();

		if(LocalDatabaseConnection.equals(null))
		{
			System.exit(0);
		}

	}


	@Override
	public String registerUser( String FirstName , String LastName, String Address,String EmailAddress, 
			String ContactNumber, int pincode , int Status)  {


		//Variable initialization //

		queryFName = FirstName;
		queryLName = LastName;
		queryAddress = Address;
		queryEmailAddress = EmailAddress;
		queryContactNumber = ContactNumber;
		queryPincode = pincode;
		QueryStatus = Status;

		try {

			LocalDatabaseConnection.setAutoCommit(false);
			QueryPreparedStatement = LocalDatabaseConnection.prepareStatement(registerUserQuery);


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

	
	//This function update the user phone number or email address//
	//future update should show old value & updated value//
	//we can provide any field name it to update but lets keep address , email & contact number//
	@Override
	public String updateUserRecord(String updateField,  String  updateValue, String UpdateFieldValue)
	{
		queryUpdateField = updateField;
		queryUpdateFieldValue = updateValue;
		QueryConditionValue = UpdateFieldValue;

		//updateStringFeilds ="Update customer set contactnumber = ? where emailaddress = ? ";
		updateStringFeilds = "Update customer set "+queryUpdateField+" = ? where emailaddress = ?";
		System.out.println(updateStringFeilds);
		try {

			LocalDatabaseConnection.setAutoCommit(false);
			QueryPreparedStatement =LocalDatabaseConnection.prepareStatement(updateStringFeilds);
			QueryPreparedStatement.setString(1, queryUpdateFieldValue);
			QueryPreparedStatement.setString(2, QueryConditionValue);
			ResultCount = QueryPreparedStatement.executeUpdate();
			if(ResultCount>0)
			{
				//only perform commit if there's no error in the data
				LocalDatabaseConnection.commit();
				return "Record updated successfully";

			}


		} catch (SQLException e) {

			try {
				LocalDatabaseConnection.rollback();
			}
			catch (SQLException e1) 
			{

				e1.printStackTrace();
			}
			e.printStackTrace();
		}


		return "Failed to update the record ";

	}

	@Override
	public String updateUserRecord(String updateField , String conditionValue ,int  updateValue )
	{
		queryUpdateField = updateField;
		queryUpdateFieldValueInt = updateValue;
		QueryConditionValue = conditionValue;


		updateStringFeilds = "Update customer set "+queryUpdateField+" = ? where emailaddress = ?";
		try {
			LocalDatabaseConnection.setAutoCommit(false);
			QueryPreparedStatement = LocalDatabaseConnection.prepareStatement(updateStringFeilds);
			QueryPreparedStatement.setInt(1,queryUpdateFieldValueInt ); //
			QueryPreparedStatement.setString(2,QueryConditionValue);
			ResultCount = QueryPreparedStatement.executeUpdate();


			if(ResultCount>0)
			{
				LocalDatabaseConnection.commit();
				return "Record has been updated!!";
			}

		} 
		catch (SQLException e)
		{

			try {
				LocalDatabaseConnection.rollback();
			} 
			catch (SQLException e1)
			{

				e1.printStackTrace();
			}
			e.printStackTrace();
			e.getMessage();
		}

		return "Failed to update the record";
	}


	@Override
	public List<PizzaUser> getAllUser() {

		
		List<PizzaUser> listUsers = new ArrayList<PizzaUser>();
		try 
		{
			QueryStatement = LocalDatabaseConnection.createStatement();
			QueryResult =  QueryStatement.executeQuery(viewAllUser);
			
			
			System.out.println("\n\t Customer ID  \t First Name \t Last Name \t Address \tEmailID \t Pincode\t Contact Number \t Status");
			System.out.println("\t====================================================================================================");
			while(QueryResult.next())
			{
				int customerID = QueryResult.getInt("customerid");
				String firstName = QueryResult.getString("firstname");
				String lastName = QueryResult.getString("lastname");
				String address = QueryResult.getString("address");
				String emailaddress = QueryResult.getString("emailaddress");
				String pincode = QueryResult.getString("pincode");
				String contactnumber = QueryResult.getString("contactnumber");
				int status = QueryResult.getInt("status");
				
				listUsers.add(new PizzaUser(customerID,firstName,lastName,address,emailaddress,pincode,contactnumber,status));
				
			}
			
		} 
		catch (SQLException e) 
		{
			
			e.printStackTrace();
			e.getMessage();
		}


		return listUsers;
	}


	
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

}
