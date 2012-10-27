package com.teamtracker.classes;

import java.util.Date;

public class Article {
	private int articleID;
	private String title;
	private String content;
	private Date articleCreation;
	private String articleURL;
	private String imageURLS;
	private String headertext;
	private int source;
	
	public Article() {}
	
	public int getSource() { return source; }
	public String getTitle() { return title; }
	public String getContent() { return content; }
	public Date getArticleCreation() { return articleCreation; }
	public String getArticleURL() { return articleURL; }
	public String getImageURLS() { return imageURLS; }
	public String getHeaderText() { return headertext; }
	
	public void setSource(int source) { this.source = source; }
	public void setArticleID(int id) { this.articleID = id; }
	public void setTitle(String title) { this.title = title; }
	public void setContent(String content) { this.content = content; }
	public void setDateTime(Date datetime) { this.articleCreation = datetime; }
	public void setImageURLS(String urls) { this.imageURLS = urls; }	
	public void setArticleURL(String url) { this.articleURL = url; }
	public void setHeaderText(String headertext) { this.headertext = headertext; }
	
	public String toString() 
	{
		return "title :\t" + title + "\nheader :" +
				"\t" + headertext + "\nurl :\t" + articleURL + "\nContent :\t" + content
				+ "\nDate :\t" + articleCreation.toString(); 
	}
}
