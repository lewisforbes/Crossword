import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public enum Direction {
    UP,
    RIGHT;

    public static ArrayList<Direction> getRndIteration() {
        ArrayList<Direction> output = new ArrayList<>(Arrays.asList(UP, RIGHT));
        Collections.shuffle(output);
        return output;
    }
}
