package com.teamtracker.scraper;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
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
				Article article = new Article();
				article.setSource(2);
				
				String generalURL = temp.attr("href");
				// ignore extra urls, eg: /sport/teams/manchester-united
				if (!generalURL.substring(7,8).equals("0")) continue;
				// ignore urls to go into /sport/0/football/womens/
				if (!isInteger(generalURL.substring(18,generalURL.length()))) continue;
				
				String currURL = bbcURL + temp.attr("href");
				article.setArticleURL(currURL);
				Document _doc = Jsoup.connect(currURL).get();
				
				//Elements articleElements = articleElement.getAllElements();
				String header = _doc.getElementsByClass("introduction").get(0).text();
				article.setHeaderText(header);
				
				// if title exists in the document then set it
				if (_doc.getElementById("headline").hasText()) 
					article.setTitle(_doc.getElementById("headline").text());
				// if it doesn't exist have to find for it
				// else				
				
				// gets the date of the article
				String date = _doc.getElementsByClass("date").get(0).text();
				
				String text = "";



//				for (Element _e : _el)
//				{
//				 	System.out.println(_e.className());
//					//if (_e.hasClass("introduction")) continue;
//					text += _e.text();
//					//System.out.println(_e.toString());
//					
//				}
				if (DEBUG == 1) {
					System.out.println("Url : " + article.getArticleURL());
					System.out.println("header: " + article.getHeaderText());
					System.out.println("title: " + article.getTitle());
					System.out.println("date : " + date);
					System.out.println("text : " + text);
					System.out.println();
				}
				System.out.println();
				
			}
			
//			// get 'comment and analysis'
//			Element commentElement = doc.getElementById("comment-and-analysis");
//			Elements commentElements = commentElement.getElementsByTag("a");
//			
//			for (Element temp : commentElements) 
//			{
//				//System.out.println(temp.toString());
//			}
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
