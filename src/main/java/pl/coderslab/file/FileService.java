package pl.coderslab.file;

import pl.coderslab.utils.ConsoleColors;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static pl.coderslab.task.TaskManager.*;

public class FileService {

    private FileService() {
    }

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
     * Reads all lines for the given file and adds number of task at the beginning.
     */
    public static void readFile() {

        int counter = 1;

        for (String[] strings : tasks) {
            System.out.printf(ConsoleColors.RESET + "%d : %s", counter, String.join(", ", strings[0], strings[1]));
            System.out.printf(", %s%n", ConsoleColors.RED + strings[2]);
            counter++;
        }
    }

    /**
     * Writes updated array of tasks to the file
     *
     * @param arr takes array of tasks which is about to be saved
     */
    public static void writeTaskToFile(String[][] arr) throws IOException {

        validateFileExists(FILE_PATH);

        try (FileWriter fileWriter = new FileWriter(FILE_PATH, false)) {
            for (String[] strings : arr) {
                fileWriter.write(String.join(", ", strings) + "\n");
            }
        }
    }
}
