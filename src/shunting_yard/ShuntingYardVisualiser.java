package shunting_yard;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class ShuntingYardVisualiser extends JPanel {

    private Timer timer;

    public ShuntingYardVisualiser() {
        setLayout(null);
		setPreferredSize(new Dimension(_main.GUI.WIDTH, _main.GUI.HEIGHT));
		setOpaque(true);
        setBounds(0, 0, _main.GUI.WIDTH, _main.GUI.HEIGHT);
        
        JTextField text = new JTextField();
        text.setBounds(10, 10, 250, 25);
        add(text);

        JButton but = new JButton("Run");
        but.setBounds(10, 45, 100, 25);
        but.addActionListener(e-> {
            try {
                ShuntingYard.algorithm(text.getText());
                stackEmulator = new Stack<>();
                queueEmulator = new ArrayDeque<>();
                binEmulator = new ArrayList<>();
                render(ShuntingYard.algorithmLog());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        add(but);
    }

    private Stack<String> stackEmulator;
    private Queue<String> queueEmulator;
    private List<String> binEmulator;

    private void render(Queue<String> log) {
        timer = new Timer(500, e -> {
            if (!log.isEmpty()) {
                String entry = log.poll();
                String[] arr = entry.split("\s");

                if (arr[0].equals("stack")) {
                    String op = arr[1];
                    if (op.equals("add")) {
                        stackEmulator.add(arr[2]);
                    }
                    else {
                        if (arr.length == 4)
                            binEmulator.add(stackEmulator.peek());
                        
                        stackEmulator.pop();
                    }
                }
                else {
                    queueEmulator.add(arr[2]);
                }
                repaint();
            }
            else {
                timer.stop();
            }
        });
        timer.start();
    }

    private static final Border b = new BevelBorder(0);

    /**
	 * draw the diagrams
	 * 
	 * @param g
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.WHITE);
    	g2D.fillRect(0, 0, getWidth(), getHeight());

        if (stackEmulator == null)
            return;

        int i = 0;
        for (String s : stackEmulator) {
            JLabel label = new JLabel(s);
            label.setBorder(b);
            label.setBounds(200, 500 - 35 * i, 30, 30);
            add(label);
            i++;
        }

        i = 0;
        for (String s : queueEmulator) {
            JLabel label = new JLabel(s);
            label.setBorder(b);
            label.setBounds(10 + i * 35, 150, 30, 30);
            add(label);
            i++;
        }

        i = 0;
        for (String s : binEmulator) {
            JLabel label = new JLabel(s);
            label.setBorder(b);
            label.setBounds(10 + i * 35, 200, 30, 30);
            add(label);
            i++;
        }

        // for (int i = 0; i < arr.size(); i++) {
        //     JLabel label = new JLabel(arr.get(i));
        //     label.setBorder(new BevelBorder(0));
        //     label.setBounds(10 + i * 35, 100, 30, 30);
        //     add(label);
        // }
		// g2D.setColor(Color.BLACK);
        // try {
        //     Thread.sleep(500);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
	}

}
