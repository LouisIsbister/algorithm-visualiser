package gui;

import javax.swing.JFrame;

public class AppFrame extends JFrame {

	/**
	 * the panel that displays the diagrams
	 */
	private static AppPanel guiPanel = new AppPanel();

	public AppFrame() {
		setName("A Star visualiser");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		getContentPane().add(guiPanel);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
