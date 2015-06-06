package cardgame.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Suit defines the suits of a playing card.
 */
public enum Suit {

    DIAMONDS("D"),
    CLUBS("C"),
    HEARTS("H"),
    SPADES("S");

    private final String initial;

    private static Map<String, Suit> initialEnumMap = new HashMap<String, Suit>();

    static {
        for (Suit suit : Suit.values()) {
            initialEnumMap.put(suit.initial.toUpperCase(), suit);
        }
    }

    private Suit(String initial) {
        this.initial = initial;
    }

    /**
     * Return suit based on the string representation.
     * @param suitInitial initial of the suit
     * @return suit corresponding to the initial
     */
    public static Suit fromString(String suitInitial) {
        Suit suit = null;
        if (suitInitial != null) {
            if (initialEnumMap.containsKey(suitInitial.toUpperCase())) {
                suit = initialEnumMap.get(suitInitial.toUpperCase());
            }
        }
        return suit;
    }

    @Override
    public String toString() {
        return initial;
    }

}
