import java.util.ArrayList;
import java.util.Objects;

public class Word {
    /** the coordinates of the first letter of a word on the board **/
    private String position;

    /** the direction of a word on the board **/
    private Direction direction;

    /** the name of the word **/
    private String name;

    /** the order in which to try directions **/
    private ArrayList<Direction> directionIteration;

    /** make a new word **/
    public Word(String name) {
        this.name = name;
        directionIteration = Direction.getRndIteration();
    }

    //GETTERS//
    public ArrayList<Direction> getDirectionIteration() {
        return directionIteration;
    }

    public String getPosition() { return position; }

    public String getName() { return name; }

    public Direction getDirection() { return direction; }


    //SETTERS//
    public void setPosition(String position) {
        this.position = position;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /** returns the individual coordinates of the first letter of the word **/
    public int getX() {
        Objects.requireNonNull(position);
        return Utils.getX(position);
    }

    public int getY() {
        Objects.requireNonNull(position);
        return Utils.getY(position);
    }


    /** returns the xTrail of a word **/
    public int[] getXTrail() {
        Objects.requireNonNull(position);
        Objects.requireNonNull(direction);
        int[] output = new int[name.length()];
        int currentX = getX();
        for (int i=0; i<name.length(); i++) {
            if (direction == Direction.RIGHT) {
                output[i] = currentX;
                currentX++;
            } else {
                output[i] = currentX;
            }
        }
        return output;
    }

    /** returns the yTrail of a word **/
    public int[] getYTrail() {
        Objects.requireNonNull(position);
        Objects.requireNonNull(direction);
        int[] output = new int[name.length()];
        int currentY = getY();
        for (int i=0; i<name.length(); i++) {
            if (direction == Direction.UP) {
                output[i] = currentY;
                currentY++;
            } else {
                output[i] = currentY;
            }
        }
        return output;
    }
}
