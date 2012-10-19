package com.teamtracker;

import com.teamtracker.classes.*;
import com.teamtracker.scraper.*;
import java.util.ArrayList;

public class ScraperService {

	public static void main(String[] args) {
		SQLHelper sqlhelper = new SQLHelper();
		GuardianScraper scraper = new GuardianScraper();
		
		ArrayList<Article> articles = scraper.retrieveFromAPI();
		
		
	}
}
