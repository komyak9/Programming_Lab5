package managering.instruments;

import content.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class WorkerManager {
    public String getWorkerInfo(Worker worker) {
        return "id: " + worker.getId() +
                "\t\tName: " + worker.getName() +
                "\t\tSalary: " + worker.getSalary() +
                "\t\tStart date: " + worker.getStartDate() +
                "\t\tEnd date: " + worker.getEndDate() +
                "\t\tcontent.Position: " + worker.getPosition() +
                "\t\tcontent.Organization: " + worker.getOrganization().getType() +
                ", name: " + worker.getOrganization().getOfficialAddress().getTown().getName();
    }

    public Worker createWorker() throws ParseException {
        String workerName;
        Coordinates coordinates;
        long coordinatesX;
        int coordinatesY;
        float salary;

        LocalDateTime startDate;
        Date endDate;

        Position position;
        Organization organization;
        int annualTurnover;
        OrganizationType type;
        Address address;
        String zipCode;

        Location location;
        String locationName;
        int locationX;
        int locationY;

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter a name: ");
            workerName = scanner.nextLine();
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " Please, try again: ");
            workerName = scanner.nextLine();
        }

        System.out.println("Enter coordinates: ");
        try {
            System.out.println("Enter \"x\" value: ");
            coordinatesX = scanner.nextLong();
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " Please, try again: ");
            coordinatesX = scanner.nextLong();
        }
        try {
            System.out.println("Enter \"y\" value: ");
            coordinatesY = scanner.nextInt();
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " Please, try again: ");
            coordinatesY = scanner.nextInt();
        }

        try {
            System.out.println("Enter salary: ");
            salary = scanner.nextFloat();
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " Please, try again: ");
            salary = scanner.nextFloat();
        }
        scanner.nextLine();

        try {
            System.out.println("Enter start date (example: 1986-04-08 12:30):");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            startDate = LocalDateTime.parse(scanner.nextLine(), formatter);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " Please, try again: ");
            startDate = ZonedDateTime.parse(scanner.nextLine()).toLocalDateTime();
        }

        try {
            System.out.println("Enter end date (example: 04/08/1999): ");
            endDate = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " Please, try again: ");
            endDate = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
        }

        try {
            System.out.println("Enter position, choose from a list: ");
            for (Position positions : Position.values()) {
                System.out.print(positions + " ");
            }
            System.out.println();
            position = Position.valueOf(scanner.nextLine());
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " Please, try again: ");
            position = Position.valueOf(scanner.nextLine());
        }

        System.out.println("Enter info about organization: ");
        try {
            System.out.println("Enter annual turnover: ");
            annualTurnover = scanner.nextInt();
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " Please, try again: ");
            annualTurnover = scanner.nextInt();
        }
        scanner.nextLine();
        try {
            System.out.println("Enter organization type, choose from a list: ");
            for (OrganizationType organizationType : OrganizationType.values()) {
                System.out.print(organizationType + " ");
            }
            System.out.println();
            type = OrganizationType.valueOf(scanner.nextLine());
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " Please, try again: ");
            type = OrganizationType.valueOf(scanner.nextLine());
        }
        System.out.println("Enter official address of the organization: ");
        try {
            System.out.println("Enter zip code: ");
            zipCode = scanner.nextLine();
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " Please, try again: ");
            zipCode = scanner.nextLine();
        }
        System.out.println("Enter location of the organization: ");
        try {
            System.out.println("Enter \"x\" value: ");
            locationX = scanner.nextInt();
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " Please, try again: ");
            locationX = scanner.nextInt();
        }
        try {
            System.out.println("Enter \"y\" value: ");
            locationY = scanner.nextInt();
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " Please, try again: ");
            locationY = scanner.nextInt();
        }
        scanner.nextLine();
        try {
            System.out.println("Enter name of the town: ");
            locationName = scanner.nextLine();
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " Please, try again: ");
            locationName = scanner.nextLine();
        }

        location = new Location(locationX, locationY, locationName);
        address = new Address(zipCode, location);
        organization = new Organization(annualTurnover, type, address);
        coordinates = new Coordinates(coordinatesX, coordinatesY);
        return new Worker(workerName, coordinates, salary, startDate, endDate, position, organization);
    }
}
