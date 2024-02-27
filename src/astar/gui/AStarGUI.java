package astar.gui;

import javax.swing.JFrame;

import astar.AStarDataLoader;

public class AStarGUI extends JFrame {

	/**
	 * the panel that displays the diagrams
	 */
	private static AStarGraphic visualiser = new AStarGraphic();

	public AStarGUI() {
		super("A Star visualiser");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// load in the A* data once
		AStarDataLoader.load();
		getContentPane().add(visualiser);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
