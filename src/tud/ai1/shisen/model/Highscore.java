package tud.ai1.shisen.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse, welche für die Verwaltung der Highscores verantworlicht ist. Laedt,
 * speichert und legt neue Highscore-Eintraege in einer ArrayList an und
 * speichert die ArrayListen zusammen mit dem Key der passenden Feldgröße in
 * eine Hashmap.
 *
 * @author Andrej Felde, Daniel Stein, Nicklas Behler
 */
public class Highscore {

	/**
	 * Maximale Anzahl an Highscore-Eintraegen in einer Highscore-Liste pro
	 * Feldgroeße.
	 */
	public static final int MAX_ENTRIES = 10;

	/**
	 * Alle Highscore-Listen mit zugehoeriger Levelgroeße
	 */
	private List<HighscoreEntry> highscores;

	/**
	 * Konstruktor der Highscore-Klasse.
	 */
	public Highscore() {
		highscores = new ArrayList<HighscoreEntry>();
	}

	/**
	 * Methode zum Erzeugen der Highscores aus dem gespeicherten Stringformat. Teilt
	 * den uebergebenen String in einzelne Eintraege und speichert diese in einem
	 * String-Array. Danach werden die einzelnen Eintraege in der Hashmap, in die
	 * passenden Arraylists ihrer Feldgroeße einsortiert.
	 *
	 * @param str String, der die vorherigen Highscores enthaelt.
	 */
	public void initHighscore(String str) {
		if (str.isEmpty())
			return;

		for (String line : str.split("\\r?\\n")) {
			addEntry(new HighscoreEntry(line));
		}
	}

	/**
	 * 
	 */
	public void saveToFile(String fileName) {
		// TODO Aufgabe 4.2b
	}

	/**
	 * Getter Methode für Highscores einzelner Level.
	 *
	 * @return Gibt die Highscore-Liste zurueck
	 */
	public List<HighscoreEntry> getHighscore() {
		return highscores;
	}

	/**
	 * 
	 */
	public void addEntry(HighscoreEntry entry) {
		// TODO Aufgabe 4.2a
	}
}
