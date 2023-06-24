package tud.ai1.shisen.view.util;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import tud.ai1.shisen.model.IToken;
import tud.ai1.shisen.model.TokenState;
import tud.ai1.shisen.util.Consts;

/**
 * Stellt die Entitaet Token fuer den View dar. Spiellogikdaten ueber den Token
 * sind im model in Klasse Token gespeichert.
 * 
 * @author Stefan Toelle
 */
public class EntityToken extends Entity {

	// Der token
	private final IToken token;

	// Bilder fuer verschiedene token states
	private final Image imageNormal;
	private final Image imageBlocked;
	private final Image imageClicked;
	private final Image imageWrong;

	private boolean displayWrongSelection;

	/**
	 * Erstellt den Token mit entschrechenden Parametern.
	 * 
	 * @param entityID Die ID fuer den Roken.
	 * @param token    Der Roken zu dieser entity.
	 * @param pos      Position des Token auf dem Bildschirm.
	 * @param scale    Skalierung auf dem Bildschirm. 1 entspricht 30x30 Pixel.
	 * @throws SlickException Wirft eine SlickException wenn eine Ausnahme passiert.
	 */
	public EntityToken(String entityID, IToken token, Vector2f pos, float scale) throws SlickException {
		super(entityID);
		this.token = token;
		displayWrongSelection = false;

		// Setze Bilder
		imageNormal = new Image(Consts.TOKEN_NORMAL);
		imageBlocked = new Image(Consts.TOKEN_BLOCKED);
		imageClicked = new Image(Consts.TOKEN_CLICKED);
		imageWrong = new Image(Consts.TOKEN_WRONG);
		this.addComponent(new BIRC(imageNormal));

		this.setPosition(pos);
		this.setScale(scale);

		// Erstelle click event
		Event clickEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		clickEvent.addAction(new Action() {
			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta, Component event) {
				if (token.getTokenState() == TokenState.DEFAULT) {
					token.setTokenState(TokenState.CLICKED); // click token
				} else if (token.getTokenState() == TokenState.CLICKED) {
					token.setTokenState(TokenState.DEFAULT); // un-click token
				}
			}
		});
		this.addComponent(clickEvent);
	}

	/**
	 * Wird vor jedem Frame ausgefuehrt.
	 * 
	 * @param gc    GameContainer.
	 * @param sb    StateBasedGame.
	 * @param delta Delta.
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		super.update(gc, sb, delta);
		// Update Bild
		switch (token.getTokenState()) {
		case DEFAULT:
			((BIRC) this.getEvent("BIRC")).setImage(imageNormal);
			break;
		case CLICKED:
			((BIRC) this.getEvent("BIRC")).setImage(imageClicked);
			break;
		case SOLVED:
			((BIRC) this.getEvent("BIRC")).setImage(imageNormal);
			break;
		// TODO:
		// case BLOCKED:
		// ((BIRC) this.getEvent("BIRC")).setImage(imageBlocked);
		// break;
		/*
		 * case WRONG: ((BIRC) this.getEvent("BIRC")).setImage(imageWrong); if
		 * (displayWrongSelection) break; // setze token state nach bestimmter Zeit
		 * zuruek Event wrongEvent = new TimedEvent("wrongEvent",
		 * Consts.DISPLAY_WRONG_TIME); wrongEvent.addAction(new Action() {
		 * 
		 * @Override public void update(GameContainer gc, StateBasedGame sb, int delta,
		 * Component event) { token.setTokenState(TokenState.DEFAULT);
		 * displayWrongSelection = false; } }); this.addComponent(wrongEvent);
		 * displayWrongSelection = true; break;
		 */
		}
	}

	/**
	 * Rendert einen Frame.
	 * 
	 * @param gc       GameContainer.
	 * @param sb       StateBasedGame.
	 * @param graphics Graphics.
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics graphics) {
		super.render(gc, sb, graphics);
		// Schreibe Zeichen auf Token, falls dieser noch nicht geloest wurde
		if (token.getTokenState() != TokenState.SOLVED) {
			String symbol = token.getDisplayValue();
			float posx = getPosition().getX() - graphics.getFont().getWidth(symbol) / 2f;
			float posy = getPosition().getY() - graphics.getFont().getHeight(symbol) / 2f;
			graphics.drawString(symbol, posx, posy);
		}
	}
}
