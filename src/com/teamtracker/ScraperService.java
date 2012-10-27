package com.teamtracker;

import com.teamtracker.classes.*;
import com.teamtracker.scraper.*;
import java.util.ArrayList;

public class ScraperService {
	private SQLHelper helper = null;
	
	public ScraperService() {
		if (helper == null)
			helper = new SQLHelper();
	}
	
	public static void main(String[] args) {
		ScraperService scraper = new ScraperService();
		GuardianScraper gscraper = new GuardianScraper();
		ESPNScraper escraper = new ESPNScraper();
		BBCScraper bscraper = new BBCScraper();
		//ArrayList<Article> articles = gscraper.retrieveFromAPI();
		//ArrayList<Article> earticles = escraper.scrap();
//		ArrayList<Article> barticles = bscraper.scrap();
		
	//	scraper.insertListToDatabase(barticles);
		
		scraper.helper.establishConnection();
		ArrayList<Article> selectquery = scraper.helper.selectUnsortedArticlesFromDB();
		for (Article a : selectquery)
		{
			System.out.println(a.toString());
		}
		scraper.helper.closeConnection();
	}
	
	
	public void insertListToDatabase(ArrayList<Article> articles)
	{
		helper.establishConnection();
		String query = "INSERT INTO ARTICLE (Title,Header,Content,DateModified,ArticleURL,SourceID) VALUES (?,?,?,?,?,?)";
		for (Article article : articles)
		{		
			helper.insertArticleToDB(query,article);
		}
		helper.closeConnection();
	}
}
