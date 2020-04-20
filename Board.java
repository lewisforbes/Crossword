import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Board {
    /** the board's dimension **/
    private int dim;

    /** the words added on the board **/
    private ArrayList<Word> wordsOnBoard = new ArrayList<>();

    /** the coordinates of every placed letter on the board: <letter, list of coordinates> **/
    private HashMap<String, ArrayList<String>> letterPositions = new HashMap<>();

    /** the board **/
    private String[][] board;

    /** make a new board **/
    public Board(int dim) {
        this.dim = dim;
        this.board = new String[dim][dim];
    }

    /** prints the board to the console **/
    public void printBoard() {
        String output = "";
        String nullChar = "-";
        for (int y=0; y<dim; y++) {
            for (int x=0; x<dim; x++) {
                if (board[x][y] == null) {
                    output += (nullChar);
                } else {
                    output += board[x][y];
                }
                output += " ";
            }
            output += "\n";
        }
        System.out.print(output);
    }

    /** tries to add a word to the board, returning true iff successful **/
    public boolean tryAddWord(String givenWord) {
        Word word = new Word(givenWord);
        if (wordsOnBoard.size() == 0) {
            addWord(word, (dim/3) + " " + (dim/3), word.getDirectionIteration().get(0));
            return true;
        }
        String name = word.getName();
        String adjustedPosition;

        for (int c=0; c<name.length(); c++) {
            if (!letterPositions.containsKey(name.charAt(c) + "")) {
                continue;
            }

            for (String possiblePosition : letterPositions.get(name.charAt(c) + "")) {
                for (Direction direction : word.getDirectionIteration()) {
                    adjustedPosition = adjustPosition(possiblePosition, direction, c);
                    if (canAdd(word, adjustedPosition, direction)) {
                        addWord(word, adjustedPosition, direction);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** adds a word to the board **/
    private void addWord(Word word, String position, Direction direction) {
        word.setPosition(position);
        word.setDirection(direction);
        updateLetterPositions(word);
        wordsOnBoard.add(word);

        int[] xTrail = word.getXTrail();
        int[] yTrail = word.getYTrail();
        String name = word.getName();
        assert ((name.length() == xTrail.length) && (xTrail.length == yTrail.length));

        for (int i=0; i<name.length(); i++) {
            board[xTrail[i]][yTrail[i]] = "" + name.charAt(i);
        }
    }

    /** adjusts a position to place specified letter on specified position **/
    private String adjustPosition(String originalPos, Direction direction, int charOn) {
        int x = Utils.getX(originalPos);
        int y = Utils.getY(originalPos);
        if (direction == Direction.UP) {
            return x + " " + (y-charOn);
        }
        if (direction == Direction.RIGHT) {

            return (x-charOn) + " " + y;
        }
        throw new IllegalArgumentException("The given direction was: " + direction);
    }

    /** updates the letter positions hashmap with a new word **/
    private void updateLetterPositions(Word word) {
        Objects.requireNonNull(word.getPosition());
        Objects.requireNonNull(word.getDirection());

        String name = word.getName().toUpperCase();
        ArrayList<String> currentValue;
        String currentKey;
        int[] xTrail = word.getXTrail();
        int[] yTrail = word.getYTrail();

        for (int c=0; c<name.length(); c++) {
            currentKey = "" + name.charAt(c);
            if (letterPositions.containsKey(currentKey)) {
                currentValue = letterPositions.get(currentKey);
                currentValue.add(xTrail[c] + " " + yTrail[c]);
            } else {
                currentValue = new ArrayList<>(Arrays.asList(xTrail[c] + " " + yTrail[c]));
            }
            letterPositions.put(currentKey, currentValue);
        }
    }

    /** checks if a word can be places at given position on the board **/
    private boolean canAdd(Word word, String position, Direction direction) {
        int currentX = Utils.getX(position);
        int currentY = Utils.getY(position);
        String name = word.getName();

        if ((currentX<0) || (currentY<0)){
            return false;
        }

        for (int c=0; c<name.length(); c++) {
            if ((currentX >= dim) || (currentY >= dim)) {
                return false;
            }
            if ((board[currentX][currentY] != null) &&
                    (!board[currentX][currentY].equals(name.charAt(c) + ""))) {
                return false;
            }
            if (direction == Direction.UP) {
                currentY++;
                continue;
            }
            if (direction == Direction.RIGHT) {
                currentX++;
                continue;
            }
            throw new IllegalArgumentException("The given direction was: " + direction);
        }
        return true;
    }
}
