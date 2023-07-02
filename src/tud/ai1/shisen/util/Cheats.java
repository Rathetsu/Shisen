package tud.ai1.shisen.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import tud.ai1.shisen.model.Grid;
import tud.ai1.shisen.model.IToken;
import tud.ai1.shisen.model.TokenState;

/**
 * 
 * This class represents the cheats functionality of the game.
 * 
 * @author Ahmed Ezzat
 *
 */
public class Cheats {

	/**
	 * This class is not intended to be initializable as it only has static methods.
	 */
	private Cheats() {
	}

	public static void findPartner(final Grid grid) {
		// Check if exactly one token is selected
		IToken[] activeTokens = grid.getActiveTokens();

		if (activeTokens[0] != null && activeTokens[1] == null) {
			IToken selectedToken = activeTokens[0];

			// Find possible partners
			List<IToken> possiblePartners = Cheats.findTokensWithType(selectedToken, grid);

			// Try to find a solvable partner
			for (IToken partner : possiblePartners) {
				if (Cheats.solvable(selectedToken, partner, grid)) {
					grid.selectToken(partner); // Select the solvable partner
					grid.updateScore(Consts.CHEAT_COST_FIND_PARTNER); // Subtract cheat cost
					return;
				}
			}

			// If no solvable partner is found, select a random partner
			if (!possiblePartners.isEmpty()) {
				grid.selectToken(possiblePartners.get(0)); // Select the first unsolvable partner
				grid.updateScore(Consts.CHEAT_COST_FIND_PARTNER + Consts.DECREASE_SCORE); // Subtract cheat cost and incorrect pair cost
			}
		}
	}


	/**
	 * This cheat marks a token that is currently solvable.
	 */
	public static void useHint(final Grid grid) {
		// If two tokens are already clicked, cancel cheat
		if (grid.bothClicked())
			return;
		if (!isCheatPossible(grid, Consts.CHEAT_HINT))
			return;
		final IToken[] tok = findValidTokens(grid);
		if (tok == null) {
			System.out.println("Cheat nicht mehr moeglich");
			return;

		}
		grid.deselectTokens();

		grid.selectToken(tok[0]);
		System.out.println("Cheat: useHint");
		grid.updateScore(Consts.CHEAT_COST_HINT);
	}

	public static void solvePair(final Grid grid) {
		if (!isCheatPossible(grid, Consts.CHEAT_SOLVE_PAIR)) {
			return;
		}
		IToken[] tokens = findValidTokens(grid);
		grid.deselectTokens();
		if (tokens != null) {
			grid.selectToken(tokens[0]);
			grid.selectToken(tokens[1]);
		}
		grid.updateScore(-getCheatCost(Consts.CHEAT_SOLVE_PAIR));
	}


	/**
	 * Finds a pair of currently solvable tokens.
	 */
	private static IToken[] findValidTokens(final Grid grid) {
		final IToken[][] board = grid.getGrid();
		IToken token = null;
		Random r = new Random();
		// Offset for the start position of the search in the grid array
		int n1 = r.nextInt(board.length);
		int n2 = r.nextInt(board[0].length);
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				token = board[(x + n1) % board.length][(y + n2) % board[0].length];
				if (token.getTokenState() != TokenState.DEFAULT)
					continue;
				// Test all theoretically possible partners
				for (IToken partner : findTokensWithType(token, grid)) {
					if (solvable(token, partner, grid)) {
						IToken[] ret = { token, partner };
						return ret;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Determines whether two tokens match (are solvable).
	 */
	private static boolean solvable(IToken token1, IToken token2, Grid grid) {
		if (token1.getTokenState() == TokenState.SOLVED || token2.getTokenState() == TokenState.SOLVED)
			return false;
		// Set TokenStates to Clicked so Search algorithm can use them
		TokenState pre1 = token1.getTokenState();
		TokenState pre2 = token2.getTokenState();
		token1.setTokenState(TokenState.CLICKED);
		token2.setTokenState(TokenState.CLICKED);
		List<IToken> list = PathFinder.getInstance().findPath(grid, (int) token1.getPos().x, (int) token1.getPos().y,
				(int) token2.getPos().x, (int) token2.getPos().y);
		// After algorithm has finished, reset TokenStates
		token1.setTokenState(pre1);
		token2.setTokenState(pre2);
		if (list != null && list.size() > 0)
			return true;
		return false;
	}

	private static List<IToken> findTokensWithType(final IToken token, final Grid grid) {
		List<IToken> tokens = new ArrayList<>();
		IToken[][] board = grid.getGrid();
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				IToken current = board[x][y];
				if (current.getDisplayValue().equals(token.getDisplayValue()) && current != token && current.getTokenState() == TokenState.DEFAULT) {
					tokens.add(current);
				}
			}
		}
		return shuffle(tokens);
	}


	private static List<IToken> shuffle(List<IToken> list) {
		Collections.shuffle(list);
		return list;
	}


	private static boolean isCheatPossible(final Grid grid, final int cheatID) {
		return grid.getScore() >= getCheatCost(cheatID);
	}


	/**
	 * Returns the cheat costs for the given cheat.
	 */
	private static int getCheatCost(final int cheatID) {
		switch (cheatID) {
		case Consts.CHEAT_HINT:
			return Consts.CHEAT_COST_HINT;
		case Consts.CHEAT_SOLVE_PAIR:
			return Consts.CHEAT_COST_SOLVE_PAIR;
		case Consts.CHEAT_FIND_PARTNER:
			return Consts.CHEAT_COST_FIND_PARTNER;
		default:
			System.out.println("Kein Cheat mit dieser ID vorhanden");
			return -1;
		}
	}
}
