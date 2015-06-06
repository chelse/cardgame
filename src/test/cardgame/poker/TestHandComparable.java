package cardgame.poker;

import cardgame.common.Card;
import cardgame.common.Rank;
import cardgame.common.Suit;
import junit.framework.TestCase;

public class TestHandComparable extends TestCase {

    private static final int FULL_HAND = 5;

    public void testCompareTo_onePairTieBreakLose() throws Throwable {
        PokerHand firstHand = new PokerHand(FULL_HAND);
        firstHand.addCard(new Card(Rank.TWO, Suit.HEARTS));
        firstHand.addCard(new Card(Rank.KING, Suit.DIAMONDS));
        firstHand.addCard(new Card(Rank.KING, Suit.CLUBS));
        firstHand.addCard(new Card(Rank.FIVE, Suit.DIAMONDS));
        firstHand.addCard(new Card(Rank.SEVEN, Suit.SPADES));

        PokerHand secondHand = new PokerHand(FULL_HAND);
        secondHand.addCard(new Card(Rank.SIX, Suit.SPADES));
        secondHand.addCard(new Card(Rank.KING, Suit.SPADES));
        secondHand.addCard(new Card(Rank.FOUR, Suit.HEARTS));
        secondHand.addCard(new Card(Rank.KING, Suit.HEARTS));
        secondHand.addCard(new Card(Rank.SEVEN, Suit.DIAMONDS));

        assertTrue("Second hand should have won", firstHand.compareTo(secondHand) < 0);
    }
    
    public void testCompareTo_onePairTieBreakWin() throws Throwable {
        PokerHand firstHand = new PokerHand(FULL_HAND);
        firstHand.addCard(new Card(Rank.EIGHT, Suit.HEARTS));
        firstHand.addCard(new Card(Rank.JACK, Suit.DIAMONDS));
        firstHand.addCard(new Card(Rank.KING, Suit.CLUBS));
        firstHand.addCard(new Card(Rank.FOUR, Suit.DIAMONDS));
        firstHand.addCard(new Card(Rank.EIGHT, Suit.SPADES));

        PokerHand secondHand = new PokerHand(FULL_HAND);
        secondHand.addCard(new Card(Rank.SIX, Suit.SPADES));
        secondHand.addCard(new Card(Rank.KING, Suit.SPADES));
        secondHand.addCard(new Card(Rank.SIX, Suit.HEARTS));
        secondHand.addCard(new Card(Rank.TEN, Suit.HEARTS));
        secondHand.addCard(new Card(Rank.SEVEN, Suit.DIAMONDS));

        assertTrue("First hand should have won", firstHand.compareTo(secondHand) > 0);
    }

    public void testCompareTo_onePairTieBreakTie() throws Throwable {
        PokerHand firstHand = new PokerHand(FULL_HAND);
        firstHand.addCard(new Card(Rank.SIX, Suit.CLUBS));
        firstHand.addCard(new Card(Rank.NINE, Suit.DIAMONDS));
        firstHand.addCard(new Card(Rank.KING, Suit.CLUBS));
        firstHand.addCard(new Card(Rank.ACE, Suit.DIAMONDS));
        firstHand.addCard(new Card(Rank.SIX, Suit.SPADES));

        PokerHand secondHand = new PokerHand(FULL_HAND);
        secondHand.addCard(new Card(Rank.ACE, Suit.SPADES));
        secondHand.addCard(new Card(Rank.KING, Suit.SPADES));
        secondHand.addCard(new Card(Rank.SIX, Suit.HEARTS));
        secondHand.addCard(new Card(Rank.NINE, Suit.HEARTS));
        secondHand.addCard(new Card(Rank.SIX, Suit.DIAMONDS));

        assertTrue("Neither hand wins", firstHand.compareTo(secondHand) == 0);
    }

    public void testCompareTo_twoPairsTieBreakLose() throws Throwable {
        PokerHand firstHand = new PokerHand(FULL_HAND);
        firstHand.addCard(new Card(Rank.TWO, Suit.HEARTS));
        firstHand.addCard(new Card(Rank.KING, Suit.DIAMONDS));
        firstHand.addCard(new Card(Rank.KING, Suit.CLUBS));
        firstHand.addCard(new Card(Rank.FOUR, Suit.DIAMONDS));
        firstHand.addCard(new Card(Rank.FOUR, Suit.SPADES));

        PokerHand secondHand = new PokerHand(FULL_HAND);
        secondHand.addCard(new Card(Rank.SIX, Suit.SPADES));
        secondHand.addCard(new Card(Rank.KING, Suit.SPADES));
        secondHand.addCard(new Card(Rank.SEVEN, Suit.HEARTS));
        secondHand.addCard(new Card(Rank.KING, Suit.HEARTS));
        secondHand.addCard(new Card(Rank.SEVEN, Suit.DIAMONDS));

        assertTrue("Second hand should have won", firstHand.compareTo(secondHand) < 0);
    }

    public void testCompareTo_twoPairsTieBreakWin() throws Throwable {
        PokerHand firstHand = new PokerHand(FULL_HAND);
        firstHand.addCard(new Card(Rank.SIX, Suit.HEARTS));
        firstHand.addCard(new Card(Rank.JACK, Suit.DIAMONDS));
        firstHand.addCard(new Card(Rank.KING, Suit.CLUBS));
        firstHand.addCard(new Card(Rank.JACK, Suit.CLUBS));
        firstHand.addCard(new Card(Rank.SIX, Suit.SPADES));

        PokerHand secondHand = new PokerHand(FULL_HAND);
        secondHand.addCard(new Card(Rank.SIX, Suit.DIAMONDS));
        secondHand.addCard(new Card(Rank.KING, Suit.SPADES));
        secondHand.addCard(new Card(Rank.SIX, Suit.CLUBS));
        secondHand.addCard(new Card(Rank.TEN, Suit.HEARTS));
        secondHand.addCard(new Card(Rank.TEN, Suit.DIAMONDS));

        assertTrue("First hand should have won", firstHand.compareTo(secondHand) > 0);
    }

    public void testCompareTo_twoPairsTieBreakTie() throws Throwable {
        PokerHand firstHand = new PokerHand(FULL_HAND);
        firstHand.addCard(new Card(Rank.SIX, Suit.CLUBS));
        firstHand.addCard(new Card(Rank.TEN, Suit.DIAMONDS));
        firstHand.addCard(new Card(Rank.KING, Suit.CLUBS));
        firstHand.addCard(new Card(Rank.TEN, Suit.CLUBS));
        firstHand.addCard(new Card(Rank.SIX, Suit.SPADES));

        PokerHand secondHand = new PokerHand(FULL_HAND);
        secondHand.addCard(new Card(Rank.TEN, Suit.SPADES));
        secondHand.addCard(new Card(Rank.KING, Suit.SPADES));
        secondHand.addCard(new Card(Rank.SIX, Suit.HEARTS));
        secondHand.addCard(new Card(Rank.TEN, Suit.HEARTS));
        secondHand.addCard(new Card(Rank.SIX, Suit.DIAMONDS));

        assertTrue("Neither hand wins", firstHand.compareTo(secondHand) == 0);
    }

}