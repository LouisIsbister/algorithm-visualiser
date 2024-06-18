package controller;

import astar.AStarDataLoader;
import treetraversals.Tree;

public class Main {
    
    public static void main(String[] args) {
        AStarDataLoader.load();
		Tree.load();
        new Thread(()-> {
            new GUI();
        }).start();
    }

}