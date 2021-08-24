package com.pizzashop.DAO;

import java.util.List;

import com.pizzashop.model.PizzaOrder;

public interface DAOInterface {
	
	public String registerUser(  String FirstName , String LastName, String Address,String EmailAddress, 
			String ContactNumber, int pincode , int Status);
	
	//update with String value 
	public String updateRecord(String TableName ,String UpdateField , String UpdateValue, String ConditionField , String CondtionValue);
	
	//update with integer value
	public String updateRecord(String TableName ,String UpdateField , int UpdateValue, String ConditionField , String CondtionValue);

	public String deleteRecord(String TableName ,  String ConditionField , String CondtionValue);
	
	public String placeNewOrder( int CustomerId , String OrderDetails, String OrderType ,  int OrderQuantity );
	
	//Search  string field
	public String viewSingleRecord(String tableName , String dataField ,String searchField,  String searchValue);
	
	
	public List<PizzaOrder> viewAllRecrods();
	
	public double calculateTotalAmount( int productQuantity , String itemName);
	public void KillSession();
	
	

}
	