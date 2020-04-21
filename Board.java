import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Board {
    /** the board's dimension **/
    private int dim;

    /** the words added on the board **/
    private ArrayList<Word> wordsOnBoard = new ArrayList<>();

    /** the number of words added for each direction **/
    private int upWords = 0;
    private int rightWords = 0;

    /** the max number of words for each direction **/
    public final int maxPerDirection = 9;

    /** the the coordinates of all numbers of words **/
    private HashMap<String, Integer> numsAtCoords = new HashMap<>();

    /** the words the player has found **/
     private ArrayList<Word> foundWords = new ArrayList<>();

    /** the coordinates of every placed letter on the board: <letter, list of coordinates> **/
    private HashMap<String, ArrayList<String>> letterPositions = new HashMap<>();

    /** the completed board **/
    private String[][] fullBoard;

    /** the words currently found on the board **/
    private String[][] partialBoard;

    /** the representation of an empty square **/
    private final String emptySquare = "-";

    /** the representation of an unvolved square **/
    private final String unsolvedSquare = "?";

    /** have words been assigned numbers? **/
    private boolean numsSet = false;


    /** make a new board **/
    public Board(int dim) {
        this.dim = dim;
        this.fullBoard = new String[dim][dim];
        this.partialBoard = new String[dim][dim];
    }

    /** prints the solved board to the console flipped on the x-axis **/
    public void printSolution() {
        String output = "";
        String nullChar = "-";
        for (int y=0; y<dim; y++) {
            for (int x=0; x<dim; x++) {
                if (fullBoard[x][y] == null) {
                    output += (nullChar);
                } else {
                    output += fullBoard[x][y];
                }
                output += " ";
            }
            output += "\n";
        }
        System.out.print(output + "\n");
        printClues(true);
    }

    /** returns true iff all of the words have been placed **/
    public boolean allWordsPlaced() {
        if (numOfPlacedWords() == (maxPerDirection * (Direction.values().length))) {
            return true;
        }
        if (numOfPlacedWords() > (maxPerDirection * (Direction.values().length))) {
            throw new IllegalArgumentException(numOfPlacedWords() + ">" + (maxPerDirection * (Direction.values().length)));
        }
        return false;
    }

    /** returns true iff all words all words has been found **/
    public boolean allWordsFound() {
        if (foundWords.size() == wordsOnBoard.size()) {
            return true;
        }
        return false;
    }

    /** prints the board with the player's current progress **/
    public void printProgress() {
        if (!numsSet) {
            setNums();
            numsSet = true;
        }

        String currentPos;
        for (int y=0; y<dim; y++) {
            for (int x=0; x < dim; x++) {
                currentPos = x + " " + y;
                if (fullBoard[x][y] == null) {
                    partialBoard[x][y] = emptySquare;
                } else {
                    if (getFoundWordLetter(currentPos) == null) {
                        partialBoard[x][y] = unsolvedSquare;
                    } else {
                        partialBoard[x][y] = getFoundWordLetter(currentPos);
                    }
                }
            }
        }


        String output = "";
        for (int y=0; y<dim; y++) {
            for (int x=0; x<dim; x++) {
                currentPos = x + " " + y;
                if (partialBoard[x][y] == null) {
                    output += emptySquare + " ";
                    continue;
                }

                if (partialBoard[x][y].equals(unsolvedSquare)) {
                    if (numsAtCoords.containsKey(Direction.UP.name().charAt(0) + currentPos)) {
                        output += numsAtCoords.get(Direction.UP.name().charAt(0) + currentPos) + " ";
                    } else if (numsAtCoords.containsKey(Direction.RIGHT.name().charAt(0) + currentPos)) {
                        output += numsAtCoords.get(Direction.RIGHT.name().charAt(0) + currentPos) + " ";
                    } else {
                        output += unsolvedSquare + " ";
                    }
                    continue;
                }
                // partialBoard[x][y] must contain part of a found word
                output += partialBoard[x][y] + " ";
            }
            output += "\n";
        }
        System.out.println(output);
        printClues(false);
    }

    /** returns the found letter at a given position, ot null if there is no found letter there **/
    private String getFoundWordLetter(String position) {
        int [] currentXTrail;
        int [] currentYTrail;
        for (Word word : foundWords) {
            currentXTrail = word.getXTrail();
            currentYTrail = word.getYTrail();

            for (int i=0; i<currentXTrail.length; i++) {
                if (position.equals(currentXTrail[i] + " " + currentYTrail[i])) {
                    return "" + word.getName().charAt(i);
                }
            }
        }
        return null;
    }

    /** prints the words' numbers and their clues **/
    public void printClues(boolean withSols) {
        String output = "DOWN\n";
        for (Word word : wordsOnBoard) {
            if (word.getDirection() == Direction.UP) {
                output += word.getNum() + ": ";
                if (foundWords.contains(word)) {
                    output += "[FOUND] ";
                }
                output += word.getClue() + " (" + word.getName().length() + ")";
                if (withSols) {
                    output += " | Answer: " + word.getName().toUpperCase();
                }
                output += "\n";
            }
        }

        output += "\nACROSS\n";
        for (Word word : wordsOnBoard) {
            if (word.getDirection() == Direction.RIGHT) {
                output += word.getNum() + ": ";
                if (foundWords.contains(word)) {
                    output += "[FOUND] ";
                }
                output += word.getClue() + " (" + word.getName().length() + ")";
                if (withSols) {
                    output += " | Answer: " + word.getName().toUpperCase();
                }
                output += "\n";
            }
        }
        System.out.println(output);
    }

    /** returns true iff a guessed word is correct **/
    public boolean guessWord(String guess) {
        for (Word word : wordsOnBoard) {
            if (word.getName().equalsIgnoreCase(guess.replace(" ", ""))) {
                foundWords.add(word);
                for (int c=0; c<word.getName().length(); c++) {
                    partialBoard[word.getXTrail()[c]][word.getYTrail()[c]] = word.getName().charAt(c) + " ";
                }
                return true;
            }
        }
        return false;
    }

    /** assigned each word a number **/
    public void setNums() {
        int upOn = 1;
        int rightOn = 1;
        String currentCoords;
        Direction otherDirection;

        for (Word word : wordsOnBoard) {
            currentCoords = word.getPosition();

            if (word.getDirection() == Direction.UP) {
                otherDirection = Direction.RIGHT;
            } else {
                otherDirection = Direction.UP;
            }
//            if (numsAtCoords.containsKey(otherDirection.name().charAt(0) + currentCoords)) {
//                word.setNum(numsAtCoords.get(otherDirection.name().charAt(0) + currentCoords));
//                continue;
//            }

            if (word.getDirection() == Direction.UP) {
                word.setNum(upOn);
                numsAtCoords.put(Direction.UP.name().charAt(0) + word.getPosition(), upOn);
                upOn++;
                continue;
            }

            if (word.getDirection() == Direction.RIGHT) {
                word.setNum(rightOn);
                numsAtCoords.put(Direction.RIGHT.name().charAt(0) + word.getPosition(), rightOn);
                rightOn++;
                continue;
            }

            throw new IllegalArgumentException("Direction is invalid");
        }
    }

    /** tries to add a word to the board, returning true iff successful **/
    public boolean tryAddWord(String givenWord) {
        Word word = new Word(givenWord);
        if ((rightWords>=maxPerDirection) && (upWords>=maxPerDirection)) {
            return false;
        }

        if (rightWords >= maxPerDirection) {
            word.removeDirectionFromIteration(Direction.RIGHT);
        }

        if (upWords >= maxPerDirection) {
            word.removeDirectionFromIteration(Direction.UP);
        }

        if (wordsOnBoard.size() == 0) {
            try {
                addWord(word, (dim/3) + " " + (dim/3), word.getDirectionIteration().get(0));
            } catch (Exception e) {
                addWord(word, "0 " + dim/2, Direction.RIGHT);
            }
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
                    if (spacedCanAdd(word, adjustedPosition, direction)) {
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
        if (direction == Direction.UP) {
            upWords++;
        } else if (direction == Direction.RIGHT) {
            rightWords++;
        } else {
            throw new IllegalArgumentException("Direction is invalid.");
        }

        int[] xTrail = word.getXTrail();
        int[] yTrail = word.getYTrail();
        String name = word.getName();
        assert ((name.length() == xTrail.length) && (xTrail.length == yTrail.length));

        for (int i=0; i<name.length(); i++) {
            fullBoard[xTrail[i]][yTrail[i]] = "" + name.charAt(i);
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

    /** checks if a word can possible be placed at given position on the board **/
    private boolean tightCanAdd(Word word, String position, Direction direction) {
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
            if ((fullBoard[currentX][currentY] != null) &&
                    (!fullBoard[currentX][currentY].equals(name.charAt(c) + ""))) {
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

    /** checks if a word can be added such that words are well spaced **/
    private boolean spacedCanAdd(Word word, String position, Direction direction) {
        if (!tightCanAdd(word, position, direction)) {
            return false;
        }

        int fixedY = Utils.getY(position);
        int plusCount = 0;
        int minusCount = 0;
        if (direction == Direction.RIGHT) {
            for (int x : Utils.getXTrail(position, direction, word.getName().length())) {
                try {
                    if (fullBoard[x][fixedY+1] != null) {
                        plusCount++;
                    } else {
                        plusCount = 0;
                    }
                } catch (IndexOutOfBoundsException e) {
                    plusCount = 0;
                }
                if (plusCount == 2) {
                    return false;
                }

                try {
                    if (fullBoard[x][fixedY-1] != null) {
                        minusCount++;
                    } else {
                        minusCount = 0;
                    }
                } catch (IndexOutOfBoundsException e) {
                    minusCount = 0;
                }
                if (minusCount == 2) {
                    return false;
                }
            }
        }

        int fixedX = Utils.getX(position);
        plusCount = 0;
        minusCount = 0;
        if (direction == Direction.UP) {
            for (int y : Utils.getYTrail(position, direction, word.getName().length())) {
                try {
                    if (fullBoard[fixedX+1][y] != null) {
                        plusCount++;
                    } else {
                        plusCount = 0;
                    }
                } catch (IndexOutOfBoundsException e) {
                    plusCount = 0;
                }
                if (plusCount == 2) {
                    return false;
                }

                try {
                    if (fullBoard[fixedX-1][y] != null) {
                        minusCount++;
                    } else {
                        minusCount = 0;
                    }
                } catch (IndexOutOfBoundsException e) {
                    minusCount = 0;
                }
                if (minusCount == 2) {
                    return false;
                }
            }
        }

        return true;
    }

    /** returns the number of words on the board **/
    public int numOfPlacedWords() {
        assert ((upWords + rightWords) == wordsOnBoard.size());
        return wordsOnBoard.size();
    }
}