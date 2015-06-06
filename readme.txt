CardGame

CardGame implements the Five Card Draw poker game using a command-line 
interface.

The zip file includes a build script, build.sh, for compiling the program. 
Note it is assumed that a Java compiler and VM is available in the path:

> ./build.sh
> java -cp bin cardgame.CardGame

To invoke the program, enter <program_name> <player_name(s)> at the 
command prompt. The program takes the user through setup and game play. 
An example of a program run follows:

> java -cp bin cardgame.CardGame Alice Brian Cathy

  Dealer: Alice
  
  Before Turn:
  Player: Brian  Hand: 9H 10S JC QS KC  straight
  How many cards do you want to discard? Enter a number between 0 and 5.
  0
  
  Before Turn:
  Player: Cathy  Hand: 4S 8C KD AD AS  one pair
  How many cards do you want to discard? Enter a number between 0 and 5.
  2
  Enter the positions (between 0 and 4) of the cards you want to discard.
  0 1
  
  Before Turn:
  Player: Alice  Hand: 2D 3S 9S JD JH  one pair
  How many cards do you want to discard? Enter a number between 0 and 5.
  3
  Enter the positions (between 0 and 4) of the cards you want to discard.
  0 1 2
  
  After turn: 
  Player: Brian  Hand: 9H 10S JC QS KC  straight
  
  After turn: 
  Player: Cathy  Hand: 2H 7S KD AD AS  one pair
  
  After turn: 
  Player: Alice  Hand: 3H 4H 4S JD JH  two pairs
  
  Winner: Brian
  
  Ranking:
  Player: Brian  Hand: 9H 10S JC QS KC  straight
  Player: Alice  Hand: 3H 4H 4S JD JH  two pairs
  Player: Cathy  Hand: 2H 7S KD AD AS  one pair

I also included some JUnit tests, which can be run with the included 
test.sh script. 

Errors: 
If at least one player name is not provided, the program will print a usage message and exit.

