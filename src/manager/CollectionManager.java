package manager;

import content.Position;
import content.Worker;
import manager.instruments.CollectionInfo;
import manager.instruments.workerInstruments.WorkerIdHolder;
import manager.instruments.workerInstruments.WorkerManager;
import manager.instruments.workerInstruments.WorkerParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Класс для работы с коллекцией.
 * @autor komyak9
 */
public class CollectionManager {
    private final HashMap<String, String> commands = new HashMap<>();
    private final WorkerManager workerManager = new WorkerManager();
    private LinkedList<Worker> workersList;
    private File file;
    private CollectionInfo collectionInfo;

    {
        commands.put("help", "Print info about commands");
        commands.put("info", "Print info about the collection");
        commands.put("show", "Print collection's elements");
        commands.put("add", "Add a new elements to the collection");
        commands.put("update", "Update an element with the given id");
        commands.put("remove_by_id", "Remove an elements with the given id");
        commands.put("clear", "Remove all the elements of the collection");
        commands.put("save", "Save the collection to the file");
        commands.put("execute_script", "Read the file and do the script");
        commands.put("exit", "Finish the program");
        commands.put("add_if_max", "Add a new element if it has the highest value");
        commands.put("add_if_min", "Add a new element if it has the lowest value");
        commands.put("remove_greater", "Remove all the elements that are higher than this one");
        commands.put("remove_all_by_position", "Remove all the elements with the same position");
        commands.put("remove_any_by_salary", "Remove the element due to the salary");
        commands.put("print_descending", "Print the elements from max to min");
    }

    public CollectionManager(String fileName) throws FileNotFoundException {
        try {
            file = new File(fileName);
            if (!file.exists())
                throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        fillCollection();
    }

    public void help() {
        System.out.println("Available commands:");
        String[] keys = commands.keySet().toArray(new String[0]);
        for (int i = 0; i < commands.size(); i++) {
            System.out.println("Command: " + keys[i] + " - " + commands.get(keys[i]));
        }
    }

    public void info() {
        System.out.println("Collection info:\n" + collectionInfo.getCollectionInfo());
    }

    public void show() {
        try {
            if (workersList.isEmpty())
                throw new Exception("The collection is empty. Nothing to show.");

            for (Worker worker : workersList) {
                System.out.println(workerManager.getWorkerInfo(worker));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void add(Scanner scanner) {
        try {
            workersList.add(workerManager.createWorker(scanner));
            System.out.println("New worker was successfully added to the collection.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void update(int id, Scanner scanner) {
        try {
            if (WorkerIdHolder.getIdSet().contains(id)) {
                for (int i = 0; i < workersList.size(); i++) {
                    if (workersList.get(i).getId() == id) {
                        WorkerIdHolder.remove(id);
                        workersList.set(i, workerManager.createWorker(scanner));
                        System.out.println("The element has been updated.");
                        break;
                    }
                }
            } else
                throw new Exception("There is no such element in the list.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removeById(int id) {
        try {
            if (WorkerIdHolder.getIdSet().contains(id)) {
                workersList.remove(id);
                WorkerIdHolder.remove(id);
                System.out.println("The element has been removed.");
            } else
                throw new Exception("There is no such element in the list.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void clear() {
        try {
            if (!workersList.isEmpty()) {
                workersList.clear();
                WorkerIdHolder.clear();
                System.out.println("The collection has been cleared.");
            } else
                throw new Exception("The collection is already empty.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void save() throws IOException, IllegalAccessException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please, enter the name of the file: ");
        File file = new File(sc.nextLine());

        FileOutputStream fstream = new FileOutputStream(file, true);
        String line = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<workers>\n";
        byte[] buffer = line.getBytes();
        fstream.write(buffer);

        WorkerParser parser = new WorkerParser();
        for (Worker worker : workersList) {
            parser.parseObjectToXML(file, worker);
        }

        line = "</workers>\n";
        buffer = line.getBytes();
        fstream.write(buffer);
    }

    public void executeScript(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String command = "";

        while (scanner.hasNextLine()) {
            String[] input = scanner.nextLine().trim().split(" ", 2);
            command = input[0];

            try {
                switch (command) {
                    case "help" -> this.help();
                    case "info" -> this.info();
                    case "show" -> this.show();
                    case "add" -> this.add(scanner);
                    case "update" -> this.update(Integer.parseInt(input[1]), scanner);
                    case "remove_by_id" -> this.removeById(Integer.parseInt(input[1]));
                    case "clear" -> this.clear();
                    case "save" -> this.save();
                    case "execute_script" -> {
                        if (!file.equals(new File(input[1])))
                            this.executeScript(new File(input[1]));
                        else
                            throw new Exception("Recursion alert. Can't execute the script that currently is being executed.");
                    }
                    case "exit" -> this.exit();
                    case "add_if_max" -> this.addIfMax(scanner);
                    case "add_if_min" -> this.addIfMin(scanner);
                    case "remove_greater" -> this.removeGreater(scanner);
                    case "remove_all_by_position" -> this.removeAllByPosition(Position.valueOf(input[1]));
                    case "remove_any_by_salary" -> this.removeAnyBySalary(Float.parseFloat(input[1]));
                    case "print_descending" -> this.printDescending();
                    default -> throw new Exception("There is no such command.");
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        scanner.close();
    }

    public void exit() {
        System.out.println("Finishing the program...");
        System.exit(0);
    }

    public void addIfMax(Scanner scanner) {
        try {
            Worker worker = workerManager.createWorker(scanner);

            if (worker.compareTo(Collections.max(workersList)) > 0)
                workersList.add(worker);
            else
                throw new Exception("The element is not the max.");

            System.out.println("New worker was successfully added to the collection.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addIfMin(Scanner scanner) {
        try {
            Worker worker = workerManager.createWorker(scanner);

            if (worker.compareTo(Collections.min(workersList)) < 0)
                workersList.add(worker);
            else
                throw new Exception("The element is not the min.");

            System.out.println("New worker was successfully added to the collection.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removeGreater(Scanner scanner) {
        try {
            Worker worker = workerManager.createWorker(scanner);
            boolean hasGreater = false;

            while (worker.compareTo(Collections.max(workersList)) <= 0) {
                WorkerIdHolder.remove(Collections.max(workersList).getId());
                workersList.remove(Collections.max(workersList));
                hasGreater = true;
            }
            if (!hasGreater)
                throw new Exception("There is no greater element. Nothing to remove.");
            else
                System.out.println("The elements have been removed.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removeAllByPosition(Position position) {
        try {
            boolean isPresent = false;
            for (Worker worker : workersList) {
                if (worker.getPosition() == position) {
                    isPresent = true;
                    WorkerIdHolder.remove(worker.getId());
                    workersList.remove(worker);
                }
            }
            if (!isPresent)
                throw new Exception("There is no worker with such position.");
            else
                System.out.println("The elements have been removed.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removeAnyBySalary(float salary) {
        try {
            boolean isPresent = false;

            for (Worker worker : workersList) {
                if (worker.getSalary() == salary) {
                    isPresent = true;
                    WorkerIdHolder.remove(worker.getId());
                    workersList.remove(worker);
                    System.out.println("The element has been removed.");
                    break;
                }
            }
            if (!isPresent)
                throw new Exception("There is no worker with such salary.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void printDescending() {
        try {
            if (workersList.isEmpty()) {
                throw new Exception("The collection is empty. Nothing to show.");
            } else {
                Collections.sort(workersList);
                Collections.reverse(workersList);
                for (Worker worker : workersList) {
                    System.out.println(workerManager.getWorkerInfo(worker));
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void fillCollection() throws FileNotFoundException {
        try {
            WorkerParser workerParser = new WorkerParser();
            workersList = workerParser.getWorkersList(file);
            collectionInfo = new CollectionInfo("Linked List", Calendar.getInstance().getTime(), workersList.size());
            System.out.println("Data from the file was successfully downloaded.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Error in xml file data. Please, check the data in the file.");
        }
    }
}
