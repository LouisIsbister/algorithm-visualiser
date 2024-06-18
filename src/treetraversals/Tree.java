package treetraversals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Tree {
    
    private static Node root;

    private static Map<String, Node> nodes;

    public static void load() {
        try {
            loadData();
        } catch(FileNotFoundException e) {}
    }

    public static Node root() {
        return root;
    }

    public static Map<String, Node> nodes() {
        return nodes;
    }

    public static void loadData() throws FileNotFoundException {
        File f = new File("src\\tree_traversals\\data\\data.txt");
        Scanner scan = new Scanner(f);
        nodes = new HashMap<>();

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] arr = line.split("\s");

            if (arr.length == 2) {
                String name = arr[0];
                int level = Integer.parseInt(arr[1]);
                nodes.put(name, new Node(name, level));
            }
            else {
                arr = line.split("->");
                Node parent = nodes.get(arr[0]);
                Node child = nodes.get(arr[1]);
                parent.addChild(child);
                child.setParent(parent);
            }
        }

        root = nodes.get("A");
        scan.close();
    }

    public static int treeDepth() {
        return nodes.values().stream()
            .map(n->n.level())
            .max((n1, n2)-> n1 - n2)
            .orElse(0);
    }

    public static int indexOnLevel(Node n) {
        if (n == null)
            return 0;

        List<Node> ns = nodes.values().stream()
            .filter(node-> node.level() == n.level())
            .toList();

        ns.sort((o1, o2) -> o1.name().compareTo(o2.name()));

        return ns.indexOf(n) + 1;
    } 
    
    public static int totalNodesOnLevel(int level) {
        return (int) nodes.values().stream()
                .filter(n-> n.level() == level)
                .count();
    }
}
