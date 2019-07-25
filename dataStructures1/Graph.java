package dataStructures1;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/*
 * Super quick graph data structure for playing with algorithms.
 */
public class Graph {
	boolean directed;
	EdgeNode[] edges;
	int nedges;
	int nverts;
	boolean[] visited;
	boolean[] processed;
	
	Graph(int _nverts){
		nverts = _nverts;
		visited = new boolean[nverts];
		processed = new boolean[nverts];
		edges = new EdgeNode[nverts];
	}
	
	/**
	 * Graph can be initialized from a file that is formatted as follows:
	 * First line contains the number of vertices, followed by the number of edges.
	 * All following lines consist of single edge definitions, they are formated as follows:
	 * Starting vertex then the ending vertex then the edge weight.
	 * All values are assumed to be integers.
	 * @param file
	 */
	Graph(File file) {
		Scanner reader = null;
		try {
			reader = new Scanner(file);
		}catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		nverts = reader.nextInt();
		int _nedges = reader.nextInt();
		visited = new boolean[nverts];
		processed = new boolean[nverts];
		edges = new EdgeNode[nverts];
		for (int i = 0; i < _nedges; i++) {
			this.addEdge(new EdgeNode(reader.nextInt(),reader.nextInt(),reader.nextInt()));
		}
		reader.close();
	}
	
	void addEdge(EdgeNode node) {
		node.next = edges[node.parent];
		edges[node.parent] = node;
		nedges++;
	}
	
	void rmEdge(EdgeNode node) {
		EdgeNode cur = edges[node.parent];
		if (cur == node) {
			edges[node.parent]= cur.next;
			nedges--;
		}
		while (cur != null && cur.next != node) {
			cur = cur.next;
		}
		if (cur != null) {
			cur.next = cur.next.next;
			nedges--;
		}
	}
	
	void dfs(GraphSearch obj) {
		obj.processEdge();
		obj.processVertexEarly();
		obj.processVertexLate();
	}
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		String newline = "\n";
		String bigspace = "   ";
		for (int i = 0; i < nverts; i++) {
			string.append(i); string.append(bigspace);
			EdgeNode edge = edges[i];
			while (edge != null) {
				string.append(edge.toString());
				edge = edge.next;
			}
			string.append(newline);
		}
		return string.toString();
	}
}
