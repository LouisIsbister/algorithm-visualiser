package main;

import java.util.List;
import gui.AppFrame;

public class Main {
    
    public static void main(String[] args) {
        DataLoader.load();
        List<City> cities = DataLoader.cities();
        List<Edge> edges = DataLoader.edges();

        AppFrame gui = new AppFrame();
    }

}