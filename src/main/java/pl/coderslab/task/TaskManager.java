package pl.coderslab.task;

import pl.coderslab.utils.ConsoleColors;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static org.apache.commons.lang3.ArrayUtils.remove;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;
import static pl.coderslab.menu.MenuService.displayMainMenu;

public class TaskManager {

    public static final String FILE_PATH = "src/main/resources/tasks.csv";
    public static final Scanner SCANNER = new Scanner(System.in);
    private static final String DATE_FORMAT_REGEX = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
    public static String[][] tasks = new String[][]{};

    public static void main(String[] args) {

        displayMainMenu();
    }

    /**
     * Loads tasks to the String array.
     *
     * @param fileName represents path to the file.
     * @return array of tasks.
     */
    public static String[][] getAllTasks(String fileName) throws IOException {

        List<String> lines = Files.readAllLines(Path.of(fileName));
        int linesCount = lines.size();

        String[][] allTasks = Arrays.copyOf(tasks, linesCount);

        try (Scanner scanner = new Scanner(new FileReader(fileName))) {
            while (scanner.hasNextLine()) {
                for (int i = 0; i < linesCount; i++) {
                    allTasks[i] = scanner.nextLine().split(", ");
                }
            }
        }
        tasks = allTasks;

        return allTasks;
    }


    /**
     * Removes task chosen by user by index
     */
    public static void removeTask() {

        int index;

        while (true) {
            try {
                index = getIndexOfTask();
                if (index == 0) break;
                String[][] updated = Arrays.copyOf(tasks, tasks.length);
                tasks = remove(updated, index - 1);
                break;

            } catch (InputMismatchException | IndexOutOfBoundsException e) {
                System.err.printf("Please provide valid number. %s %n", e.getMessage());
            }
        }
    }

    /**
     * Asks user for index of the task
     *
     * @return index of task advised by user, which should be removed
     */
    public static int getIndexOfTask() {

        while (true) {
            System.out.println(""" 
                    Please enter the task index, which you would like to remove. For exit press 0.
                    """);
            String index = SCANNER.nextLine();
            if (isParsable(index)) return Integer.parseInt(index);
            else System.out.println("Please enter a valid number index.");
        }
    }

    /**
     * Creates new task based on user input.
     *
     * @return object of class StringBuilder converted to object of class String.
     */
    public static String createTask() {

        var sb = new StringBuilder();

        var taskDescription = askForDescription();
        var dueDate = askForDueDate();
        var isImportant = askForImportance();

        sb.append(taskDescription).append(", ").append(dueDate).append(", ").append(isImportant);

        return sb.toString();
    }

    public static void addTask() throws IOException {

        String newTask = createTask();

        String[][] updated = Arrays.copyOf(tasks, tasks.length + 1);
        updated[updated.length - 1] = newTask.split(", ");
        tasks = updated;
    }

    /**
     * Asks user for task importance between true and false and validates the input.
     *
     * @return task importance as true or false, but as a String object.
     */
    private static String askForImportance() {

        while (true) {
            System.out.println(ConsoleColors.RESET + "Is your task important? true/false");
            var isImportant = SCANNER.nextLine();
            switch (isImportant) {
                case "true", "false":
                    return isImportant;
                default:
                    System.out.println(ConsoleColors.RED_UNDERLINED + "Invalid task importance option. Please try again.");
            }
        }
    }

    /**
     * Asks user for task due date and validates correctness of the date
     *
     * @return due date as String.
     */
    private static String askForDueDate() {

        while (true) {
            System.out.println(ConsoleColors.RESET + "Please enter due date in format YYYY-MM-DD:");
            var date = SCANNER.nextLine();

            if (date.matches(DATE_FORMAT_REGEX)) {
                if (isValidDate(date)) return date;
                else System.out.println(ConsoleColors.RED_UNDERLINED + "Invalid due date. Please try again.");
            }
        }
    }

    /**
     * Asks user for task description.
     *
     * @return task description as String.
     */
    private static String askForDescription() {

        System.out.println("Please add task description:");
        return SCANNER.nextLine();
    }

    /**
     * Validates correctness of the given date.
     *
     * @param dateStr takes date as a String.
     * @return true or false depending on correctness of the date.
     */
    private static boolean isValidDate(String dateStr) {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(dateStr, formatter);
            return true;

        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
