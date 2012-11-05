package com.teamtracker;

import com.teamtracker.classes.*;
import com.teamtracker.scraper.*;
import java.util.ArrayList;
import java.util.Date;

public class ScraperService {
	
	private SQLHelper helper = null;
	private Date guardianDate;
	private Date espnDate;
	private Date bbcDate;
	
	private GuardianScraper guardianScraper;
	private ESPNScraper espnScraper;
	private BBCScraper bbcScraper;
	
	public ScraperService() {
		if (helper == null)
			helper = new SQLHelper();
		getLatestDates();
		initializeScrapers();			
	}
	
	private void getLatestDates() {
		helper.establishConnection();
		guardianDate = helper.getLatestArticleDate(1);
		bbcDate = helper.getLatestArticleDate(2);
		espnDate = helper.getLatestArticleDate(3);
		helper.closeConnection();
	}
	
	private void initializeScrapers() 
	{
		this.guardianScraper = new GuardianScraper(guardianDate);
		this.espnScraper = new ESPNScraper(espnDate);
		this.bbcScraper = new BBCScraper(bbcDate);
	}
	
	public static void main(String[] args) {
		ScraperService scraper = new ScraperService();
		System.out.println(scraper.bbcDate.toString());
		//ArrayList<Article> articles = gscraper.retrieveFromAPI();
		//ArrayList<Article> earticles = escraper.scrap();
		ArrayList<Article> barticles = scraper.bbcScraper.scrap();
		
		//scraper.insertListToDatabase(barticles);
		
//		scraper.helper.establishConnection();
//		ArrayList<Article> selectquery = scraper.helper.selectUnsortedArticlesFromDB();
//		for (Article a : selectquery)
//		{
//			System.out.println(a.toString());
//		}
//		scraper.helper.closeConnection();
	}
	
	public void insertListToDatabase(ArrayList<Article> articles)
	{
		System.out.println("here");
		helper.establishConnection();
		String query = "INSERT INTO ARTICLE (Title,Header,Content,DateModified,ArticleURL,SourceID) VALUES (?,?,?,?,?,?)";
		for (Article article : articles)
		{		
			System.out.println(article.getArticleURL());
			helper.insertArticleToDB(query,article);
		}
		helper.closeConnection();
	}
}
