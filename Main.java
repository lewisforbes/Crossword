import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static String[] getRandomWords(int number) {
        String[] words = readFile("parsedData.txt", 0).split(",");
        Random RANDOM = new Random();
        ArrayList<Integer> indexesToGet = new ArrayList<>();
        int currentIndex;
        while (indexesToGet.size() < number) {
            currentIndex = RANDOM.nextInt(words.length);
            if (!indexesToGet.contains(currentIndex)) {
                indexesToGet.add(currentIndex);
            }
        }
        String[] output = new String[number];
        for (int i=0; i<number; i++) {
            output[i] = words[indexesToGet.get(i)].toUpperCase();
        }
        return output;
    }

    public static void formatData(int minWordLength) {
        String fileContents = readFile("rawData.txt", minWordLength);
        fileContents = fileContents.replace("\n", "");
        fileContents = fileContents.replace(", ", "\n");
        fileContents = fileContents.replace(".", "\n");
        fileContents = fileContents.replace("\n", ",");
        fileContents = fileContents.substring(0, fileContents.length()-1);
        writeToFile(fileContents);
    }

    private static String readFile(String fileName, int minWordLength) {
        String data = "";
        String currentLine;
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                currentLine = myReader.nextLine();
                if (currentLine.strip().length()<minWordLength) {
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