package tud.ai1.shisen.model;

import org.newdawn.slick.geom.Vector2f;

/**
  * Interface that represents the default for a token.
  *
  * @author Ahmed Ezzat
  *
  */
public interface IToken {

	TokenState getTokenState();

	void setTokenState(TokenState abc);

	String getDisplayValue();

	int getValue();

	int getID();

	Vector2f getPos();

	void setPos(Vector2f pos);

}
