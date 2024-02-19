package main;

public record Edge(City start, City end, double length) {
    
    @Override
    public String toString() {
        return start + " -> " + end + " : " + length;
    }

}
