package tud.ai1.shisen.view;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.action.basicactions.QuitAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import tud.ai1.shisen.util.Consts;
import tud.ai1.shisen.view.util.Button;

/**
 * @author Timo Bähr, Niklas Grimm, Niklas Vogel
 *
 *         Diese Klasse repraesentiert das Menuefenster, indem ein neues Spiel
 *         gestartet werden kann und das gesamte Spiel beendet werden kann,
 */
public class MainMenuState extends BasicGameState {

	private int stateID; // Identifier dieses BasicGameState
	private StateBasedEntityManager entityManager; // Zugehoeriger entityManager

	MainMenuState(int sid) {
		stateID = sid;
		entityManager = StateBasedEntityManager.getInstance();
	}

	/**
	 * Wird vor dem (erstmaligen) Starten dieses States ausgefuehrt.
	 * 
	 * @param container GameContainer.
	 * @param game      StateBasedGame.
	 * 
	 * @throws SlickException Wirft eine SlickException, wenn beim Initialisieren
	 *                        dieses States ein Fehler passiert.
	 */
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// Hintergrund laden
		Entity background = new Entity("menu"); // Entitaet fuer Hintergrund
		background.setPosition(new Vector2f(400, 300)); // Startposition des Hintergrunds
		background.addComponent(new ImageRenderComponent(new Image(Consts.BG_GENERAL))); // Bildkomponente
		entityManager.addEntity(stateID, background);

		new ChangeStateInitAction(Consts.GAMEPLAY_STATE);
		new QuitAction();

		// Buttons
		Action[] buttonActions = new Action[] { new ChangeStateInitAction(Consts.GAMEPLAY_STATE),
				new ChangeStateInitAction(Consts.HIGHSCORE_STATE), new QuitAction() };
		String[] buttonLabels = new String[] { "Start Game", "Highscore", "Quit Game" };

		for (int i = 0; i < buttonActions.length; i++) {
			Button button = new Button(buttonLabels[i], 400, 150 + (i * 150), buttonActions[i], 2);
			button.setScale(2);
			entityManager.addEntity(stateID, button);
		}
	}

	/**
	 * Wird vor dem Frame ausgefuehrt.
	 * 
	 * @param container GameContainer.
	 * @param game      StateBasedGame.
	 * @param delta     Delta.
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		entityManager.updateEntities(container, game, delta);
	}

	/**
	 * Wird mit dem Frame ausgefuehrt.
	 * 
	 * @param container GameContainer.
	 * @param game      StateBasedGame.
	 * @param g         Graphics.
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		entityManager.renderEntities(container, game, g);
	}

	/**
	 * Gibt ID des States zurueck.
	 * 
	 * @return ID
	 */
	@Override
	public int getID() {
		return stateID;
	}
}
