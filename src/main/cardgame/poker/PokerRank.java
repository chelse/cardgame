package cardgame.poker;

/**
 * PokerRank defines the ranks for poker hands, in increasing rank order.
 */
public enum PokerRank {
    NO_RANK("no rank"),
    ONE_PAIR("one pair"),
    TWO_PAIRS("two pairs"),
    THREE_OF_A_KIND("three of a kind"),
    STRAIGHT("straight"),
    FLUSH("flush"),
    FULL_HOUSE("full house"),
    FOUR_OF_A_KIND("four of a kind"),
    STRAIGHT_FLUSH("straight flush");

    private final String name;

    private PokerRank(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
