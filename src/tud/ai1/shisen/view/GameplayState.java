package tud.ai1.shisen.view;

import java.awt.Desktop;
import java.net.URL;
import java.time.LocalDateTime;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import tud.ai1.shisen.model.Grid;
import tud.ai1.shisen.model.HighscoreEntry;
import tud.ai1.shisen.util.Cheats;
import tud.ai1.shisen.util.Consts;
import tud.ai1.shisen.view.util.BIRC;
import tud.ai1.shisen.view.util.Button;
import tud.ai1.shisen.view.util.GameToken;

/**
 * @author Niklas Grimm, Niklas Vogel
 *
 *         Diese Klasse repraesentiert das Spielfenster.
 */
public class GameplayState extends BasicGameState {

	private int stateID; // Identifier dieses BasicGameState
	private StateBasedEntityManager entityManager; // zugehoeriger entityManager
	private Vector2f startPos = Consts.startPosition; // Startposition des Fensters auf dem Bildschirm
	private int offset = 4; // Abstand zwischen angezeigten Tokens
	private int imgSize = 36; // Groesse der Tokens
	private long startTime;
	private Grid grid;

	GameplayState(int sid) {
		stateID = sid;
		entityManager = StateBasedEntityManager.getInstance();
	}

	/**
	 * Wird vor dem (erstmaligen) Starten dieses States ausgefuehrt.
	 * 
	 * @param container GameContainer
	 * @param game      StateBasedGame
	 * 
	 * @throws SlickException Wirft eine SlickException, wenn bei der
	 *                        Spielerstellung ein Fehler auftritt.
	 */
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {

		// Hintergrund laden
		Entity background = new Entity("background"); // Entitaet fuer Hintergrund
		background.setPosition(new Vector2f(400, 300)); // Startposition des Hintergrunds
		background.addComponent(new ImageRenderComponent(new Image(Consts.BG_GENERAL)));
		StateBasedEntityManager.getInstance().addEntity(stateID, background);

		createGameBoard(); // Erzeugt das Spielfeld
		Action cheat1 = new Action() {

			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta, Component event) {
				executeCheat(Consts.CHEAT_HINT);

			}

		};
		Action cheat2 = new Action() {

			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta, Component event) {
				executeCheat(Consts.CHEAT_SOLVE_PAIR);
			}

		};
		Action cheat3 = new Action() {
			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta, Component event) {
				executeCheat(Consts.CHEAT_FIND_PARTNER);
			}

		};

		Action toSide = new Action() {
			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta, Component event) {
				try {
					Desktop.getDesktop().browse(
							new URL("https://moodle.informatik.tu-darmstadt.de/mod/assign/view.php?id=55356").toURI());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		// Buttons
		Action[] buttonActions = new Action[] { new ChangeStateAction(Consts.MAINMENU_STATE),
				new ChangeStateAction(Consts.HIGHSCORE_STATE), toSide, cheat1, cheat2, cheat3 };
		String[] buttonLabels = new String[] { "Back to Main Menu", "Surrender", "Moodle", "Hint", "Solve Pair",
				"Find Partner" };

		for (int i = 0; i < 3; i++) {
			Button button = new Button(buttonLabels[i], 135 + (i * 200), 100, buttonActions[i], 2);
			entityManager.addEntity(stateID, button);
		}
		// Cheats
		for (int i = 3; i < 6; i++) {
			Button button = new Button(buttonLabels[i], 135 + ((i - 3) * 200), 171, buttonActions[i], 2);
			entityManager.addEntity(stateID, button);
		}
	}

	/**
	 * Fuert den Cheat mit der passenden ID auf dem Grid aus.
	 * 
	 * @param cheatID.
	 */
	private void executeCheat(int cheatID) {
		switch (cheatID) {
		case Consts.CHEAT_FIND_PARTNER:
			Cheats.findPartner(grid);
			break;
		case Consts.CHEAT_HINT:
			Cheats.useHint(grid);
			break;
		case Consts.CHEAT_SOLVE_PAIR:
			Cheats.solvePair(grid);
			break;
		default:
			System.out.println("Fehler: Kein Cheat mit dieser ID vorhanden");
		}
	}

	/**
	 * Wird ausgefuehrt, wenn in diesen GameState gewechselt wird.
	 * 
	 * @param container GameContainer
	 * @param game      StateBasedGame
	 * 
	 * @throws SlickException Wirft eine SlickException, wenn beim Wechseln in den
	 *                        GameState ein Fehler auftritt.
	 */
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);

		startTime = System.currentTimeMillis();

	}

	/**
	 * Wird vor dem Frame ausgefuehrt.
	 * 
	 * @param container GameContainer
	 * @param game      StateBasedGame
	 * 
	 * @throws SlickException Wirft eine Slickexception beim Updaten des
	 *                        EntityManagers, wenn ein Fehler auftritt.
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// StatedBasedEntityManager soll alle Entities aktualisieren
		entityManager.updateEntities(container, game, delta);
	}

	/**
	 * Wird mit dem Frame ausgefuehrt.
	 * 
	 * @param container GameContainer
	 * @param game      StateBasedGame
	 * @param g         Graphics
	 * 
	 * @throws SlickException Wirft eine SlickException, wenn beim Rendern ein
	 *                        Fehler auftritt.
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// StatedBasedEntityManager soll alle Entities rendern
		if (Grid.isSolved()) {
			game.enterState(Consts.HIGHSCORE_STATE);
		}
		entityManager.renderEntities(container, game, g);
		// Zeigt die Zeit an
		g.drawString(String.format("Time: %02d:%02d", Math.round((System.currentTimeMillis() - startTime) / 60000),
				Math.round(((System.currentTimeMillis() - startTime) / 1000) % 60)), 650, 100);
		g.drawString("Score: " + grid.getScore(), 650, 150);

		// Hintergrund fuer Score und Zeitanzeige
		Entity label = new Entity("label");
		label.setPosition(new Vector2f(700, 135));
		label.setScale(1.2f);
		label.addComponent(new BIRC(new Image("assets/buttons/panel_brown.png")));

		entityManager.addEntity(stateID, label);
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

	/**
	 * Berechnet aus der Position im Array die Position auf dem Bildschirm.
	 * 
	 * @param x X-Koordinate
	 * @param y Y-Koordinate
	 * @return Position als Vector2f
	 */
	public Vector2f grid2Pos(int x, int y) {
		return new Vector2f(x * (imgSize + offset) + startPos.getX(), y * (imgSize + offset) + startPos.getY());
	}

	/**
	 * Erzeugt das Spielfeld.
	 * 
	 * @throws SlickException Wirft eine SlickException, wenn beim Erzeugen des
	 *                        Spielfelds ein Fehler auftritt.
	 */
	private void createGameBoard() throws SlickException {
		this.grid = new Grid("assets/maps/001.map");
		for (int x = 0; x < grid.getGrid().length; x++) {
			for (int y = 0; y < grid.getGrid()[0].length; y++) {
				entityManager.addEntity(stateID, new GameToken("Card(" + x + ", " + y + ")", grid2Pos(x, y),
						grid.getTokenAt(x, y), grid, imgSize));
			}
		}
		grid.updateScore(-grid.getScore() + Consts.START_POINTS);
	}

	/**
	 * Wird aufgerufen, wenn der GameState verlassen wird.
	 * 
	 * @param container GameContainer
	 * @param game      StateBasedGame
	 * 
	 * @throws SlickException Wirft eine SlickException, wenn beim Verlassen des
	 *                        GameStates ein Fehler auftritt.
	 */
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		if (grid != null && (grid.getScore() > 0)) {
			LocalDateTime date = LocalDateTime.now();
			int score = grid.getScore();
			HighscoreState.highscore
					.addEntry(new HighscoreEntry(date.withSecond(0), score, (System.currentTimeMillis() - startTime)));
			HighscoreState.highscore.saveToFile(Consts.HIGHSCORE_PATH);
		}
	}
}
