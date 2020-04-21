import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Deals with everything involved in getting random words from pre-existing file
 */

public class WordsGetter {

    /** returns an array of the specified number to get, of all possible words if a negative is passed through **/
    public static String[] getRandomWords(int givenNumToGet) {
        String[] words = readFile("parsedData.txt", 2).split(",");
        int toGet;
        if (givenNumToGet < 0) {
            toGet = words.length;
        } else {
            toGet = givenNumToGet;
        }
        Random RANDOM = new Random();
        ArrayList<Integer> indexesToGet = new ArrayList<>();
        int currentIndex;
        while (indexesToGet.size() < toGet) {
            currentIndex = RANDOM.nextInt(words.length);
            if (!indexesToGet.contains(currentIndex)) {
                indexesToGet.add(currentIndex);
            }
        }
        String[] output = new String[toGet];
        for (int i = 0; i < toGet; i++) {
            output[i] = words[indexesToGet.get(i)].toUpperCase();
        }
        return removeNulls(output);
    }


    /** gets the raw data from the pre-made text file **/
    private static String readFile(String fileName, int minWordLength) {
        String data = "";
        String currentLine;
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                currentLine = myReader.nextLine();
                if (currentLine.strip().length() < minWordLength) {
                    continue;
                }
                data += currentLine + "\n";
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }

    /** removes nulls from a string array **/
    private static String[] removeNulls(String[] original) {
        List<String> noNulls = new ArrayList<String>();

        for (String s : original) {
            if (s != null && s.length() > 0) {
                noNulls.add(s);
            }
        }

        return noNulls.toArray(new String[noNulls.size()]);
    }


    // UNUSED //
    /** was used to format the raw words data **/
    private static void formatData(int minWordLength) {
        String fileContents = readFile("rawData.txt", minWordLength);
        fileContents = fileContents.replace("\n", "");
        fileContents = fileContents.replace(", ", "\n");
        fileContents = fileContents.replace(".", "\n");
        fileContents = fileContents.replace("\n", ",");
        fileContents = fileContents.substring(0, fileContents.length()-1);
        writeToFile(fileContents);
    }

    /** was used to add the formatted data to a new file **/
    private static void writeToFile(String toWrite) {
        try {
            FileWriter myWriter = new FileWriter("parsedData.txt");
            myWriter.write(toWrite);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}