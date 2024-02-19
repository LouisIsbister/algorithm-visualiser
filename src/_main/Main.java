package _main;

import astar.AStar;
import astar.AStarDataLoader;
import gui.AppFrame;

public class Main {
    
    public static void main(String[] args) {
        // A* visualiser
        AStarDataLoader.load();
        AppFrame gui = new AppFrame();
        
        new AStar();

    }

}