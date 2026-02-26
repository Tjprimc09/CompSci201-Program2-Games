import games.*;
import java.util.Scanner;

public class Main {
    // PRE: None
    // POST: Displays a menu of games to the user and allows them to select a game to play until they choose to quit.
    public static void main(String[] args) {

        System.out.printf("%30s", "WELCOME TO YOUR GAMES!!\n\n");
        Scanner input = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            displayMenu();
            char choice = getChoice(input);
            switch (choice) {
                case 'L':
                    Lottery.PlayLottery(args, input);
                    break;
                case 'C':
                    Craps.PlayCraps(args, input);
                    break;
                case 'S':
                    Scraps.PlayScraps(args, input);
                    break;
                case 'R':
                    RockPaperScissors.PlayRockPaperScissors(args, input);
                    break;
                case 'P':
                    RPSS.PlayRockPaperScissorsSpock(args, input);
                    break;
                case 'B':
                    Blackjack.PlayBlackjack(args, input);
                    break;
                case 'H':
                    Hangman.PlayHangman(args, input);
                    break;
                case 'Q':
                    System.out.println("Thanks for playing!");
                    quit = true;
                    break;
            }
        }
        input.close();
        return;
    }

    // Pre: None
    // Post: Displays a menu of games to the user.
    public static void displayMenu() {
        String[] menu = new String[] {
                "Lottery",
                "Craps",
                "Scraps",
                "Rock, Paper, Scissors",
                "Rock, Paper, Scissors, Spock",
                "Blackjack",
                "Hangman",
                "Quit" };

        System.out.println("Please select a game to play:\n");
        System.out.println("---MENU---\n");

        for (int i = 0; i < menu.length; i++) {
            if (menu[i].equals("Rock, Paper, Scissors, Spock")) {
                System.out.println("P. " + menu[i] + "\n");
            } else {
                System.out.println(menu[i].charAt(0) + ". " + menu[i] + "\n");
            }
        }
    }

    // PRE: Accepts a Scanner object for user input.
    // POST: Prompts the user for a choice and validates the input until a valid choice is made, then returns the choice as a character.
    
    public static char getChoice(Scanner input) {
        System.out.print("Enter your choice: ");
        String choice = input.nextLine().trim().toUpperCase();
        while (choice.length() != 1 ||
                (!choice.equals("Q") &&
                        !choice.equals("L") &&
                        !choice.equals("C") &&
                        !choice.equals("S") &&
                        !choice.equals("R") &&
                        !choice.equals("P") &&
                        !choice.equals("B") &&
                        !choice.equals("H"))) {
            System.out.print("Invalid choice. Please try again: ");
            choice = input.nextLine().trim().toUpperCase();
        }
        return choice.charAt(0);
    }
}
