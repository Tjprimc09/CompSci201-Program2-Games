package games;

import java.util.Random;
import java.util.Scanner;

public class RPSS {
    // PRE: accepts scanner input
    // POST: plays rock, paper, scissors, spock, or lizard with user

    public static void PlayRockPaperScissorsSpock(String[] args, Scanner input) {
        System.out.println("WELCOME TO ROCK, PAPER, SCISSORS, SPOCK!");
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

    //PRE: accepts scanner input
    //POST: plays rock, paper, scissors, spock with user
    public static void play(Scanner input) {
        System.out.print("Enter \"rock\", \"paper\", \"scissors\", or \"spock\": ");
        String userChoice = input.nextLine();

        while (!userChoice.equals("rock") && !userChoice.equals("paper") && !userChoice.equals("scissors")
                && !userChoice.equals("spock")) {
            System.out.println(
                    "Invalid choice. Please choose \"rock\", \"paper\", \"scissors\", or \"spock\".");
            userChoice = input.nextLine();
        }

        System.out.println("You chose: " + userChoice);

        Random random = new Random();
        int computerIndex = random.nextInt(4);
        String[] options = { "rock", "paper", "scissors", "spock" };
        String computerChoice = options[computerIndex];

        System.out.println("The computer chose: " + computerChoice);

        if (userChoice.equals(computerChoice)) {
            System.out.println("It's a tie!");
            return;
        }

        switch (userChoice) { // determine the winner based on the user's choice against the computer's choice
            case "rock" -> { // using a switch statement here to reduce the number of if statements and make the code more readable
                if (computerChoice.equals("paper") || computerChoice.equals("spock")) {
                    System.out.println("Computer wins!");
                } else {
                    System.out.println("You win!");
                }
            }

            case "paper" -> {
                if (computerChoice.equals("rock") || computerChoice.equals("spock")) {
                    System.out.println("You win!");
                } else {
                    System.out.println("Computer wins!");
                }
            }

            case "scissors" -> {
                if (computerChoice.equals("paper")) {
                    System.out.println("You win!");
                } else {
                    System.out.println("Computer wins!");
                }
            }

            case "spock" -> {
                if (computerChoice.equals("rock") || computerChoice.equals("scissors")) {
                    System.out.println("You win!");
                } else {
                    System.out.println("Computer wins!");
                }
            }
        }
        return;
    }

    // PRE: accepts scanner input
    // POST: asks the user if they want to play again and returns true if they do,
    public static boolean playAgain(Scanner input) {
        System.out.print("Play again? (y/n): ");
        String playAgain = input.nextLine();
        if (!playAgain.equals("y") && !playAgain.equals("n")) {
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
            return playAgain(input);
        }
        return playAgain.equals("y");
    }
}
