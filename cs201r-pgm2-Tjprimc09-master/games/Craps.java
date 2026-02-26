package games;

import java.util.*;

public class Craps {

    public static void PlayCraps(String[] args, Scanner input) {
        boolean playing = true;
        while (playing) {
            play(input);
            if (!playAgain(input)) {
                playing = false;
            }
        }
        System.out.println("Thanks for playing!");
        return;
    }

    // Craps Game Methods
    // PRE: accepts scanner from main
    // POST: while the user chooses to play:
    // the user will place their bet & roll dice
    // the game will determine the outcome and update the bet
    // the user cannot play anymore if they are out of money

    public static void play(Scanner input) {
        System.out.println("\nWELCOME TO CRAPS!!\n"); // welcome the user

        double netWorth = 50; // initiate user's starting money at ($)50.00

        Random random = new Random();// create random object to pass to rollDice

        while (netWorth > 0) { // while we are deciding to continue playing and we have money left
            System.out.println("You have $" + netWorth); // display current wallet
            System.out.print("Enter your bet amount: $"); // prompt for bet
            double bet = input.nextDouble(); // read bet amount from input
            if (bet > netWorth) { // check if bet exceeds current money
                System.out.println("You cannot bet more than you have!");
                break;
            }
            // see rollDice method below for details about rolling the dice
            int roll = rollDice(random); // "Roll the Dice" by calling rollDice method and storing the value returned
            boolean won = evaluateRoll(roll, random); // evaluate the roll and determine win/loss

            if (!won) { // if the user lost
                netWorth -= bet; // subtract bet from net worth
                System.out.println("You lost $" + bet);
            } else { // if the user won
                netWorth += bet; // add bet to net worth
                System.out.println("You won $" + bet);
            }

            if (!rollAgain(input)) {
                return; // if the user does not want to roll again, break out of the loop
            }
        }
        System.out.println("Sorry, you're out of money! Better luck next time!");
        return;
    }

    // Helper method
    // PRE: accept the roll value
    // POST: evaluate the roll and return win/loss as boolean

    public static boolean evaluateRoll(int roll, Random random) { // We want to pass the roll value and random object
        // Question: This method relies on int roll, which is in playCraps.
        // Does the method need to be within playCraps or can it be separate? Why?

        // Answer: It can be separate because it only needs the roll value.
        // Methods can be defined separately as long as they receive the necessary
        // parameters.
        // (Even if those parameters are defined in another method.)

        // Source: Copilot suggestion based on Java method principles.

        // Answer: A "rule switch" is a modern way of writing switch statements
        // that uses arrow (->) syntax for cases. Removes the need for break
        // statements and prevents accidental fall-through.

        // Question: What is fall-through?

        // Answer: Fall-through occurs when a case in a switch statement
        // does not have a break statement, causing execution to continue.

        // Answer: Pass methods using parameters.
        // Sources:
        // https://docs.oracle.com/en/java/javase/24/language/switch-expressions-and-statements.html
        // https://docs.oracle.com/en/java/javase/17/language/switch-expressions-and-statements.html

        switch (roll) {
            case 2, 3, 12 -> {
                System.out.println("Craps! You lose!");
                return false;
            }
            case 7, 11 -> {
                System.out.println("You win!!");
                return true;
            }
            default -> {
                int point = roll;
                while (true) {
                    roll = rollDice(random);
                    if (roll == point) {
                        System.out.println("You win!!");
                        return true;
                    } else if (roll == 7) {
                        System.out.println("You lose!");
                        return false;
                    }
                }
            }
        }
    }

    // Question: why am I required to have this (return false) here if my switch
    // covers all cases and returns a boolean?

    // Answer: The compiler require a return statement outside the switch
    // because it cannot guarantee that all paths within the switch will
    // return a value, especially if the switch is modified in the future.

    // Source:
    // https://docs.oracle.com/en/java/javase/25/language/switch-expressions-and-statements.html

    // Question: How do I refactor the code in lines 149-171 to avoid redundant
    // return statements?
    // Answer:

    // Source:

    // Helper method
    // PRE: accept the scanner object input
    // POST: prompt the user to roll again & return true/false

    public static boolean rollAgain(Scanner input) {
        while (true) {
            System.out.println("Roll Again (Y/N)?");
            String again = input.nextLine();
            if (!again.equals("Y") && !again.equals("N")) {
                System.out.println("Invalid input, please enter Y or N");
                continue;
            }

            if (again.equals("Y")) {
                return true;
            }
            if (again.equals("N")) {
                return false;
            }
        }
    }
    // Helper method
    // PRE: accept the random object rand
    // POST: get 2 random values 1-6, print the die and return the sum

    private static int rollDice(Random rand) {
        int die1 = rand.nextInt(1, 7);
        int die2 = rand.nextInt(1, 7);
        System.out.println("You rolled " + die1 + " and " + die2 + " for a total of " + (die1 + die2));
        return die1 + die2;
    }

    public static boolean playAgain(Scanner input) {
        System.out.println("Play Again (y/n)?:");
        String again = input.nextLine();
        if (!again.equals("y") && !again.equals("n")) {
            System.out.println("Invalid input, please enter y or n");
            return playAgain(input);
        }
        return again.equals("y");
    }

}
