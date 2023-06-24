package tud.ai1.shisen.view.util;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import tud.ai1.shisen.util.Consts;

/**
 * Steht fuer die Buttons im gesamten Spiel. Ermoeglicht diese zu skalieren, zu
 * (de)aktivieren und deren Aktionen sind direkt mit der Instanz verknuepft.
 * 
 * @author Andrej Felde, Stefan Toelle
 *
 */
public class Button extends Entity {

	private static final Map<Integer, TrueTypeFont> FONT_STYLES;
	private static final Map<Integer, String> ACTIVE_BUTTON_STYLES;
	private static final Map<Integer, String> INACTIVE_BUTTON_STYLES;

	static {
		FONT_STYLES = new HashMap<>();
		FONT_STYLES.put(0, Consts.FONT_30);
		FONT_STYLES.put(1, Consts.FONT_20);
		FONT_STYLES.put(2, Consts.FONT_15);

		ACTIVE_BUTTON_STYLES = new HashMap<>();
		ACTIVE_BUTTON_STYLES.put(0, Consts.BUTTON_ACTIVE_MENU);
		ACTIVE_BUTTON_STYLES.put(1, Consts.BUTTON_ACTIVE_SIZE);

		INACTIVE_BUTTON_STYLES = new HashMap<>();
		INACTIVE_BUTTON_STYLES.put(0, Consts.BUTTON_INACTIVE_MENU);
		INACTIVE_BUTTON_STYLES.put(1, Consts.BUTTON_INACTIVE_SIZE);
	}

	private final TrueTypeFont font;
	private final Vector2f labelPos;
	private final Image activeImg;
	private final Image inactiveImg;
	private boolean active;

	/**
	 * Erstellt einen Button mit entsprechendem Design an der uebergebenen Stelle.
	 * 
	 * @param label  Label fuer den Button, der Text, der angezeigt werden soll.
	 * @param x      x-Koordinate des Buttons.
	 * @param y      y-Koordinate des Buttons.
	 * @param action Eine Action, die der Button ausfuehren soll, wenn auf dieser
	 *               geklickt wird und dieser aktiv ist.
	 * @param style  Ermoeglicht verschiedene Stile fuer den Button. int[0]:
	 *               Schriftgroesse: 0->30, 1->20, 2->15 int[1]: 0->grauer button;
	 *               1->roter button
	 * 
	 * @throws SlickException Wirft eine SlickException, wenn eine Ausnahme
	 *                        passiert.
	 */
	public Button(String label, float x, float y, Action action, int... style) throws SlickException {
		super(label);
		this.active = true;

		// setze style
		this.font = FONT_STYLES.get(style.length > 0 ? style[0] : 0);
		this.labelPos = new Vector2f(x - this.font.getWidth(label) / 2f, y - this.font.getHeight(label) / 2f);
		int buttonStyle = style.length > 1 ? style[1] : 0;
		this.setPosition(new Vector2f(x, y));

		// setze Bilder
		// this.activeImg = new Image(ACTIVE_BUTTON_STYLES.get(buttonStyle));
		this.inactiveImg = new Image(INACTIVE_BUTTON_STYLES.get(buttonStyle));
		activeImg = new Image("/assets/buttons/buttonLong_brown.png");
		this.addComponent(new BIRC(activeImg));

		// erstelle click event
		Event activeEvent = new Event(this.getID() + "activeEvent") {
			@Override
			protected boolean performAction(GameContainer gc, StateBasedGame sb, int delta) {
				return ((Button) (this.getOwnerEntity())).isActive();
			}
		};
		ANDEvent clickEvent = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent(), activeEvent);
		clickEvent.addAction(action);
		this.addComponent(clickEvent);
	}

	/**
	 * Setzt den Aktivitaetsstatus des Buttons.
	 * 
	 * @param active True, damit der Button aktiv wird und false damit dieser
	 *               inkativ wird.
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Gibt zurueck ob der Button eine Aktion ausfuehrt, wenn dieser angeklickt
	 * wird.
	 * 
	 * @return true, wenn der Button aktiv ist; sonst false
	 */
	public boolean isActive() {
		return this.active;
	}

	/**
	 * Rendert einen Frame.
	 * 
	 * @param gameContainer  GameContainer.
	 * @param stateBasedGame StatebasedGame.
	 */
	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
		super.render(gameContainer, stateBasedGame, graphics);
		this.font.drawString(this.labelPos.x, this.labelPos.y, this.getID());
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
		((BIRC) (this.getEvent("BIRC"))).setImage(this.isActive() ? this.activeImg : this.inactiveImg);
		super.update(gc, sb, delta);
	}
}
