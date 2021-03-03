package manager;

import content.Position;

import java.io.File;
import java.util.Scanner;

/**
 * Класс для считывания и обработки команд.
 * @autor komyak9
 */
public class Commander {
    private final CollectionManager collectionManager;
    private String command;

    public Commander(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        command = "";
    }

    public void interactiveMod() {
        Scanner scanner = new Scanner(System.in);
        while (!command.equals("exit")) {
            System.out.println("\nPLease, enter a command. You can inspect the list of available commands by \"help\".");
            String[] input = scanner.nextLine().trim().split(" ", 2);
            command = input[0];


            try {
                switch (command) {
                    case "help" -> collectionManager.help();
                    case "info" -> collectionManager.info();
                    case "show" -> collectionManager.show();
                    case "add" -> collectionManager.add(new Scanner(System.in));
                    case "update" -> collectionManager.update(Integer.parseInt(input[1]), new Scanner(System.in));
                    case "remove_by_id" -> collectionManager.removeById(Integer.parseInt(input[1]));
                    case "clear" -> collectionManager.clear();
                    case "save" -> collectionManager.save();
                    case "execute_script" -> collectionManager.executeScript(new File(input[1]));
                    case "exit" -> collectionManager.exit();
                    case "add_if_max" -> collectionManager.addIfMax(new Scanner(System.in));
                    case "add_if_min" -> collectionManager.addIfMin(new Scanner(System.in));
                    case "remove_greater" -> collectionManager.removeGreater(new Scanner(System.in));
                    case "remove_all_by_position" -> collectionManager.removeAllByPosition(Position.valueOf(input[1]));
                    case "remove_any_by_salary" -> collectionManager.removeAnyBySalary(Float.parseFloat(input[1]));
                    case "print_descending" -> collectionManager.printDescending();
                    default -> throw new Exception("There is no such command.");
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        scanner.close();
    }
}

