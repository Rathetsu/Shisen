package tud.ai1.shisen.model;

/**
 * 
 * Interface zur Implementierung eines Spielfelds.
 * 
 * @author Robert Jakobi, Max Kratz, Niklas Vogel
 *
 */
public interface IGrid {
	/**
	 * Gibt den Token an Position (x,y) zurueck.
	 * 
	 * @param x X-Koordinate des Tokens.
	 * @param y Y-Koordinate des Tokens.
	 * @return Token an Position (x,<)
	 */
	IToken getTokenAt(int x, int y);

	/**
	 * Gibt das Grid zurueck.
	 * 
	 * @return Grid
	 */
	IToken[][] getGrid();

	/**
	 * Tested ob beide selektierten Tokens angeklickt wurden.
	 * 
	 * @return True wenn beide selektierten Token angeklickt wurde
	 */
	boolean bothClicked();

	/**
	 * Waehlt den uebergebenen Token aus.
	 * 
	 * @param token Token, der ausgewaehlt werden soll.
	 */
	void selectToken(IToken token);

	/**
	 * Deselektiert die beiden Token.
	 */
	void deselectTokens();

	/**
	 * Prueft ob Anzeigezeit bei falscher/richtiger Auswahl bereits ueberschritten
	 * ist. Falls ja soll entsprechender Code ausgefuehrt werden.
	 */
	void getTimeOver();

	/**
	 * Liefert die gerade aktiven Tokens zurueck.
	 * 
	 * @return Aktive Tokens
	 */
	IToken[] getActiveTokens();

	/**
	 * Falls der uebergebene Token derzeit angewaehlt ist, wird er abgewaehlt.
	 * 
	 * @param Token Der abzuwaehlende Token.
	 */
	void deselectToken(IToken token);
}
