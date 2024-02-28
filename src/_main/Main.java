package _main;

import astar.AStarDataLoader;
import tree_traversals.Tree;

public class Main {
    
    public static void main(String[] args) {
        // A* visualiser
        AStarDataLoader.load();
		Tree.load();
        new Thread(()-> {
            new GUI();
        }).start();
    }

}