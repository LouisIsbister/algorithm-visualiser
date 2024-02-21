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
import java.util.Queue;
import java.util.Map.Entry;
import java.util.ArrayDeque;
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
public class AppPanel extends JPanel {

	public static final int WIDTH = 625;
	public static final int HEIGHT = 700;

	private JTextField start = new JTextField("Enter start location");
	private JTextField end = new JTextField("Enter end location");

	private JButton confirmButton = new JButton("Confirm Selection");

	private List<City> astarPath;

	private Timer timer;

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
				pathRender = new ArrayList<>();
				retraceRender = new ArrayList<>();
				history = new ArrayDeque<>();
				cityptr = null;

				String startStr = start.getText();
				String endStr = end.getText();

				Map<String, City> cities = AStarDataLoader.cities();
				astarPath = AStar.findShortestPath(cities.get(startStr), cities.get(endStr));

				visualise(cities.get(startStr), cities.get(endStr), AStar.getBackptrs());
			}

		});

	}

	// ----- updating the visualiser -----

	private List<City> pathRender = new ArrayList<>();

	private City cityptr = null;

	private Queue<City> history = new ArrayDeque<>();

	private void visualise(City start, City goal, Map<City, Edge> backptrs) {
		cityptr = start;
		history.add(cityptr);
		
		// Initialize the Timer in your constructor or initialization block
		timer = new Timer(500, event -> {
			Edge e = null;
			City cityrm = null;

			for (Entry<City, Edge> entry : backptrs.entrySet()) {
				Edge edge = entry.getValue();
				if (edge != null && cityptr == edge.start()) {
					e = edge;
					cityrm = entry.getKey();
				}
			}

			if (cityrm != null) {
				backptrs.remove(cityrm, e);
				
				history.add(cityptr);
				pathRender.add(e.start());
				cityptr = e.end();
			}
			else { // then there existed no edge where the cityptr was the start of the edge
				pathRender.add(cityptr);
				cityptr = history.poll();
			}

			repaint();

			if (cityptr == goal) {
				pathRender.add(goal);
				timer.stop();
				retracePath();
			}
		});

		timer.start();
	}

	private List<City> retraceRender = new ArrayList<>();

	private int pathPtr;

	private void retracePath() {
		retraceRender = new ArrayList<>();
		pathPtr = 0;
		
		timer = new Timer(500, event -> {
			City c = astarPath.get(pathPtr);
			retraceRender.add(c);
			pathPtr++;

			repaint();
			if (pathPtr == astarPath.size())
				timer.stop();
		});

		// Start the timer when needed
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
