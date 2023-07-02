package tud.ai1.shisen.model;

import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import tud.ai1.shisen.util.Consts;
import tud.ai1.shisen.util.IOOperations;
import tud.ai1.shisen.util.PathFinder;


public class Grid implements IGrid {

    private int waitTime = 1000;
    private TokenState destiny;
    private long currTime;
    private boolean timerActive = false;
    private List<IToken> list;
    private static int score = 0;
    
    private static IToken[][] grid;
    private IToken selectedTokenOne = null;
    private IToken selectedTokenTwo = null;
    
	public Grid(String mapFilePath) {
		grid = parseMap(mapFilePath);
		fillTokenPositions();
		score = Consts.INITIAL_SCORE;
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
	public void selectToken(final IToken token) {
		if (this.selectedTokenOne == null) {
			this.selectedTokenOne = token;
			selectedTokenOne.setTokenState(TokenState.CLICKED);
		} else if (this.selectedTokenTwo == null) {
			this.selectedTokenTwo = token;
			selectedTokenTwo.setTokenState(TokenState.CLICKED);
			this.list = PathFinder.getInstance().findPath(this, (int) this.selectedTokenOne.getPos().x,
					(int) this.selectedTokenOne.getPos().y, (int) this.selectedTokenTwo.getPos().x,
					(int) this.selectedTokenTwo.getPos().y);
			if (this.list == null || this.list.size() == 0
					|| !this.selectedTokenOne.getDisplayValue().equals(this.selectedTokenTwo.getDisplayValue())) {
				this.selectedTokenOne.setTokenState(TokenState.WRONG);
				this.selectedTokenTwo.setTokenState(TokenState.WRONG);
				this.updateScore(Consts.DECREASE_SCORE);
				this.startTimer(Consts.DISPLAY_WRONG_TIME, TokenState.DEFAULT);
			} else {
				for (final IToken tok : this.list) {
					tok.setTokenState(TokenState.CLICKED);
				}
				this.updateScore(Consts.GAIN_SCORE);
				this.startTimer(Consts.DISPLAY_WRONG_TIME, TokenState.SOLVED);
			}
		}
	}

	private void startTimer(final double waitTime, final TokenState dest) {
		this.timerActive = true;
		this.currTime = System.currentTimeMillis();
		this.waitTime = (int) waitTime * 1000;
		this.destiny = dest;
	}

	@Override
	public void getTimeOver() {
		if (this.timerActive) {
			if (System.currentTimeMillis() - this.currTime > this.waitTime) {
				try {
					if (this.list != null) {
						for (final IToken tok : this.list) {
							tok.setTokenState(TokenState.SOLVED);
						}
					}
					this.selectedTokenOne.setTokenState(this.destiny);
					this.selectedTokenTwo.setTokenState(this.destiny);
					this.selectedTokenOne = null;
					this.selectedTokenTwo = null;
					this.timerActive = false;
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public IToken getTokenAt(int x, int y) {
		if(x < 0 || y < 0 || x >= grid.length || y >= grid[0].length) {
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
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j].getTokenState() != TokenState.SOLVED) {
					return false;
				}
			}
		}
		return true;
	}


	private IToken[][] parseMap(String path) {
		// Read the file content using IOOperations
		String content = IOOperations.readFile(path);
		// Split the content into lines
		String[] lines = content.split(System.lineSeparator());
		
		// Create an array with border size included
		IToken[][] tokens = new IToken[lines.length + 2][lines[0].split(",").length + 2];

		// Create border tokens
		for (int i = 0; i < tokens.length; i++) {
			for (int j = 0; j < tokens[i].length; j++) {
				tokens[i][j] = new Token(-1, TokenState.SOLVED, new Vector2f(i, j));
			}
		}

		// Create the normal tokens
		for (int i = 0; i < lines.length; i++) {
			// Split each line into individual token values
			String[] tokenValues = lines[i].split(",");
			for (int j = 0; j < tokenValues.length; j++) {
				tokens[i+1][j+1] = new Token(Integer.parseInt(tokenValues[j]), TokenState.DEFAULT, new Vector2f(i+1, j+1));
			}
		}
		
		return tokens;
	}



}


