package tud.ai1.shisen.util;

import java.awt.Font;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * This interface contains all important constants for the
 * Shisen project.
 *
 * @author Ahmed Ezzat
 *
 */
public interface Consts {
	// Cheat Costs
	public static final int CHEAT_COST_SOLVE_PAIR = -20;
	public static final int CHEAT_COST_HINT = -10;
	public static final int CHEAT_COST_FIND_PARTNER = -10;
	public static final int START_POINTS = 0;

	// Game States
	// Score Values
	public static final int GAIN_SCORE = 5;
	public static final int WRONG_PAIR = -1;
	public static final int DECREASE_SCORE = -5;
	public static final int INITIAL_SCORE = 0;
	int MAINMENU_STATE = 0;
	int GAMEPLAY_STATE = 1;
	int HIGHSCORE_STATE = 2;

	// Operating System Constants
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

	// Parser Separator
	String SYMBOL_SEPARATOR = ",";

	// Window Settings
	int WINDOW_WIDTH = 800;
	int WINDOW_HEIGHT = 600;

	// Fonts
	public static final TrueTypeFont FONT_15 = new TrueTypeFont(new Font("Courier New", Font.BOLD, 15), false);
	public static final TrueTypeFont FONT_20 = new TrueTypeFont(new Font("Courier New", Font.BOLD, 20), false);
	public static final TrueTypeFont FONT_30 = new TrueTypeFont(new Font("Courier New", Font.BOLD, 30), false);
	public static final TrueTypeFont FONT_15_GAMETOKEN = new TrueTypeFont(new Font("Courier New", Font.BOLD, 15),
			false);

	// Button Image Paths
	public static final String BUTTON_ACTIVE_MENU = "/assets/buttons/menuButton.png";
	public static final String BUTTON_INACTIVE_MENU = "/assets/buttons/menuButton_inactive.png";

	public static final String BUTTON_ACTIVE_SIZE = "/assets/buttons/fieldSizeButton.png";
	public static final String BUTTON_INACTIVE_SIZE = "/assets/buttons/fieldSizeButton_inactive.png";

	// Token Image Paths
	public static final String TOKEN_NORMAL = "/assets/tokens/normal.png";
	public static final String TOKEN_CLICKED = "/assets/tokens/clicked.png";
	public static final String TOKEN_BLOCKED = "/assets/tokens/blocked.png";
	public static final String TOKEN_WRONG = "/assets/tokens/wrong.png";

	// Time a selected element is highlighted
	public static final int DISPLAY_WRONG_TIME = 1;

	// Starting position of a grid
	public final Vector2f startPosition = new Vector2f(20, 220);

	// Cheat IDs
	public static final int CHEAT_SOLVE_PAIR = 0;
	public static final int CHEAT_HINT = 1;
	public static final int CHEAT_FIND_PARTNER = 2;

	// Assets
	String ASSETS_PATH = "assets";
	String MAPS_PATH = ASSETS_PATH + "/maps";
	String SYMBOLS_PATH = ASSETS_PATH + "/symbol/symbols_latin.txt";

	// Highscore Path
	String HIGHSCORE_PATH = ASSETS_PATH + "/highscore/highscores.hs";

	// Backgrounds
	String BG_GENERAL = ASSETS_PATH + "/background_2.png";
	String BG_HIGHSCORE = ASSETS_PATH + "/background_2_highscore.png";
}
