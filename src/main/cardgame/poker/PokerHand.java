package cardgame.poker;

import cardgame.common.Card;
import cardgame.common.Hand;

import java.util.ArrayList;
import java.util.List;

/**
 * Hand implements a hand of cards.
 */
public class PokerHand extends Hand<PokerHand> {

    /**
     * Construct a poker hand.
     * @param numCardsInFullHand the number of cards in a full hand
     */
    public PokerHand(int numCardsInFullHand) {
        setNumCardsInFullHand(numCardsInFullHand);
    }

    /**
     * Helper method for compareTo that breaks the tie between two one-pair hands.
     * Compare rank of the pair. If tie, then compare three single cards, from
     * highest rank to lowest rank.
     *
     * Hand is presumed to be sorted, lowest to highest.
     * @param other the other hand to compare to
     * @return 0 if equal, > 0 if greater than other, < 0 if less than other
     */
    private int compareOnePair(PokerHand other) {
        // find the pair in each hand and compare the rank of the pairs
        Card pairCard = findRankPairCardDescending(getNumCardsInFullHand() - 1);
        Card otherPairCard = other.findRankPairCardDescending(other.getNumCardsInFullHand() - 1);
        int result = pairCard.getRank().compareTo(otherPairCard.getRank());

        if (result == 0) {
            // if tie, then tie-break by comparing single cards, skipping over
            // the pairs
            List<Card> skipPairs = new ArrayList<Card>();
            skipPairs.add(pairCard);
            List<Card> skipOtherPairs = new ArrayList<Card>();
            skipOtherPairs.add(otherPairCard);
            result = compareSingleCards(other, skipPairs, skipOtherPairs);
        }
        return result;
    }

    /**
     * Helper method for compareTo that breaks the tie between two two-pair hands.
     * For two pairs, compare the rank of the highest pair, followed by the rank of
     * the second highest pair, then then the rank of the single card.
     *
     * Hand is presumed to be sorted, lowest to highest.
     * @param other the other hand to compare to
     * @return 0 if equal, > 0 if greater than other, < 0 if less than other
     */
    private int compareTwoPair(PokerHand other) {
        // find the pair in each hand and compare the rank of the pairs
        Card firstPairCard = findRankPairCardDescending(getNumCardsInFullHand() - 1);
        Card otherFirstPairCard = other.findRankPairCardDescending(other.getNumCardsInFullHand() - 1);
        int result = firstPairCard.getRank().compareTo(otherFirstPairCard.getRank());

        if (result == 0) {

            // find the second pair in each hand and compare the rank of the pairs
            // -2 is for skipping past the previous pair
            Card secondPairCard = findRankPairCardDescending(getCards().indexOf(firstPairCard) - 2);
            Card otherSecondPairCard = other.findRankPairCardDescending(other.getCards().indexOf(otherFirstPairCard) - 2);
            result = secondPairCard.getRank().compareTo(otherSecondPairCard.getRank());

            if (result == 0) {
                // if tie, then tie-break by comparing single cards, skipping over
                // the pairs
                List<Card> skipPairs = new ArrayList<Card>();
                skipPairs.add(firstPairCard);
                skipPairs.add(secondPairCard);
                List<Card> skipOtherPairs = new ArrayList<Card>();
                skipOtherPairs.add(otherFirstPairCard);
                skipOtherPairs.add(otherSecondPairCard);
                result = compareSingleCards(other, skipPairs, skipOtherPairs);
            }
        }
        return result;
    }

    /**
     * Helper method for compareTo that breaks the tie between flush and no rank hands.
     * For flushes and no rank, compare rank from highest card to lowest card.
     *
     * Hand is presumed to be sorted, lowest to highest.
     * @param other other hand to compare to
     * @return 0 if equal, > 0 if greater than other, < 0 if less than other
     */
    private int compareSingleCards(PokerHand other) {
        // nothing to skip so create empty lists
        List<Card> skipPairCards = new ArrayList<Card>();
        List<Card> skipOtherPairCards = new ArrayList<Card>();
        return compareSingleCards(other, skipPairCards, skipOtherPairCards);
    }

    /**
     * Helper method for compareTo that breaks the tie using single cards. In the case
     * of one-pair and two-pair, the pairs can be skipped by providing the card in the
     * higher position of the pair as parameters.
     *
     * Hand is presumed to be sorted, lowest to highest.
     * @param other other hand to compare to
     * @param skipPairCards pairs to skip, specified by card in the higher position
     * @param skipOtherPairCards pairs to skip in other hand, specified by card in
     * the higher position
     * @return 0 if equal, > 0 if greater than other, < 0 if less than other
     */
    private int compareSingleCards(PokerHand other, List<Card> skipPairCards, List<Card> skipOtherPairCards) {
        int result = 0;
        Integer cardIndex = getNumCardsInFullHand() - 1;
        Integer otherCardIndex = getNumCardsInFullHand() - 1;
        while (cardIndex >= 0 && otherCardIndex >= 0) {

            Card card = getCard(cardIndex);
            Card otherCard = other.getCard(cardIndex);

            if (skipPairCards.contains(card)) {
                // skip the pair
                cardIndex -= 2;
                if (cardIndex < 0) {
                    // got to the end, which means we must have tied
                    break;
                }
                card = getCard(cardIndex);
            }

            if (skipOtherPairCards.contains(otherCard)) {
                // skip the pair
                otherCardIndex -= 2;
                if (otherCardIndex < 0) {
                    // got to the end, which means we must have tied
                    break;
                }
                otherCard = getCard(otherCardIndex);
            }

            // compare ranks of cards from highest to lowest
            result = card.getRank().compareTo(otherCard.getRank());

            if (result != 0) {
                // a tie-breaker was found
                break;
            }

            // the two cards had the same rank; check next card
            cardIndex--;
            otherCardIndex--;
        }

        return result;
    }

    /**
     * Helper method for compareTo that breaks the tie by comparing only the
     * middle card.
     * For three-of-a-kind, compare rank of the three of a kind group because
     * in a deck no two sets of three-of-a-kind can match so one set must have
     * a higher rank than the other.
     *
     * Note this can also be used to tie-break full-house and four-of-a-kind
     * for the reasons stated above. It can also be used to tie-break straights
     * because we only need to compare one card between two hands in straight
     * flush or straight hands.
     * Hand is presumed to be sorted, lowest to highest.
     * @param other the other hand to compare to
     * @return 0 if equal, > 0 if greater than other, < 0 if less than other
     */
    private int compareMiddleCard(PokerHand other) {
        final int middlePosition = getNumCardsInFullHand() / 2;
        return getCard(middlePosition).getRank().compareTo(other.getCard(middlePosition).getRank());
    }

    /**
     * Return the poker rank of the hand.
     * @return the poker rank of the hand if the hand is full;
     * otherwise, null
     */
    public PokerRank getPokerRank() {
        if (!isFullHand()) {
            // unexpected; nothing to do
            return null;
        }

        PokerRank pokerRank = PokerRank.NO_RANK;
        boolean isConsecutiveRanks = getIsConsecutiveRanks();
        boolean isSameSuits = getIsSameSuit();

        // same rank comparison makes 10 comparisons for cards a through e as follows:
        // a-b a-c a-d a-e
        //     b-c b-d b-e
        //         c-d c-e
        //             d-e
        int numSameRank = getNumSameRankInTwoWayComparisons();

        // assign ranks in rank order, highest first
        if (isConsecutiveRanks && isSameSuits) {
            pokerRank = PokerRank.STRAIGHT_FLUSH;
        } else if (numSameRank == 6) {
            // in 10-way comparison, 6 would match if 4 cards have the same rank
            pokerRank = PokerRank.FOUR_OF_A_KIND;
        } else if (numSameRank == 4) {
            // in 10-way comparison,
            // 3 would match if 3 cards have the same rank
            // plus another 1 would match if 2 other cards have the same rank
            pokerRank = PokerRank.FULL_HOUSE;
        } else if (isSameSuits) {
            pokerRank = PokerRank.FLUSH;
        } else if (isConsecutiveRanks) {
            pokerRank = PokerRank.STRAIGHT;
        } else if (numSameRank == 3) {
            // in 10-way comparison, 3 would match if 3 cards have the same rank
            pokerRank = PokerRank.THREE_OF_A_KIND;
        } else if (numSameRank == 2) {
            // in 10-way comparison,
            // 1 would match if 2 cards have the same rank
            // plus another 1 would match if 2 other cards have the same rank
            pokerRank = PokerRank.TWO_PAIRS;
        } else if (numSameRank == 1) {
            // in 10-way comparison, 1 would match if 2 cards have the same rank
            pokerRank = PokerRank.ONE_PAIR;
        }
        return pokerRank;
    }

    @Override
    public int compareTo(PokerHand other) {
        PokerRank pokerRank = getPokerRank();
        PokerRank otherPokerRank = other.getPokerRank();
        if (pokerRank == null) {
            return -1; // if we don't have a full hand, just return less than
        } else if (otherPokerRank == null) {
            return 1;  // if other doesn't have a full hand, just return more than
        }
        int result = pokerRank.compareTo(otherPokerRank);
        if (result == 0) {
            // if hands are same poker rank, do further comparison by card ranks, according to poker rank

            if (pokerRank == PokerRank.STRAIGHT_FLUSH || pokerRank == PokerRank.STRAIGHT) {
                result = compareMiddleCard(other);

            } else if (pokerRank == PokerRank.FOUR_OF_A_KIND) {
                result = compareMiddleCard(other);

            } else if (pokerRank == PokerRank.FULL_HOUSE) {
                result = compareMiddleCard(other);

            } else if (pokerRank == PokerRank.FLUSH || pokerRank == PokerRank.NO_RANK) {
                result = compareSingleCards(other);

            } else if (pokerRank == PokerRank.THREE_OF_A_KIND) {
                result = compareMiddleCard(other);

            } else if (pokerRank == PokerRank.TWO_PAIRS) {
                result = compareTwoPair(other);

            } else if (pokerRank == PokerRank.ONE_PAIR) {
                result = compareOnePair(other);
            }

        }
        return result;
    }

    @Override
    public String toString() {
        String hand = super.toString();
        if (isFullHand()) {
            hand = hand + " " + getPokerRank();
        }
        return hand;
    }
}
