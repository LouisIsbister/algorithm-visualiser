package treetraversals;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class BFS {

    public static List<Node> performBFS() {
        List<Node> ret = new ArrayList<>();
        Node root = Tree.root();
        Queue<Node> q = new ArrayDeque<>();
        q.add(root);

        while (!q.isEmpty()) {
            Node n = q.poll();
            ret.add(n);

            for (Node c : n.children())
                q.add(c);
        }

        return ret;
    }
    
}
