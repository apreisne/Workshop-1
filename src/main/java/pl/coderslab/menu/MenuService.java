package pl.coderslab.menu;

import pl.coderslab.utils.ConsoleColors;

import java.io.IOException;
import java.util.InputMismatchException;

import static pl.coderslab.file.FileService.readFile;
import static pl.coderslab.file.FileService.writeTaskToFile;
import static pl.coderslab.task.TaskManager.*;

public class MenuService {

    private MenuService() {
    }

    /**
     * Displays the main menu of the application and handles user input.
     * Allows the user to navigate between submenus.
     */

    public static void displayMainMenu() {

        try {
            getAllTasks(FILE_PATH);
        } catch (IOException e) {
            System.err.printf("Error occurred while reading file: %s%n", e.getMessage());
        }

        while (true) {
            try {
                entryMenu();
                var userChoice = SCANNER.nextLine();

                switch (userChoice) {
                    case "add" -> addTask();
                    case "remove" -> removeTask();
                    case "list" -> readFile();
                    case "exit" -> {

                        writeTaskToFile(tasks);
                        System.out.println(ConsoleColors.RED + "Bye bye");
                        return;
                    }
                    default ->
                            System.out.println(ConsoleColors.RED_UNDERLINED + "Invalid choice. Please choose between available options.");
                }
            } catch (InputMismatchException ex) {
                System.err.printf("Invalid input! %n");
                SCANNER.nextLine();
            } catch (IOException e) {
                System.err.printf("Could not finish operations for file: %s%n", FILE_PATH);
            }
        }
    }

    /**
     * Prints the main menu options to the console.
     */
    private static void entryMenu() {

        System.out.println(ConsoleColors.BLUE + "Please select an option:");
        System.out.println(ConsoleColors.RESET + """
                add
                remove
                list
                exit
                """);
    }
}
