package cardgame.common;

/**
 * Player implements a player in a card game.
 */
public class Player {

    private String name;
    protected Hand hand;

    /**
     * Construct a player with given name.
     * @param name name of player
     */
    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    @Override
    public String toString() {
        String playerInfo = "Player: " + name;
        playerInfo = playerInfo + "  Hand: " + hand;
        return playerInfo;
    }

}
