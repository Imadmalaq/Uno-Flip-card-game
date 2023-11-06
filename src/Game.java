import java.util.*;

/**
 * SYSC3110
 * Group20
 *
 * Represents the main Uno game.
 * This class handles the game logic, player turns, and game flow.
 */
public class Game {

    private Deck deck;
    private List<Player> players;
    private Card currentCard;
    private Scanner scanner;
    private boolean isReversed = false; // To handle the direction of play
    private boolean skipNextPlayer = false; // To handle the SKIP card effect


    /**
     * Constructs a new Uno game with the specified number of players.
     *
     * @param numPlayers The number of players.
     * @author Mohamed Imad
     * @author Taticek Kyle
     */
    public Game(int numPlayers) {
        if (numPlayers < 2) {
            throw new IllegalArgumentException("Minimum of 2 players required.");
        }

        deck = new Deck();
        players = new ArrayList<>();
        scanner = new Scanner(System.in);
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter name for Player " + (i + 1) + ": ");
            String playerName = scanner.nextLine();
            Player player = new Player(playerName);
            for (int j = 0; j < 7; j++) {  // Each player starts with 7 cards
                player.addCardToHand(deck.draw());
            }
            players.add(player);
        }

        // Ensure the game doesn't start with a wild card, reverse, skip, or draw two
        do {
            currentCard = deck.draw();
        } while (currentCard.getColor() == Card.Color.WILD ||
                currentCard.getValue() == Card.Value.REVERSE ||
                currentCard.getValue() == Card.Value.SKIP ||
                currentCard.getValue() == Card.Value.DRAW_TWO);
    }

    /**
     * Handles the turn for a given player. This method prompts the player to play a card from their hand or draw a card.
     * If the player chooses to play a card, the method checks if the card is valid based on the current card on the table.
     * Special cards like SKIP, REVERSE, DRAW_TWO, WILD, and WILD_DRAW_FOUR have additional effects that are handled within this method.
     *
     * @param player The current player whose turn is being handled.
     * @author Mohamed Imad
     * @author Taticek Kyle
     * @author Aleksiev Toman
     * @author Galic Filip
     */
    private void playerTurn(Player player) {
        System.out.println(player.getName() + "'s turn.");
        System.out.println("Current card: " + currentCard);
        System.out.println("Your hand: " + player.getHand());
        System.out.println("Choose a card to play or type 'draw' to draw a card (e.g., BLUE EIGHT):");
        String input = scanner.nextLine();

        if (input.equals("draw")) {
            player.addCardToHand(deck.draw());
            return;
        }

        // Split the input to get color and value
        String[] parts = input.split(" ");
        Card.Color chosenColor;
        Card.Value chosenValue;
        try {
            chosenColor = Card.Color.valueOf(parts[0]);
            chosenValue = Card.Value.valueOf(parts[1]);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input format. Please enter in the format 'COLOR VALUE' (e.g., BLUE EIGHT).");
            playerTurn(player);
            return;
        }

        Card chosenCard = new Card(chosenColor, chosenValue);
        if (player.getHand().contains(chosenCard) && (chosenCard.getColor() == currentCard.getColor() || chosenCard.getValue() == currentCard.getValue() || chosenCard.getColor() == Card.Color.WILD)) {
            player.removeCardFromHand(chosenCard);

            if (chosenCard.getColor() == Card.Color.WILD) {
                System.out.println("You played a WILD card. Choose a color (RED, GREEN, BLUE, YELLOW):");
                String chosenColorStr = scanner.nextLine().toUpperCase();
                try {
                    Card.Color newColor = Card.Color.valueOf(chosenColorStr);
                    if (newColor == Card.Color.WILD) {
                        throw new IllegalArgumentException();
                    }
                    currentCard = new Card(newColor, chosenCard.getValue());  // Update the current card with the chosen color
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid color choice. Try again.");
                    playerTurn(player);
                    return;
                }
            } else {
                currentCard = chosenCard;
            }

            // Handle special cards
            if (chosenCard.getValue() == Card.Value.SKIP) {
                skipNextPlayer = true;
            } else if (chosenCard.getValue() == Card.Value.REVERSE) {
                isReversed = !isReversed;
            } else if (chosenCard.getValue() == Card.Value.DRAW_TWO) {
                Player nextPlayer = getNextPlayer(player);
                System.out.println(nextPlayer.getName() + " has drawn 2 cards due to DRAW_TWO card.");
                nextPlayer.addCardToHand(deck.draw());
                nextPlayer.addCardToHand(deck.draw());
            }
            else if (chosenCard.getValue() == Card.Value.WILD_DRAW_FOUR) {
                Player nextPlayer = getNextPlayer(player);
                System.out.println(nextPlayer.getName() + " has drawn 4 cards due to WILD_DRAW_FOUR card.");
                for (int i = 0; i < 4; i++) {
                    nextPlayer.addCardToHand(deck.draw());
                }
                skipNextPlayer = true; // The next player is skipped after drawing 4 cards
            }
            // Check if the player has won
            if (player.getHand().isEmpty()) {
                System.out.println(player.getName() + " has won!");
                System.exit(0);  // Exit the game
            }
        } else {
            System.out.println("Invalid card choice. Please choose a card from your hand that matches the current card's color or value.");
            playerTurn(player);
        }
    }

    /**
     * Gets the next player based on the current direction of play.
     *
     * @param currentPlayer The current player.
     * @return The next player.
     * @author Mohamed Imad
     */
    private Player getNextPlayer(Player currentPlayer) {
        int currentIndex = players.indexOf(currentPlayer);
        if (isReversed) {
            return players.get((currentIndex - 1 + players.size()) % players.size());
        } else {
            return players.get((currentIndex + 1) % players.size());
        }
    }

    /**
     * Starts the game and handles the game flow.
     *
     * @throws IllegalStateException if the game has not been properly initialized
     * @author Mohamed Imad
     */
    public void start() {
        Player currentPlayer = players.get(0); // Start with the first player
        while (true) {
            playerTurn(currentPlayer);

            if (skipNextPlayer) {
                currentPlayer = getNextPlayer(getNextPlayer(currentPlayer)); // Skip the next player
                skipNextPlayer = false; // Reset the flag
            } else if (currentCard.getValue() == Card.Value.REVERSE && players.size() == 2) {
                // In a two-player game, the REVERSE card gives the current player another turn.
                // So, currentPlayer remains the same.
            } else {
                currentPlayer = getNextPlayer(currentPlayer); // Move to the next player
            }
        }
    }

    /**
     * Main
     *
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of players (2 or more): ");
        int numPlayers = scanner.nextInt();
        Game game = new Game(numPlayers);
        game.start();
    }
}
