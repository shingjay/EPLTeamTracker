package com.teamtracker.scraper;

import com.teamtracker.classes.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jsoup.Jsoup;
import org.w3c.dom.*;
import java.util.Date;

public class GuardianScraper implements Scraper{
	
	private String apiKey = "4ujzha4x7ref5phcaxms7y56";
	private Date latestDate;
	private int DEBUG = 1;
	
	public GuardianScraper(Date date) {
		this.latestDate = date;
		System.out.println(latestDate);
	}
	
	// time for illegal scraping!
	public ArrayList<Article> scrap()
	{
		ArrayList<Article> list = new ArrayList<Article>();		
		return list;
	}
	
	public ArrayList<Article> retrieveFromAPI()
	{
		ArrayList<Article> articles = new ArrayList<Article>();
		String apiURL = "http://content.guardianapis.com/search?section=football&format=xml&show-fields=body%2Clive-blogging-now%2Cbyline%2Ctrail-text&api-key=4ujzha4x7ref5phcaxms7y56";
		System.out.println(apiURL);
		try 
		{
			int count = 0;
			DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbuilder = dbfactory.newDocumentBuilder();
			Document doc = dbuilder.parse(apiURL);
			
			NodeList articleNodes = doc.getElementsByTagName("content");

			for (int i = 0 ; i < articleNodes.getLength() ; i++)
			{
				if (DEBUG == 1) 
					System.out.println("Iteration number : " + count++);
				
				Article article = new Article();
				article.setSource(1);
				Node node = articleNodes.item(i);
				
				if (node.getNodeType() != Node.ELEMENT_NODE) continue;
				
				Element e = (Element) node;
				boolean isMBM = false;
				boolean hasBody = true;
												
				NodeList articleElements = e.getElementsByTagName("field");
				
				String headerText = "";
				String body = "";
				String author = "";
				
				// go through different field tags	
				for (int j = 0 ; j < articleElements.getLength() ; j++)
				{
					Node child = articleElements.item(j);
					if(child.getNodeType() == Node.ELEMENT_NODE) {
						Element tempNode = (Element)child;
						
						if (tempNode.getAttribute("name").equals("trail-text")) {
							headerText = tempNode.getTextContent();
						}
						else if (tempNode.getAttribute("name").equals("body")) {
							body = tempNode.getTextContent();
							if (body.equals("<!-- Redistribution rights for this field are unavailable -->"))
								hasBody = false;
						}
						else if (tempNode.getAttribute("name").equals("byline")) {
							author = tempNode.getTextContent();
						}
						else if (tempNode.getAttribute("name").equals("live-blogging-now")) {
							if(tempNode.getTextContent().equals("true")) isMBM = true;							
						}
					}
				}
				
				// if it's a MBM, then don't scrap
				if (isMBM) continue;
				
				// if it's a video then don't scrap
				if (!hasBody) continue;
				
				//TODO: have to fix conversion to date
				String tempDate = e.getAttribute("web-publication-date");
				Date d = new SimpleDateFormat("yyyy-MM-dd").parse(tempDate);
				
				//if (d.before(latestDate)) continue;
				
				body = Jsoup.parse(body).text();
				body.replaceAll("\\<.*?>","");
				//System.out.println(body);
					
				String tempTitle = e.getAttribute("web-title");
				if (tempTitle.contains("The Gallery")) continue;
				
				article.setDateTime(d);
				article.setTitle(tempTitle);
				article.setArticleURL(e.getAttribute("web-url"));
				article.setHeaderText(headerText);
				article.setContent(body);
				
				if (DEBUG == 1) {
					System.out.println("Url : " + article.getArticleURL());
					System.out.println("header: " + article.getHeaderText());
					System.out.println("title: " + article.getTitle());
					System.out.println("date : " + article.getArticleCreation());
					//System.out.println("content: " + article.getContent());
					System.out.println();
				}
				
				articles.add(article);
			}
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
		return articles;
	}
	
}