package com.teamtracker;

import com.teamtracker.classes.*;
import com.teamtracker.scraper.*;
import java.util.ArrayList;

public class ScraperService {

	public static void main(String[] args) {
		SQLHelper sqlhelper = new SQLHelper();
		GuardianScraper gscraper = new GuardianScraper();
		ESPNScraper escraper = new ESPNScraper();
		BBCScraper bscraper = new BBCScraper();
		//ArrayList<Article> articles = gscraper.retrieveFromAPI();
		//ArrayList<Article> earticles = escraper.scrap();
		ArrayList<Article> barticles = bscraper.scrap();
	}
}
