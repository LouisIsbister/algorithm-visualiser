package tree_traversals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import tree_traversals.DFS.Order;

public class TreeVisualiser extends JPanel {
    
    private List<Node> renderNodes = new ArrayList<>();

    private Timer timer;

    private JButton dfsPreBut = new JButton("DFS: Pre-order");
    private JButton dfsPostBut = new JButton("DFS: Post-order");
    private JButton bfsBut = new JButton("BFS");

    private int renderPtr = 0;

    public TreeVisualiser() {
        setLayout(null);
		setPreferredSize(new Dimension(_main.GUI.WIDTH, _main.GUI.HEIGHT));
		setOpaque(true);
        setBounds(0, 0, _main.GUI.WIDTH, _main.GUI.HEIGHT);
        //setVisible(true);

        setNodeCoords();
        setUpButtons();

        renderNodes.addAll(Tree.nodes().values());
                
        timer = new Timer(500, event -> {
            visualise();
        });
    }

    private void setNodeCoords() {
        Queue<Node> q = new ArrayDeque<>();
        q.add(Tree.root());
        
        int xwid = _main.GUI.WIDTH;
        int yfact = _main.GUI.HEIGHT / Tree.treeDepth();
        
        while (!q.isEmpty()) {
            Node n = q.poll();

            int x = (xwid / (1 + Tree.totalNodesOnLevel(n.level()))) * Tree.indexOf(n);
            int y = yfact * (n.level() - 1) + 50;
            n.setCoordinates(x, y);
            
            for (Node c :n.children()) {
                q.add(c);
            }
        }
    }

    private void setUpButtons() {
        int wid = 135;
        dfsPreBut.setBounds(10, 10, wid, 20);
        dfsPostBut.setBounds(10, 35, wid, 20);
        bfsBut.setBounds(10, 60, wid, 20);

        addButtonListener(dfsPreBut, DFS.performDFS(Order.PREORDER));
        addButtonListener(dfsPostBut, DFS.performDFS(Order.POSTORDER));
        addButtonListener(bfsBut, BFS.performBFS());

        add(dfsPreBut);
        add(dfsPostBut);
        add(bfsBut);
    }

    private void addButtonListener(JButton b, List<Node> col) {
        b.addActionListener((e) -> {
            renderPtr = 0;
            renderNodes = col;
            timer.start();
        });
    }

    private void visualise() {
        if (renderPtr < renderNodes.size()) {
            renderPtr++;
            revalidate();
            repaint();
        }
        else {
            renderPtr = 0;
            timer.stop();
        }
    }

    /**
	 * draw the diagrams
	 * 
	 * @param g
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(getBackground());
    	g2D.fillRect(0, 0, getWidth(), getHeight());
		g2D.setColor(Color.BLACK);

        for (int i = 0; i < renderNodes.size(); i++) {
            Node n = renderNodes.get(i);
            for (Node c : n.children())
                g2D.drawLine(n.x(), n.y(), c.x(), c.y());

            if (i < renderPtr)
                g2D.setColor(Color.GREEN);
            else
                g2D.setColor(Color.WHITE);
            
            g2D.fillOval(n.x() - 10, n.y() - 10, 20, 20);
            g2D.setColor(Color.BLACK);
            g2D.drawOval(n.x() - 10, n.y() - 10, 20, 20);
            g2D.drawString(n.name(), n.x() - 4, n.y() + 5);

        }
	}

    public void stop() {
		if (timer != null) 
			timer.stop();
        
        renderPtr = 0;
	}

}
