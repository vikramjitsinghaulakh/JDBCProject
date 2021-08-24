package com.pizzashop.view;
import com.pizzashop.DAO.DatabaseConection;
import com.pizzashop.DAO.JDBCDAOFunction;

public class PizzaShop extends JDBCDAOFunction{



	public static void main(String[] args)
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
			JDBCDAOFunction DatabaseFunctionObject = new JDBCDAOFunction();
			System.out.println("\n\t*******************************************");
			
			//System.out.println(DatabaseFunctionObject.registerUser("Sarwan singh ","Aulakh","3930 Carman Dr","sarwan@gmail.com","9165981234", 97035 , 1 ));
			System.out.println(DatabaseFunctionObject.placeNewOrder(1, "paporoni pizza", "Website", 3));
			
			//DatabaseFunctionObject.viewSingleRecord("Email","tk@gsmail.com");
			System.out.println("\n\t\t\tAll the Recrods\n=======================================================================================");
			System.out.println(DatabaseFunctionObject.viewAllRecrods());
			System.out.println("\n\t\n==========================================================================================================");
			System.out.println(" \n\tInserting record in the Customer table \n");
			
			System.out.println("\n\tDelete Records\n\t===================================================\n"
					+ "===============================================================");
			//System.out.println(DatabaseFunctionObject.deleteRecord("Customer", "Phone", "9198957485"));
			
			System.out.println("\n\tUpdate Records\n\t===================================================\n"
					+ "===============================================================");
			//System.out.println("Single data from table: "+DatabaseFunctionObject.viewSingleRecord("items", "itemid", "itemdescription","5 Chesse pizza" ));
			//System.out.println("Single data from table: "+DatabaseFunctionObject.viewSingleRecord("items", "price", "itemdescription","5 Chesse pizza" ));
			//System.out.println(DatabaseFunctionObject.updateRecord("Customer", "status", 1,"emailaddress","vikram@gmail.com"));
			//System.out.println(DatabaseFunctionObject.updateRecord("Customer", "address", "3850 New Janta nagar, Gill road , Ludhiana","emailaddress","vikram@gmail.com"));
			
			//System.out.println(DatabaseFunctionObject.calculateTotalAmount( 5 , "5 Chesse pizza"));

			

		}
		
		
}							

	
	
}
