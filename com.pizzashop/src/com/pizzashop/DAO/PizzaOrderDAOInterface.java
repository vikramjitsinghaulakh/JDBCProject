package com.pizzashop.DAO;


import java.util.List;

import com.pizzashop.model.PizzaOrder;

public interface PizzaOrderDAOInterface {
	
	
	//update with String value 
	public String updateRecord(String TableName ,String UpdateField , String UpdateValue, String ConditionField , String CondtionValue);
	
	//update with integer value
	public String updateRecord(String TableName ,String UpdateField , int UpdateValue, String ConditionField , String CondtionValue);
	
	//update function with integer condition value
	public String updateRecord(String TableName ,String UpdateField , int UpdateValue, String ConditionField , int CondtionValue);

	public String deleteRecord(String TableName ,  String ConditionField , String CondtionValue);
	
	public String placeNewOrder( int CustomerId , String OrderDetails, String OrderType ,  int OrderQuantity , String OrderStatus );
	
	//method to find record & by passing string value
	public String viewSingleRecord(String tableName , String dataField ,String searchField,  String searchValue);
	
	//method to find record & by passing int value
	public String viewSingleRecord(String tableName , String dataField ,String searchField,  int searchValue);
	
	
	
	//Method to list all the records from table
	public List<PizzaOrder> viewAllRecrods();
	
	public double calculateTotalAmount( int productQuantity , String itemName);
	public void KillSession();
	
	public String cancelOrder(int OrderID );
	

}
	