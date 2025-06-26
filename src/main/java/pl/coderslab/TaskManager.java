package pl.coderslab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args) {

        readFile("src/main/resources/tasks.csv");

    }

    /**
     * Reads all lines for the given file
     *
     * @param fileName represents path to the file
     */
    public static void readFile(String fileName) {

        validateFileExists(fileName);

        try (Scanner scanner = new Scanner((Paths.get(fileName)))) {
            scanner.useDelimiter("\n");
            while (scanner.hasNext()) {
                var line = scanner.next();
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.printf("Error while reading file: %s%n", fileName);
        }
    }

    /**
     * Validates, that the files exists at the given path
     *
     * @param fileName represents path to the file
     */
    private static void validateFileExists(String fileName) {

        if (!Files.exists(Paths.get(fileName))) {
            throw new IllegalArgumentException(String.format("File does not exists %s", fileName));
        }
    }
}
