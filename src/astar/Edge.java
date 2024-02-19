package astar;

public record Edge(City start, City end, double length) implements Comparable<Edge> {

    @Override
    public int compareTo(Edge o) {
        return Double.compare(o.length, this.length);
    }

    
    @Override
    public String toString() {
        return start + " -> " + end + " : " + length;
    }

}
