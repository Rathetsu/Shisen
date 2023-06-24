package tud.ai1.shisen.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 
 * Klasse zum Ubersetzen von Token-Werten zu Symbolen zum Anzeigen auf dem
 * Spielfeld.
 * 
 * @author Robert Jakobi
 *
 */
public class TokenDisplayValueProvider {
	private static TokenDisplayValueProvider inst;
	private final String[] map;

	/**
	 * Konstruktor zum Initialisieren eines Objekts mit einem uebergebenen Pfad zur
	 * Symbol-Datei als String.
	 * 
	 * @param path Pfad zur Symbol-Datei.
	 */
	private TokenDisplayValueProvider(final String path) {
		this.map = this.loadMap(path);
	}

	/**
	 * Laedt eine Symbol-Datei von einem als String uebergebenen Pfad.
	 * 
	 * @param path Pfad zur Symbol-Datei.
	 * @return Symbole als String-Array
	 */
	private String[] loadMap(final String path) {
		final List<String> symbols = Arrays.asList(IOOperations.readFile(path).split(Consts.SYMBOL_SEPARATOR));
		// Self-explaining
		Collections.shuffle(symbols);
		return symbols.toArray(String[]::new);
	}

	/**
	 * Gibt den zu einer ID passenden String-Wert zum Anzeigen zurueck.
	 * 
	 * @param id ID, fuer die der Wert zurueckgegeben wird.
	 * @return Wert
	 */
	public String getDisplayValue(final int id) {
		if (id < 0)
			return "";
		return this.map[id];
	}

	/**
	 * Statische Methode zum Zurueckgeben der Instanz der Klasse.
	 * 
	 * @return Instanz
	 */
	public static TokenDisplayValueProvider getInstance() {
		if (inst == null) {
			initilize(Consts.SYMBOLS_PATH);
		}
		return inst;
	}

	/**
	 * Initialisiert die Instanz der Klasse mit einem gegebenen Pfad als String.
	 * 
	 * @param path Pfad zur Symbol-Datei.
	 * @return True, wenn Instanz initialisiert wurde, ansonsten false
	 */
	public static boolean initilize(final String path) {
		if (inst == null) {
			inst = new TokenDisplayValueProvider(path);
		} else
			return false;
		return true;
	}
}
