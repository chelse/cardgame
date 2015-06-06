javac -g src/main/cardgame/common/Rank.java -cp bin -d bin
javac -g src/main/cardgame/common/Suit.java -cp bin -d bin
javac -g src/main/cardgame/common/Card.java -cp bin -d bin
javac -g src/main/cardgame/common/Hand.java -cp bin -d bin
javac -g src/main/cardgame/poker/PokerRank.java -cp bin -d bin
javac -g src/main/cardgame/poker/PokerHand.java -cp bin -d bin
javac -g src/test/cardgame/common/TestCard.java -cp bin:lib/junit-4.7.jar -d bin
javac -g src/test/cardgame/poker/TestHandComparable.java -cp bin:lib/junit-4.7.jar -d bin
javac -g src/test/cardgame/poker/TestHandPokerRank.java -cp bin:lib/junit-4.7.jar -d bin
java -cp bin:lib/junit-4.7.jar org.junit.runner.JUnitCore cardgame.common.TestCard
java -cp bin:lib/junit-4.7.jar org.junit.runner.JUnitCore cardgame.poker.TestHandPokerRank
java -cp bin:lib/junit-4.7.jar org.junit.runner.JUnitCore cardgame.poker.TestHandComparable

