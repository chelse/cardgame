package cardgame.common;

import junit.framework.TestCase;

public class TestCard extends TestCase {
    
    private String[] diamonds = {"2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "10D", "JD", "QD", "KD", "AD"};
    private String[] clubs = {"2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "10C", "JC", "QC", "KC", "AC"};
    private String[] hearts = {"2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "10H", "JH", "QH", "KH", "AH"};
    private String[] spades = {"2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "10S", "JS", "QS", "KS", "AS"};    

    public void testCompareTo_sameSuitLowerRank() throws Throwable {
        Card card1 = new Card(Rank.TWO, Suit.CLUBS);
        Card card2 = new Card(Rank.THREE, Suit.CLUBS);

        assertEquals("Card1 should be less than card2", -1, card1.compareTo(card2));
    }

    public void testCompareTo_sameSuitHigherRank() throws Throwable {
        Card card1 = new Card(Rank.THREE, Suit.CLUBS);
        Card card2 = new Card(Rank.TWO, Suit.CLUBS);

        assertEquals("Card1 should be greater than card2", 1, card1.compareTo(card2));
    }

    public void testCompareTo_sameSuitSameRank() throws Throwable {
        Card card1 = new Card(Rank.THREE, Suit.CLUBS);
        Card card2 = new Card(Rank.THREE, Suit.CLUBS);

        assertEquals("Card1 should be same as card2", 0, card1.compareTo(card2));
    }

    public void testCompareTo_diffSuitSameRank() throws Throwable {
        Card card1 = new Card(Rank.THREE, Suit.CLUBS);
        Card card2 = new Card(Rank.THREE, Suit.DIAMONDS);

        assertTrue("Card1 should not be same as card2", card1.compareTo(card2) != 0);
    }

    public void testCompareTo_jackToQueen() throws Throwable {
        Card card1 = new Card(Rank.JACK, Suit.SPADES);
        Card card2 = new Card(Rank.QUEEN, Suit.DIAMONDS);

        assertEquals("Card1 should be less than card2", -1, card1.compareTo(card2));
    }

    public void testCompareTo_kingToQueen() throws Throwable {
        Card card1 = new Card(Rank.KING, Suit.HEARTS);
        Card card2 = new Card(Rank.QUEEN, Suit.DIAMONDS);

        assertEquals("Card1 should be greater than card2", 1, card1.compareTo(card2));
    }

    public void testCompareTo_aceToKing() throws Throwable {
        Card card1 = new Card(Rank.ACE, Suit.DIAMONDS);
        Card card2 = new Card(Rank.KING, Suit.CLUBS);

        assertEquals("Card1 should be greater than card2", 1, card1.compareTo(card2));
    }
    
    public void testCompareTo_equals() throws Throwable {
        for (int i = 0; i < diamonds.length; i++) {
            assertEquals("Diamonds should equal diamonds", 0, Card.getInstance(diamonds[i]).compareTo(Card.getInstance(diamonds[i])));
            assertEquals("Clubs should equal clubs", 0, Card.getInstance(clubs[i]).compareTo(Card.getInstance(clubs[i])));
            assertEquals("Hearts should equal hearts", 0, Card.getInstance(hearts[i]).compareTo(Card.getInstance(hearts[i])));
            assertEquals("Spades should equal spades", 0, Card.getInstance(spades[i]).compareTo(Card.getInstance(spades[i])));
        }
    }

    public void testCompareTo_lessThan() throws Throwable {
        for (int i = 0; i < diamonds.length; i++) {
            for (int j = i + 1; j < diamonds.length; j++) {
                assertTrue("Diamonds should be less than clubs", Card.getInstance(diamonds[i]).compareTo(Card.getInstance(clubs[j])) < 0);
                assertTrue("Diamonds should be less than spades", Card.getInstance(diamonds[i]).compareTo(Card.getInstance(spades[j])) < 0);
                assertTrue("Diamonds should be less than hearts", Card.getInstance(diamonds[i]).compareTo(Card.getInstance(hearts[j])) < 0);
                assertTrue("Diamonds should be less than diamonds", Card.getInstance(diamonds[i]).compareTo(Card.getInstance(diamonds[j])) < 0);
                assertTrue("Clubs should be less than clubs", Card.getInstance(clubs[i]).compareTo(Card.getInstance(clubs[j])) < 0);
                assertTrue("Clubs should be less than spades", Card.getInstance(clubs[i]).compareTo(Card.getInstance(spades[j])) < 0);
                assertTrue("Clubs should be less than hearts", Card.getInstance(clubs[i]).compareTo(Card.getInstance(hearts[j])) < 0);
                assertTrue("Clubs should be less than diamonds", Card.getInstance(clubs[i]).compareTo(Card.getInstance(diamonds[j])) < 0);
                assertTrue("Hearts should be less than clubs", Card.getInstance(hearts[i]).compareTo(Card.getInstance(clubs[j])) < 0);
                assertTrue("Hearts should be less than spades", Card.getInstance(hearts[i]).compareTo(Card.getInstance(spades[j])) < 0);
                assertTrue("Hearts should be less than hearts", Card.getInstance(hearts[i]).compareTo(Card.getInstance(hearts[j])) < 0);
                assertTrue("Hearts should be less than diamonds", Card.getInstance(hearts[i]).compareTo(Card.getInstance(diamonds[j])) < 0);
                assertTrue("Spades should be less than clubs", Card.getInstance(spades[i]).compareTo(Card.getInstance(clubs[j])) < 0);
                assertTrue("Spades should be less than spades", Card.getInstance(spades[i]).compareTo(Card.getInstance(spades[j])) < 0);
                assertTrue("Spades should be less than hearts", Card.getInstance(spades[i]).compareTo(Card.getInstance(hearts[j])) < 0);
                assertTrue("Spades should be less than diamonds", Card.getInstance(spades[i]).compareTo(Card.getInstance(diamonds[j])) < 0);
            }
        }
    }

    public void testCompareTo_greaterThan() throws Throwable {
        for (int i = 0; i < diamonds.length; i++) {
            for (int j = i + 1; j < diamonds.length; j++) {
                assertTrue("Diamonds should be greater than clubs", Card.getInstance(diamonds[j]).compareTo(Card.getInstance(clubs[i])) > 0);
                assertTrue("Diamonds should be greater than spades", Card.getInstance(diamonds[j]).compareTo(Card.getInstance(spades[i])) > 0);
                assertTrue("Diamonds should be greater than hearts", Card.getInstance(diamonds[j]).compareTo(Card.getInstance(hearts[i])) > 0);
                assertTrue("Diamonds should be greater than diamonds", Card.getInstance(diamonds[j]).compareTo(Card.getInstance(diamonds[i])) > 0);
                assertTrue("Clubs should be greater than clubs", Card.getInstance(clubs[j]).compareTo(Card.getInstance(clubs[i])) > 0);
                assertTrue("Clubs should be greater than spades", Card.getInstance(clubs[j]).compareTo(Card.getInstance(spades[i])) > 0);
                assertTrue("Clubs should be greater than hearts", Card.getInstance(clubs[j]).compareTo(Card.getInstance(hearts[i])) > 0);
                assertTrue("Clubs should be greater than diamonds", Card.getInstance(clubs[j]).compareTo(Card.getInstance(diamonds[i])) > 0);
                assertTrue("Hearts should be greater than clubs", Card.getInstance(hearts[j]).compareTo(Card.getInstance(clubs[i])) > 0);
                assertTrue("Hearts should be greater than spades", Card.getInstance(hearts[j]).compareTo(Card.getInstance(spades[i])) > 0);
                assertTrue("Hearts should be greater than hearts", Card.getInstance(hearts[j]).compareTo(Card.getInstance(hearts[i])) > 0);
                assertTrue("Hearts should be greater than diamonds", Card.getInstance(hearts[j]).compareTo(Card.getInstance(diamonds[i])) > 0);
                assertTrue("Spades should be greater than clubs", Card.getInstance(spades[j]).compareTo(Card.getInstance(clubs[i])) > 0);
                assertTrue("Spades should be greater than spades", Card.getInstance(spades[j]).compareTo(Card.getInstance(spades[i])) > 0);
                assertTrue("Spades should be greater than hearts", Card.getInstance(spades[j]).compareTo(Card.getInstance(hearts[i])) > 0);
                assertTrue("Spades should be greater than diamonds", Card.getInstance(spades[j]).compareTo(Card.getInstance(diamonds[i])) > 0);
            }
        }
    }
}