import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hangman{
    public static void main(String[] args) throws FileNotFoundException {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("1 or 2 players?");
        String players = keyboard.nextLine();

        String guessedWord;
        if (players.equals("1")) {
            //load a file
            Scanner sc = new Scanner(new File("C:\\Users\\luada\\words_alpha.txt"));
            List<String> words = new ArrayList<>();

            //go through the file and add elements to the list
            while (sc.hasNext()) {
                words.add((sc.nextLine()));
            }
            //choose random word from a list
            guessedWord = words.get(new Random().nextInt(words.size()));
        }
        else {
            System.out.println("Player 1, input your word: ");
            guessedWord = keyboard.nextLine().toLowerCase();
            //move a bit down, to hold friend back from cheating
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("Player 2 - get ready!");
        }

        //Words to guess commented out, but can be uncommented if player wanna cheat
        //System.out.println(guessedWord);

        //list with player guesses
        List<Character> playerGuesses = new ArrayList<>();

        int wrongCount = 0;
        while (true) {
            //prompt current state of gallows (and hangman)
            printHangman(wrongCount);

            //if player lost, prompt a message and reveal the word
            if (wrongCount >= 6) {
                System.out.println("You lost!");
                System.out.println("The word was: " + guessedWord);
                break;
            }

            //iterate through the word and check if letter is in the guessed word
            currentWordState(guessedWord, playerGuesses);

            //prompt user for a letter
            if (!getPlayerGuess(keyboard, guessedWord, playerGuesses)) {
                System.out.println("Sorry, that letter isn't here :(");
                wrongCount++;
            }

            //check if there are any spots in a word to fill or if guess is correct
            if(currentWordState(guessedWord, playerGuesses) || (wannaGuess(keyboard, guessedWord))){
                System.out.println("You win!");
                break;
            }
        }
    }

    private static void printHangman(int wrongCount) {
        //print the gallows
        System.out.println(" -------");
        System.out.println(" |     |");

        //print parts of hangman
        if (wrongCount >= 1)
            System.out.println(" O");


        if (wrongCount >= 2) {
            System.out.print("\\");
            if (wrongCount >= 3)
                System.out.println(" /");
            else
                System.out.println();
        }

        if (wrongCount >= 4)
            System.out.println(" |");


        if (wrongCount >= 5) {
            System.out.print("/");
            if (wrongCount >= 6)
                System.out.println(" \\");
            else
                System.out.println();
        }
        System.out.println();
    }

    //ask if user wanna guess the word and check if guessed right
    private static boolean wannaGuess(Scanner keyboard, String guessedWord) {
        System.out.println("Do you want to guess a word? (y/n)");
        String yesOrNo = keyboard.nextLine().toLowerCase();
        if (yesOrNo.equals("y")) {
            System.out.println("Type your guess: ");
            String typeGuess = keyboard.nextLine().toLowerCase();
            if (typeGuess.equals(guessedWord))
                return true;
            else {
                System.out.println("That ain't right :( Try again");
                System.out.println();
            }
        }
        return false;
    }
    //ask user for a letter, return true if letter is in a word
    private static boolean getPlayerGuess(Scanner keyboard, String guessedWord, List<Character> playerGuesses) {
        System.out.println("Please enter a letter: ");
        String letterGuess = keyboard.nextLine().toLowerCase();
        playerGuesses.add(letterGuess.charAt(0));

        return guessedWord.contains(letterGuess);
    }

    //print current state of word
    private static boolean currentWordState(String guessedWord, List<Character> playerGuesses) {
        int correctCount = 0;
        for (int i = 0; i < guessedWord.length(); i++) {
            if (playerGuesses.contains(guessedWord.charAt(i))) {
                System.out.print(guessedWord.charAt(i));
                correctCount++;
            }
            else
                System.out.print("_");
        }
        System.out.println();
        return (guessedWord.length() == correctCount);
    }
}
