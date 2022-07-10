package GPS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
* The Path class stores an ArrayList of edges.
*
* @author  Dylan Parker
* @version 1.0
* @since   2021-12-05 
* 
* @apiNote
* 		Project_04
* 		Final Project: GPS
*/
public class Path implements Comparable<Path>{

	//============================================================================= Properties
	public int cost = 0;
	private ArrayList<Vertex> vertices; 
	
	//============================================================================= Constructors
	public Path() {
		vertices = new ArrayList<Vertex>();
	}
	
	public Path(ArrayList<Vertex> path, int cost) {
		for (Vertex v : path) {
			vertices.add(v);
		}
		this.cost = cost;
	}
	
	// Starting path constructor
	public Path(Vertex v, int cost) {
		vertices = new ArrayList<Vertex>();
		vertices.add(v);
		this.cost = cost;
	}
	
	public Path(Path p) {
		this.cost = p.cost;
		vertices = new ArrayList(p.vertices);
	}
	
	//============================================================================= Methods
	@Override
	public int compareTo(Path o) {
		Integer thisCost = this.cost;
		Integer otherCost = o.cost;
		
		return otherCost.compareTo(thisCost);
	}
	
	public void add(Graph g, Vertex v) {
		ArrayList<Edge> edges;
		
		if (!vertices.isEmpty()) {
			Vertex prev = vertices.get(vertices.size() - 1);
			edges = g.getEdges(prev);
			
			for (Edge e : edges) {
				if (e.toVertex.equals(v) && g.useDistCost) {
					cost += e.distCost;
					break;
				} else if (e.toVertex.equals(v) && !g.altCost) {
					cost += e.timeCost;
					break;
				} else if (e.toVertex.equals(v) && g.altCost) {
					cost += e.constructionCost;
					break;
				}
			}
		}
		
		vertices.add(v);
	}
	
	public Vertex peek() {
		return vertices.get(vertices.size() - 1);
	}
	
	public boolean contains(Vertex v) {
		for (Vertex ver : vertices) {
			if (ver.equals(v)) { return true; }
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		String ret = "";
		
		for (Vertex v : vertices) {
			ret += v.toString() + " ";
		}
		
		return ret;
	}
	
}