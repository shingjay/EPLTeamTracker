package com.teamtracker;

import java.sql.*;
import java.util.*;

public class SQLHelper {
	
	private Connection connection = null;
	
	public SQLHelper() 
	{
		if (connection == null) 
			establishConnection();
	}
	
	/***
	 * method to establish a connection to the database via jdbc
	 * @return true if connection is established
	 */
	private boolean establishConnection()
	{
		try 
		{
			Properties prop = new Properties();
			prop.put("user","dev");
			prop.put("password", "testpassword");
			String url = "jdbc:mysql://localhost/";
			connection = DriverManager.getConnection(url,prop);
		} catch (Exception e) 
		{
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	/***
	 * returns a resultset of results to the caller based on the database
	 * @param a SELECT SQL query to run
	 * @return a resultset of values based on the param
	 */
	public ResultSet selectQuery(String query)
	{
		ResultSet rs;
		try 
		{	
			Statement statement = connection.createStatement();
			rs = statement.executeQuery(query);
			connection.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		return rs;
	}
	
	/***
	 * inserts data into the database based on the query passed in
	 * @param a INSERT SQL query to run
	 * @return true if insertion is successful
	 */
	public boolean insertQuery(String query) 
	{
		try 
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
}
