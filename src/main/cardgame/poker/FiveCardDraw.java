package cardgame.poker;

import cardgame.common.Card;
import cardgame.common.Deck;
import cardgame.common.Game;
import cardgame.common.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * FiveCardDraw implements the rules for the Five Card Draw poker game.
 */
public class FiveCardDraw extends Game {
    private static final int NUM_CARDS_IN_FULL_HAND = 5;

    private Deck discardDeck;
    private int dealerPlayerIndex;

    /**
     * Construct Five Card Draw game.
     */
    public FiveCardDraw() {
        super();
        dealerPlayerIndex = 0;
        discardDeck = new Deck(false);
        for (Player player : getPlayers()) {
            player.setHand(new PokerHand(NUM_CARDS_IN_FULL_HAND));
        }
    }

    /**
     * Add a player to the game if the player is not already playing.
     * Initializes the player with an empty poker hand.
     * @param name name of player
     * @return newly added player if successful; otherwise, null
     */
    @Override
    public Player addPlayer(String name) {
        Player player = super.addPlayer(name);
        if (player != null) {
            player.setHand(new PokerHand(NUM_CARDS_IN_FULL_HAND));
        }
        return player;
    }

    /**
     * Deal a card to the player. If cards in deck have been exhausted,
     * deal card from the discard deck.
     * @param player the player to deal the card to
     * @return true if successful, false if there are no more cards
     * in deck or discard deck
     */
    public Boolean dealCard(Player player) {
        Boolean gameEnded = false;
        if (!getDeck().isEmpty()) {
            Card card = getDeck().drawCard();
            if (card != null) {
                player.getHand().addCard(card);
            }
        } else {
            if (!discardDeck.isEmpty()) {
                discardDeck.shuffle();
                Card card = discardDeck.drawCard();
                if (card != null) {
                    player.getHand().addCard(card);
                }
            } else {
                // both deck & discard deck are empty
                gameEnded = true;
            }
        }
        return gameEnded;
    }

    /**
     * Prompt player for number of cards to discard, followed by discard positions.
     * @return the positions to discard
     */
    private List<Integer> promptForDiscards() {
        int numDiscards = promptForNumDiscards();
        List <Integer> positionsToDiscard = new ArrayList<Integer>();
        if (numDiscards > 0) {
            promptForDiscardPositions(numDiscards, positionsToDiscard);
        }
        return positionsToDiscard;
    }

    /**
     * Prompt player for number of cards to discard.
     * @return number of cards to discard
     */
    private int promptForNumDiscards() {
        int numDiscards = -1;  // initialize to an invalid number
        System.out.println("How many cards do you want to discard? Enter a number between 0 and 5.");
        Scanner scanner = new Scanner(System.in);
        while ((numDiscards < 0) || (numDiscards  > NUM_CARDS_IN_FULL_HAND)) {
            if (scanner.hasNextInt()) {
                numDiscards = scanner.nextInt();
            }
            if ((numDiscards < 0) || (numDiscards  > NUM_CARDS_IN_FULL_HAND)) {
                // if input if invalid, then prompt again
                System.out.println("Please enter a number between 0 and 5.");
            }
        }
        return numDiscards;
    }

    /**
     * Prompt for positions to discard
     * @param numDiscards number of discards to prompt for
     * @param positionsToDiscard a list to which the positions to discard should be added
     */
    private void promptForDiscardPositions(int numDiscards, List<Integer> positionsToDiscard) {
        Scanner scanner = new Scanner(System.in);
        if (numDiscards > 0 && numDiscards <= NUM_CARDS_IN_FULL_HAND) {
            System.out.println("Enter the positions (between 0 and 4) of the cards you want to discard.");
            while (positionsToDiscard.size() < numDiscards) {
                if (scanner.hasNextInt()) {
                    int position = scanner.nextInt();
                    if (positionsToDiscard.contains(position)) {
                        System.out.println("That position has already been entered. Please enter another position between 0 and 4.");
                    } else {
                        if ((position >= 0) &&  (position < NUM_CARDS_IN_FULL_HAND)) {
                            positionsToDiscard.add(position);
                        } else {
                            System.out.println("Please enter a number between 0 and 4.");
                        }
                    }
                } else {
                    System.out.println("Please enter a number between 0 and 4.");
                }
            }
        }
    }

    /**
     * Display player's cards and prompt for discards.
     * @param player the player for whom to prompt for discards
     */
    public void beforeTurn(Player player) {
        List<Integer> positionsToDiscard;
        System.out.println("\nBefore Turn:");
        System.out.println(player);

        positionsToDiscard = promptForDiscards();
        Collections.sort(positionsToDiscard);
        while (!positionsToDiscard.isEmpty()) {
            // discard from the highest position first so that the indices
            // still work as we discard cards
            int currentPosition = positionsToDiscard.get(positionsToDiscard.size() - 1);
            discardDeck.addCard(player.getHand().getCard(currentPosition));
            player.getHand().removeCard(currentPosition);
            positionsToDiscard.remove(positionsToDiscard.size() - 1);
        }
    }

    /**
     * Deal cards to player until player has full hand.
     * @param player the player for whom cards are dealt
     * @return whether the game has ended
     */
    public Boolean turn(Player player) {
        Boolean gameEnded = false;
        while (!player.getHand().isFullHand()) {
            gameEnded = dealCard(player);
            if (gameEnded) {
                break;
            }
        }
        return gameEnded;
    }

    /**
     * Display new hand for player.
     * @param player the player for whom to display hand
     */
    public void afterTurn(Player player) {
        System.out.println("\nAfter turn: ");
        System.out.println(player);
    }

    /**
     * Deal hands and prompt for discards starting at player after dealer.
     * @return whether the game has ended
     */
    @Override
    public Boolean beforeRound() {
        Boolean gameEnded;
        gameEnded = dealCards();
        if (!gameEnded) {
            int numPlayers = getPlayers().size();
            int playerNum = (dealerPlayerIndex + 1) % numPlayers; // start 1 beyond dealer
            for (int i = 0; i < numPlayers; i++) {
                getPlayers().get(playerNum).getHand();
                beforeTurn(getPlayers().get(playerNum));
                playerNum = (playerNum + 1) % numPlayers;
            }
        }
        return gameEnded;
    }

    /**
     * Deal cards to all of the players until each player has a full hand
     * or the deck has run out of cards.
     * @return whether the game has ended
     */
    private Boolean dealCards() {
        Boolean gameEnded = false;
        int numPlayers = getPlayers().size();
        int playerNum = (dealerPlayerIndex + 1) % numPlayers; // start 1 beyond dealer
        System.out.println("\nDealer: " + getPlayers().get(dealerPlayerIndex).getName());
        for (int i = 0; i < NUM_CARDS_IN_FULL_HAND; i++) {
            for (int j = 0; j < numPlayers; j++) {
                gameEnded = dealCard(getPlayers().get(playerNum));
                if (gameEnded) {
                    break;
                }
                playerNum = (playerNum + 1) % numPlayers;
            }
        }
        return gameEnded;
    }

    /**
     * Deal cards and show new hands, starting at player after dealer.
     * @return whether the game has ended
     */
    @Override
    public Boolean round() {
        Boolean gameEnded = false;
        int numPlayers = getPlayers().size();
        int playerNum = (dealerPlayerIndex + 1) % numPlayers; // start 1 beyond dealer
        for (int i = 0; i < numPlayers; i++) {
            // go around the table
            gameEnded = turn(getPlayers().get(playerNum));
            if (gameEnded) {
                break;
            }
            afterTurn(getPlayers().get(playerNum));
            playerNum = (playerNum + 1) % numPlayers;
        }
        return gameEnded;
    }

    /**
     * Score the round by sorting players, from lowest to highest ranking.
     * @return the list of players, sorted by rank, lowest rank first
     */
    @Override
    protected List<Player> scoreRound() {
        List<Player> sortedPlayers = new ArrayList<Player>(getPlayers());
        Collections.sort(sortedPlayers, new java.util.Comparator<Player>() {
            public int compare(Player player1, Player player2) {
                PokerHand hand1;
                PokerHand hand2;
                if (player1.getHand() instanceof PokerHand) {
                    hand1 = (PokerHand)player1.getHand();
                } else {
                    // unexpected since Hand is internal to FiveCardDraw
                    return -1;
                }
                if (player2.getHand() instanceof PokerHand) {
                    hand2 = (PokerHand)player2.getHand();
                } else {
                    // unexpected since Hand is internal to FiveCardDraw
                    return 1;
                }
                return hand1.compareTo(hand2);
            }
        });
        return sortedPlayers;
    }

}
