package cardgame.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Rank defines the rank of a playing card, in increasing rank order.
 */
public enum Rank {
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "10"),
    JACK(11, "J"),
    QUEEN(12, "Q"),
    KING(13, "K"),
    ACE(14, "A");
    // adjust MIN_VALUE and MAX_VALUE when adding ranks

    // used for testing purposes
    public static int MIN_VALUE = 2;  // inclusive
    public static int MAX_VALUE = 14; // inclusive

    private final int value;
    private final String name;

    private static Map<String, Rank> nameEnumMap = new HashMap<String, Rank>();
    private static Map<Integer, Rank> valueEnumMap = new HashMap<Integer, Rank>();

    static {
        for (Rank rank : Rank.values()) {
            nameEnumMap.put(rank.name.toUpperCase(), rank);
            valueEnumMap.put(rank.value, rank);
        }
    }

    private Rank(int value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * Return the next rank, ascending.
     * @return the next rank
     */
    public Rank getNext() {
        if (this == ACE) {
            return TWO;
        } else {
            return Rank.values()[ordinal() + 1];
        }
    }

    /**
     * Return rank based on value of the rank.
     * @param value value of the rank
     * @return rank for the given value
     */
    public static Rank fromValue(Integer value) {
        Rank rank = null;
        if (value != null) {
            if (valueEnumMap.containsKey(value)) {
                rank = valueEnumMap.get(value);
            }
        }
        return rank;
    }

    /**
     * Return rank based on the string representation.
     * @param rankString string representation of the rank
     * @return rank corresponding to the string representation
     */
    public static Rank fromString(String rankString) {
        Rank rank = null;
        if (rankString != null) {
            if (nameEnumMap.containsKey(rankString.toUpperCase())) {
                rank = nameEnumMap.get(rankString.toUpperCase());
            }
        }
        return rank;
    }

    @Override
    public String toString() {
        return name;
    }

}

