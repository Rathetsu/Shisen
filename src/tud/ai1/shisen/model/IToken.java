package tud.ai1.shisen.model;

import org.newdawn.slick.geom.Vector2f;

/**
 * Interface, das die Vorgabe fuer einen Token darstellt.
 * 
 * @author Robert Jakobi, Max Kratz
 *
 */
public interface IToken {

	/**
	 * Gebe TokenState zurueck.
	 * 
	 * @return TokenState
	 */
	TokenState getTokenState();

	/**
	 * Setze TokenState auf uebergebenen Wert.
	 * 
	 * @param abc TokenState, welcher gesetzt wird.
	 */
	void setTokenState(TokenState abc);

	/**
	 * Gebe Anzeige-Wert als String zurueck.
	 * 
	 * @return Anzeige-Wert als String
	 */
	String getDisplayValue();

	/**
	 * Gebe Wert des Felds zurueck.
	 * 
	 * @return Wert des Felds
	 */
	int getValue();

	/**
	 * Gebe ID des Felds zurueck.
	 * 
	 * @return ID des Felds
	 */
	int getID();

	/**
	 * Gebe Position des Felds als Vector2f zurueck.
	 * 
	 * @return Position des Felds als Vector2f
	 */
	Vector2f getPos();

	/**
	 * Setze Position des Felds auf uebergebenen Vector2f Wert.
	 * 
	 * @param pos Vector2f Wert, der gesetzt wird.
	 */
	void setPos(Vector2f pos);

}
