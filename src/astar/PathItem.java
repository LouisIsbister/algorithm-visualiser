package astar;

public class PathItem implements Comparable<PathItem> {
    
    private City city;
    private Edge edge;
    
    private double currentPathCost;
    private double estimatedCost;
    
    public PathItem(City c, Edge e, double cpc, double ec) { 
        city = c;
        edge = e;
        currentPathCost = cpc;
        estimatedCost = ec;
    }
    
    public City city() {
        return city;
    }

    public Edge edge() {
        return edge;
    }

    public double cost() {
        return currentPathCost;
    }

    @Override
    public int compareTo(PathItem pi){
        return Double.compare(this.estimatedCost, pi.estimatedCost);
    }
    
}
