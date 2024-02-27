package _main;

import astar.gui.AStarGUI;

public class Main {
    
    public static void main(String[] args) {
        // A* visualiser
        new Thread(()-> {
            new AStarGUI();
        }).start();
    }

}