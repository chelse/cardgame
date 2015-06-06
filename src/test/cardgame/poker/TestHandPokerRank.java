package cardgame.poker;

import cardgame.common.Card;
import cardgame.common.Rank;
import cardgame.common.Suit;
import junit.framework.TestCase;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TestHandPokerRank extends TestCase {

    // because test cases are not drawing from a deck, some of the test scenarios are not realistic
    // for example, there may be duplicate cards, which does not happen in real life
    
    public static final int FULL_HAND = 5;

    private Random rand = new Random();
    private static final int NUM_SUITS = EnumSet.allOf(Suit.class).size();

    private Suit getRandomSuit() {
        int randomNum = rand.nextInt(NUM_SUITS);
        Suit suit = null;
        switch (randomNum) {
            case 0:
                suit = Suit.CLUBS;
                break;
            case 1:
                suit = Suit.DIAMONDS;
                break;
            case 2:
                suit = Suit.HEARTS;
                break;
            case 3:
                suit = Suit.SPADES;
                break;
            default:
                break;
        }
        return suit;
    }

    private Rank getRandomRank() {
        int randomNum = rand.nextInt(Rank.MAX_VALUE - Rank.MIN_VALUE + 1) + Rank.MIN_VALUE;
        return Rank.fromValue(randomNum);
    }

    public void testGetPokerRank_straightFlush() throws Throwable {
        // generate consecutive ranks and same suit
        Suit randomSuit = getRandomSuit();
        for (int i = Rank.MIN_VALUE; i <= Rank.MAX_VALUE - (FULL_HAND - 1); i++) {
            PokerHand hand = new PokerHand(FULL_HAND);
            hand.addCard(new Card(Rank.fromValue(i), randomSuit));
            hand.addCard(new Card(Rank.fromValue(i + 1), randomSuit));
            hand.addCard(new Card(Rank.fromValue(i + 2), randomSuit));
            hand.addCard(new Card(Rank.fromValue(i + 3), randomSuit));
            hand.addCard(new Card(Rank.fromValue(i + 4), randomSuit));
            assertEquals("Straight flush not recognized: " + hand, PokerRank.STRAIGHT_FLUSH, hand.getPokerRank());
        }
    }

    public void testGetPokerRank_straightFlushStartingWithAce() throws Throwable {
        // generate consecutive ranks and same suit
        Suit randomSuit = getRandomSuit();
        int i = Rank.MIN_VALUE;
        PokerHand hand = new PokerHand(FULL_HAND);
        hand.addCard(new Card(Rank.ACE, randomSuit));
        hand.addCard(new Card(Rank.fromValue(i + 3), randomSuit));
        hand.addCard(new Card(Rank.fromValue(i + 2), randomSuit));
        hand.addCard(new Card(Rank.fromValue(i), randomSuit));
        hand.addCard(new Card(Rank.fromValue(i + 1), randomSuit));
        assertEquals("Straight flush not recognized: " + hand, PokerRank.STRAIGHT_FLUSH, hand.getPokerRank());
    }

    public void testGetPokerRank_fourOfAKind() throws Throwable {
        // 4 same rank; random suits
        Rank sameRank = getRandomRank();
        Set<Integer> randomOrdering = new HashSet<Integer>();
        do {
            int randomNum = rand.nextInt(5);
            if (!randomOrdering.contains(randomNum)) {
               randomOrdering.add(randomNum);
            }
        } while (randomOrdering.size() < 4); // 4 for 4 of a kind
        PokerHand hand = new PokerHand(FULL_HAND);
        for (int i = 0; i < FULL_HAND; ) {
            if (randomOrdering.contains(i)) {
                hand.addCard(new Card(sameRank, getRandomSuit()));
                i++;
            } else {
                Rank randomRank = getRandomRank();
                if (randomRank != sameRank) {
                    hand.addCard(new Card(randomRank, getRandomSuit()));
                    i++;
                }
            }
        }
        assertEquals("Four of a kind not recognized: " + hand, PokerRank.FOUR_OF_A_KIND, hand.getPokerRank());
    }

    public void testGetPokerRank_fullHouse() throws Throwable {
        // 3 same rank & 2 same rank; random suits

        Rank sameRank1 = getRandomRank();
        Set<Integer> randomOrdering1 = new HashSet<Integer>();
        do {
            int randomNum = rand.nextInt(5);
            if (!randomOrdering1.contains(randomNum)) {
               randomOrdering1.add(randomNum);
            }
        } while (randomOrdering1.size() < 3); // 3 for 3 same rank

        Rank sameRank2;
        do {
            sameRank2 = getRandomRank();
        } while (sameRank1 == sameRank2);

        Set<Integer> randomOrdering2 = new HashSet<Integer>();
        do {
            int randomNum = rand.nextInt(5);
            if (!randomOrdering2.contains(randomNum) && !randomOrdering1.contains(randomNum)) {
               randomOrdering2.add(randomNum);
            }
        } while (randomOrdering2.size() < 2); // 2 for 2 same rank

        PokerHand hand = new PokerHand(FULL_HAND);
        for (int i = 0; i < FULL_HAND; ) {
            if (randomOrdering1.contains(i)) {
                hand.addCard(new Card(sameRank1, getRandomSuit()));
                i++;
            } else {
                if (randomOrdering2.contains(i)) {
                    hand.addCard(new Card(sameRank2, getRandomSuit()));
                    i++;
                }
            }
        }
        assertEquals("Full house not recognized: " + hand, PokerRank.FULL_HOUSE, hand.getPokerRank());
    }

    public void testGetPokerRank_flush() throws Throwable {
        Suit randomSuit = getRandomSuit();
        PokerHand hand = new PokerHand(FULL_HAND);
        for (int i = 0; i < FULL_HAND; i++) {
            hand.addCard(new Card(getRandomRank(), randomSuit));
        }
        // sometimes we will roll a higher ranked poker hand
        if (PokerRank.FLUSH != hand.getPokerRank()) {
            if (PokerRank.FLUSH.compareTo(hand.getPokerRank()) > 0) {
                // a lower rank was obtained through random rank generation
                fail("Flush or higher rank not recognized: " + hand + " rank: " + hand.getPokerRank());
            }
        }
    }

    public void testGetPokerRank_straight() throws Throwable {
        // generate consecutive ranks and different suits
        for (int i = Rank.MIN_VALUE; i <= Rank.MAX_VALUE - (FULL_HAND - 1); i++) {
            PokerHand hand = new PokerHand(FULL_HAND);
            hand.addCard(new Card(Rank.fromValue(i), getRandomSuit()));
            hand.addCard(new Card(Rank.fromValue(i + 1), getRandomSuit()));
            hand.addCard(new Card(Rank.fromValue(i + 2), getRandomSuit()));
            hand.addCard(new Card(Rank.fromValue(i + 3), getRandomSuit()));
            hand.addCard(new Card(Rank.fromValue(i + 4), getRandomSuit()));
            if (PokerRank.STRAIGHT != hand.getPokerRank() && PokerRank.STRAIGHT_FLUSH != hand.getPokerRank()) {
                fail("Straight (or straight flush) rank not recognized: " + hand + " rank: " + hand.getPokerRank());
            }
        }
    }

    public void testGetPokerRank_threeOfAKind() throws Throwable {
        // 3 same rank; random suits
        Rank sameRank = getRandomRank();
        Set<Integer> randomOrdering = new HashSet<Integer>();
        do {
            int randomNum = rand.nextInt(5);
            if (!randomOrdering.contains(randomNum)) {
               randomOrdering.add(randomNum);
            }
        } while (randomOrdering.size() < 3); // 3 for 3 of a kind
        PokerHand hand = new PokerHand(FULL_HAND);
        for (int i = 0; i < FULL_HAND; ) {
            if (randomOrdering.contains(i)) {
                hand.addCard(new Card(sameRank, getRandomSuit()));
                i++;
            } else {
                Rank randomRank = getRandomRank();
                if (randomRank != sameRank) {
                    hand.addCard(new Card(randomRank, getRandomSuit()));
                    i++;
                }
            }
        }
        // sometimes we will roll a higher ranked poker hand
        if (!(PokerRank.THREE_OF_A_KIND.equals(hand.getPokerRank()) || PokerRank.FULL_HOUSE.equals(hand.getPokerRank()))) {
            fail("Three of a kind (or full house) not recognized: " + hand + " rank: " + hand.getPokerRank());
        }
    }

    public void testGetPokerRank_twoPairs() throws Throwable {
        // 2 same rank * 2; random suits

        Rank sameRank1 = getRandomRank();
        Set<Integer> randomOrdering1 = new HashSet<Integer>();
        do {
            int randomNum = rand.nextInt(5);
            if (!randomOrdering1.contains(randomNum)) {
               randomOrdering1.add(randomNum);
            }
        } while (randomOrdering1.size() < 2); // 2 for first pair

        Rank sameRank2;
        do {
            sameRank2 = getRandomRank();
        } while (sameRank1 == sameRank2);

        Set<Integer> randomOrdering2 = new HashSet<Integer>();
        do {
            int randomNum = rand.nextInt(5);
            if (!randomOrdering2.contains(randomNum) && !randomOrdering1.contains(randomNum)) {
               randomOrdering2.add(randomNum);
            }
        } while (randomOrdering2.size() < 2); // 2 for second pair

        PokerHand hand = new PokerHand(FULL_HAND);
        for (int i = 0; i < FULL_HAND; ) {
            if (randomOrdering1.contains(i)) {
                hand.addCard(new Card(sameRank1, getRandomSuit()));
                i++;
            } else {
                if (randomOrdering2.contains(i)) {
                    hand.addCard(new Card(sameRank2, getRandomSuit()));
                    i++;
                } else {
                    Rank randomRank = getRandomRank();
                    if ((randomRank != sameRank1) && (randomRank != sameRank2)) {
                        hand.addCard(new Card(randomRank, getRandomSuit()));
                        i++;
                    }
                }
            }
        }
        if (!(PokerRank.TWO_PAIRS.equals(hand.getPokerRank()) || PokerRank.FULL_HOUSE.equals(hand.getPokerRank()))) {
            fail("Two pairs (or full house) not recognized: " + hand + " rank: " + hand.getPokerRank());
        }
    }

    public void testGetPokerRank_onePair() throws Throwable {
        // 2 same rank; random suits
        Rank sameRank = getRandomRank();
        Set<Integer> randomOrdering = new HashSet<Integer>();
        do {
            int randomNum = rand.nextInt(5);
            if (!randomOrdering.contains(randomNum)) {
               randomOrdering.add(randomNum);
            }
        } while (randomOrdering.size() < 2); // 2 for one pair
        PokerHand hand = new PokerHand(FULL_HAND);
        for (int i = 0; i < FULL_HAND; ) {
            if (randomOrdering.contains(i)) {
                hand.addCard(new Card(sameRank, getRandomSuit()));
                i++;
            } else {
                Rank randomRank = getRandomRank();
                if (randomRank != sameRank) {
                    hand.addCard(new Card(randomRank, getRandomSuit()));
                    i++;
                }
            }
        }
        if (!(PokerRank.ONE_PAIR.equals(hand.getPokerRank()) || PokerRank.TWO_PAIRS.equals(hand.getPokerRank()) || PokerRank.FULL_HOUSE.equals(hand.getPokerRank()))) {
            fail("One pair (or two pairs or full house) not recognized: " + hand + " rank: " + hand.getPokerRank());
        }
    }

    public void testGetPokerRank_noRank() throws Throwable {
        PokerHand hand = new PokerHand(FULL_HAND);
        for (int i = 0; i < FULL_HAND; i++) {
            hand.addCard(new Card(getRandomRank(), getRandomSuit()));
        }
        // sometimes we will roll a higher ranked poker hand
        if (PokerRank.NO_RANK != hand.getPokerRank()) {
            if (PokerRank.NO_RANK.compareTo(hand.getPokerRank()) > 0) {
                // a lower rank was obtained through random rank generation
                fail("No rank or higher rank not recognized: " + hand + " rank: " + hand.getPokerRank());
            }
        }
    }

    public void testGetPokerRank_invalid() throws Throwable {
        PokerHand hand = new PokerHand(FULL_HAND);
        assertNull("Poker hand should not be returned for invalid hand", hand.getPokerRank());
    }

}