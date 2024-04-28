package _main;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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

	public static void diplayError(String msg) {
		JDialog dialog = new JDialog();
		JPanel panel = new JPanel();
		panel.setVisible(true);
		panel.setPreferredSize(new Dimension(450, 300));
		panel.setLayout(null);

		JLabel label = new JLabel(msg);
		label.setFont(new java.awt.Font("Monospaced", 1, 15));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 0, 450, 200);

		JButton contButton = new JButton("Continue");
		contButton.addActionListener(e-> dialog.dispose());
		contButton.setBounds(175, 200, 100, 50);

		panel.add(contButton);
		panel.add(label);
		dialog.add(panel);
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	} 
}
