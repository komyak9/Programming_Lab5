package manager.instruments.workerInstruments;

import content.*;
import manager.instruments.CreationMethodsWrapper;
import manager.instruments.ScannerWrapper;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Formatter;
import java.util.Scanner;

/**
 * Класс для выполнения действий над работниками.
 * @autor komyak9
 */
public class WorkerManager {
    public String getWorkerInfo(Worker worker) {
        return "id: " + worker.getId() +
                "\t\tName: " + worker.getName() +
                "\t\tSalary: " + worker.getSalary() +
                "\t\tStart date: " + worker.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                "\t\tEnd date: " + new Formatter().format("%tD", worker.getEndDate()) +
                "\t\tPosition: " + worker.getPosition() +
                "\t\tOrganization: " + worker.getOrganization().getType() +
                "\t\tCity name: " + worker.getOrganization().getOfficialAddress().getTown().getName();
    }

    public Worker createWorker(Scanner scanner) throws Exception {
        String workerName = null, zipCode = null, locationName = null;
        Coordinates coordinates;
        long coordinatesX = -1000;
        int coordinatesY = -1000, annualTurnover = 0, locationX = 0, locationY = 0;
        float salary = 0;
        LocalDateTime startDate = null;
        Date endDate = null;
        Position position = null;
        Organization organization = null;
        OrganizationType type = null;
        Address address = null;
        Location location = null;

        ScannerWrapper input = new ScannerWrapper(scanner);
        boolean isFilled = false;
        CreationMethodsWrapper wrapper = new CreationMethodsWrapper();

        while (!isFilled) {
            try {
                System.out.println("Enter a name: ");
                wrapper.setWorkerName(input.readData());
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\nPlease, try again: ");
            }
        }
        isFilled = false;

        System.out.println("Enter coordinates: ");
        while (!isFilled) {
            try {
                System.out.println("Enter \"x\" value: ");
                wrapper.setCoordinatesX(Long.parseLong(input.readData()));
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\nPlease, try again: ");
            }
        }
        isFilled = false;
        while (!isFilled) {
            try {
                System.out.println("Enter \"y\" value: ");
                wrapper.setCoordinatesY(Integer.parseInt(input.readData()));
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\nPlease, try again: ");
            }
        }
        isFilled = false;

        while (!isFilled) {
            try {
                System.out.println("Enter salary: ");
                wrapper.setSalary(Float.parseFloat(input.readData()));
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " Please, try again: ");
            }
        }
        isFilled = false;

        while (!isFilled) {
            try {
                System.out.println("Enter start date (example: 1986-04-08 12:30):");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                wrapper.setStartDate(LocalDateTime.parse(input.readData(), formatter));
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " Please, try again: ");
            }
        }
        isFilled = false;

        while (!isFilled) {
            try {
                System.out.println("Enter end date (example: 04/08/1999): ");
                String line = input.readData();
                if (line == null)
                    wrapper.setEndDate(null);
                else
                    wrapper.setEndDate(new SimpleDateFormat("dd/MM/yyyy").parse(input.readData()));
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\nPlease, try again: ");
            }
        }
        isFilled = false;

        while (!isFilled) {
            try {
                System.out.println("Enter position, choose from a list: ");
                for (Position positions : Position.values())
                    System.out.print(positions + " ");

                System.out.println();
                wrapper.setPosition(Position.valueOf(input.readData()));
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\nPlease, try again: ");
            }
        }
        isFilled = false;

        System.out.println("Enter info about organization: ");
        while (!isFilled) {
            try {
                System.out.println("Enter annual turnover: ");
                wrapper.setAnnualTurnover(Integer.parseInt(input.readData()));
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\nPlease, try again: ");
            }
        }
        isFilled = false;
        while (!isFilled) {
            try {
                System.out.println("Enter organization type, choose from a list: ");
                for (OrganizationType organizationType : OrganizationType.values()) {
                    System.out.print(organizationType + " ");
                }
                System.out.println();
                wrapper.setOrganizationType(OrganizationType.valueOf(input.readData()));
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\nPlease, try again: ");
            }
        }
        isFilled = false;

        System.out.println("Enter official address of the organization: ");
        while (!isFilled) {
            try {
                System.out.println("Enter zip code: ");
                wrapper.setZipCode(input.readData());
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\nPlease, try again: ");
            }
        }
        isFilled = false;

        System.out.println("Enter location of the organization: ");
        while (!isFilled) {
            try {
                System.out.println("Enter \"x\" value: ");
                wrapper.setLocationX(Integer.parseInt(input.readData()));
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + "\nPlease, try again: ");
            }
        }
        isFilled = false;
        while (!isFilled) {
            try {
                System.out.println("Enter \"y\" value: ");
                wrapper.setLocationY(Integer.parseInt(input.readData()));
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " Please, try again: ");
            }
        }
        isFilled = false;

        while (!isFilled) {
            try {
                System.out.println("Enter name of the town: ");
                wrapper.setlocationName(input.readData());
                isFilled = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage() + " Please, try again: ");
            }
        }

        location = new Location(wrapper.getLocationX(), wrapper.getLocationY(), wrapper.getLocationName());
        address = new Address(wrapper.getZipCode(), location);
        organization = new Organization(wrapper.getAnnualTurnover(), wrapper.getOrganizationType(), address);
        coordinates = new Coordinates(wrapper.getCoordinatesX(), wrapper.getCoordinatesY());
        return new Worker(WorkerIdHolder.createWorkerId(), ZonedDateTime.now(), wrapper.getWorkerName(), coordinates, wrapper.getSalary(),
                wrapper.getStartDate(), wrapper.getEndDate(), wrapper.getPosition(), organization);
    }
}
