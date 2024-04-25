package _main;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import astar.AStarVisualiser;
import shunting_yard.ShuntingYardVisualiser;
import tree_traversals.TreeVisualiser;

public class GUI extends JFrame {

	public static final int WIDTH = 625;
	public static final int HEIGHT = 700;

	private final AStarVisualiser aStarVisualiser = new AStarVisualiser();
	private final TreeVisualiser treeVisualiser = new TreeVisualiser();
	private final ShuntingYardVisualiser shuntingYardVis = new ShuntingYardVisualiser();

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
		JMenuItem sya = new JMenuItem("Shunting Yard Math Evaluation");
		
		menus.add(astar);
		menus.add(trees);
		menus.add(sya);
		menu.add(menus);
		setJMenuBar(menu);
		
		addActionList(astar, aStarVisualiser);
		addActionList(trees, treeVisualiser);
		addActionList(sya, shuntingYardVis);
	}

	private void addActionList(JMenuItem menu, JPanel p) {
		menu.addActionListener((e) -> {
			aStarVisualiser.stop();
			treeVisualiser.stop();
			getContentPane().removeAll();
			getContentPane().add(p);
			revalidate();
			repaint();
		});
	}
}
