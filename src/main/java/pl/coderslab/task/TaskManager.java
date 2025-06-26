package pl.coderslab.task;

import pl.coderslab.utils.ConsoleColors;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static pl.coderslab.menu.MenuService.displayMainMenu;

public class TaskManager {

    public static final String FILE_PATH = "src/main/resources/tasks.csv";
    public static final Scanner SCANNER = new Scanner(System.in);
    private static final String DATE_REGEX = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";
    private static List<String> cachedTasks = new ArrayList<>();

    public static void main(String[] args) {

        displayMainMenu();
    }

    /**
     * Loads tasks to the list and handles any exceptions.
     *
     * @param fileName represents path to the file.
     * @return list of tasks or an empty list in case of error.
     */
    public static List<String> getTasks(String fileName) {

        try (Scanner scanner = new Scanner((Paths.get(fileName)))) {
            while (scanner.hasNextLine()) {
                var line = scanner.nextLine();
                cachedTasks.add(line);
            }
            return cachedTasks;
        } catch (Exception e) {
            System.err.printf("Error while reading file: %s%n", fileName);
        }
        return List.of();
    }

    /**
     * Removes task chosen by user by task number
     */
    //todo
    public static void removeTask() {
    }

    /**
     * Creates new task based on user input.
     *
     * @return object of class StringBuilder converted to object of class String.
     */
    public static String createTask() {

        var sb = new StringBuilder();

        //todo set tasks number
        String taskNumber = "0";

        var taskDescription = askForDescription();
        var dueDate = askForDueDate();
        var isImportant = askForImportance();


        sb.append(taskNumber).append(" : ").append(taskDescription).append(", ").append(dueDate).append(", ").append(isImportant);

        return sb.toString();
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

            if (date.matches(DATE_REGEX)) return date;
            else System.out.println(ConsoleColors.RED_UNDERLINED + "Invalid due date format. Please try again.");

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


}
