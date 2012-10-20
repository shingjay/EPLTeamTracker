package com.teamtracker.scraper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.teamtracker.classes.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.*;

public class ESPNScraper {

	public ArrayList<Article> scrap() 
	{
		String url = "http://soccernet.espn.go.com/news/archive?cc=5901";
		ArrayList<Article> articles = new ArrayList<Article>();
		
		String espnURL = "http://soccernet.espn.go.com";
		
		try {
			Document doc = Jsoup.connect(url).get();
			Elements elements = doc.getElementsByClass("result");
			for (Element e : elements)
			{
				Article article = new Article();
				
				// get and set article url 
				Elements temp = e.getElementsByTag("a");
				String currentURL = (espnURL + temp.attr("href"));
				article.setArticleURL(currentURL);
				
				// get and set article title
				String title = e.getElementsByTag("a").get(0).text();
				article.setTitle(title);
				
				// TODO somehow something is wrong here!
	
				// get and set headertext
				//String header = e.getElementsByTag("p").get(0).text();
				//article.setHeaderText(header);
				
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

				// go into the link
				Document _doc = Jsoup.connect(currentURL).get();
				Element _element = _doc.getElementsByTag("p").get(0);
				//System.out.println(article.getArticleURL());
				System.out.println(_element.text());
				
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}		
		return articles;
	}
}
