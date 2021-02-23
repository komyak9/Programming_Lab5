package content;

public class Location {
    private Integer x; //Поле не может быть null
    private int y;
    private String name; //Длина строки не должна быть больше 429, Поле может быть null

    public Location(Integer x, int y, String name) {
        try {
            if (x == null)
                throw new Exception("Error. \"x\" can't be null.");
            else if (name == null)
                throw new Exception("Error. Name of the location can't be null.");

            setX(x);
            setY(y);
            setName(name);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        try {
            if (name.length() <= 429) {
                this.name = name;
            } else {
                throw new Exception("Wrong length. Name length must me <= 429.");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public String toString() {
        return "content.content.Location{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
    }
}
