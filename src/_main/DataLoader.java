package _main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DataLoader {

    // x min = 167
    // y min = 35

    private static final int SCALE_FACTER = 50;

    private static Map<String, City> cities;

    private static List<Edge> edges;

    public static void load() {
        try {
            loadCities();
            loadEdges();
        } catch (FileNotFoundException e) {
        }
    }

    private static void loadCities() throws FileNotFoundException {
        File f = new File("src\\cities.txt");
        Scanner scan = new Scanner(f);
        cities = new HashMap<>();

        while (scan.hasNextLine()) {
            String[] arr = scan.nextLine().split("\t");

            if (arr.length != 3) {
                scan.close();
                return;
            }

            try {
                String cityName = arr[0].trim();
                double x = Math.abs(Double.parseDouble(arr[2].trim()));
                double y = Math.abs(Double.parseDouble(arr[1].trim()));

                x = (x - 167) * SCALE_FACTER;
                y = (y - 34) * SCALE_FACTER;

                cities.put(cityName, new City(cityName, (int) x, (int) y));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                scan.close();
                return;
            }
        }

        scan.close();
    }

    private static void loadEdges() throws FileNotFoundException {
        File f = new File("src\\edges.txt");
        Scanner scan = new Scanner(f);
        edges = new ArrayList<>();

        while (scan.hasNextLine()) {
            String[] arr = scan.nextLine().split("\t");

            if (arr.length != 2) {
                scan.close();
                return;
            }

            City city1 = cities.get(arr[0]);
            City city2 = cities.get(arr[1]);
            Edge e = new Edge(city1, city2, city1.distanceTo(city2));
            edges.add(e);
        }

        scan.close();
    }

    public static List<City> cities() {
        return cities.values().parallelStream().toList();
    }

    public static List<Edge> edges() {
        return edges;
    }

    // cities.sort(new Comparator<City>() {
    // @Override
    // public int compare(City o1, City o2) {
    // return Double.compare(o2.y(), o1.y());
    // }
    // });

    // for (City c : cities) System.out.println(c);
    // System.out.println("\n");

    // cities.sort(new Comparator<City>() {
    // @Override
    // public int compare(City o1, City o2) {
    // return Double.compare(o2.y(), o1.y());
    // }
    // });
    // for (City c : cities) System.out.println(c);

}
