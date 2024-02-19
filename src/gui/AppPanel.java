package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import astar.City;
import astar.AStarDataLoader;
import astar.Edge;


/**
 * panel that displays the Venn diagrams
 */
public class AppPanel extends JPanel {

	public static final int WIDTH = 625;
	public static final int HEIGHT = 700;

	private JTextField start = new JTextField("Enter start location");
	private JTextField end = new JTextField("Enter end location");

	private JButton confirmButton = new JButton("Confirm Selection");

	public AppPanel() {
		setLayout(null);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setOpaque(true);
		
		createUserInterface();

		add(confirmButton);
		add(start);
		add(end);
	}

	private void createUserInterface() {
		start.setBounds(10, 6, 150, 24);
		end.setBounds(10, 36, 150, 24);
		start.setForeground(Color.GRAY);
		end.setForeground(Color.GRAY);

		addTextFieldListener(start, "Enter start location");
		addTextFieldListener(end, "Enter end location");

		confirmButton.setBounds(10, 66, 150, 24);
		confirmButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				confirmButton.requestFocusInWindow();
			}
			
		});

	}

	private void addTextFieldListener(JTextField tf, String msg) {
		tf.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				// When the text field gains focus, clear the background text
				if (tf.getText().equals(msg)) {
					tf.setText("");
					tf.setForeground(Color.BLACK);
				}
			}
	
			@Override
			public void focusLost(FocusEvent e) {
				// When the text field loses focus, set the background text if it's empty
				if (tf.getText().isEmpty()) {
					tf.setText(msg);
					tf.setForeground(Color.GRAY);
				}
			}
		});
	}

	/**
	 * draw the diagrams
	 * 
	 * @param g
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;

		for (Edge e : AStarDataLoader.edges()) {
			g2D.drawLine(e.start().x(), e.start().y(), e.end().x(), e.end().y());
		}

		for (City c : AStarDataLoader.cities()) {
			g2D.setColor(Color.BLACK);
			g2D.drawOval(c.x() - 5, c.y() - 5, 10, 10);

			g2D.drawString(c.name(), c.x() + 6, c.y() + 4);

			g2D.setColor(Color.GREEN);
			g2D.fillOval(c.x() - 5, c.y() - 5, 10, 10);
		}		
	}
}
