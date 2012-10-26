package com.teamtracker.scraper;

import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.teamtracker.classes.Article;

public class ESPNScraper implements Scraper {

	private int DEBUG = 1;
	
	public ArrayList<Article> scrap() 
	{
		String url = "http://soccernet.espn.go.com/news/archive?cc=5901";
		ArrayList<Article> articles = new ArrayList<Article>();
		
		String espnURL = "http://soccernet.espn.go.com";
		
		try {
			int ij = 0;
			Document doc = Jsoup.connect(url).get();
			Elements elements = doc.getElementsByClass("result");
			for (Element e : elements)
			{
				if (DEBUG == 1) 
					System.out.println("Iteration : " + ij++);
				
				Article article = new Article();
				article.setSource(3);
				// get and set article url 
				Elements temp = e.getElementsByTag("a");
				String currentURL = (espnURL + temp.attr("href"));
				if (DEBUG == 1) 
					System.out.println("url : " + currentURL);
				
				article.setArticleURL(currentURL);
				
				// get and set article title
				String title = e.getElementsByTag("a").get(0).text();
				if (DEBUG == 1) 
					System.out.println("title: " + title);
				article.setTitle(title);
				
				// TODO somehow something is wrong here!
	
				// get and set headertext
				String header = e.getElementsByTag("p").get(0).text();
				article.setHeaderText(header);
				if (DEBUG == 1)
					System.out.println("header : " + header);
				
				
				// getting results and date of article
				String strResults = e.getElementsByTag("span").get(0).text();
				char [] results = strResults.toCharArray();

				boolean isAuthor = true;
				StringBuilder author = new StringBuilder();
			//	StringBuilder date = new StringBuilder();
				
				// check if there's a | or not. if not, then either date or author is missing
				if (strResults.indexOf('|') == -1)
				{
					// then process differently
					author.append("ESPN Staff");
				//	date.append(new Date().toString());
				}
				else
				{
					for (int i = 0 ; i < results.length ; i++) {
						if (results[i] == '|') {
							isAuthor = false;
							continue;
						}
						if (isAuthor) author.append(results[i]);
					//	else date.append(results[i]);
					}
					// removing extra spaces
					author.delete(author.length() - 1, author.length());
					//date.delete(0, 1);
				}
				article.setAuthor(author.toString());
				article.setDateTime(new Date());
				if (DEBUG == 1) System.out.println("Author : " + article.getAuthor());
				
				
				String content = "";
				
				// go into the link
				Document _doc = Jsoup.connect(currentURL).get();
				// get all <p> elements instead of just 1, so now have to clear
				Elements _elements = _doc.getElementsByTag("p");
				//System.out.println(article.getArticleURL());
				//System.out.println(_element.text());
				for (Element _element : _elements)
				{

					content += _element.text();
				}
				
				if (DEBUG == 1)
				{
					System.out.println(content);
					System.out.println();
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}		
		return articles;
	}
}
