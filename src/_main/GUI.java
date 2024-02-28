package _main;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import astar.AStarVisualiser;
import tree_traversals.TreeVisualiser;

public class GUI extends JFrame {

	public static final int WIDTH = 625;
	public static final int HEIGHT = 700;

	private final AStarVisualiser aStarVisualiser = new AStarVisualiser();
	private final TreeVisualiser treeVisualiser = new TreeVisualiser();

	public GUI() {
		super("Algorithm visualiser");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		menuSetup();

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void menuSetup() {
		JMenuBar menu = new JMenuBar();

		JMenu menus = new JMenu("Algoithms");
		JMenuItem astar = new JMenuItem("AStar (A*) Algorithm");
		JMenuItem trees = new JMenuItem("Tree Traversal Algorithms");
		
		menus.add(astar);
		menus.add(trees);
		menu.add(menus);
		setJMenuBar(menu);

		astar.addActionListener((e) -> {
			aStarVisualiser.stop();
			treeVisualiser.stop();
			getContentPane().removeAll();
			getContentPane().add(aStarVisualiser);
			revalidate();
			repaint();
		});
		
		trees.addActionListener((e) -> {
			aStarVisualiser.stop();
			treeVisualiser.stop();
			getContentPane().removeAll();
			getContentPane().add(treeVisualiser);
			revalidate();
			repaint();
		});

	}
}
