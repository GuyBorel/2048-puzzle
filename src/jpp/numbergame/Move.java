package jpp.numbergame;

public class Move {

    private Coordinate2D from;
    private Coordinate2D to;
    private int oldValue;
    private int newValue;
    public Move(Coordinate2D from, Coordinate2D to, int oldValue, int newValue){
         if (oldValue < 1 || newValue < 1){
             throw new IllegalArgumentException();
         }
         this.from = from;
         this.to = to;
         this.oldValue = oldValue;
         this.newValue = newValue;
    }

    public Move(Coordinate2D from, Coordinate2D to, int value){
        if (value < 1){
            throw new IllegalArgumentException();
        }
        this.from = from;
        this.to = to;
        this.oldValue = value;
        this.newValue = value;
    }

    public Coordinate2D getFrom() {
        return this.from;
    }

    public Coordinate2D getTo() {
        return this.to;
    }

    public int getOldValue() {
        return this.oldValue;
    }

    public int getNewValue() {
        return this.newValue;
    }

    public boolean isMerge(){
        return oldValue != newValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;

        Move move = (Move) o;

        if (oldValue != move.oldValue) return false;
        if (newValue != move.newValue) return false;
        if (!from.equals(move.from)) return false;
        return to.equals(move.to);
    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + oldValue;
        result = 31 * result + newValue;
        return result;
    }

    @Override
    public String toString() {
        if (isMerge()){
            return from + " = " + this.oldValue + " -> " + to + " = " + this.newValue + " (M)";
        }
        return from + " = " + this.oldValue + " -> " + to + " = " + this.newValue;
    }
}
