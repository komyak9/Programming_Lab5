package parser;

import content.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class WorkerParser {
    public LinkedList<Worker> parseXMLToObject(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        LinkedList<String> data = new LinkedList<>();
        XMLToObjectParser parser = new XMLToObjectParser();
        LinkedList<Worker> result = new LinkedList<>();

        String workerName = null;
        Coordinates coordinates = null;
        long coordinatesX = -1000;
        int coordinatesY = -1000;
        float salary = 0;

        LocalDateTime startDate = null;
        Date endDate = null;

        Position position = null;
        Organization organization = null;
        int annualTurnover = 0;
        OrganizationType type = null;
        Address address = null;
        String zipCode = null;

        Location location = null;
        String locationName = null;
        Integer locationX = null;
        int locationY = 0;


        while (scanner.hasNextLine()) {
            data.add(scanner.nextLine());
        }

        try {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).contains("<worker>")) {
                    for (int j = i + 1; j < data.size() && !data.get(i).contains("</worker>"); j++) {
                        if (data.get(j).contains("<name>"))
                            workerName = parser.getSingleValue("name", data.get(j));
                        else if (data.get(j).contains("<coordinates>")) {
                            coordinatesX = Long.parseLong(parser.getMultiValues("coordinates", data, j).get("x"));
                            coordinatesY = Integer.parseInt(parser.getMultiValues("coordinates", data, j).get("y"));
                        } else if (data.get(j).contains("<startDate>")) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                            startDate = LocalDateTime.parse(parser.getSingleValue("startDate", data.get(j)), formatter);
                        } else if (data.get(j).contains("<endDate>"))
                            endDate = new SimpleDateFormat("dd-MM-yyyy").parse(parser.getSingleValue("endDate", data.get(j)));
                        else if (data.get(j).contains("<salary>"))
                            salary = Float.parseFloat(parser.getSingleValue("salary", data.get(j)));
                        else if (data.get(j).contains("<position>"))
                            position = Position.valueOf(parser.getSingleValue("position", data.get(j)));
                        else if (data.get(j).contains("<organization>")) {
                            for (int k = j + 1; k < data.size() && !data.get(k).contains("</organization>"); k++) {
                                if (data.get(k).contains("<annualTurnover>"))
                                    annualTurnover = Integer.parseInt(parser.getSingleValue("annualTurnover", data.get(k)));
                                else if (data.get(k).contains("<type>"))
                                    type = OrganizationType.valueOf(parser.getSingleValue("type", data.get(k)));
                                else if (data.get(k).contains("<address>")) {
                                    for (int l = k + 1; l < data.size() && !data.get(l).contains("</address>"); l++) {
                                        if (data.get(l).contains("<zipCode>"))
                                            zipCode = parser.getSingleValue("zipCode", data.get(l));
                                        else if (data.get(l).contains("<location>")) {
                                            locationName = parser.getMultiValues("location", data, l).get("name");
                                            locationX = Integer.parseInt(parser.getMultiValues("location", data, l).get("x"));
                                            locationY = Integer.parseInt(parser.getMultiValues("location", data, l).get("y"));
                                        }
                                        k = l;
                                    }
                                }
                                j = k;
                            }
                        }
                        i = j;
                    }
                    location = new Location(locationX, locationY, locationName);
                    address = new Address(zipCode, location);
                    organization = new Organization(annualTurnover, type, address);
                    coordinates = new Coordinates(coordinatesX, coordinatesY);
                    result.add(new Worker(workerName, coordinates, salary, startDate, endDate, position, organization));
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Error in xml file data. Please, check the data in the file.");
        }
        return result;
    }

    public void parseObjectToXML(File file, Worker worker) throws IllegalAccessException, IOException {
        Field[] fields = worker.getClass().getDeclaredFields();
        String data = "";
        data += "Element name: " + worker.getClass() + "\n";
        for (Field field : fields) {
            if (Modifier.isPrivate(field.getModifiers())) {
                field.setAccessible(true);
                data += "Tag: " + field.getName() + ", value: " + field.get(worker) + '\n';
            }
        }
        ObjectToXMLParser parser = new ObjectToXMLParser();
        parser.writeDataToXMLFile(file, data, "worker");
    }
}
