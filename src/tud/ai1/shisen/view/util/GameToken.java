package tud.ai1.shisen.view.util;

import org.newdawn.slick.Color;
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
import tud.ai1.shisen.model.IGrid;
import tud.ai1.shisen.model.IToken;
import tud.ai1.shisen.model.TokenState;
import tud.ai1.shisen.util.Consts;

/**
 * Stellt die Entitaet Token fuer den View dar. Spiellogikdaten ueber den Token
 * sind im model in Klasse Token gespeichert.
 * 
 * @author Niklas Grimm, Niklas Vogel, Stefan Toelle
 */
public class GameToken extends Entity {
	private final Vector2f pos;
	private final IGrid grid;
	private final IToken token;

	// Bilder fuer verschiedene TokenStates
	private final Image imageClicked;
	private final Image imageNormal;
	private final Image imageWrong;
	private final Image imageSolved;

	private String toDraw;

	int size;

	/**
	 * Erstellt den Token mit entschrechenden Parametern.
	 * 
	 * @param entityID Die ID fuer den Token.
	 * @param pos      Position des Tokens auf dem Bildschirm.
	 * @param token    Der Token zu dieser entity.
	 * @param grid     Das Spielfeld.
	 * @param size     Die Anzeigegroesse des Tokens.
	 * 
	 * @throws SlickException Wirft eine SlickException, wenn eine Ausnahem
	 *                        passiert.
	 */
	public GameToken(String entityID, Vector2f pos, IToken token, IGrid grid, int size) throws SlickException {
		super(entityID);

		this.size = size;
		this.token = token;
		this.pos = pos;
		this.grid = grid;
		this.toDraw = token.getDisplayValue();

		imageNormal = new Image("/assets/buttons/panel_beige.png");
		imageClicked = new Image("assets/buttons/panel_brown.png");
		imageWrong = new Image("assets/buttons/token_red.png");
		imageSolved = new Image("assets/buttons/test.png");
		this.setPosition(pos);
		this.setSize(new Vector2f(size, size));
		this.addComponent(new BIRC(imageNormal));
		// Event wenn Token angeklickt wird
		Event markEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		markEvent.addAction(new Action() {
			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta, Component event) {
				if (!grid.bothClicked())
					toggleActive();
				if (token.getTokenState() == TokenState.CLICKED)
					grid.selectToken(token);
				else if (token.getTokenState() == TokenState.DEFAULT)
					grid.deselectToken(token);

			}
		});
		this.addComponent(markEvent);
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
		graphics.setColor(Color.white);
		// Schreibe Zeichen auf Token, falls dieser noch nicht geloest wurde
		graphics.drawString(toDraw, (pos.getX() - size / 4) + 4, pos.getY() - size / 4);
		graphics.setFont(Consts.FONT_15_GAMETOKEN);
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
		grid.getTimeOver();
		// Veraendere Token-Bild, abhaenging vom TokenState
		switch (token.getTokenState()) {
		case DEFAULT:
			((BIRC) this.getEvent("BIRC")).setImage(imageNormal);
			break;
		case CLICKED:
			((BIRC) this.getEvent("BIRC")).setImage(imageClicked);
			break;
		case WRONG:
			((BIRC) this.getEvent("BIRC")).setImage(imageWrong);
			break;
		case SOLVED:
			((BIRC) this.getEvent("BIRC")).setImage(imageSolved);
			toDraw = "";
			break;
		default:
			break;
		}
	}

	/**
	 * Aktiviert / Deaktiviert den GameToken beim Anklicken.
	 */
	public void toggleActive() {
		if (token.getTokenState() == TokenState.DEFAULT)
			token.setTokenState(TokenState.CLICKED);
		else if (token.getTokenState() == TokenState.CLICKED)
			token.setTokenState(TokenState.DEFAULT);
	}

	/**
	 * Gibt die X-Position auf dem Bildschirm zurueck.
	 * 
	 * @return X-Position (Pixel vom oberen linken Rand)
	 */
	public float getX() {
		return pos.getX();
	}

	/**
	 * Gibt die Y-Position auf dem Bildschirm zurueck.
	 * 
	 * @return Y-Position (Pixel vom oberen linken Rand)
	 */
	public float getY() {
		return pos.getY();
	}
}
