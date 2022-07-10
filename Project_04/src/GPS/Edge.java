package GPS;

import java.awt.Color;
import java.awt.Graphics;

/**
* The Edge class stores the various costs of a path between
* addresses.
*
* @author  Dylan Parker
* @version 1.0
* @since   2021-12-05 
* 
* @apiNote
* 		Project_04
* 		Final Project: GPS
*/
public class Edge {
	public Vertex fromVertex;
	public Vertex toVertex;
	public int timeCost;
	public int distCost;
	public int constructionCost;
	
	public Edge(Vertex fromVertex, Vertex toVertex, int timeCost, int distCost, int conCost) {
		super();
		this.fromVertex = fromVertex;
		this.toVertex = toVertex;
		this.timeCost = timeCost;
		this.distCost = distCost;
		this.constructionCost = conCost;
	}
	
	@Override
	public String toString() {
		return String.format("%s -> %s (%d %s)", 
					fromVertex,
					toVertex,
					Graph.useDistCost ? distCost : timeCost,
					Graph.useDistCost ? "miles" : "minutes"
				);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		int off = toVertex.width/2;
		g.drawLine(fromVertex.x+off, fromVertex.y+off, toVertex.x+off, toVertex.y+off);
		fromVertex.draw(g);
		toVertex.draw(g);
	}
	
	public int focusCost(boolean useDistCost, boolean useConCost) {
		if (useDistCost) {
			return distCost;
		} else if (useConCost) {
			return constructionCost;
		} else {
			return timeCost;
		}
	}
}
