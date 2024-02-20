package astar.gui;

import javax.swing.JFrame;

import astar.AStarDataLoader;

public class AStarGUI extends JFrame {

	/**
	 * the panel that displays the diagrams
	 */
	private static AppPanel guiPanel = new AppPanel();

	public AStarGUI() {
		// load in the A* data
		AStarDataLoader.load();

		setTitle("A Star visualiser");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		getContentPane().add(guiPanel);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
