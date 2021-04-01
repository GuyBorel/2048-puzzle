package jpp.numbergame;

public class Tile {

    private Coordinate2D coord;
    private int value;
    public Tile(Coordinate2D coord, int value){
        if (value < 1){
            throw new IllegalArgumentException();
        }
        this.coord = coord;
        this.value = value;
    }

    public Coordinate2D getCoordinate(){
        return coord;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;

        Tile tile = (Tile) o;

        if (value != tile.value) return false;
        return coord.equals(tile.coord);
    }

    @Override
    public int hashCode() {
        int result = coord.hashCode();
        result = 31 * result + value;
        return result;
    }
}
