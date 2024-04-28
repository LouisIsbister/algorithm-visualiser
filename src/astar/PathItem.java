package astar;

public record PathItem(City city, Edge edge, double pathCost, double estCost) implements Comparable<PathItem> {
    
    @Override
    public int compareTo(PathItem other){
        return Double.compare(this.estCost, other.estCost);
    }
    
}
