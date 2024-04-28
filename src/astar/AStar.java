package astar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class AStar {

    /**
     * Map of backpointers where each key corresponds to the edge
     * that was traversed to get that city  
     */
    private static Map<City, Edge> backPtrs;

    /** 
     * A list used for storing every all the cities the algorithm 
     * went to, even if they were not the shortest path. 
     * It is used for rendering the choices the algorithm makes. 
     */
    private static List<City> pathsTaken;

    /**
     * @return each visited city (in order)
     */
    public static List<City> allPathsTaken() {
        return pathsTaken == null ? List.of() : pathsTaken;
    }

    /**
     * Find the shortest path using A* algorithm
     * 
     * @param start city to being the search from
     * @param goal destination
     * @return list of cities that make up the shortest path
     */
    public static List<City> findShortestPath(City start, City goal) {
        if (start == null || goal == null)
            throw new IllegalArgumentException("<html><center>Start or goal have not been resolved."
                + "<br>Cannot perform search.</center></html>"); 
        
 
        backPtrs = new HashMap<>();
        pathsTaken = new ArrayList<>();

        Set<City> visited = new HashSet<>();
        PriorityQueue<PathItem> queue = new PriorityQueue<>();

        PathItem pI = new PathItem(start, null, 0, start.distanceTo(goal));
        queue.add(pI);
        while (!queue.isEmpty()) {
            PathItem pi = queue.poll();
            City city = pi.city();

            // ---- doesn't affect the algorithm itself
            pathsTaken.add(city);
            // ----

            if (!visited.contains(city)) {
                visited.add(city);
                backPtrs.put(city, pi.edge());

                if (city == goal)
                    return reconstructPath(start, goal);
                
                for (Edge e : city.edges()) {
                    City to = e.end();
                    if (!visited.contains(to)) {
                        double currentPathDistance = pi.pathCost() + e.length();

                        // variable allows the algorithm to traverse multiple 
                        // paths/options instead of just one that may be inaccurate 
                        double estimatedPathCost = currentPathDistance + to.distanceTo(goal);
                        PathItem newItem = new PathItem(to, e, currentPathDistance, estimatedPathCost);
                        queue.add(newItem);
                    }
                }
            }
        }
        return new ArrayList<City>();
    }

    /**
     * Retrace each edge from the goal to the start to 
     * construct the shortest path 
     * 
     * @param start
     * @param goal
     * @return the shortest path that was found
     */
    private static List<City> reconstructPath(City start, City goal) {
        if (start == goal)
            return List.of(start);

        List<City> res = new ArrayList<>();
        res.add(goal);
        City cptr = goal;
        // traverse backptrs as the 'end' city
        do {
            Edge e = backPtrs.get(cptr);
            res.add(e.start());
            cptr = e.start();
        } while (cptr != start);

        return res;
    }

}
