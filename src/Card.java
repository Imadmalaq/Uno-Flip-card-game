import java.util.*;

/**
 * SYSC3110
 * Group 20
 *
 * Represents a single Uno card with a specific color and value.
 */
public class Card {

    /**
     * Enum representing the possible colors of an Uno card.
     *
     * @author Aleksiev Toman
     */
    public enum Color {
        RED, GREEN, BLUE, YELLOW, WILD
    }

    /**
     * Enum representing the possible values of an Uno card.
     *
     * @author Aleksiev Toman
     */
    public enum Value {
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP, REVERSE, DRAW_TWO, WILD, WILD_DRAW_FOUR
    }

    private Color color;
    private Value value;

    /**
     * Constructs a new Uno card with the specified color and value.
     *
     * @param color The color of the card.
     * @param value The value of the card.
     * @author Aleksiev Toman
     */
    public Card(Color color, Value value) {
        this.color = color;
        this.value = value;
    }

    /**
     * Returns the color of the card.
     *
     * @return The color of the card.
     * @author Aleksiev Toman
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns the value of the card.
     *
     * @return The value of the card.
     * @author Aleksiev Toman
     */
    public Value getValue() {
        return value;
    }

    /**
     * Provides a string representation of the card in "COLOR VALUE" format.
     *
     * @return A concatenated string of color and value.
     * @author Aleksiev Toman
     */
    @Override
    public String toString() {
        return color + " " + value;
    }

    /**
     * Compares this card with another object for equality based on color and value.
     *
     * @param obj The object to be compared for equality with this card.
     * @return true if the specified object is equal to this card.
     * @author Aleksiev Toman
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return color == card.color && value == card.value;
    }


    /**
     * Generates a hash code for this card based on its color and value.
     *
     * @return A hash code value for this card.
     * @author Galic Filip
     */
    @Override
    public int hashCode() {
        return Objects.hash(color, value);
    }
}
