package app.collection;


public class Coordinates {
    private double x; //Максимальное значение поля: 212
    private Float y; //Поле не может быть null

    public Coordinates() {}

    public Coordinates(double x, Float y) {
        this.x  = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "x = " + x + "; y = " + y;
    }

    public boolean check() {
        return x <= 212 && y != null;
    }
}