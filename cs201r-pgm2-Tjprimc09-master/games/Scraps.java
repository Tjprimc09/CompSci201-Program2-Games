package games;

import java.util.Random;
import java.util.Scanner;

public class Scraps {

    // PRE:  accepts scanner from caller (your main/menu)
    // POST: runs Scraps in rounds, asks "Play Again" once per round
    public static void PlayScraps(String[] args, Scanner input) {
        boolean playing = true;

        while (playing) {
            play(input);                 // play one full "session" (until user stops rolling or runs out of money)
            playing = playAgain(input);  // ask once per session
        }

        System.out.println("Thanks for playing!");
    }

    // Scraps Game Methods
    // PRE:  accepts scanner
    // POST: user starts with $50, can place bets and roll; wallet updates per roll
    //       asks "Roll Again" exactly once per roll
    //       stops when user chooses to stop rolling or runs out of money
    public static void play(Scanner input) {
        System.out.println("\nWELCOME TO SCRAPS!!\n");

        double netWorth = 50.00;     // starting money
        Random random = new Random(); // random object to pass to rollDice/evaluateRoll

        while (netWorth > 0) {

            System.out.println("\nYou have $" + netWorth);

            // Read bet safely (avoids nextDouble + nextLine issues)
            double bet = readBet(input, netWorth);

            // Roll once, evaluate once, pay out once
            int[] dice = rollDice(random);
            boolean won = evaluateRoll(dice, random);

            if (won) {
                netWorth += bet;
                System.out.println("\nYou won $" + bet);
            } else {
                netWorth -= bet;
                System.out.println("\nYou lost $" + bet);
            }

            // Stop immediately if user is out of money
            if (netWorth <= 0) {
                break;
            }

            // Ask exactly once per roll
            if (!rollAgain(input)) {
                return; // exit this session; PlayScraps will ask "Play Again" once
            }
        }

        System.out.println("\nSorry, you're out of money! Better luck next time!");
    }

    // Helper method
    // PRE:  accept the initial dice roll value and random object
    // POST: evaluate the roll and return win/loss as boolean
    public static boolean evaluateRoll(int[] dice, Random random) {

        int sum = dice[0] + dice[1] + dice[2];

        switch (sum) {
            case 8, 20, 23, 24 -> {
                System.out.println("\nYou lose!");
                return false;
            }
            case 9, 10, 14 -> {
                System.out.println("\nYou win!");
                return true;
            }
            default -> {
                // Any 8 on the dice: win
                if (dice[0] == 8 || dice[1] == 8 || dice[2] == 8) {
                    System.out.println("\nYou win!");
                    return true;
                }

                // Any 1 on the dice: lose
                if (dice[0] == 1 || dice[1] == 1 || dice[2] == 1) {
                    System.out.println("\nYou lose!");
                    return false;
                }

                // Point phase: point stays constant until matched (win) or a losing condition hits
                int point = sum;

                while (true) {
                    int[] newDice = rollDice(random);
                    int newSum = newDice[0] + newDice[1] + newDice[2];

                    // Losing conditions during point phase (your existing rule)
                    if (newSum == 15 || newDice[0] == 8 || newDice[1] == 8 || newDice[2] == 8) {
                        System.out.println("\nYou lose!");
                        return false;
                    }

                    // Win if you hit the original point again
                    if (newSum == point) {
                        System.out.println("\nYou win!");
                        return true;
                    }
                }
            }
        }
    }

    // Helper method
    // PRE:  accept the scanner object input
    // POST: prompt the user to roll again & return true/false
    // FIX:  trims, accepts lowercase, avoids empty reads
    public static boolean rollAgain(Scanner input) {
        while (true) {
            System.out.print("\nRoll Again (Y/N)? ");
            String again = input.nextLine().trim().toUpperCase();

            if (!again.equals("Y") && !again.equals("N")) {
                System.out.println("Invalid input, please enter Y or N");
                continue;
            }

            return again.equals("Y");
        }
    }

    // Helper method
    // PRE:  accept the random object rand
    // POST: get 3 random values 1-8, print them, return the dice array
    private static int[] rollDice(Random rand) {
        int die1 = rand.nextInt(1, 9);
        int die2 = rand.nextInt(1, 9);
        int die3 = rand.nextInt(1, 9);

        int[] dice = new int[] { die1, die2, die3 };

        System.out.println("You rolled " + die1 + ", " + die2 + ", and " + die3
                + " for a total of " + (die1 + die2 + die3));

        return dice;
    }

    // Helper method
    // PRE:  accepts scanner input
    // POST: asks the user if they want to play again and returns true if they do, false otherwise
    // FIX:  trims, accepts uppercase/lowercase, loops (no recursion)
    public static boolean playAgain(Scanner input) {
        while (true) {
            System.out.print("Play Again (Y/N)?: ");
            String again = input.nextLine().trim().toUpperCase();

            if (!again.equals("Y") && !again.equals("N")) {
                System.out.println("Invalid input, please enter Y or N");
                continue;
            }

            return again.equals("Y");
        }
    }

    // Helper method
    // PRE:  input is Scanner, netWorth is current wallet
    // POST: returns a valid bet in [0, netWorth]
    // FIX:  avoids nextDouble/nextLine newline bug and handles bad input safely
    private static double readBet(Scanner input, double netWorth) {
        while (true) {
            System.out.print("\nEnter your bet amount: $");
            String line = input.nextLine().trim();

            double bet;
            try {
                bet = Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid bet. Enter a number.");
                continue;
            }

            if (bet <= 0) {
                System.out.println("Bet must be greater than 0.");
                continue;
            }

            if (bet > netWorth) {
                System.out.println("\nYou cannot bet more than you have!");
                continue;
            }

            return bet;
        }
    }
}