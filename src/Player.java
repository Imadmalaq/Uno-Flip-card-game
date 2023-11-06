import java.util.*;

/**
 * SYSC3110
 * Group20
 *
 * Represents a player in the Uno game.
 * This class is responsible for managing the player's hand of cards.
 */

public class Player {

    private List<Card> hand;
    private String name;

    /**
     * Constructs a new player with an empty hand and a specified name.
     *
     * @param name The name of the player.
     * @author Taticek Kyle
     */
    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param card The card to be added.
     * @author Taticek Kyle
     */
    public void addCardToHand(Card card) {
        hand.add(card);
    }

    /**
     * Removes a card from the player's hand.
     *
     * @param card The card to be removed.
     * @author Taticek Kyle
     */
    public void removeCardFromHand(Card card) {
        hand.remove(card);
    }

    /**
     * Returns the player's hand of cards.
     *
     * @return The player's hand.
     * @author Taticek Kyle
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Returns the name of the player.
     *
     * @return The player's name.
     * @author Taticek Kyle
     */
    public String getName() {
        return name;
    }
}
