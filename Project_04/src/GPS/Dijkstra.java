package GPS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
* The Dijkstra class finds the shortest paths between
* two vertices in the GPS data.
*
* @author  Dylan Parker
* @version 1.0
* @since   2021-12-05 
* 
* @apiNote
* 		Project_04
* 		Final Project: GPS
*/
public class Dijkstra {
	
	//============================================================================= Properties
	public static int totalCost;
	private static PriorityQueue<Path> pathQueue;

	private static Graph g;
	private static HashMap<Vertex, Integer> costs;
	private static Set<Vertex> seen;
	
	
	//============================================================================= Constructors
	public Dijkstra(Graph graph) {
		g = graph;
		costs = new HashMap<Vertex, Integer>();
		seen = new HashSet<Vertex>();
		pathQueue = new PriorityQueue<Path>();
	}
	
	//============================================================================= Methods
	public static Path shortestPath(String startVertex, String endVertex) {
		Vertex source = g.getVertex(startVertex);
		
		pathQueue.add(new Path(g.getVertex(startVertex), 0));
		costs.put(source, 0);
		
		Path first = new Path(g.getVertex(startVertex), 0);
		if (first.peek().equals(g.vertices().get(endVertex))) return first;
		
		while(pathQueue.size() > 0 && !(seen.contains(g.vertices().get(endVertex)))) {
			
			if (pathQueue.isEmpty()) { break; }
			
			if (pathQueue.peek().peek().equals(g.vertices().get(endVertex))) { break; }
			Path curr = pathQueue.remove();

			if (seen.contains(curr.peek())) { continue; }
			
			seen.add(curr.peek());
			calcMin(curr);
			
		}
		//System.out.println(startVertex + endVertex);
		
		Path ret = pathQueue.peek();
		if(ret != null) totalCost = ret.cost;
		return ret;
	}
	
	//============================================================================= Helper Methods
	private static void calcMin(Path p) {
		ArrayList<Vertex> adjVerts = new ArrayList<Vertex>();
		ArrayList<Edge> adjEdges = g.getEdges(p.peek());
		//System.out.println(p.toString());
		for(Edge e: adjEdges) {
			if(!seen.contains(e.toVertex)) { adjVerts.add(e.toVertex); }
		}
		
		for(Vertex v : adjVerts) {
			if(vertexCost(v) > (vertexCost(p.peek()) + edgeCost(v, adjEdges))) {
				costs.put(v, vertexCost(p.peek()) + edgeCost(v, adjEdges));
				Path newP = new Path(p);
				newP.add(g, v);
				pathQueue.add(newP);
			}	
		}
	}
	
	private static int vertexCost(Vertex v) {
		Integer cost = costs.get(v);
		if (cost == null) { return Integer.MAX_VALUE; }
		else { return cost; }
	}
	
	private static int edgeCost(Vertex v, ArrayList<Edge> edges) {
		for (Edge e : edges) {
			if (e.toVertex.equals(v)) {
				//System.out.println(e.focusCost(g.useDistCost, g.altCost));
				return e.focusCost(g.useDistCost, g.altCost);
			}
		}
		return 0;
	}
}
