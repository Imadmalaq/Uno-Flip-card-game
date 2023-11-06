import java.util.*;

/**
 * SYSC3110
 * Group20
 *
 * Represents the deck of Uno cards.
 * This class is responsible for shuffling and drawing cards.
 */
public class Deck {

    private List<Card> cards;

    /**
     * Constructs a new deck of Uno cards and shuffles them.
     * This constructor ensures that the deck does not start with a wild card on top.
     * If the top card is a wild card, it is moved to the bottom of the deck.
     *
     * @author Galic Filip
     * @author Aleksiev Toman
     */
    public Deck() {
        cards = new ArrayList<>();
        for (Card.Color color : Card.Color.values()) {
            for (Card.Value value : Card.Value.values()) {
                // Skip invalid combinations like WILD SEVEN
                if (color == Card.Color.WILD && (value != Card.Value.WILD && value != Card.Value.WILD_DRAW_FOUR)) {
                    continue;
                }
                if (color != Card.Color.WILD && (value == Card.Value.WILD || value == Card.Value.WILD_DRAW_FOUR)) {
                    continue;
                }

                // Add two of each card to the deck, except for ZERO and WILD cards.
                int count = (value == Card.Value.ZERO || color == Card.Color.WILD) ? 1 : 2;
                for (int i = 0; i < count; i++) {
                    cards.add(new Card(color, value));
                }
            }
        }
        ensureNonWildStart(); // Ensure the top card is not a wild card
        shuffle();
    }

    /**
     * Shuffles the deck of cards.
     *
     * @author Galic Filip
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Ensure that the top card of the deck is not a wild card.
     * If the top card is a wild card, it is moved to the bottom of the deck.
     *
     * @author Galic Filip
     */
    private void ensureNonWildStart() {
        Card topCard = cards.get(cards.size() - 1);
        while (topCard.getColor() == Card.Color.WILD) {
            cards.remove(cards.size() - 1);  // Remove the top card
            cards.add(0, topCard);  // Add the card to the bottom of the deck
            topCard = cards.get(cards.size() - 1);
        }
    }

    /**
     * Draws the top card from the deck.
     *
     * @return The top card.
     * @throws IllegalStateException if the deck is empty.
     * @author Galic Filip
     */
    public Card draw() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        return cards.remove(cards.size() - 1);
    }

    /**
     * Returns the number of cards left in the deck.
     *
     * @return The number of cards left.
     * @author Galic Filip
     */
    public int size() {
        return cards.size();
    }
}
