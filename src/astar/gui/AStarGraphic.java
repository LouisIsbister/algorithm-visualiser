package astar.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import astar.City;
import astar.AStar;
import astar.AStarDataLoader;
import astar.Edge;

/**
 * panel that displays the Venn diagrams
 */
public class AStarGraphic extends JPanel {

	public static final int WIDTH = 625;
	public static final int HEIGHT = 700;

	private JTextField start = new JTextField("Enter start location");
	private JTextField end = new JTextField("Enter end location");

	private JButton confirmButton = new JButton("Confirm Selection");

	private List<City> shortestPath;

	private Timer timer;

	public AStarGraphic() {
		setLayout(null);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setOpaque(true);

		createUserInterface();
		
		add(start);
		add(end);
		add(confirmButton);
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
				pathRender = new ArrayList<>();
				retraceRender = new ArrayList<>();
				countptr = 0;

				String startStr = start.getText();
				String endStr = end.getText();

				Map<String, City> cities = AStarDataLoader.cities();
				shortestPath = AStar.findShortestPath(cities.get(startStr), cities.get(endStr));

				visualise(cities.get(startStr), cities.get(endStr), AStar.allPathsTaken());
			}

		});

	}

	// ----- updating the visualiser -----

	private List<City> pathRender = new ArrayList<>();

	private List<City> retraceRender = new ArrayList<>();

	private int countptr = 0;

	private void visualise(City start, City goal, List<City> allPathsTaken) {
		timer = new Timer(500, event -> {
			City c = allPathsTaken.get(countptr);
			pathRender.add(c);

			countptr++;
			repaint();

			if (c == goal) {
				timer.stop();
				retracePath();
			}
		});

		timer.start();
	}

	private void retracePath() {
		retraceRender = new ArrayList<>();
		countptr = 0;
		
		timer = new Timer(500, event -> {
			City c = shortestPath.get(countptr);
			retraceRender.add(c);
			countptr++;

			repaint();
			if (countptr == shortestPath.size())
				timer.stop();
		});

		timer.start();
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

		for (City c : AStarDataLoader.cities().values()) {
			g2D.setColor(Color.BLACK);
			g2D.drawOval(c.x() - 5, c.y() - 5, 10, 10);

			g2D.drawString(c.name(), c.x() + 6, c.y() + 4);

			g2D.setColor(Color.GREEN);
			g2D.fillOval(c.x() - 5, c.y() - 5, 10, 10);
		}

		for (City c : pathRender) {
			g2D.setColor(Color.RED);
			g2D.fillOval(c.x() - 5, c.y() - 5, 10, 10);
		}

		for (City c : retraceRender) {
			g2D.setColor(Color.BLUE);
			g2D.fillOval(c.x() - 5, c.y() - 5, 10, 10);
		}
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
}
