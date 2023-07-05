package tud.ai1.shisen.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 
 * Class for translating token values into symbols to display on theMatchfield.
 * 
 * @author Ahmed Ezzat
 *
 */
public class TokenDisplayValueProvider {
	private static TokenDisplayValueProvider inst;
	private final String[] map;


	private TokenDisplayValueProvider(final String path) {
		this.map = this.loadMap(path);
	}

	private String[] loadMap(final String path) {
		final List<String> symbols = Arrays.asList(IOOperations.readFile(path).split(Consts.SYMBOL_SEPARATOR));
		Collections.shuffle(symbols);
		return symbols.toArray(String[]::new);
	}

	public String getDisplayValue(final int value) {
		if (value < 0)
			return "";
		return this.map[value];
	}

	public static TokenDisplayValueProvider getInstance() {
		if (inst == null) {
			initilize(Consts.SYMBOLS_PATH);
		}
		return inst;
	}

	public static boolean initilize(final String path) {
		if (inst == null) {
			inst = new TokenDisplayValueProvider(path);
		} else
			return false;
		return true;
	}
}
