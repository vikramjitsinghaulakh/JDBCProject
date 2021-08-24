package com.pizzashop.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.pizzshop.utility.ReadProperties;

public class DatabaseConection {

	/*private static String databaseURL = "jdbc:mysql://sql3.freesqldatabase.com:3306/sql3426774";
	private static String userName = "sql3426774";
	private static String password = "x1ywkUweF4";
	private static Connection connection = null;*/

	static Properties readprop =  ReadProperties.readConfig("./config/config.properties");
	private static String databaseURL = readprop.getProperty("postgresURL");
	private static String userName = readprop.getProperty("postgresUsername");
	private static String password = readprop.getProperty("postgrespassword");
	private static Connection connection = null;



	public static Connection getConnected()
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			return DriverManager.getConnection(databaseURL, userName, password);
		}
		catch(SQLException |ClassNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
		return connection;
	}





}
