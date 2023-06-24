package tud.ai1.shisen.util;

import java.util.ArrayList;
import java.util.List;

import tud.ai1.shisen.model.IGrid;
import tud.ai1.shisen.model.IToken;
import tud.ai1.shisen.model.TokenState;

/**
 * 
 * Hilfsklasse fuer das Finden eines Pfades zwischen zwei IToken auf einem
 * IGrid.
 *
 * (Disclaimer: Do NOT try to hard to understand this mess. Its not worth it.)
 *
 * @author Robert Jakobi
 *
 */
public class PathFinder {
	/**
	 * Singleton Instanziierung
	 */
	private final static PathFinder inst = new PathFinder();
	private final Integer[][] offset = new Integer[][] { //
			new Integer[] { 1, 0 }, //
			new Integer[] { 0, -1 }, //
			new Integer[] { -1, 0 }, //
			new Integer[] { 0, 1 }, //
	};

	/**
	 * Konstruktor des {@link PathFinder}
	 */
	private PathFinder() {
	}

	/**
	 * Gibt die Singleton-Instanz zurueck.
	 *
	 * @return Singleton-Instanz
	 */
	public static PathFinder getInstance() {
		return inst;
	}

	/**
	 * Gibt den kuerzesten Pfad zwischen Start und Ziel zurueck.
	 *
	 * @param grid   Grid auf dem der Algorithmus ausgefuehrt werden soll
	 * @param xStart X-Koordinate des Startpunkts
	 * @param yStart Y-Koordinate des Startpunkts
	 * @param xEnd   X-Koordinate des Endpunkts
	 * @param yEnd   Y-Koordinate des Endpunkts
	 * @return Liste mit Pfaden vom Start- zum Endpunkt
	 */
	public List<IToken> findPath(final IGrid grid, final int xStart, final int yStart, final int xEnd, final int yEnd) {

		final IToken[] sNeigh = this.getNeighbours(grid, xStart, yStart);

		boolean possible = true;
		for (int mul = 0; possible; mul++) {
			possible = false;
			for (int i = 0; i < sNeigh.length; i++) {

				if (sNeigh[i] == null || (sNeigh[i].getTokenState() == TokenState.DEFAULT)) {
					continue;
				}
				final int end = (i % 2 == 0 ? xStart : yStart) + mul * this.offset[i][i % 2 == 0 ? 0 : 1];
				final List<IToken> path = this.lineFree(grid, xStart, yStart, end, i % 2 == 0);
				if (path == null) {
					continue;
				}
				possible = true;
				final List<IToken> temp = this.directPath(grid, i % 2 == 0 ? end : xStart, i % 2 == 0 ? yStart : end,
						xEnd, yEnd);
				if (temp == null) {
					continue;
				}
				path.addAll(temp);
				return path;
			}
		}
		return null;
	}

	/**
	 * Gibt eine Liste von IToken von Start zu im Umkreis liegenden Zielen zurueck,
	 * die keine direkten Pfade sind.
	 * 
	 * @param grid   Spielfeld.
	 * @param xStart X-Koordinate des Startpunkts.
	 * @param yStart Y-Koordinate des Startpunkts.
	 * @param end    Ende-Parameter.
	 * @param mode   Modus.
	 * @return Liste von Pfaden
	 */
	private List<IToken> lineFree(final IGrid grid, final int xStart, final int yStart, final int end,
			final boolean mode) {
		final List<IToken> path = new ArrayList<>();
		for (int i = mode ? xStart : yStart; i != end; i += (i < end) ? 1 : -1) {
			if (grid.getTokenAt(mode ? i : xStart, mode ? yStart : i) == null || (grid
					.getTokenAt(mode ? i : xStart, mode ? yStart : i).getTokenState() != TokenState.SOLVED
					&& grid.getTokenAt(mode ? i : xStart, mode ? yStart : i).getTokenState() != TokenState.CLICKED))
				return null;
			path.add(grid.getTokenAt(mode ? i : xStart, mode ? yStart : i));
		}
		return path;
	}

	/**
	 * Gibt eine Liste von IToken zurueck, zu denen von einem gegebenen Startpunkt
	 * direkte Pfade bestehen.
	 * 
	 * @param grid   Spielfeld.
	 * @param xStart X-Koordinate des Startpunkts.
	 * @param yStart Y-Koordinate des Startpunkts.
	 * @param xEnd   X-Koordinate des Endpunkts.
	 * @param yEnd   Y-Koordinate des Endpunkts.
	 * @return Liste von IToken
	 */
	private List<IToken> directPath(final IGrid grid, final int xStart, final int yStart, final int xEnd,
			final int yEnd) {
		List<IToken> path;
		if ((path = this.lineFree(grid, xStart, yStart, xEnd, true)) != null) {
			final List<IToken> temp = this.lineFree(grid, xEnd, yStart, yEnd, false);
			if (temp != null) {
				path.addAll(temp);
				path.add(grid.getTokenAt(xEnd, yEnd));
				return path;
			}
		}
		if ((path = this.lineFree(grid, xStart, yStart, yEnd, false)) != null) {
			final List<IToken> temp = this.lineFree(grid, xStart, yEnd, xEnd, true);
			if (temp != null) {
				path.addAll(temp);
				path.add(grid.getTokenAt(xEnd, yEnd));
				return path;
			}
		}
		return null;
	}

	/**
	 * Gibt die vier benachbarten Felder eines uebergebenen Felds zurueck.
	 *
	 * @param grid Spielfeld zum finden der benachbarten Felder.
	 * @param x    X-Koordinate des Felds.
	 * @param y    Y-Koordinate des Felds
	 * @return [~+1][~](r); [~][~-1](u); [~-1][~](l); [~][~+1](d)
	 */
	private IToken[] getNeighbours(final IGrid grid, final int x, final int y) {
		return new IToken[] { grid.getTokenAt(x + 1, y), grid.getTokenAt(x, y - 1), grid.getTokenAt(x - 1, y),
				grid.getTokenAt(x, y + 1), };
	}
}
