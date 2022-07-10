package GPS;


import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
* The Graph class parses and stores GPS information fed into
* it via a text file.
*
* @author  Dylan Parker
* @version 1.0
* @since   2021-12-05 
* 
* @apiNote
* 		Project_04
* 		Final Project: GPS
*/
public class Graph {
	public static boolean useDistCost = true;
	public static boolean altCost = false;
	public static boolean returnAddress = false;
	
	private HashMap<String, Vertex> vertices;
	private ArrayList<Edge> edges;
	
	public Graph(String fileName, int xOffset, int yOffset, int size) {
		vertices = new HashMap<String, Vertex>();
		edges = new ArrayList<Edge>();
		String[] parts;
		
		try(Scanner fin = new Scanner(new File(fileName))){
			while(fin.hasNextLine()) {
				parts = split(fin);
				if(parts[0].equals("<Nodes>")) {
					fin.nextLine();
					while(true) {
						parts = split(fin);
						if(parts[0].equals("</Nodes>")) break;
						vertices.put(parts[0], new Vertex(
								parts[0], 
								parts[1],
								Integer.parseInt(parts[2]) + xOffset,
								Integer.parseInt(parts[3]) + yOffset,
								size
						));
					}
				} else if (parts[0].equals("<Edges>")) {
					fin.nextLine();
					while(true) {
						parts = split(fin);
						if(parts[0].equals("</Edges>")) break;
						edges.add(new Edge(
									vertices.get(parts[0]),
									vertices.get(parts[1]),
									Integer.parseInt(parts[2]),
									Integer.parseInt(parts[3]),
									Integer.parseInt(parts[4])
								));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Vertex getVertex(String startVertexName) {
		return vertices.get(startVertexName);
	}
	
	public ArrayList<Edge> getEdges(Vertex fromVertex) {
		ArrayList<Edge> ret = new ArrayList<Edge>();
		for(Edge e : edges)
			if(e.fromVertex.equals(fromVertex))
				ret.add(e);
		return ret;
	}
	
	private String[] split(Scanner fin) {
		return fin.nextLine().split("\t");
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(Edge e : edges)
			s.append(e).append("\n");
		return s.toString();
	}

	public HashMap<String, Vertex> vertices() {
		return (HashMap<String, Vertex>) vertices.clone();
	}

	public void draw(Graphics g) {
		for(Edge e : edges)
			e.draw(g);
	}

}
