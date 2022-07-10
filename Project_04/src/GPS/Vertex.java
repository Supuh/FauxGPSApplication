package GPS;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
* The Vertex class stores address information.
*
* @author  Dylan Parker
* @version 1.0
* @since   2021-12-05 
* 
* @apiNote
* 		Project_04
* 		Final Project: GPS
*/
public class Vertex extends Rectangle {
	private static int longestAddress = 1;
	public String symbol;
	public String address;
	public enuState state;
		
	public Vertex(String symbol, String address, int x, int y, int size) {
		super(x, y, size, size);
		this.symbol = symbol;
		this.address = address;
		longestAddress = Math.max(longestAddress, address.length());
		state = enuState.UNSELECTED;
	}
	
	@Override
	public String toString() {
		return 	String.format("%-" + (Graph.returnAddress ? longestAddress : 1) + "s", 
				Graph.returnAddress ? address : symbol);		
		// ...("%-1s", "A")
		// ...("%-15s", "2100 S St.")
	}
	
	public void draw(Graphics g) {
		g.setColor(state.getColor());
		g.fillOval(x, y, width, height);
		g.setColor(Color.BLACK);
		g.drawOval(x, y, width, height);
		int off = width/2;
		g.drawString(symbol, x+off-4, y+off+4);
	}
}
