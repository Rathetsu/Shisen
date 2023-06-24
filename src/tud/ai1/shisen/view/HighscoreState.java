package tud.ai1.shisen.view;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import tud.ai1.shisen.model.Highscore;
import tud.ai1.shisen.model.HighscoreEntry;
import tud.ai1.shisen.util.Consts;
import tud.ai1.shisen.util.IOOperations;
import tud.ai1.shisen.view.util.Button;

/**
 * Die Klasse regelt die graphische Darstellung der Highscore-Seiten.
 *
 * @author Lennart Fedler, Andrej Felde, Daniel Stein
 */
public class HighscoreState extends BasicGameState {
	private int stateID;
	private StateBasedEntityManager entityManager;

	public static Highscore highscore;

	/**
	 * Konstruktor des HighscoreState.
	 *
	 * @param stateID Uebergebene ID des zu aufrufenden States.
	 *
	 */
	public HighscoreState(int stateID) {
		this.stateID = stateID;
		entityManager = StateBasedEntityManager.getInstance();
		highscore = new Highscore();
	}

	/**
	 * Initialisiert den HighscoreState.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer,
	 *      org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		highscore.initHighscore(IOOperations.readFile(Consts.HIGHSCORE_PATH));

		// Set background
		Entity background = new Entity("background");
		background.setPosition(new Vector2f(gc.getWidth() / 2, gc.getHeight() / 2));
		background.addComponent(new ImageRenderComponent(new Image(Consts.BG_HIGHSCORE)));
		this.entityManager.addEntity(this.stateID, background);

		// set buttons and actions
		float xPos = gc.getWidth() / 2;
		float yPos = gc.getHeight() - 100;
		entityManager.addEntity(this.getID(),
				new Button("Home", xPos, yPos, new ChangeStateAction(Consts.MAINMENU_STATE), 2));
	}

	/**
	 * Rendert einen Frame des HighscoreStates.
	 * 
	 * @param gc       GameContainer
	 * @param sb       StateBasedGame
	 * @param graphics Graphics
	 * 
	 * @throws SlickException Wirft eine SlickException, wenn beim Rendern ein
	 *                        Fehler auftritt.
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics graphics) throws SlickException {
		this.entityManager.renderEntities(gc, sb, graphics);
		TrueTypeFont font = Consts.FONT_30;
		font.drawString(gc.getWidth() / 2 - font.getWidth("Highscore") / 2, 10, "Highscore");
		font.drawString(50, 70, String.format(" #  %-12s  - %-8s - %-6s", "Date", "Score", "Seconds"));
		List<HighscoreEntry> entries = highscore.getHighscore();
		for (int i = 0; i < Highscore.MAX_ENTRIES; i++) {
			float x = 50;
			float y = 100 + (35 * i);
			if (i < entries.size()) {
				HighscoreEntry entry = entries.get(i);
				String line = String.format("%02d. %-10s  - %04d    - %06.0f", i + 1, entry.getDate(), entry.getScore(),
						entry.getDuration() / 1000);
				font.drawString(x, y, line);
			} else {
				font.drawString(x, y, String.format("%02d.%s", (i + 1), "/"));
			}
		}
	}

	/**
	 * Updated den EntityManager und alle seine Entities.
	 * 
	 * @param gameContainer  GameContainer
	 * @param stateBasedGame StateBasedGame
	 * @param delta          Delta
	 */
	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
		this.entityManager.updateEntities(gameContainer, stateBasedGame, delta);
	}

	/**
	 * Gibt die ID zurueck.
	 * 
	 * @return ID
	 */
	@Override
	public int getID() {
		return stateID;
	}
}
