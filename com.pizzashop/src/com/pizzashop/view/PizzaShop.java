package com.pizzashop.view;
import java.text.ParseException;

import com.pizzashop.DAO.DatabaseConection;
import com.pizzashop.DAO.PizzaOrderDAOImplementation;
import com.pizzashop.DAO.PizzaUserDAOImplementation;

public class PizzaShop extends PizzaOrderDAOImplementation{



	public static void main(String[] args) throws ParseException
	{

		
		
		 
		 

		if(DatabaseConection.getConnected()!= null)
		{


			System.out.println("\n\tKimmy's 7 Color Pizza \n\t==============================");

			System.out.println(" \t START YOUR ORDER \n\t==============================");
			System.out.println("\n \tPress 1 to Order Pizza "
					+ "\n \tPress 2 to View existing Order"
					+ "\n\tPress 3 to Update an existing Pizza Order"
					+ "\n\tPress 4 to View Daily Report"
					+ "\n \tPress X to Exit"
					+ "\n\t==================================================");
			PizzaOrderDAOImplementation OrderFunctionImplementation = new PizzaOrderDAOImplementation();
			PizzaUserDAOImplementation userFunctionImplemenation = new PizzaUserDAOImplementation();
			
			System.out.println("\n\t*******************************************");
			
			
			
			System.out.println("\n\t Registring new User");
			//System.out.println(userFunctionImplemenation.registerUser("Arneet Kaur ","Aulakh","3930 Carman Dr","arneet@gmail.com","9165983121", 97036 , 1 ));
			
			System.out.println(" Update User Phone Number or Email address");
			//System.out.println(userFunctionImplemenation.updateUserRecord("contactnumber", "2500377" , "sarwan@gmail.com"));
			
			
			System.out.println("\n\t Placing new order");
			//System.out.println(OrderFunctionImplementation.placeNewOrder(2, "paporoni pizza", "website", 2 , "Ordered"));
			
			
			
			//System.out.println(userFunctionImplemenation.updateUserRecord("pincode", "vikram@gmail.com" , 12345));	
			
			System.out.println("User's Details");
			System.out.println(userFunctionImplemenation.getAllUser());
			
			System.out.println(userFunctionImplemenation.viewSingleRecord("Email","tk@gsmail.com"));
			//System.out.println("\n\t\n==========================================================================================================");
			//System.out.println(" \n\t\t"+ DatabaseFunctionObject.cancelOrder(42));
			
			
			
			//System.out.println("\n\t\t\tAll the Recrods\n=======================================================================================");
			//System.out.println(DatabaseFunctionObject.viewAllRecrods());
			
			//System.out.println("\n\tDelete Records\n\t===================================================\n"
				//	+ "===============================================================");
			//System.out.println(DatabaseFunctionObject.deleteRecord("Customer", "Phone", "9198957485"));
			
			//System.out.println("\n\tUpdate Records\n\t===================================================\n"
				//	+ "===============================================================");
			//System.out.println("Single data from table: "+DatabaseFunctionObject.viewSingleRecord("items", "itemid", "itemdescription","5 Chesse pizza" ));
			//System.out.println("Single data from table: "+DatabaseFunctionObject.viewSingleRecord("items", "price", "itemdescription","5 Chesse pizza" ));
			//System.out.println(DatabaseFunctionObject.updateRecord("Customer", "status", 1,"emailaddress","vikram@gmail.com"));
			//System.out.println(DatabaseFunctionObject.updateRecord("Customer", "address", "3850 New Janta nagar, Gill road , Ludhiana","emailaddress","vikram@gmail.com"));
			
			//System.out.println(DatabaseFunctionObject.calculateTotalAmount( 5 , "5 Chesse pizza"));

			

		}
		
		
}							

	
	
}
