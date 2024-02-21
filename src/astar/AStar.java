package astar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class AStar {

    private static Map<City, Edge> backptrs;

    public static Map<City, Edge> getBackptrs() {
        return backptrs;
    }

    public static List<City> findShortestPath(City start, City goal) {
        if (start == null || goal == null)
            return null;

        Set<City> visited = new HashSet<>();
        backptrs = new HashMap<>();
        PriorityQueue<PathItem> queue = new PriorityQueue<>();

        PathItem pI = new PathItem(start, null, 0, start.distanceTo(goal));
        queue.add(pI);

        while (!queue.isEmpty()) {
            PathItem p = queue.poll();
            City curStop = p.city();

            if (!visited.contains(curStop)) {
                visited.add(curStop);
                backptrs.put(curStop, p.edge());

                if (curStop == goal)
                    return reconstructPath(start, goal);
                
                for (Edge e : curStop.edges()) {
                    City to = e.end();
                    if (!visited.contains(to)) {
                        double lengthToNeigh = p.cost() + e.length();
                        double estTotalDist = lengthToNeigh + to.distanceTo(goal);
                        PathItem pi = new PathItem(to, e, lengthToNeigh, estTotalDist);
                        queue.add(pi);
                    }
                }
            }
        }
        return new ArrayList<City>();
    }

    private static List<City> reconstructPath(City start, City goal) {
        if (start == goal)
            return List.of(start);

        List<City> res = new ArrayList<>();
        res.add(goal);
        City cptr = goal;
        // traverse backptrs as the 'end' city
        do {
            Edge e = backptrs.get(cptr);
            res.add(e.start());
            cptr = e.start();
        } while (cptr != start);

        return res;
    }

}
