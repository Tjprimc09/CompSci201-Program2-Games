package games;

import java.util.*;

public class Blackjack {

    // Main Method: playBlackjack

    // PRE: accepts scanner input from main
    // POST: Sets up the deck for users
    // should loop while the player wants to play
    // should shuffle the deck and deal two cards to player and dealer
    // should allow player to hit or stay until they bust or choose to stay
    // should then play out the dealer's hand (dealer hits until 17 or higher)
    // should determine the winner and ask if player wants to play again

    public static void PlayBlackjack(String[] args, Scanner input) {
        boolean playing = true;
        List<Integer> deck = new ArrayList<>(); // new array list to represent the deck

        System.out.println("\nWELCOME TO BLACKJACK!\n");

        while (playing) {
            deck.clear(); // clear the deck at the start of each round to reset it for the next game
                          // (caught this during actual gameplay testing).
            // If we don't clear the deck, then the deck will grow by 52 cards each round.
            buildDeck(deck); // builds the deck with card numbers 0-51
            play(input, deck); // plays out a round of blackjack with the user, including shuffling, dealing,
                               // player turn, dealer turn, and determining winner

            if (!askPlayAgain(input)) {
                System.out.println("Thanks for playing!");
                return; // exits the main method to end the program
            }
        }
    }

    // Method 1: buildDeck
    // PRE: accepts the deck list
    // POST: fills the deck with card numbers 0-51 to represent a standard deck of
    // cards

    public static void buildDeck(List<Integer> deck) {
        for (int i = 0; i < 52; i++) {
            deck.add(i); // add card numbers 0-51 to represent a standard deck of cards
        }
    }

    // Method 2: playBlackjack
    // PRE: accepts scanner input and the deck
    // POST: plays out a round of blackjack with the user, including shuffling,
    // dealing, player turn, dealer turn, and determining winner

    public static void play(Scanner input, List<Integer> deck) {
        Collections.shuffle(deck); // shuffles the deck
        List<Integer> playerHand = new ArrayList<>(); // new array list to hold player hand
        List<Integer> dealerHand = new ArrayList<>(); // new array list to hold dealer hand

        // Deal initial two cards each

        for (int i = 0; i < 2; i++) { // for two loops
            playerHand.add(deck.remove(0)); // remove the top card from deck and add to player hand
            dealerHand.add(deck.remove(0)); // remove the top card from deck and add to dealer hand
        }
        // Player turn

        System.out.println("\nYOUR HAND:\n" + handAsString(playerHand) + "\nTOTAL:" + getHandValue(playerHand)); // player
                                                                                                                 // can
                                                                                                                 // see
                                                                                                                 // their
                                                                                                                 // own
                                                                                                                 // cards
        System.out.println("\nDealer's hand:\n" + cardToString(dealerHand.get(0)) + "\n? ?\nTOTAL:??\n"); // and only
                                                                                                          // one
                                                                                                          // of the
                                                                                                          // dealers
                                                                                                          // cards

        boolean playerTurn = true;

        while (playerTurn) {
            if (getHandValue(playerHand) == 21) {
                System.out.println("\nYou have Blackjack! Let's see if the dealer can match it...\n");
                // we don't return here because we still need to play out the dealer's hand to
                // see if they can match the player's blackjack
                playerTurn = false; // player has blackjack, end player's turn to see if dealer can match
                break;
            }
            System.out.println("\nDo you want to hit or stay? (h/s)");
            String choice = input.nextLine().trim().toLowerCase();
            switch (choice) {
                case "h" -> {
                    playerHand.add(deck.remove(0)); // add a card to player's hand
                    System.out.printf("You hit\n\nYOUR HAND:\n%s\nTOTAL:%d\n", handAsString(playerHand),
                            getHandValue(playerHand)); // print updated hand and total

                    if (getHandValue(playerHand) > 21) {
                        System.out.println("\nYou bust! Dealer wins!\n");
                        return; // exits the playBlackjack method to end the program
                    }

                    else if (getHandValue(playerHand) == 21) {
                        System.out.println("\nYou have 21! Let's see if the dealer can match it...\n");
                        playerTurn = false; // player has 21, end player's turn to see if dealer can match
                    }
                }
                case "s" -> {
                    playerTurn = false; // player stays, exit loop
                }
                default -> {
                    System.out.println("\nInvalid selection. Please check your input and try again."); // handles
                                                                                                       // invalid
                                                                                                       // input

                }
            }
        }

        // Dealer turn
        // Reveal dealer's hand

        System.out.println("\nDEALER'S HAND:\n" + handAsString(dealerHand) + "\nTOTAL:" + getHandValue(dealerHand)); // reveal
                                                                                                                     // dealer's
                                                                                                                     // hand
                                                                                                                     // and
                                                                                                                     // total

        if (getHandValue(dealerHand) == 21) {
            if (getHandValue(playerHand) == 21) {
                System.out.println("\nIt's a push! Both you and the dealer have Blackjack!\n");
                return; // ends the playBlackjack method since the game is over with a push
            }

            else {
                System.out.println("\nDealer has Blackjack! You lose!\n");
                return; // ends the playBlackjack method since the game is over with a dealer blackjack
                        // win
            }
        }

        while (getHandValue(dealerHand) < 17) { // then, dealer hits until 17 or higher
            dealerHand.add(deck.remove(0));
            System.out.println("\nDealer hits.\n\nDEALER'S HAND:\n" + handAsString(dealerHand) + "\nTOTAL:"
                    + getHandValue(dealerHand)); // print updated dealer hand and total
            if (getHandValue(dealerHand) > 21) {
                System.out.println("\nDealer busts! You win!\n");
                return;
            } else if (getHandValue(dealerHand) == 21) {
                if (getHandValue(playerHand) == 21) {
                    System.out.println("\nIt's a push! Both you and the dealer have Blackjack!\n");
                    return; // ends the playBlackjack method since the game is over with a push
                }

                else {
                    System.out.println("\nDealer has 21! Dealer wins!\n");
                    return; // exit loop to compare hands and determine winner
                }
            }
        }

        // Determine winner

        int playerDiff = 21 - getHandValue(playerHand);
        int dealerDiff = 21 - getHandValue(dealerHand);

        if (playerDiff < dealerDiff) {
            System.out.println("\nYou win with a hand value of " + getHandValue(playerHand) + " against the dealer's "
                    + getHandValue(dealerHand) + "!\n");
        } else if (playerDiff > dealerDiff) {
            System.out.println("\nDealer wins with a hand value of " + getHandValue(dealerHand) + " against your "
                    + getHandValue(playerHand) + "!\n");
        } else {
            System.out.println("\nIt's a push! Both you and the dealer have the same hand value of "
                    + getHandValue(playerHand) + "!\n");
        }
    }

    // Method 3: getCardValue
    // PRE: accepts the # of the card (0-51)
    // POST: determines the 'value' of the card based on position in the deck
    // NOTE: cards 2-10 are face value, 11-13 (J, Q, K) are worth 10 each
    //

    public static int getCardValue(int cardNumber) {
        int faceValue = cardNumber % 13; // 0-12 corresponds to Ace, 2, 3, ..., 10, Jack, Queen, King
        if (faceValue == 0)
            return 1; // Ace
        else if (faceValue >= 10)
            return 10; // Jack, Queen, King
        return faceValue + 1; // 2-10 are worth their face value (1-9 in 0-based index, so add 1)
    }

    // Method 4: handAsString
    // PRE: accepts the card values for a hand
    // POST: returns the equivalent hand in string form ("Ace of Hearts")
    public static String handAsString(List<Integer> hand) {
        List<String> cards = new ArrayList<>();
        for (int card : hand) {
            cards.add(cardToString(card));

        }
        return String.join("\n", cards);
    }

    // Method 5: cardToString
    // PRE: Accepts the cardnumber
    // POST: Converts a card number to suit and face (e.g., "Ace of Hearts")

    public static String cardToString(int cardNumber) {
        char[] suits = { '\u2665', '\u2666', '\u2663', '\u2660' }; // Using Unicode characters for card suits: Hearts,
                                                                   // Diamonds, Clubs, Spades... Since this is extra
                                                                   // credit.
        // I looked up the Unicode characters for the suits and just used them in place
        // of "Hearts, Diamonds, Clubs, Spades" in the suits array. I also added color
        // coding to the card string based on suit, with red for hearts and diamonds and
        // no color for clubs and spades.
        // Source: https://unicode.org
        String[] faces = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };

        int faceIndex = cardNumber % 13;
        int suitIndex = cardNumber / 13;
        // modulo 4 is preferred for suit index since it directly corresponds to the 4
        // suits, while face index is based on 13 faces.
        // Makes things more intuitive from a developer perspective.

        switch (suits[suitIndex]) {
            case '\u2665', '\u2666' -> { // in the case of hearts || diamonds
                return "\u001B[31m" + suits[suitIndex] + " " + faces[faceIndex] + "\u001B[0m"; // add red color code and
                                                                                               // reset
                // ANSI Color code
                // I asked chatGPT for an explanation of how to add color coding to the card
                // strings and it provided me with the ANSI escape codes for red text and
                // resetting text color, which I added to the return statement for hearts and
                // diamonds.
                // ANSI "reset" SGR 0 means "clear all formatting and return to defaults"
            }
            default -> { // no color for clubs and spades
                return suits[suitIndex] + " " + faces[faceIndex];
            }
        }

    }

    // Method 6: getHandValue
    // PRE: accepts the hand
    // POST: computes the Blackjack hand value with Ace handling (1 or 11)
    // can use the getCardValue method to do this
    // and returns the hand total (ex: if user has J and 10 the total is 20)

    public static int getHandValue(List<Integer> hand) {
        int handValue = 0;
        int aceCount = 0;
        for (int card : hand) {
            int cardValue = getCardValue(card);
            handValue += cardValue;
            if (cardValue == 1)
                aceCount++; // count aces
        }

        // Handle Aces as 11 if it doesn't cause bust
        while (aceCount > 0 && handValue + 10 <= 21) {
            handValue += 10; // treat Ace as 11 instead of 1
            aceCount--; // reduce ace count since we've now treated one Ace as 11
        }
        return handValue;
    }

    // PRE: accepts scanner input from main
    // POST: Asks the user if they want to play again and returns true/false

    public static boolean askPlayAgain(Scanner input) {

        System.out.println("Do you want to play again? (y/n)");
        String response = input.nextLine().trim().toLowerCase();

        if (!response.equals("y") && !response.equals("n")) {
            System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
            return askPlayAgain(input); // recursively ask again for valid input
        }
        return response.equals("y"); // returns true if 'y', false if 'n'
    }
}
