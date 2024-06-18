package treetraversals;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private final String name;
    
    private final int level;
    
    private List<Node> children;

    private Node parent;
    
    public Node(String name, int level) {
        this.name = name;
        this.level = level;
        this.children = new ArrayList<>();
    }

    public String name() {
        return name;
    }

    public int level() {
        return level;
    }

    public List<Node> children() {
        return children;
    }

    public Node parent() {
        return parent;
    }

    public void setParent(Node n) {
        parent = n;
    }

    public void addChild(Node n) {
        children.add(n);
    }

    private int x;
    private int y;

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    @Override
    public String toString() {
        String str = name;
        for (Node n : children) {
            str += "\n" + name + " -> " + n.name();
        }
        return str;
    }

}
