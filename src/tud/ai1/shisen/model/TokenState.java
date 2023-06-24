package tud.ai1.shisen.model;

/**
 * Enumeration to represent the state of a field on the game board.
 */
public enum TokenState {
    DEFAULT,   // Initial State
    CLICKED,   // State when the field has been clicked
    WRONG,     // State when a wrong move is made
    SOLVED     // State when the field is solved
}
