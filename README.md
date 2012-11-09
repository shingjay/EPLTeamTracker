EPLTeamTracker
==============

Shing Jay Ong

###project for CS490: Web Info Retrieval class. 

1. Scrap football articles from guardian.co.uk/football, bbc.co.uk/football and espn soccernet.
2. Store the scrapped data into the database.
3. Then feed the NLTK the information regarding the teams.
4. Classify articles in the database to teams through NLTK.
5. Display output!

###Future objectives:
- use Sax XML parsing instead of DOM
- write a scheduler to scrap at every day/hour
	- scrap based on the times of the games. eg: scrap game results & reviews 1 hr+ after UCL/EPL games
- batch insertion to db?
- other sites to consider:
	* Telegraph.co.uk
	* Reuters/Football
	* Goal.com?
- now trending feature
- handle pictures for each article
- refactor code to be more elegant