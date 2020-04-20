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
}