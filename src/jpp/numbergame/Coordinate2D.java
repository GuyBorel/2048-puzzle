package jpp.numbergame;

public class Coordinate2D {

    private final int x;
    private final int y;
    public Coordinate2D(int x, int y){
        if (x < 0 || y < 0){
            throw new IllegalArgumentException("value negative");
        }
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return  "(" + x + "," + y + ")" ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate2D)) return false;

        Coordinate2D that = (Coordinate2D) o;

        if (x != that.x) return false;
        return y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
