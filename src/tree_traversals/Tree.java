package tree_traversals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    public static int depth() {
        return nodes.values().stream()
            .max((Node n1, Node n2)-> {
                return n1.level() - n2.level();
            })
            .get()
            .level();
    }

    public static int indexOf(Node n) {
        if (n == null)
            return 1;

        List<Node> ns = nodes.values().stream()
            .filter(node-> node.level() == n.level())
            .collect(Collectors.toList());
        ns.sort((Node o1, Node o2) -> {
            return o1.name().compareTo(o2.name());
        });

        return ns.indexOf(n) + 1;
    } 

    
    public static int totalNodesOnLevel(int level) {
        return (int) nodes.values().stream()
                .filter(n-> n.level() == level)
                .count();
    }

    public static String printTree() {
        Queue<Node> q = new ArrayDeque<>();
        StringBuilder ret = new StringBuilder();
        q.add(root);
        int spaces = 20;
        int currentLevel = 1;

        while (!q.isEmpty()) {
            Node n = q.poll();
            int offset = n.level() == 1 || totalNodesOnLevel(n.level()) > totalNodesOnLevel(n.level() - 1) ?
                    spaces / totalNodesOnLevel(n.level()) : spaces / totalNodesOnLevel(n.level() - 1);

            if (currentLevel < n.level()) {
                ret.append("\n");
                currentLevel = n.level();
            }
            if (indexOf(n) ==1)
                ret.append(" ".repeat((offset - 1) * indexOf(n.parent())) + n.name() + " ".repeat(offset));
            else 
                ret.append(" ".repeat(offset) + n.name() + " ".repeat(offset));

            for (Node c : n.children()) {
                q.add(c);
            }
        }

        return ret.toString();
    }
}
