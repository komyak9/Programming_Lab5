package content;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

public class Worker implements Comparable<Worker> {
    private final Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private float salary; //Значение поля должно быть больше 0
    private LocalDateTime startDate; //Поле не может быть null
    private Date endDate; //Поле может быть null
    private Position position; //Поле не может быть null
    private Organization organization; //Поле не может быть null

    public Worker(String name, Coordinates coordinates, float salary, LocalDateTime startDate, Date endDate, Position position, Organization organization) {
        creationDate = ZonedDateTime.now();
        try {
            if (name == null)
                throw new Exception("Error. Name of the worker can't be null.");
            else if (coordinates == null)
                throw new Exception("Error. content.content.Coordinates of the worker can't be null.");
            else if (startDate == null)
                throw new Exception("Error. Start date of the worker can't be null.");
            else if (position == null)
                throw new Exception("Error. content.content.Position of the worker can't be null.");
            else if (organization == null)
                throw new Exception("Error. content.content.Organization of the worker can't be null.");

            setName(name);
            setCoordinates(coordinates);
            setSalary(salary);
            setStartDate(startDate);
            setEndDate(endDate);
            setPosition(position);
            setOrganization(organization);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " Please, add info and try again.");
        }
        id = this.hashCode();
    }

    public int compareTo(Worker worker) {
        return id.compareTo(worker.getId());
    }

    public int getId() {
        return id;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        try {
            if (name.equals("")) {
                throw new Exception("Error. content.content.Worker's name can't be empty.");
            } else
                this.name = name;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, salary, startDate, endDate, position, organization);
    }
}
