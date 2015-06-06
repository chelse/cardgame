package cardgame.common;

/**
 * Card implements a playing card.
 */
public class Card implements Comparable<Card> {

    private Rank rank;
    private Suit suit;

    /**
     * Construct a card of given rank and suit.
     * @param rank rank for the card
     * @param suit suit for the card
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Make a card from a string input of the form KH (King of Hearts),
     * for testing purposes.
     * @param cardString a string that represents the card
     * @return the card described by cardString, or null for invalid cardString
     */
    public static Card getInstance(String cardString) {
        Card card = null;
        String suitString;
        String rankString;
        Rank rank;
        Suit suit;
        if (cardString.length() >= 2) {
            if (cardString.length() == 3) {
                // cardString looks like "10D" = 10 of Diamonds
                rankString = "" + cardString.charAt(0) + cardString.charAt(1);
                suitString = "" + cardString.charAt(2);
            } else {
                // cardString looks like "AC" = Ace of Clubs or "2S" = 2 of Spades
                rankString = "" + cardString.charAt(0);
                suitString = "" + cardString.charAt(1);
            }
            rank = Rank.fromString(rankString);
            suit = Suit.fromString(suitString);
            if (rank != null && suit != null) {
                card = new Card(rank, suit);
            }
        }
        return card;
    }

    @Override
    public int compareTo(Card other) {
        int result;
        if ((result = rank.compareTo(other.getRank())) == 0) {
            result = suit.compareTo(other.getSuit());
        }
        return result;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;

        Card card = (Card) o;

        if (rank != card.rank) return false;
        if (suit != card.suit) return false;

        return true;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        // show cards in shorthand for readability in command line
        return "" + rank + suit;
    }

}
