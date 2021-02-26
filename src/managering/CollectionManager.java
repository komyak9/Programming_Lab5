package managering;

import content.Position;
import content.Worker;
import managering.instruments.CollectionInfo;
import managering.instruments.WorkerManager;
import parser.WorkerParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class CollectionManager {
    private final HashMap<String, String> commands = new HashMap<>();
    private final WorkerManager workerManager = new WorkerManager();
    private LinkedList<Worker> workersList = new LinkedList<>();
    private File file;
    private CollectionInfo collectionInfo;

    {
        commands.put("help", "Print info about commands");
        commands.put("info", "Print info about the collection");
        commands.put("show", "Print collection's elements");
        commands.put("add", "Add a new elements to the collection");
        commands.put("update", "Update an element with the given id");
        commands.put("removeById", "Remove an elements with the given id");
        commands.put("clear", "Remove all the elements of the collection");
        commands.put("save", "Save the collection to the file");
        commands.put("executeScript", "Read the file and do the script");
        commands.put("exit", "Finish the program");
        commands.put("addIfMax", "Add a new element if it has the highest value");
        commands.put("addIfMin", "Add a new element if it has the lowest value");
        commands.put("removeGreater", "Remove all the elements that are higher than this one");
        commands.put("removeAllByPosition", "Remove all the elements with the same position");
        commands.put("removeAnyBySalary", "Remove the element due to the salary");
        commands.put("printDescending", "Print the elements from max to min");
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

    public void add() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter an id: ");
            int id = scanner.nextInt();
            for (Worker worker : workersList) {
                if (worker.getId() == id) {
                    throw new Exception("content.content.Worker with such id is already exists.");
                }
            }
            workersList.add(workerManager.createWorker());
            System.out.println("New worker was successfully added to the collection.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void update() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter id for the update: ");
            int id = scanner.nextInt();

            boolean isPresent = false;
            for (int i = 0; i < workersList.size(); i++) {
                if (workersList.get(i).getId() == id) {
                    isPresent = true;
                    workersList.set(i, workerManager.createWorker());
                    System.out.println("The element has been updated.");
                }
            }
            if (!isPresent)
                throw new Exception("There is no such element in the list.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removeById() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter id for the removing: ");
            int id = scanner.nextInt();

            if (workersList.contains(workersList.get(id))) {
                workersList.remove(id);
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
        String command;

        while (scanner.hasNextLine()) {
            String[] input = scanner.nextLine().trim().split(" ", 2);
            command = input[0];

            try {
                switch (command) {
                    case "help" -> this.help();
                    case "info" -> this.info();
                    case "show" -> this.show();
                    case "add" -> this.add();
                    case "update" -> this.update();
                    case "removeById" -> this.removeById();
                    case "clear" -> this.clear();
                    case "save" -> this.save();
                    case "executeScript" -> {
                        if (!file.equals(new File(input[1])))
                            this.executeScript(new File(input[1]));
                        else
                            throw new Exception("Recursion alert. Can't execute the script that currently is being executed.");
                    }
                    case "exit" -> this.exit();
                    case "addIfMax" -> this.addIfMax();
                    case "addIfMin" -> this.addIfMin();
                    case "removeGreater" -> this.removeGreater();
                    case "removeAllByPosition" -> this.removeAllByPosition(Position.valueOf(input[1]));
                    case "removeAnyBySalary" -> this.removeAnyBySalary(Float.parseFloat(input[1]));
                    case "printDescending" -> this.printDescending();
                    default -> throw new Exception("There is no such command.");
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void exit() {
        System.out.println("Finishing the program...");
        System.exit(0);
    }

    public void addIfMax() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter an id: ");
            int id = scanner.nextInt();

            for (Worker worker : workersList) {
                if (worker.getId() == id)
                    throw new Exception("content.content.Worker with such id is already exists.");
                else if (worker.getId() > id) {
                    throw new Exception("The element is not the max.");
                }
            }
            workersList.add(workerManager.createWorker());
            System.out.println("New worker was successfully added to the collection.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void addIfMin() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter an id: ");
            int id = scanner.nextInt();

            for (Worker worker : workersList) {
                if (worker.getId() == id)
                    throw new Exception("content.content.Worker with such id is already exists.");
                else if (worker.getId() < id) {
                    throw new Exception("The element is not the min.");
                }
            }
            workersList.add(workerManager.createWorker());
            System.out.println("New worker was successfully added to the collection.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void removeGreater() {
        try {
            System.out.println("Enter an id: ");
            Worker comparingWorker = workerManager.createWorker();
            boolean hasGreater = false;

            for (Worker worker : workersList) {
                if (worker.compareTo(comparingWorker) > 0) {
                    hasGreater = true;
                    break;
                }
            }

            if (!hasGreater)
                throw new Exception("There is no greater element. Nothing to remove.");
            else {
                workersList.removeIf(worker -> worker.compareTo(comparingWorker) > 0);
                System.out.println("The elements have been removed.");
            }
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
                    workersList.remove(worker);
                }
            }
            if (isPresent)
                System.out.println("The element has been removed.");
            else
                throw new Exception("There is no worker with such position.");
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
                    workersList.remove(worker);
                    break;
                }
            }
            if (isPresent)
                System.out.println("The element has been removed.");
            else
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
            workersList = workerParser.parseXMLToObject(file);
            collectionInfo = new CollectionInfo("Linked List", Calendar.getInstance().getTime(), workersList.size());
            System.out.println("Data from the file was successfully downloaded.");
        }catch (Exception ex){
            System.out.println("Error in xml file data. Please, check the data in the file.");
        }
    }
}
