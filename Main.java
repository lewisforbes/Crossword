import java.util.Scanner;

public class Main {

    /** allows the user to enter input **/
    private final static Scanner input = new Scanner(System.in);

    /** the board of the current game **/
    private static Board board;

    /** the entry point for the program **/
    public static void main(String[] args) {
        System.out.println("Welcome to Random Crossword Maker!\n");
        startGame(10);
        gameLoop();
        board.printSolution();
        System.out.println("\nThanks for playing!");
    }

    /** initialises and starts the game **/
    private static void startGame(int dim) {
        String[] words = WordsGetter.getRandomWords(-1);
        board = new Board(dim);

        for (String word : words) {
            if (!board.allWordsPlaced()) {
                board.tryAddWord(word);
            } else {
                break;
            }
        }
        board.printProgress();
    }

    /** the main loop for the game **/
    private static void gameLoop() {
        String userInput;
        while (true) {
            if (board.allWordsFound()) {
                System.out.println("Well done! Here are the answers:");
            }

            final String giveUp = "???";
            System.out.println("Enter your word or '" + giveUp + "' to give up: ");
            userInput = input.nextLine();

            if (userInput.equals(giveUp)) {
                System.out.println("Here is the solution:");
                return;
            }

            if (board.guessWord(userInput.replace(" ", "").toUpperCase())) {
                board.printProgress();
                System.out.println("Well done, '" + userInput.toUpperCase().strip() + "' is correct!");
            } else {
                board.printProgress();
                System.out.println("Sorry, '" + userInput.toUpperCase().strip() + "' is incorrect.");
            }

            System.out.println();
        }
    }
}
