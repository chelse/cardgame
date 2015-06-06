package cardgame.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Deck implements a deck of cards.
 */
public class Deck {

    private List<Card> cards;

    /**
     * Construct a playing card deck. If isEmpty is not true, the deck
     * is initialized with 52 cards and shuffled. An empty deck is useful for
     * discards.
     * @param isEmpty whether the card deck should be empty on initialization
     */
    public Deck(boolean isEmpty) {
        cards = new ArrayList<Card>();
        if (!isEmpty) {
            for (Rank rank : Rank.values()) {
                for (Suit suit : Suit.values()) {
                    Card card = new Card(rank, suit);
                    cards.add(card);
                }
            }
            Collections.shuffle(cards);
        }
    }

    /**
     * Shuffle the deck.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Draw the first card in the deck, removing it from the deck.
     * @return the first card in the deck, or null if no cards left in deck
     */
    public Card drawCard() {
        Card card = null;
        if (!cards.isEmpty()) {
            card = cards.get(0);
            cards.remove(0);
        }
        return card;
    }

    /**
     * Add card to deck, such as in the case of discard decks.
     * @param card add a card to the deck.
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Return whether the deck is empty.
     * @return true if deck is empty; otherwise, false
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    @Override
    public String toString() {
        String deck = "";
        for (Card card : cards) {
            deck = deck + card + " ";
        }
        return deck;
    }

}
