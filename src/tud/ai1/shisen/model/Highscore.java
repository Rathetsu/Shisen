package tud.ai1.shisen.model;

import tud.ai1.shisen.util.IOOperations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
  * Class responsible for managing high scores. loads,
  * saves and creates new highscore entries in an ArrayList and
  * stores the ArrayLists together with the key of the appropriate array size in a hash map.
  *
  * @author Ahmed Ezzat
  * 
  */
public class Highscore {

	public static final int MAX_ENTRIES = 10;

	private List<HighscoreEntry> highscores;

	public Highscore() {
		highscores = new ArrayList<HighscoreEntry>();
	}

	public void initHighscore(String str) {
		if (str.isEmpty())
			return;

		for (String line : str.split("\\r?\\n")) {
			addEntry(new HighscoreEntry(line));
		}
	}

	public void saveToFile(String fileName) {
		StringBuilder sb = new StringBuilder();
		for(HighscoreEntry entry : highscores) {
			sb.append(entry.toString());
			sb.append(System.lineSeparator());
		}
		IOOperations.writeFile(fileName, sb.toString());
	}

	public List<HighscoreEntry> getHighscore() {
		return highscores;
	}

	public void addEntry(HighscoreEntry entry) {
		highscores.add(entry);
		Collections.sort(highscores);
		if(highscores.size() > MAX_ENTRIES) {
			highscores.remove(MAX_ENTRIES);
		}
	}
}
