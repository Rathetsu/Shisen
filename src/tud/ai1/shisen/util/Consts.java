package tud.ai1.shisen.util;

import java.awt.Font;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * Dieses Interface beinhaltet alle wichtigen Konstanten fuer das
 * Shisen-Projekt.
 *
 * @author Maximilian Kratz
 * @date 2019-05-06
 *
 */
public interface Consts {
	// Cheat Kosten
	public static final int CHEAT_COST_SOLVE_PAIR = -20;
	public static final int CHEAT_COST_HINT = -10;
	public static final int CHEAT_COST_FIND_PARTNER = -10;
	public static final int START_POINTS = 0;

	// Game States
	// Score-Werte
	public static final int GAIN_SCORE = 5;
	public static final int WRONG_PAIR = -1;
	public static final int DECREASE_SCORE = -5;
	int MAINMENU_STATE = 0;
	int GAMEPLAY_STATE = 1;
	int HIGHSCORE_STATE = 2;

	// Betriebssystem Konstanten
	String WINDOWS_LIB_PATH = "org.lwjgl.librarypath";
	String WINDOWS_USER_DIR = "/native/windows";
	String MAC_LIB_PATH = "org.lwjgl.librarypath";
	String MAC_USER_DIR = "/native/macosx";
	String LINUX_LIB_PATH = "org.lwjgl.librarypath";
	String LINUX_USER_DIR = "/native/";
	String OS_NAME = "os.name";
	String USER_DIR = "user.dir";
	String WINDOWS_OS_NAME = "windows";
	String MAC_OS_NAME = "mac";

	// Parser Separierer
	String SYMBOL_SEPARATOR = ",";

	// Fenster Einstellungen
	int WINDOW_WIDTH = 800;
	int WINDOW_HEIGHT = 600;

	// Schriftarten
	public static final TrueTypeFont FONT_15 = new TrueTypeFont(new Font("Courier New", Font.BOLD, 15), false);
	public static final TrueTypeFont FONT_20 = new TrueTypeFont(new Font("Courier New", Font.BOLD, 20), false);
	public static final TrueTypeFont FONT_30 = new TrueTypeFont(new Font("Courier New", Font.BOLD, 30), false);
	public static final TrueTypeFont FONT_15_GAMETOKEN = new TrueTypeFont(new Font("Courier New", Font.BOLD, 15),
			false);

	// Button Bilder Pfade
	public static final String BUTTON_ACTIVE_MENU = "/assets/buttons/menuButton.png";
	public static final String BUTTON_INACTIVE_MENU = "/assets/buttons/menuButton_inactive.png";

	public static final String BUTTON_ACTIVE_SIZE = "/assets/buttons/fieldSizeButton.png";
	public static final String BUTTON_INACTIVE_SIZE = "/assets/buttons/fieldSizeButton_inactive.png";

	// Token Bilder Pfade
	public static final String TOKEN_NORMAL = "/assets/tokens/normal.png";
	public static final String TOKEN_CLICKED = "/assets/tokens/clicked.png";
	public static final String TOKEN_BLOCKED = "/assets/tokens/blocked.png";
	public static final String TOKEN_WRONG = "/assets/tokens/wrong.png";

	// Zeit, die ein ausgeaehltes Elemente hervorgehoben wird
	public static final int DISPLAY_WRONG_TIME = 1;

	// Start-Position eines Grids
	public final Vector2f startPosition = new Vector2f(20, 220);

	// Cheat IDs
	public static final int CHEAT_SOLVE_PAIR = 0;
	public static final int CHEAT_HINT = 1;
	public static final int CHEAT_FIND_PARTNER = 2;

	// Assets
	String ASSETS_PATH = "assets";
	String MAPS_PATH = ASSETS_PATH + "/maps";
	String SYMBOLS_PATH = ASSETS_PATH + "/symbol/symbols_latin.txt";

	// Highscore-Pfad
	String HIGHSCORE_PATH = ASSETS_PATH + "/highscore/highscores.hs";

	// Hintergruende
	String BG_GENERAL = ASSETS_PATH + "/background_2.png";
	String BG_HIGHSCORE = ASSETS_PATH + "/background_2_highscore.png";
}
