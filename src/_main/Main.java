package _main;

import astar.AStarDataLoader;
import shunting_yard.ShuntingYard;
import shunting_yard.ShuntingYardVisualiser;
import tree_traversals.Tree;

public class Main {
    
    public static void main(String[] args) {
        // A* visualiser
        AStarDataLoader.load();
		Tree.load();
        new Thread(()-> {
            new GUI();
        }).start();
        try {
            // ShuntingYard.algorithm("3 + 4 * 2 / (1 - 5) ^ 2 ^ 3");
            // ShuntingYard.algorithm("(2 * 5 - 5) ^ 2");
            // ShuntingYard.algorithm("32 / 2 ^ 2");
            // ShuntingYard.algorithm("1 - 5 * 4 + 100 / 2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}