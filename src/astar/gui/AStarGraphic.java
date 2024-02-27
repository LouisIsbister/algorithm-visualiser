package astar.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
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

	private JTextField start = new JTextField();
	private JTextField end = new JTextField();

	private JButton confirmButton = new JButton("Confirm Selection");

	private Timer timer;

	public AStarGraphic() {
		
		setLayout(null);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setOpaque(true);

		createUserInterface();
	}

	private void createUserInterface() {
		start.setBounds(10, 6, 150, 24);
		end.setBounds(10, 36, 150, 24);

		confirmButton.setBounds(10, 66, 150, 24);
		confirmButton.addActionListener((ActionEvent e) -> {
			if (timer != null)
				timer.stop();

			String startStr = start.getText();
			String endStr = end.getText();

			Map<String, City> cities = AStarDataLoader.cities();
			City start = cities.get(startStr);
			City end = cities.get(endStr);

			visualise(start, end,
				AStar.findShortestPath(start, end), 
				AStar.allPathsTaken());
		});

		add(start);
		add(end);
		add(confirmButton);
	}

	// ----- visualiser -----

	private List<City> pathRender = new ArrayList<>();

	private List<City> retraceRender = new ArrayList<>();

	/**
	 * shortest path list pointer
	 */
	private int spPtr;

	/**
	 * all paths taken list pointer
	 */
	private int aptPtr;

	/**
	 * This method renders the A* algorithm in the same order that occurred. i.e. it starts
	 * by sequentially adding all the paths taken to the pathRender list which is highlighted
	 * on that map by red cities. Once it found the goal / rendered all the paths it will
	 * sequentially render the cities that form the shortest path that was taken. 
	 * 
	 * @param start of the path.
	 * @param goal end of the path.
	 * @param shortestPath shortest path between start -> goal.
	 * @param allPathsTaken all the paths taken before finding the shortest.
	 */
	private void visualise(City start, City goal, List<City> shortestPath, List<City> allPathsTaken) {
		pathRender = new ArrayList<>();
		retraceRender = new ArrayList<>();
		spPtr = 0;
		aptPtr = 0;
		
		timer = new Timer(500, event -> {
			City c = allPathsTaken.get(aptPtr);
			pathRender.add(c);

			if (aptPtr < allPathsTaken.size() - 1) {
				aptPtr++;
			}
			else {
				c = shortestPath.get(spPtr);
				retraceRender.add(c);
				spPtr++;

				if (spPtr == shortestPath.size())
					timer.stop();
			}
			repaint();
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
}
