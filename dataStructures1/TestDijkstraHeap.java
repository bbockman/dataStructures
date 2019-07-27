package dataStructures1;

import java.io.File;

/**
 * Class for testing DijkstraHeap data structure class.
 * Graph is read from text file using Graph class.
 * Runtime should be O(ElogV), Fibonacci heap could produce O(VlogV)
 * @author Brooks
 *
 */
public class TestDijkstraHeap {
	Graph graph;
	
	public static void main(String[] args) {
		int[] parents;
		TestDijkstraHeap myTest = new TestDijkstraHeap();
		File file = new File("C:\\Users\\Brooks\\Documents\\PROGRAMMING\\Eclipse\\GenFiles\\bin\\dataStructures1\\graph_in.txt");
		myTest.graph = new Graph(file);
		parents = new int[myTest.graph.nverts];
		DijkstraHeap heap = DijkstraHeap.initializeHeap(myTest.graph.nverts,0);
		System.out.println("Initial Heap");
		System.out.println(heap);
		System.out.println("Initial Graph");
		System.out.println(myTest.graph);
		while (heap.getSize() > 0) {
			int v = heap.pop();
			EdgeNode edge = myTest.graph.edges[v];
			while (edge != null) {
				if (edge.child == edge.parent) {throw new IllegalArgumentException("Non-simple graphs are not allowed.");} 
				if (heap.getPosition(edge.child)!=-1 && heap.getDistance(edge.child) > heap.getDistance(v) + edge.weight) {
					parents[edge.child] = v;
					heap.setDistance(edge.child, heap.getDistance(v)+edge.weight);
					heap.heapify(heap.getPosition(edge.child));
				}
				edge = edge.next;
			}
		}
		System.out.println("Resulting Edges");
		for (int i = 0; i < parents.length; i++) {
			System.out.println(new EdgeNode(parents[i],i,heap.getDistance(i)));
		}
		
		/*
		 * Initialize heap with node for each vertex
		 * nodes should contain distance and vertex number (in separate arrays in DijHeap DS)
		 * heap should have a vector translating vertex number to heap position
		 * initial heap positions are arbitrary, use 1 to n (all weights initially MAX_INT)
		 * starting vertex should be set to position 1 in heap and weight set to 0
		 * 
		 * while heap.size > 0
		 * 	pop top element v
		 * 	iterate over edges of element (v,u)
		 * 		if u == v error(not a simple graph)
		 * 		
		 * 		if heap.p[u] != -1 && heap.d[u] > heap.d[v] + w
		 * 			heap.d[u] = heap.d[v] + w
		 * 			heapify(heap, heap.p[u])
		 * 
		 */
		
	}
}
