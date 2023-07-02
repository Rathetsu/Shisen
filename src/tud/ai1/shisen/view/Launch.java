package tud.ai1.shisen.view;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.StateBasedEntityManager;
import tud.ai1.shisen.util.Consts;

/**
 * @author Ahmed Ezzat
 * 
 *  This class starts the game "Shisen". It contains three states for the menu, the actual game and the high score view.
 */
public class Launch extends StateBasedGame {

	/**
	 * constructor of the class. Calls the super constructor with the name of the game on.
	 */
	public Launch() {
		super("Shisen");
	}


	public static void main(String[] args) throws SlickException {
		// Set the library path depending on the operating system
		if (System.getProperty(Consts.OS_NAME).toLowerCase().contains(Consts.WINDOWS_OS_NAME)) {
			System.setProperty(Consts.WINDOWS_LIB_PATH, System.getProperty(Consts.USER_DIR) + Consts.WINDOWS_USER_DIR);
		} else if (System.getProperty(Consts.OS_NAME).toLowerCase().contains(Consts.MAC_OS_NAME)) {
			System.setProperty(Consts.MAC_LIB_PATH, System.getProperty(Consts.USER_DIR) + Consts.MAC_USER_DIR);
		} else {
			System.setProperty(Consts.LINUX_LIB_PATH, System.getProperty(Consts.USER_DIR) + Consts.LINUX_USER_DIR
					+ System.getProperty(Consts.OS_NAME).toLowerCase());
		}

		// Put this StateBasedGame in an app container (or window)
		AppGameContainer app = new AppGameContainer(new Launch());

		// Sets the window's settings and launches the window (but not in full screen mode)
		app.setDisplayMode(Consts.WINDOW_WIDTH, Consts.WINDOW_HEIGHT, false);

		// Set refresh rate (maximum)
		app.setVSync(true);

		// Don't show FPS value in game
		app.setShowFPS(false);

		// Start game
		app.start();
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		// Fuege dem StateBasedGame die States hinzu
		// (der zuerst hinzugefuegte State wird als erster State gestartet)
		addState(new MainMenuState(Consts.MAINMENU_STATE));
		addState(new GameplayState(Consts.GAMEPLAY_STATE));
		addState(new HighscoreState(Consts.HIGHSCORE_STATE));

		// Fuege dem StateBasedEntityManager die States hinzu
		StateBasedEntityManager.getInstance().addState(Consts.MAINMENU_STATE);
		StateBasedEntityManager.getInstance().addState(Consts.GAMEPLAY_STATE);
		StateBasedEntityManager.getInstance().addState(Consts.HIGHSCORE_STATE);
	}
}
