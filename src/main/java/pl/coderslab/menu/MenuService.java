package pl.coderslab.menu;

import pl.coderslab.utils.ConsoleColors;

import java.util.InputMismatchException;

import static pl.coderslab.task.TaskManager.*;
import static pl.coderslab.file.FileService.readFile;
import static pl.coderslab.file.FileService.writeTaskToFile;

public class MenuService {

    /**
     * Displays the main menu of the application and handles user input.
     * Allows the user to navigate between submenus.
     */
    public static void displayMainMenu() {

        while (true) {
            try {
                entryMenu();
                String userChoice = SCANNER.nextLine();

                switch (userChoice) {
                    case "add" -> writeTaskToFile(FILE_PATH);
                    case "remove" -> removeTask();
                    case "list" -> readFile(FILE_PATH);
                    case "exit" -> {

                        System.out.println(ConsoleColors.RED + "Bye bye");
                        return;
                    }
                    default ->
                            System.out.println(ConsoleColors.RED_UNDERLINED + "Invalid choice. Please choose between available options.");
                }
            } catch (InputMismatchException ex) {
                System.err.printf("Invalid input! %n");
                SCANNER.nextLine();
                entryMenu();
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
