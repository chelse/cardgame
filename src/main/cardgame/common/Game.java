package cardgame.common;

import java.util.*;

public abstract class Game {

    private Deck deck;
    private List<Player> players;
    private Set<String> playerNames;

    /**
     * Construct a playing card game, initializing the deck.
     */
    public Game() {
        players = new ArrayList<Player>();
        playerNames = new HashSet<String>();
        deck = new Deck(true);
    }

    /**
     * Add a player to the game if the player is not already playing.
     * @param name name of player
     * @return newly added player if successful; otherwise, null
     */
    public Player addPlayer(String name) {
        Player player = null;
        if (!playerNames.contains(name)) {
            player = new Player(name);
            players.add(player);
            playerNames.add(name);
        }
        return player;
    }

    /**
     * Perform actions before a round. Subclasses should provide
     * game-specific implementation.
     * @return whether the game has ended.
     */
    public abstract Boolean beforeRound();

    /**
     * Perform actions in a round. Subclasses should provide
     * game-specific implementation.
     * @return whether the game has ended.
     */
    public abstract Boolean round();

    /**
     * Perform actions after a round. In the abstract implementation,
     * score the round. Subclasses should provide game-specific
     * implementation.
     * @return the list of players, sorted by rank, lowest rank first
     */
    public List<Player> afterRound() {
        return scoreRound();
    }

    /**
     * Score the round by sorting players, from lowest to highest ranking.
     * Subclasses should provide game-specific implementation.
     * @return the list of players, sorted by rank, lowest rank first
     */
    protected abstract List<Player> scoreRound();

    /**
     * Return the deck associated with the game.
     * @return the deck associated with the game.
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Return the list of players playing the game.
     * @return the list of players playing the game.
     */
    protected List<Player> getPlayers() {
        return players;
    }

    /**
     * Return the set of player names.
     * @return the set of player names.
     */
    protected Set<String> getPlayerNames() {
        return playerNames;
    }
}
