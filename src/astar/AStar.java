package astar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class AStar {

    public AStar(String start, String end) {
        Map<String, City> cities = AStarDataLoader.cities();

        if (cities.get(start) == null || cities.get(end) == null)
            return;

        System.out.println(findShortestPath(cities.get(start), cities.get(end)));
    }

    public static List<City> findShortestPath(City start, City goal) {
        if (start == null || goal == null)
            return null;

        Set<City> visited = new HashSet<>();
        PriorityQueue<PathItem> queue = new PriorityQueue<>();
        HashMap<City, Edge> backpointers = new HashMap<>();

        PathItem pI = new PathItem(start, null, 0, start.distanceTo(goal));
        queue.add(pI);

        while (!queue.isEmpty()) {
            PathItem p = queue.poll();
            City curStop = p.city();

            System.out.println(curStop);

            if (!visited.contains(curStop)) {
                visited.add(curStop);
                backpointers.put(curStop, p.edge());
                if (curStop == goal) {
                    return reconstructPath(start, goal, backpointers);
                }
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

    private static List<City> reconstructPath(City start, City goal, HashMap<City, Edge> backpointers) {
        List<City> res = new ArrayList<>();
        res.add(start);
        City cptr = goal;

        do {
            Edge e = backpointers.get(cptr);
            res.add(e.start());
            cptr = e.start();
        } while (cptr != start);

        return res;
    }

}
