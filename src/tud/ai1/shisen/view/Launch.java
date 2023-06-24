package tud.ai1.shisen.view;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.StateBasedEntityManager;
import tud.ai1.shisen.util.Consts;

/**
 * @author Maximilian Kratz
 * @date 2019-05-04
 *
 *       Diese Klasse startet das Spiel "Shisen". Es enthaelt drei States fuer
 *       das Menue, das eigentliche Spiel und die Highscore-Ansicht.
 */
public class Launch extends StateBasedGame {

	/**
	 * Konstruktor der Klasse. Ruft den Super-Konstruktor mit dem Namen des Spiels
	 * auf.
	 */
	public Launch() {
		super("Shisen");
	}

	/**
	 * Main-Methode zum Starten des Spiels. Testet, auf welchem Betriebssystem das
	 * Programm gestartet wurde und setzt systemabhaengige Bibliotheken.
	 * 
	 * @param args Laufzeit-Argumente, die nicht verwendet werden.
	 * @throws SlickException Wirft eine SlickException wenn im Slick-Framework eine
	 *                        Ausnahme passiert.
	 */
	public static void main(String[] args) throws SlickException {
		// Setze den library Pfad abhaengig vom Betriebssystem
		if (System.getProperty(Consts.OS_NAME).toLowerCase().contains(Consts.WINDOWS_OS_NAME)) {
			System.setProperty(Consts.WINDOWS_LIB_PATH, System.getProperty(Consts.USER_DIR) + Consts.WINDOWS_USER_DIR);
		} else if (System.getProperty(Consts.OS_NAME).toLowerCase().contains(Consts.MAC_OS_NAME)) {
			System.setProperty(Consts.MAC_LIB_PATH, System.getProperty(Consts.USER_DIR) + Consts.MAC_USER_DIR);
		} else {
			System.setProperty(Consts.LINUX_LIB_PATH, System.getProperty(Consts.USER_DIR) + Consts.LINUX_USER_DIR
					+ System.getProperty(Consts.OS_NAME).toLowerCase());
		}

		// Setze dieses StateBasedGame in einen App Container (oder Fenster)
		AppGameContainer app = new AppGameContainer(new Launch());

		// Legt die Einstellungen des Fensters fest und starte das Fenster (nicht aber
		// im Vollbildmodus)
		app.setDisplayMode(Consts.WINDOW_WIDTH, Consts.WINDOW_HEIGHT, false);

		// Lege Bildwiederholrate fest (Maximum)
		app.setVSync(true);

		// Zeige FPS-Wert nicht im Spiel an
		app.setShowFPS(false);

		// Starte Spiel
		app.start();
	}

	/**
	 * Initialisiert die GameStates im StateBasedGame und im
	 * StateBasedEntityManager.
	 * 
	 * @param arg0 GameContainer
	 * @throws SlickException Wirft eine SlickException, wenn im Slick-Framework
	 *                        eine Ausnahme passiert.
	 */
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
