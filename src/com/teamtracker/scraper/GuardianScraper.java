package com.teamtracker.scraper;

import com.teamtracker.classes.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;
import java.util.Date;

public class GuardianScraper {
	
	private String apiKey = "4ujzha4x7ref5phcaxms7y56";
	
	
	// time for illegal scraping!
	public void scrap()
	{
		ArrayList<Article> list = new ArrayList<Article>();
	//	String root 
		
	}
	
	public ArrayList<Article> retrieveFromAPI()
	{
		ArrayList<Article> articles = new ArrayList<Article>();
		String apiURL = "http://content.guardianapis.com/search?section=football&format=xml&show-fields=body%2Ctrail-text%2Clive-blogging-now%2Cbyline&api-key=" + apiKey;
		try 
		{
			DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbuilder = dbfactory.newDocumentBuilder();
			Document doc = dbuilder.parse(apiURL);
			
			NodeList articleNodes = doc.getElementsByTagName("content");
			for (int i = 0 ; i < articleNodes.getLength() ; i++)
			{
				Article article = new Article();
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
				// being cheap - setting date to current time scraped instead of date published
				//String tempDate = e.getAttribute("web-publication-date");
				//Date d = new SimpleDateFormat("yy-MM-dd'T'HH:mm:ssZ",Locale.UK).parse(tempDate);
				article.setDateTime(new Date());
				
				article.setTitle(e.getAttribute("web-title"));
				article.setArticleURL(e.getAttribute("web-url"));
				article.setHeaderText(headerText);
				article.setContent(body);
				article.setAuthor(author);
				articles.add(article);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
		return articles;
	}
	
}