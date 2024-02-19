package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import main.City;
import main.DataLoader;
import main.Edge;


/**
 * panel that displays the Venn diagrams
 */
public class AppPanel extends JPanel {

	public static final int WIDTH = 600;
	public static final int HEIGHT = 700;

	public AppPanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setOpaque(true);
	}

	/**
	 * draw the diagrams
	 * 
	 * @param g
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;

		for (City c : DataLoader.cities()) {
			g2D.setColor(Color.BLACK);
			g2D.drawOval(c.x() - 5, c.y() - 5, 10, 10);

			g2D.drawString(c.name(), c.x(), c.y());

			g2D.setColor(Color.GREEN);
			g2D.fillOval(c.x() - 5, c.y() - 5, 10, 10);
		}

		g2D.setColor(Color.BLACK);
		for (Edge e : DataLoader.edges()) {
			g2D.drawLine(e.start().x(), e.start().y(), e.end().x(), e.end().y());
		}
	}
}
