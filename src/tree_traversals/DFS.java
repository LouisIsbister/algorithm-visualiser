package tree_traversals;

import java.util.ArrayList;
import java.util.List;

public class DFS {
    
    public enum Order {PREORDER, POSTORDER};

    public static List<Node> performDFS(Order o) {
        List<Node> ret = new ArrayList<>();
        Node root = Tree.root();
        // Queue<Node> q = new ArrayDeque<>();
        return o == Order.PREORDER ? preOrder(root, ret): postOrder(root, ret);
    }

    private static List<Node> preOrder(Node root, List<Node> ret) {
        if (root == null) 
            return ret;

        ret.add(root);
        for (Node c: root.children())
            preOrder(c, ret);

        return ret;
    }

    private static List<Node> postOrder(Node root, List<Node> ret) {
        if (root == null) 
            return ret;
        
        for (Node c: root.children())
            postOrder(c, ret);
        ret.add(root);

        return ret;
    }

}
