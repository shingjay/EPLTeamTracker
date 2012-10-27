package com.teamtracker.scraper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.teamtracker.classes.Article;

public class BBCScraper implements Scraper {
	
	private int DEBUG = 0;
	
	public ArrayList<Article> scrap()  
	{
		ArrayList<Article> articles = new ArrayList<Article>();
		String url = "http://www.bbc.co.uk/sport/0/football/";
		String bbcURL = "http://www.bbc.co.uk";
		
		try {
			Document doc = Jsoup.connect(url).get();
			
			// get 'headlines'
			Element headlineElement = doc.getElementById("more-news");
			Elements headlineElements = headlineElement.getElementsByTag("a");
			
			for (Element temp : headlineElements)
			{
				boolean isPreMatchReview = false;
				boolean isLiveCommentary = false;
						
				String generalURL = temp.attr("href");
				
				// ignore extra urls, eg: /sport/teams/manchester-united
				if (!generalURL.substring(7,8).equals("0")) continue;
				// ignore urls to go into /sport/0/football/womens/
				if (!isInteger(generalURL.substring(18,generalURL.length()))) continue;
				
				String currURL = bbcURL + temp.attr("href");				
				
				Document _doc = Jsoup.connect(currURL).get();
				
				String header = _doc.getElementsByClass("introduction").get(0).text();
							
				String articleTitle = temp.text();				
				if (articleTitle.indexOf(" v ") != -1) isPreMatchReview = true;
					
				String date = _doc.getElementsByClass("date").get(0).text();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
				Date newDate = dateFormat.parse(date);
				
				
				Elements paragraphElements = _doc.getElementsByTag("p");
				int paragraphCount = paragraphElements.size();
				if (isPreMatchReview) 
					paragraphCount -= 3;
				String content = "";
				
				for (int i = 3 ; i < paragraphCount - 3; i++)
				{
					String line = paragraphElements.get(i).text();
					if (line.isEmpty()) continue;
					if (line.indexOf("Please turn on JavaScript. Media requires JavaScript to play.") != -1) continue;
					if (line.indexOf("BST") != -1) continue;
					if (line.indexOf("GMT") != -1) continue;
					if (line.indexOf("Last updated") != -1) {
						isLiveCommentary = true;
						break;
					}
					content += line;
				}
				
				if (isLiveCommentary) continue;
				
				// finally set article properties here
				Article article = new Article();
				article.setSource(2);
				article.setArticleURL(currURL);
				article.setTitle(articleTitle);
				article.setHeaderText(header);
				article.setContent(content);
				article.setDateTime(newDate);
				
				if (DEBUG == 1) {
					System.out.println("Url : " + article.getArticleURL());
					System.out.println("header: " + article.getHeaderText());
					System.out.println("title: " + article.getTitle());
					System.out.println("date : " + date);
					System.out.println("isPrematchreview " + isPreMatchReview);
					System.out.println("isLiveCommentary " + isLiveCommentary);
					System.out.println();
				}
				System.out.println();
				
				articles.add(article);
			}
			
			//TODO: work on getting comments and analysis
			// get 'comment and analysis'
			Element commentElement = doc.getElementById("comment-and-analysis");
			Elements commentElements = commentElement.getElementsByTag("a");
			
			for (Element temp : commentElements) 
			{
				//System.out.println(temp.toString());
			}
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		return articles;
	}
	
	// check if the string is an integer
	public boolean isInteger(String s)
	{
		try 
		{
			Integer.parseInt(s);
		} catch (Exception e)
		{
			return false;
		}
		return true;
	}
	}
