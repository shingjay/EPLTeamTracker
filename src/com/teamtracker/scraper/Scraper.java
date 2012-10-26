package com.teamtracker.scraper;

import java.util.ArrayList;

import com.teamtracker.classes.Article;

public interface Scraper {
	
	public ArrayList<Article> scrap();
}
