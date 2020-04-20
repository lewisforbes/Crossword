import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] words = Main.getRandomWords(20);
        Board board = new Board(20);
        board.printBoard();
        in.nextLine();

        for (String word : words) {
            System.out.println(word);
            in.nextLine();
            System.out.println(board.tryAddWord(word));
            in.nextLine();
            board.printBoard();
            in.nextLine();
        }
    }
}
