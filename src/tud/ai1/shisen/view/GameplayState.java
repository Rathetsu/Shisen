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
 * @author Ahmed Ezzat
 *
 *  This class represents the game window.
 */
public class GameplayState extends BasicGameState {

	private int stateID; // Identifier of this BasicGameState
	private StateBasedEntityManager entityManager; // associated entityManager
	private Vector2f startPos = Consts.startPosition; // Starting position of the window on the screen
	private int offset = 4; // Distance between displayed tokens
	private int imgSize = 36; // Size of tokens
	private long startTime;
	private Grid grid;

	GameplayState(int sid) {
		stateID = sid;
		entityManager = StateBasedEntityManager.getInstance();
	}

	/**
	* Is executed before the (first) start of this state.
	*
	*/
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {

		// loading the background
		Entity background = new Entity("background"); // entity for background
		background.setPosition(new Vector2f(400, 300)); // Background start position
		background.addComponent(new ImageRenderComponent(new Image(Consts.BG_GENERAL)));
		StateBasedEntityManager.getInstance().addEntity(stateID, background);

		createGameBoard(); // Creates the playing field
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
	 * Execute the cheat with the matching ID on the grid.
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
	 * Executed when changing to this GameState.
	 */
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);

		startTime = System.currentTimeMillis();

	}

	/**
	 * Executed before the frame.
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// StatedBasedEntityManager shall update all entities
		entityManager.updateEntities(container, game, delta);
	}

	/**
	 * Runs with the frame. Renders the game.
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// StatedBasedEntityManager shall render all entities
		if (Grid.isSolved()) {
			game.enterState(Consts.HIGHSCORE_STATE);
		}
		entityManager.renderEntities(container, game, g);
		// Shows the time
		g.drawString(String.format("Time: %02d:%02d", Math.round((System.currentTimeMillis() - startTime) / 60000),
				Math.round(((System.currentTimeMillis() - startTime) / 1000) % 60)), 650, 100);
		g.drawString("Score: " + grid.getScore(), 650, 150);

		// Background for score and time display
		Entity label = new Entity("label");
		label.setPosition(new Vector2f(700, 135));
		label.setScale(1.2f);
		label.addComponent(new BIRC(new Image("assets/buttons/panel_brown.png")));

		entityManager.addEntity(stateID, label);
	}

	/**
	 * Returns the ID.
	 */
	@Override
	public int getID() {
		return stateID;
	}

	/**
	 * Calculates the position on the screen from the position in the array.
	 */
	public Vector2f grid2Pos(int x, int y) {
		return new Vector2f(x * (imgSize + offset) + startPos.getX(), y * (imgSize + offset) + startPos.getY());
	}

	/**
	 * Creates the playing field.
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
	 * Called when the GameState is exited.
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
