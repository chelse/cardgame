package cardgame.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Hand implements a hand of cards.
 */
public abstract class Hand<T extends Hand<T>> implements Comparable<T> {

    private int numCardsInFullHand;
    private List<Card> cards;

    /**
     * Construct an empty hand.
     */
    public Hand() {
        cards = new ArrayList<Card>();
    }

    /**
     * Add a card to hand. When the hand is full, sorts the hand.
     * If a hand is already full, then the card will not be added.
     * @param card card to be added to the hand
     * @return whether the card was added successfully
     */
    public Boolean addCard(Card card) {
        Boolean cardAdded = false;
        if (!isFullHand()) {
            cards.add(card);
            cardAdded = true;
            if (isFullHand()) {
                sort();
            }
        }
        // if hand is full, do nothing
        return cardAdded;
    }

    /**
     * Get card at specified position.
     * @param position the position for the card
     * @return card at position if exists; otherwise, null
     */
    public Card getCard(int position) {
        Card card = null;
        if (position < cards.size()) {
            card = cards.get(position);
        }
        return card;
    }

    /**
     * Get list of cards in hand.
     * @return list of cards in hand
     */
    protected List<Card> getCards() {
        return cards;
    }

    /**
     * Remove card at specified position from hand. If card
     * position is invalid, nothing happens.
     * @param position position of the card to be removed
     */
    public void removeCard(int position) {
        if (cards.size() > position) {
            cards.remove(position);
        }
    }

    /**
     * Return whether the hand is full.
     * @return whether the hand is full
     */
    public Boolean isFullHand() {
        return cards.size() == getNumCardsInFullHand();
    }

    /**
     * Sort the hand.
     */
    protected void sort() {
        Collections.sort(cards);
    }

    /**
     * Return whether the cards in the hand have consecutive
     * ranks. For example, Eight, Nine, Ten, Jack, Queen.
     *
     * Hand is presumed to be sorted, lowest to highest.
     * @return whether the cards in the hand have consecutive ranks
     */
    protected boolean getIsConsecutiveRanks() {
        boolean consecutive = true;
        // -1 because we look ahead by 1
        for (int i = 0; i < getNumCardsInFullHand() - 1; i++) {
            if (!cards.get(i).getRank().getNext().equals(cards.get(i + 1).getRank())) {
                // card and next card are not in sequence
                // look at -2 for 2nd to last
                if (!((i == getNumCardsInFullHand() - 2) &&
                      cards.get(i).getRank().equals(Rank.FIVE) &&
                      cards.get(i + 1).getRank().equals(Rank.ACE))) {
                    // make an exception for Ace in the last position
                    // for all others, set consecutive to false
                    consecutive = false;
                    break;
                }
            }
        }
        return consecutive;
    }

    /**
     * Return whether the cards in the hand have the same suit.
     * For example, all clubs.
     * @return whether the cards in the hand have the same suit
     */
    protected boolean getIsSameSuit() {
        boolean same = true;
        for (int i = 1; i < getNumCardsInFullHand(); i++) {
            if (!cards.get(0).getSuit().equals(cards.get(i).getSuit())) {
                same = false;
                break;
            }
        }
        return same;
    }

    /**
     * Return number of same rank comparisons by comparing every card
     * to every other card. For example, for 5 cards: a, b, c, d, e,
     * the comparisons are:
     * a-b a-c a-d a-e
     *     b-c b-d b-e
     *         c-d c-e
     *             d-e
     * The number of same ranks is the number of comparisons for which
     * the same rank was found between the two cards being compared.
     * @return the number of same rank two-way comparisons
     */
    protected int getNumSameRankInTwoWayComparisons() {
        int numSameRank = 0;
        for (int i = 0; i < getNumCardsInFullHand(); i++) {
            for (int j = i + 1; j < getNumCardsInFullHand(); j++) {
                if (cards.get(i).getRank() == cards.get(j).getRank()) {
                    numSameRank++;
                }
            }
        }
        return numSameRank;
    }

    /**
     * Return number of cards in a full hand.
     * @return number of cards in a full hand
     */
    public int getNumCardsInFullHand() {
        return numCardsInFullHand;
    }

    /**
     * Set the number of cards in a full hand. Only applicable
     * to concrete subclasses.
     * @param numCardsInFullHand number of cards in a full hand
     */
    protected void setNumCardsInFullHand(int numCardsInFullHand) {
        this.numCardsInFullHand = numCardsInFullHand;
    }

    /**
     * Return the card at the higher position that in is a pair
     * of same rank cards, searching from highest rank to lowest rank.
     *
     * Hand is presumed to be sorted, lowest to highest.
     * @return the card that belongs to a pair in a hand.
     * @param startPosition starting position for finding the pair, inclusive
     */
    protected Card findRankPairCardDescending(int startPosition) {
        Card card = null;
        // > 0 because we are checking for pairs
        for (int i = startPosition; i > 0; i--) {
            if (getCard(i).getRank().equals(getCard(i - 1).getRank())) {
                card = getCard(i);
                break;
            }
        }
        return card;
    }

    @Override
    public abstract int compareTo(T other);

    @Override
    public String toString() {
        // show a hand as card shorthand separated by spaces, for example:
        // 2H 3H 4S 5D 6D
        String hand = "";
        for (Card card : cards) {
            hand = hand + card + " ";
        }
        return hand;
    }

}
