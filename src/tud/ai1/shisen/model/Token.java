package tud.ai1.shisen.model;

import org.newdawn.slick.geom.Vector2f;

import tud.ai1.shisen.util.TokenDisplayValueProvider;

/**
 * Class that represents a single field on the entire playing field.
 * Implements the IToken interface.
 * 
 * @author Ahmed Ezzat
 * 
 */

public class Token implements IToken {

    private static int counter = 0;
    private final int id;
    private TokenState state;
    private final int value;
    private Vector2f pos;

    public Token(int value, TokenState state, Vector2f pos) {
        this.id = counter++;
        this.state = state;
        this.value = value;
        this.pos = pos;
    }

    public Token(int value) {
        this(value, TokenState.DEFAULT, new Vector2f(0, 0));
    }

    @Override
    public TokenState getTokenState() {
        return this.state;
    }

    @Override
    public void setTokenState(TokenState newState) {
        this.state = newState;
    }

    @Override
    public String getDisplayValue() {
        return TokenDisplayValueProvider.getInstance().getDisplayValue(this.value);
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public Vector2f getPos() {
        return this.pos;
    }

    @Override
    public void setPos(Vector2f newPos) {
        this.pos = newPos;
    }
}
