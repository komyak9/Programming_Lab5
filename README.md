# Console application that implements interactive management of a collection of objects

The collection must store objects of the Worker class, the description of which is given below.

#### The developed program must meet the following requirements:

1. The class whose collection of instances the program manages must implement sorting by default.
2. All class field requirements (specified as comments) must be met.
3. For storage, you must use a collection of type java.util.LinkedList.
4. When the application starts, the collection should automatically populate with the values from the file.
5. The filename must be passed to the program with: command line argument.
6. The data must be stored in a file in xml format.
7. Reading data from a file must be implemented using the java.util.Scanner class.
8. Writing data to a file must be implemented using the java.io.FileOutputStream class.
9. All classes in a program must be documented in javadoc format.
10. The program should work correctly with incorrect data (user input errors, lack of file access rights, etc.).

#### In interactive mode, the program must support the execution of the following commands:

1. help : show help for available commands
2. info : print information about the collection to standard output (type, initialization date, number of elements, etc.)
3. show : print to standard output all elements of the collection in string representation
4. add {element} : add a new element to the collection
5. update id {element} : update the value of the collection element whose id is equal to the given one
6. remove_by_id id : remove an element from the collection by its id
7. clear : clear the collection
8. save : save the collection to a file
9. execute_script file_name : read and execute script from specified file. The script contains commands in the same form in which they are entered by the user in interactive mode.
exit : exit the program (without saving to a file)
10. add_if_max {element} : add a new element to the collection if its value is greater than the value of the largest element in this collection
11. add_if_min {element} : add a new element to the collection if its value is less than the smallest element in this collection
12. remove_greater {element} : remove from the collection all elements greater than the given one
13. remove_all_by_position position : remove from the collection all elements whose position field value is equivalent to the given one
14. remove_any_by_salary salary : Remove one element from the collection whose salary field value is equivalent to the given one
15. print_descending : Print the elements of a collection in descending order

#### Command input format:

1. All command arguments that are standard data types (primitive types, wrapper classes, String, date storage classes) must be entered on the same line as the command name.
2. All composite data types (objects of classes stored in a collection) must be entered one field per line.
3. When entering composite data types, the user should be shown a prompt containing the name of the field (for example, "Enter date of birth:")
4. If the field is an enum, then the name of one of its constants is entered (in this case, the list of constants must be previously displayed).
5. In case of incorrect user input (a string is entered that is not the name of a constant in the enum; a string is entered instead of a number; the entered number is not within the specified limits, etc.) an error message should be displayed and the field should be asked to repeat.
6. To enter null values, use an empty string.
7. Fields with the comment "The value of this field should be generated automatically" should not be entered manually by the user when adding.

#### Description of the classes stored in the collection:

public class Worker {
    
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    
    private String name; //Поле не может быть null, Строка не может быть пустой
    
    private Coordinates coordinates; //Поле не может быть null
    
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    
    rivate long salary; //Значение поля должно быть больше 0
    
    private java.time.ZonedDateTime startDate; //Поле не может быть null
    
    private java.util.Date endDate; //Поле может быть null
    
    private Position position; //Поле не может быть null
    
    private Organization organization; //Поле не может быть null

}

public class Coordinates {
    
    private float x; //Значение поля должно быть больше -690
    
    private int y; //Значение поля должно быть больше -247

}

public class Organization {
    
    private int annualTurnover; //Значение поля должно быть больше 0
    
    private OrganizationType type; //Поле не может быть null
    
    private Address officialAddress; //Поле не может быть null

}

public class Address {
    
    private String zipCode; //Длина строки должна быть не меньше 6, Поле не может быть null
    
    private Location town; //Поле не может быть null

}

public class Location {
    
    private Double x; //Поле не может быть null
    
    private int y;
    
    private String name; //Длина строки не должна быть больше 429, Поле может быть null

}

public enum Position {
    
    MANAGER,
    
    HUMAN_RESOURCES,
    
    BAKER,
    
    COOK,
    
    CLEANER;

}

public enum OrganizationType {
    
    COMMERCIAL,
    
    PUBLIC,
    
    GOVERNMENT,
    
    TRUST,
    
    OPEN_JOINT_STOCK_COMPANY;

}
