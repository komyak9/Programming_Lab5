package managering;

import content.Position;

import java.io.File;
import java.util.Scanner;

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
                    case "add" -> collectionManager.add();
                    case "update" -> collectionManager.update();
                    case "removeById" -> collectionManager.removeById();
                    case "clear" -> collectionManager.clear();
                    case "save" -> collectionManager.save();
                    case "executeScript" -> collectionManager.executeScript(new File(input[1]));
                    case "exit" -> collectionManager.exit();
                    case "addIfMax" -> collectionManager.addIfMax();
                    case "addIfMin" -> collectionManager.addIfMin();
                    case "removeGreater" -> collectionManager.removeGreater();
                    case "removeAllByPosition" -> collectionManager.removeAllByPosition(Position.valueOf(input[1]));
                    case "removeAnyBySalary" -> collectionManager.removeAnyBySalary(Float.parseFloat(input[1]));
                    case "printDescending" -> collectionManager.printDescending();
                    default -> throw new Exception("There is no such command.");
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}

