package pl.coderslab.file;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static pl.coderslab.task.TaskManager.createTask;
import static pl.coderslab.task.TaskManager.getTasks;

public class FileService {

    /**
     * Validates, that the files exists at the given path.
     *
     * @param fileName represents path to the file.
     */
    public static void validateFileExists(String fileName) {

        if (!Files.exists(Paths.get(fileName))) {
            throw new IllegalArgumentException(String.format("File does not exists %s", fileName));
        }
    }

    /**
     * Reads all lines for the given file.
     *
     * @param fileName represents path to the file.
     */
    public static void readFile(String fileName) {

        validateFileExists(fileName);
        getTasks(fileName).forEach(System.out::println);

    }

    /**
     * Writes new task to the file
     * @param fileName represents path of the file
     */
    public static void writeTaskToFile(String fileName) {

        var newTask = createTask();

        try (FileWriter fw = new FileWriter(fileName, true)) {
            fw.write(newTask + "\n");

        } catch (IOException ioe) {
            System.err.printf("Error writing to file %s.", fileName);
        }
    }
}
