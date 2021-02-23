package content;

public class Coordinates {
    private long x; //Значение поля должно быть больше -690
    private int y; //Значение поля должно быть больше -247

    public Coordinates(long x, int y) {
        setX(x);
        setY(y);
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        try {
            if (x > -690) {
                this.x = x;
            } else {
                throw new Exception("Wrong value. Coordinate \"x\" must be > -690.");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        try {
            if (y > -247) {
                this.y = y;
            } else {
                throw new Exception("Wrong value. Coordinate \"y\" must be > -247.");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public String toString() {
        return "content.content.Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
