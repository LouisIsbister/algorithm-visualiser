package astar;

public record City(String name, int x, int y) {
    
    public double distanceTo(City other) {

        double a = Math.abs(other.y - this.y);
        double b = Math.abs(other.x - this.x);

        double c = Math.sqrt(a * a + b * b);

        return c;
    }

    @Override
    public String toString() {
        return this.name + " " + x + " " + y;
    }

}
