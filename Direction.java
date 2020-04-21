import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public enum Direction {
    UP,
    RIGHT; //MUST NOT START WITH THE SAME LETTER

    public static ArrayList<Direction> getRndIteration() {
        ArrayList<Direction> output = new ArrayList<>(Arrays.asList(UP, RIGHT));
        Collections.shuffle(output);
        return output;
    }
}
