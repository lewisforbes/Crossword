import java.util.ArrayList;
import java.util.Objects;

public class Utils {
    /** returns a deep copy of a 2D array **/
    public static String[][] clone2DArray(String[][] original) {
        int length = original.length;
        String[][] target = new String[length][original[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(original[i], 0, target[i], 0, original[i].length);
        }
        return target;
    }

    /** returns a deep copy of a string arrayList **/
    public static ArrayList<String> cloneStrArrLst(ArrayList<String> original) {
        ArrayList<String> output = new ArrayList<>();
        for (String str : original) {
            output.add(str);
        }
        return output;
    }

    /** capitalises every word in an arrayList and returns it **/
    public static ArrayList<String> capitaliseArrLst(ArrayList<String> original) {
        ArrayList<String> output = new ArrayList<>();
        for (String str : original) {
            output.add(str.toUpperCase());
        }
        return output;
    }

    /** puts word in an arrayList into sentence case and returns it **/
    public static ArrayList<String> sentenceCaseArrLst(ArrayList<String> original) {
        ArrayList<String> output = new ArrayList<>();
        String elemOfOutput;
        String[] strAsArr;
        ArrayList<String> strAsArrLst;
        for (String str : original) {
            strAsArrLst = new ArrayList<>();
            strAsArr = str.split(" ");
            for (String word : strAsArr) {
                if (word.length() <= 1) {
                    strAsArrLst.add(word.toLowerCase());
                    break;
                }
                strAsArrLst.add(Character.toUpperCase(word.charAt(0)) +
                        word.substring(1).toLowerCase());
            }

            elemOfOutput = "";
            elemOfOutput += strAsArrLst.get(0);
            if (strAsArrLst.size() > 1) {
                for (int i=1; i<strAsArrLst.size(); i++) {
                    elemOfOutput += " " + strAsArrLst.get(i);
                }
            }
            output.add(elemOfOutput);

        }
        return output;
    }

    /** removes spaces from every word in an arrayList and returns it **/
    public static ArrayList<String> removeSpacesFromArrLst(ArrayList<String> original) {
        ArrayList<String> output = new ArrayList<>();
        for (String str : original) {
            output.add(str.replaceAll(" ", ""));
        }
        return output;
    }

    /** returns true if a given string array contains a given string **/
    public static boolean arrContainsStr(String[] arr, String target) {
        for (String str : arr) {
            if (str.equals(target)) {
                return true;
            }
        }
        return false;
    }

    /** returns the first index of a given string in a given string array **/
    public static int arrIndexOf(String target, String[] arr) {
        for (int i=0; i<arr.length; i++) {
            if (arr[i].equals(target)) {
                return i;
            }
        }
        throw new IllegalArgumentException("The target is not in the array.");
    }

    /** prints a 2D array **/
    public static void print2DArr(String[][] arr) {
        for (int y = (arr[0].length - 1); y >= 0; y--) {
            for (int x = 0; x <= (arr.length - 1); x++) {
                if (arr[x][y] == null) {
                    System.out.print("." + " ");
                } else {
                    System.out.print(arr[x][y] + " ");
                }
            }
            System.out.print("\n");
        }
    }

    /** prints a 1D array **/
    public static void printArr(String[] arr) {
        if (arr.length== 0) {
            System.out.println("[]");
            return;
        }
        String output = "[" + arr[0];
        if (arr.length>1) {
            for (int i=1; i<arr.length; i++) {
                output += ", " + arr[i];
            }
        }
        System.out.println(output + "]");
    }

    /** splits a well-formatted position into coordinates **/
    public static int getX(String position) {
        Objects.requireNonNull(position);
        return Integer.parseInt(position.split(" ")[0]);
    }

    public static int getY(String position) {
        Objects.requireNonNull(position);
        return Integer.parseInt(position.split(" ")[1]);
    }
    /***/

    /** returns the an xTrail og given length **/
    public static int[] getXTrail(String initPos, Direction direction, int length) {
        int[] output = new int[length];
        int currentX = getX(initPos);
        for (int i=0; i<length; i++) {
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
    public static int[] getYTrail(String initPos, Direction direction, int length) {
        int[] output = new int[length];
        int currentY = getY(initPos);
        for (int i=0; i<length; i++) {
            if (direction == Direction.UP) {
                output[i] = currentY;
                currentY++;
            } else {
                output[i] = currentY;
            }
        }
        return output;
    }

    /** removes any completely null rows or columns from a 2D array, but keeps it square **/
    public static String[][] strip2DArray(String[][] givenArray) {
        int dim = givenArray.length;
        ArrayList<Integer> rowsToRemove = new ArrayList<>();
        ArrayList<Integer> colsToRemove = new ArrayList<>();

        for (int x=0; x<dim; x++) {
            if (nullCol(x, dim, givenArray)) {
                colsToRemove.add(x);
            }
        }

        for (int y=0; y<dim; y++) {
            if (nullRow(y, dim, givenArray)) {
                rowsToRemove.add(y);
            }
        }

        String[][] output =
                new String[dim - colsToRemove.size()][dim - rowsToRemove.size()];

        int xOn = -1;
        int yOn = -1;


        for (int y=0; y<dim; y++) {
            xOn = -1;
            if (!rowsToRemove.contains(y)) {
                yOn++;
                for (int x=0; x<dim; x++) {
                    if (!colsToRemove.contains(x)) {
                        xOn++;
                        output[xOn][yOn] = givenArray[x][y];
                    }
                }
            }
        }
        return mkSquare(output);
    }

    /** returns true iff a given column or row is completely null in a given 2D array **/
    private static boolean nullCol(int x, int dim, String[][] board) {
        for (int y=0; y<dim; y++) {
            if (board[x][y] != null) {
                return false;
            }
        }
        return true;
    }

    private static boolean nullRow(int y, int dim, String[][] board) {
        for (int x=0; x<dim; x++) {
            if (board[x][y] != null) {
                return false;
            }
        }
        return true;
    }

    /** makes a board square **/
    private static String[][] mkSquare(String[][] givenBoard) {
        int xSize = givenBoard.length;
        int ySize = givenBoard[0].length;
        int finalDim = Integer.max(xSize, ySize);
        String[][] output = new String[finalDim][finalDim];

        if (xSize == ySize) {
            return givenBoard;
        }

        int addAbove;
        int addBelow;
        if (xSize > ySize) {
            addAbove = (xSize-ySize)/2;
            addBelow = (xSize-ySize)-addAbove;

            for (int y=0; y<finalDim; y++) {
                if ((y<addBelow) || (y>(ySize+addBelow))) {
                    continue;
                }
                for (int x=0; x<finalDim; x++) {
                    output[x][y] = givenBoard[x][y-addBelow];
                }
            }
        }

        int addRight;
        int addLeft;
        if (ySize > xSize) {
            addRight = (ySize-xSize)/2;
            addLeft = (ySize-xSize)-addRight;

            for (int x=0; x<finalDim; x++) {
                if ((x<addLeft) || (x>(xSize+addLeft))) {
                    continue;
                }
                for (int y=0; y<finalDim; y++) {
                    output[x][y] = givenBoard[x-addLeft][y];
                }
            }
        }
        return output;
    }
}