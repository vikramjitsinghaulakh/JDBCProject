package com.pizzashop.DAO;

import java.util.List;

import com.pizzashop.model.PizzaUser;

public interface PizzaUserDAOInterface {
	
	public String registerUser(  String FirstName , String LastName, String Address,String EmailAddress, 
			String ContactNumber, int pincode , int Status);
	
	//Function will update String type fields 
	public String updateUserRecord(String updateField,  String  updateValue, String conditionValue);
	
	//Function will update the Integer type fields
	public String updateUserRecord(String updateField , String conditionValue,int  updateValue);
	
	//function to show all the users 
	public List<PizzaUser> getAllUser();
	
	//function to find user with email id 
	public String viewSingleUser(String emailID);
	
}
