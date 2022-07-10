package OLD_GPS;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import GPS.Graph;
import GPS.Vertex;
import GPS.enuState;

/**
* The Tester class utilizes the GPS as a
* full, GUI program.
*
* @author  Dylan Parker
* @version 1.0
* @since   2021-12-05 
* 
* @apiNote
* 		Project_04
* 		Final Project: GPS
*/
public class NewTester extends JPanel {
	JFrame window = new JFrame("Game Template");
	Timer tmr = null;
	Random rnd = new Random();
	Graph map = new Graph("MapInformationXY.txt",50,25,28);
	Vertex fv = null, tv = null;
	public NewTester() {
		window.setBounds(50, 50, 500, 500);
		window.setAlwaysOnTop(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.getContentPane().add(this);
		window.setVisible(true);

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
							fv = v;
							fv.state = enuState.START;
						} else if(tv == null) {
							tv = v;
							tv.state = enuState.END;
							JOptionPane.showMessageDialog(window, "From: " + fv.symbol + "\nTo  : " + tv.symbol);
						} else {
							fv.state = tv.state = enuState.UNSELECTED;
							tv = null;
							fv = v;
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
	}

	//======================================================
	public static void main(String[] args) {
		new NewTester();
	}
	//======================================================
}
