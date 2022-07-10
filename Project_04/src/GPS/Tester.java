package GPS;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BasicStroke;
import java.awt.Color;

import javax.swing.JRadioButton;
import java.awt.Point;
import java.awt.Stroke;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;

/**
* The Tester class creates a GPS with a GUI
* that implements the Dijkstra algorithm to find the best
* routes for time, distance, and construction.
*
* @author  Dylan Parker
* @version 1.0
* @since   2021-12-05 
* 
* @apiNote
* 		Project_04
* 		Final Project: GPS
*/
public class Tester extends JPanel {
	
	//============================================================================= Properties
	JFrame window = new JFrame("Game Template");
	Timer tmr = null;
	Random rnd = new Random();
	private String key = "";
	private boolean mouseCalc = false;
	Vertex m1, m2;
	Stroke linePath = new BasicStroke(
			5.0f,
			BasicStroke.CAP_ROUND,
			BasicStroke.JOIN_MITER,
			5.0f,
			new float[] {20},
			0.0f
		);
	ArrayList<Point> pts = new ArrayList<Point>();
	Graph map = new Graph("MapInformationXY.txt",50,25,28);
	Vertex fv = null, tv = null;
	private JRadioButton timeButton;
	private JRadioButton distButton;
	private JRadioButton altButton;
	private JTextArea textArea;
	
	//============================================================================= Constructor
	public Tester() {
		setBounds(0, 0, 561, 471);
		window.setTitle("GPS Application");
		window.setBounds(50, 50, 800, 500);
		window.setAlwaysOnTop(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.getContentPane().setLayout(null);
		window.getContentPane().add(this);
		
		JOptionPane.showMessageDialog(window, "Welcome to Dylan's GPS! \n "
				+ "Feel free to use your mouse or the option panes to start!");
		
		//============================================================ GUI
		JPanel panel = new JPanel();
		panel.setBounds(571, 0, 223, 471);
		window.getContentPane().add(panel);
		panel.setLayout(null);
		
		timeButton = new JRadioButton("Shortest Time");
		timeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (timeButton.isSelected()) {
					distButton.setSelected(false);
					altButton.setSelected(false);
					
					key = "time";
					map.useDistCost = false;
					map.altCost = false;
				}
			}
		});
		timeButton.setBounds(6, 48, 199, 23);
		panel.add(timeButton);
		
		distButton = new JRadioButton("Shortest Distance");
		distButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (distButton.isSelected()) {
					timeButton.setSelected(false);
					altButton.setSelected(false);
					
					key = "distance";
					map.useDistCost = true;
					map.altCost = false;
				}
			}
		});
		distButton.setBounds(6, 74, 199, 23);
		panel.add(distButton);
		
		altButton = new JRadioButton("Avoid Construction");
		altButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (altButton.isSelected()) {
					distButton.setSelected(false);
					timeButton.setSelected(false);
					
					key = "alt";
					map.useDistCost = false;
					map.altCost = true;
				}
			}
		});
		altButton.setBounds(6, 100, 199, 23);
		panel.add(altButton);
		
		JLabel sortLabel = new JLabel("Choose your sort!");
		sortLabel.setBounds(6, 27, 199, 14);
		panel.add(sortLabel);
		
		JLabel fromVertexLabel = new JLabel("From:");
		fromVertexLabel.setBounds(6, 164, 46, 14);
		panel.add(fromVertexLabel);
		
		JLabel toVertexLabel = new JLabel("To:");
		toVertexLabel.setBounds(6, 250, 46, 14);
		panel.add(toVertexLabel);
		
		JComboBox fromComboBox = new JComboBox();
		fromComboBox.setBounds(6, 189, 184, 23);
		panel.add(fromComboBox);
		
		JComboBox toComboBox = new JComboBox();
		toComboBox.setBounds(6, 275, 184, 23);
		panel.add(toComboBox);
		
		for (Vertex v : map.vertices().values()) {
			fromComboBox.addItem(v.symbol + " - " + v.address);
			toComboBox.addItem(v.symbol + " - " + v.address);
		}
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(6, 380, 184, 80);
		panel.add(textArea);
		window.setVisible(true);
		
		//============================================================ Calculation button
		JButton calcButton = new JButton("Calculate");
		calcButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fromVertex = (String) fromComboBox.getSelectedItem();
				String toVertex = (String) toComboBox.getSelectedItem();
				
				operateDijkstra(fromVertex.charAt(0) + "", toVertex.charAt(0) + "");
			}
		});
		calcButton.setBounds(53, 346, 89, 23);
		panel.add(calcButton);

		//============================================================ Events
		tmr = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});

		//============================================================ Mouse Pressed
		addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {
				for(Vertex v : map.vertices().values()) {
					if(v.contains(e.getPoint())) {
						if(fv == null) {
							if (pts.size() > 0) { pts.clear(); }
							fv = v;
							fv.state = enuState.START;
							pts.add(e.getPoint());
							
							m1 = v;
							mouseCalc = true;
						} else if(tv == null) {
							tv = v;
							tv.state = enuState.END;
							//JOptionPane.showMessageDialog(window, "From: " + fv.symbol + "\nTo  : " + tv.symbol);
							pts.add(e.getPoint());
							m2 = v;
							
							if (mouseCalc) { operateDijkstra(m1.toString().charAt(0) + "", 
									m2.toString().charAt(0) + ""); }
							
							m1 = null;
							m2 = null;
							mouseCalc = false;
						} else {
							fv.state = tv.state = enuState.UNSELECTED;
							pts.clear();
							tv = null;
							fv = v;
							pts.add(e.getPoint());
							m1 = v;
							mouseCalc = true;
							fv.state = enuState.START;
						}
						return;
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		//============================================================ Mouse Moved, Dragged
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				mouseMoved(e);
			}
		});

		//============================================================ Key pressed
		window.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

		});

		tmr.start();
	}

	//============================================================ Drawing
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		map.draw(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(linePath);
		
		g2.setColor(Color.RED);
		
		if (pts.size() > 0) { g2.fillOval(pts.get(0).x - 5, pts.get(0).y - 5, 10, 10); }
		for (int i = 1; i < pts.size(); i++) {
			g2.drawLine(pts.get(i - 1).x, pts.get(i - 1).y, pts.get(i).x, pts.get(i).y);
			g2.fillOval(pts.get(i).x - 5, pts.get(i).y - 5, 10, 10);
		}
	}

	//====================================================== Methods
	public void operateDijkstra(String start, String end) {
		Dijkstra sorter = new Dijkstra(map);
		Path p = Dijkstra.shortestPath(start, end);
		
		if (p != null) {
			chartPath(p);
			System.out.println(p.toString());
			String traversalType = "";
			
			switch (key) {
				case "distance":
					traversalType = sorter.totalCost + " miles";
					break;
				case "time":
					traversalType = sorter.totalCost + " minutes";
					break;
				case "alt":
					traversalType = sorter.totalCost + " construction crews avoided";
					break;
				default:
					traversalType = sorter.totalCost + " miles";
			}
			
			textArea.setText("Route: [" + p.toString() + "]\n" + traversalType);
		} else {
			JOptionPane.showMessageDialog(window, "ERROR - No Path Found \n Please Try Again!");
		}
	}
	
	public void chartPath(Path p) {
		pts.clear();
		String[] strings = p.toString().split(" ");
		
		for (String str : strings) {
			Vertex v = map.vertices().get(str);
			pts.add(new Point(v.x + 14, v.y + 14));
		}
	}
	
	public static void main(String[] args) {
		new Tester();
	}
}
