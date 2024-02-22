package astar;

import java.util.HashSet;
import java.util.Set;

public class City {

    private String name;
    private int x;
    private int y;

    private Set<Edge> edges;

    public City(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        edges = new HashSet<>();
    }

    public void addEdge(Edge e) {
        edges.add(e);
    }

    public String name() {
        return name;
    }
    
    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public Set<Edge> edges() {
        return edges;
    }

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
