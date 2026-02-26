package games;

//import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Hangman {

    // Main Method
    // Handles main gameflow by calling helper methods

    public static void PlayHangman(String[] args, Scanner input) {
        Random random = new Random();
        Vector<String> wordList = new Vector<>();
        loadWords(wordList);
        boolean playing = true;

        System.out.println("\nWELCOME TO HANGMAN!!\n");

        while (playing) {
            playRound(wordList, input, random);
            if (!playAgain(input)) {
                System.out.println("Thanks for playing Hangman!");
                return;
            }
        }
    }

    // Method 1: playRound
    // PRE: accepts scanner input from main
    // POST: Reads words from an input file into the wordList
    // should loop while the player wants to play
    // selecting a random word from wordList & allowing the user
    // to guess letters to fill in the words (see assignment for more detail)

    public static void playRound(Vector<String> wordList, Scanner input, Random random) {
        // Select a random word from the list

        String wordToGuess = wordList.get(random.nextInt(wordList.size())); // selects a random word from the wordList
        String displayWord = "_".repeat(wordToGuess.length()); // creates a display word with underscores for each
                                                               // letter
        System.out.println("Hidden word: " + displayWord); // displays the current word

        ArrayList<Character> guessedLetters = new ArrayList<>(); // creates an array list to store guessed letters
        int attemptsLeft = 6; // sets the number of attempts left to 6

        while (attemptsLeft > 0 && !displayWord.equals(wordToGuess)) { // loops while the player has attempts left and
            System.out.println("\nYou have " + attemptsLeft + " attempts to guess the word.\nGuess a letter:");

            String guess = input.nextLine().toLowerCase(); // gets the player's guess and converts it to lowercase
            if (guess.length() != 1 || !Character.isLetter(guess.charAt(0))) { // checks if the guess is a single letter
                System.out.println("Invalid guess. Please try again.\n");
                continue;
            }
            char g = guess.charAt(0);
            // gets the first character of the guess
            if (guessedLetters.contains(g)) { // checks if the guessed letter has already been guessed
                System.out.println("You already guessed that letter.\n");
                continue;
            }

            guessedLetters.add(g); // if both checks pass, add the guessed letter to the list

            boolean hit = false;

            for (int x = 0; x < wordToGuess.length(); x++) { // displayed word at position (x) with g
                if (wordToGuess.charAt(x) == g) {
                    displayWord = displayWord.substring(0, x) + g + displayWord.substring(x + 1);
                    hit = true;
                }
            }

            if (hit) {
                System.out.println("You guessed a letter correctly!\n");
                if (displayWord.equals(wordToGuess)) {
                    System.out.println("Congratulations! You guessed the word: " + wordToGuess);
                    return;
                }
                System.out.println("\nHidden word: " + displayWord);
                continue;
            } else {
                attemptsLeft--;
                System.out.println("Your guess was incorrect.\nHidden word: " + displayWord);
            }
        }

        System.out.println("Game Over! The word was: " + wordToGuess);

        return;
    }

    // Method 2 (Helper Method): loadWords
    // PRE: accepts the wordList vector
    // POST: fills the wordList vector with words from the words.txt file

    public static void loadWords(Vector<String> wordList) {
        try (Scanner fileScanner = new Scanner(new File("games/words.txt"))) {
            while (fileScanner.hasNextLine()) {
                String word = fileScanner.nextLine().trim().toLowerCase();
                if (!word.isEmpty()) {
                    wordList.add(word);
                }
            }
        }

        catch (FileNotFoundException e) {
            System.out.println("Error: words.txt file not found.");
            return;
        }

        if (wordList.isEmpty()) {
            System.out.println("The words.txt file is empty.");
            return;
        }
    }

    // Method 3 (Helper Method): playAgain
    // PRE: accepts scanner input
    // POST: asks the user if they want to play again and returns true if they do,
    // false otherwise

    public static boolean playAgain(Scanner input) {
        System.out.println("Do you want to play again? (y/n)");
        String choice = input.nextLine().trim().toLowerCase();
        if (!choice.equals("y") && !choice.equals("n")) {
            System.out.println("Invalid selection. Please try again.");
            return playAgain(input); // recursive call to playAgain if the input is invalid
        }
        return choice.equals("y"); // returns true if the choice is "y", false otherwise
    }
}