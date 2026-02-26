package games;

import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {

    public static void PlayRockPaperScissors(String[] args, Scanner input) {
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
    // PRE: accepts scanner input
    // POST: plays rock, paper, scissors with user

    public static void play(Scanner input) {
        System.out.print("Enter \"rock\", \"paper\", or \"scissors\": ");
        String userChoice = input.nextLine();

        while (!userChoice.equals("rock") && !userChoice.equals("paper") && !userChoice.equals("scissors")) {
            System.out.println("Invalid choice. Please choose \"rock\", \"paper\", or \"scissors\".");
            userChoice = input.nextLine();
        }
        System.out.println("You chose: " + userChoice);
        Random random = new Random();

        int computerIndex = random.nextInt(3); // generates the computer's choice as a random index

        String[] options = { "rock", "paper", "scissors" };
        String computerChoice = options[computerIndex];
        System.out.println("The computer chose: " + computerChoice);

        if (userChoice.equals(computerChoice)) {
            System.out.println("It's a tie!");
        }

        else if ((userChoice.equals("rock") && computerChoice.equals("scissors")) ||
                (userChoice.equals("paper") && computerChoice.equals("rock")) ||
                (userChoice.equals("scissors") && computerChoice.equals("paper"))) {
            System.out.println("You win!");
        }

        else {
            System.out.println("The computer wins!");
        }
    }

    // PRE: accepts scanner input
    // POST: asks the user if they want to play again and returns true if they do,
    // false otherwise
    public static boolean playAgain(Scanner input) {
        while (true) {
            System.out.println("Play Again (y/n)?");
            String again = input.nextLine().trim().toLowerCase();
            if (!again.equals("y") && !again.equals("n")) {
                System.out.println("Invalid input, please enter y or n");
                continue;
            }

            return again.equals("y");
        }
    }
}