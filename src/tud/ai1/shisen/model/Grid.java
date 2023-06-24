package tud.ai1.shisen.model;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import tud.ai1.shisen.util.Consts;
import tud.ai1.shisen.util.PathFinder;

public abstract class Grid implements IGrid {

    private int waitTime = 1000;
    private TokenState destiny;
    private long currTime;
    private boolean timerActive = false;
    private List<IToken> list;
    private static int score = 0;
    
    private static IToken[][] grid;
    private IToken selectedTokenOne = null;
    private IToken selectedTokenTwo = null;
    
    public Grid(String path) {
        parseMap(path);
        fillTokenPositions();
        score = 0;  // initial value
    }

	public void updateScore(final int incr) {
		if (score + incr >= 0) {
			score += incr;
		} else {
			score = 0;
		}
	}

	public int getScore() {
		return score;
	}

	private void fillTokenPositions() {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				grid[x][y].setPos(new Vector2f(x, y));
			}
		}
	}

    @Override
    public IToken getTokenAt(int x, int y) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) {
            return null;
        }
        return grid[x][y];
    }

    @Override
    public IToken[][] getGrid() {
        return grid;
    }

    @Override
    public IToken[] getActiveTokens() {
        return new IToken[]{selectedTokenOne, selectedTokenTwo};
    }

    @Override
    public boolean bothClicked() {
        return selectedTokenOne != null && selectedTokenTwo != null;
    }

    @Override
    public void deselectToken(IToken token) {
        if (token == null) {
            return;
        }
        if (token.equals(selectedTokenOne)) {
            selectedTokenOne.setTokenState(TokenState.DEFAULT);
            selectedTokenOne = null;
        } else if (token.equals(selectedTokenTwo)) {
            selectedTokenTwo.setTokenState(TokenState.DEFAULT);
            selectedTokenTwo = null;
        }
    }

    @Override
    public void deselectTokens() {
        if (selectedTokenOne != null) {
            selectedTokenOne.setTokenState(TokenState.DEFAULT);
            selectedTokenOne = null;
        }
        if (selectedTokenTwo != null) {
            selectedTokenTwo.setTokenState(TokenState.DEFAULT);
            selectedTokenTwo = null;
        }
    }
    
    public static boolean isSolved() {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                if (grid[x][y].getTokenState() != TokenState.SOLVED) {
                    return false;
                }
            }
        }
        return true;
    }

    private Token[][] parseMap(String path) {
        String content = IOOperations.readFile(path);  // reads file content
        String[] lines = content.split(System.lineSeparator());  // splits content into lines
        int rows = lines.length;
        int cols = lines[0].split(" ").length;

        // initialize the grid with border, so it's rows+2 x cols+2
        Token[][] map = new Token[rows+2][cols+2];

        // initialize the borders
        for(int i=0; i<rows+2; i++) {
            map[i][0] = new Token(-1, TokenState.SOLVED, new Vector2f(i, 0));
            map[i][cols+1] = new Token(-1, TokenState.SOLVED, new Vector2f(i, cols+1));
        }
        for(int i=0; i<cols+2; i++) {
            map[0][i] = new Token(-1, TokenState.SOLVED, new Vector2f(0, i));
            map[rows+1][i] = new Token(-1, TokenState.SOLVED, new Vector2f(rows+1, i));
        }

        // loop over lines
        for(int i=0; i<rows; i++) {
            String[] tokens = lines[i].split(" ");  // split line into tokens
            // loop over tokens in line
            for(int j=0; j<tokens.length; j++) {
                int value = Integer.parseInt(tokens[j]);  // parse token value
                map[i+1][j+1] = new Token(value, TokenState.DEFAULT, new Vector2f(i+1, j+1));
            }
        }

        return map;
    }


}


