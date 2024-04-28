package astar;

public record Edge(City start, City end, double length) {
    
    @Override
    public String toString() {
        return "EDGE [" + start + " -> " + end + " : " + length + "]";
    }

}
