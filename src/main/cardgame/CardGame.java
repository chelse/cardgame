package cardgame;

import cardgame.common.Game;
import cardgame.common.Player;
import cardgame.poker.FiveCardDraw;

import java.util.List;

/**
 * CardGame is the entry point for card games. Currently, users can only play FiveCardDraw.
 */
public class CardGame {

    public static final int VALID_ARG_LENGTH = 2;

    public static void main(String args[]) {

        if (!validateArgs(args)) {
            return;
        }

        Game game = new FiveCardDraw();
        for (String arg : args) {
            if (game.addPlayer(arg) == null) {
                System.out.println("Player with the name " + arg + " is already playing.");
            }
        }

        if (!game.beforeRound()) {
            // if game did not end prematurely, play round
            if (!game.round()) {

                // if game did not end prematurely, get final scores
                List<Player> players = game.afterRound();

                // winning player is the highest ranking player;
                // players are sorted lowest rank first
                int playerNum = players.size() - 1;
                System.out.println("\nWinner: " + players.get(playerNum).getName());

                System.out.println("\nRanking:");
                while (playerNum >= 0) {
                    System.out.println(players.get(playerNum));
                    playerNum--;
                }
            }
        }
    }

    /**
     * Check for valid arg length, print usage if incorrect.
     * @param args command line arguments
     * @return whether the arguments are valid
     */
    public static Boolean validateArgs(String args[]) {
        Boolean isValidArgs = true;
        if (args.length < VALID_ARG_LENGTH) {
            System.out.println("cardgame.exe <player name(s)>");
            isValidArgs = false;
        }
        return isValidArgs;

    }

}
