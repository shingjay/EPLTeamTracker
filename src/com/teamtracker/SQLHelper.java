package com.teamtracker;

import java.sql.*;
import java.util.*;
import com.mysql.jdbc.Driver;
import com.teamtracker.classes.Article;

public class SQLHelper {
	
	private Connection connection = null;
	
	public SQLHelper() 
	{
		//if (connection == null) 
			//establishConnection();
		
	}
	
	/***
	 * method to establish a connection to the database via jdbc
	 * @return true if connection is established
	 */
	public boolean establishConnection()
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Properties prop = new Properties();
			prop.put("user","dev");
			prop.put("password", "testpassword");
			String url = "jdbc:mysql://localhost/teamtracker";
			connection = DriverManager.getConnection(url,prop);
		} catch (Exception e) 
		{
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean closeConnection()
	{
		try
		{
			if (connection == null) return true;
			connection.close();
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	/***
	 * select every unsorted articles in the article table
	 * query -> check if articles exist in teamnews table. if it does, it means that articles there have been sorted
	 * @return
	 */
	public ArrayList<Article> selectUnsortedArticlesFromDB()
	{
		String query = "SELECT * FROM ARTICLE, TEAMNEWS WHERE ARTICLE.ArticleID != TEAMNEWS.ArticleID";
		ArrayList<Article> articles = new ArrayList<Article>();
		PreparedStatement preparedStatement = null; 
		try 
		{	
			preparedStatement = connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery(query);
			while(rs.next())
			{
				Article article = new Article();
				article.setArticleID(rs.getInt("ArticleID"));
				article.setHeaderText(rs.getString("header"));
				article.setContent(rs.getString("content"));
				article.setDateTime(rs.getTimestamp("dateModified"));
				article.setArticleURL(rs.getString("articleURL"));
				article.setSource(rs.getInt("sourceID"));
				article.setTitle(rs.getString("title"));
				
				articles.add(article);
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		finally {
			try {
				preparedStatement.close();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				return null;
			}
			
			
		}
		return articles;
	}
	
	/***
	 * inserts data into the database based on the query passed in
	 * @param a INSERT SQL query to run
	 * @param article object with values to be inserted
	 * @return true if insertion is successful
	 */
	public boolean insertArticleToDB(String query, Article article) 
	{
		PreparedStatement preparedstatement = null;
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(article.getArticleCreation().getTime());
		try 
		{
			preparedstatement = connection.prepareStatement(query);
			preparedstatement.setString(1, article.getTitle());
			preparedstatement.setString(2, article.getHeaderText());
			preparedstatement.setString(3, article.getContent());
			preparedstatement.setTimestamp(4, sqlDate);
			preparedstatement.setString(5, article.getArticleURL());
			preparedstatement.setInt(6, article.getSource());
			
			preparedstatement.executeUpdate();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		finally 
		{
			try {
				if (preparedstatement != null) preparedstatement.close();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
				return false;
			}
		}
		return true;
	}
}
