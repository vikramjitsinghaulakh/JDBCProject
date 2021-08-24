package com.pizzshop.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {

	private static FileInputStream inputFile  ; 
	private static Properties prop;
	private static String fileName; 

	
	public static Properties readConfig(String fileName) 
	{
		try 
		{
			inputFile = new FileInputStream(fileName);
			prop = new Properties();
			prop.load(inputFile);
		}
		
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				inputFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		return prop;
	}



}
